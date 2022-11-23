package com.example.newsreleasemanagementsystem.controller;

import com.example.newsreleasemanagementsystem.domian.ERole;
import com.example.newsreleasemanagementsystem.domian.Role;
import com.example.newsreleasemanagementsystem.domian.User;
import com.example.newsreleasemanagementsystem.payload.request.LoginRequest;
import com.example.newsreleasemanagementsystem.payload.request.SignupRequest;
import com.example.newsreleasemanagementsystem.payload.response.UserInfoResponse;
import com.example.newsreleasemanagementsystem.repository.RoleRepository;
import com.example.newsreleasemanagementsystem.repository.UserRepository;
import com.example.newsreleasemanagementsystem.security.jwt.JwtUtils;
import com.example.newsreleasemanagementsystem.security.service.UserDetailslmpl;
import com.example.newsreleasemanagementsystem.util.ResponseResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailslmpl userDetails = (UserDetailslmpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(
                       ResponseResult.success(new UserInfoResponse(
                               userDetails.getId(),
                               userDetails.getUsername(),
                               userDetails.getEmail(),
                               roles
                       ))
                );
    }
    @PostMapping("/signup")
    public ResponseResult<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if(userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseResult.fail("Error: Username is already taken!");
        }

        if(userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseResult.fail("Error: Email is already in use!");
        }

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(
                    role -> {
                        switch (role) {
                            case "agency":
                                Role agencyRole = roleRepository.findByName(ERole.ROLE_AGENCY)
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                roles.add(agencyRole);

                                break;
                            case "admin":
                                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                roles.add(adminRole);

                                break;
                            case "region":
                                Role regionRole = roleRepository.findByName(ERole.ROLE_REGIONADMIN)
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                roles.add(regionRole);

                                break;
                            case "super":
                                Role superRole = roleRepository.findByName(ERole.ROLE_SUPERADMIN)
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                roles.add(superRole);

                                break;
                            default:
                                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                roles.add(userRole);
                        }
                    }
            );
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseResult.success("User registered successfully!");
    }

    @PostMapping("signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ResponseResult.success("You've been signed out!"));
    }
}
