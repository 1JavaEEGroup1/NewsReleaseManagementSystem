package com.example.newsreleasemanagementsystem.controller;

import com.example.newsreleasemanagementsystem.domian.EState;
import com.example.newsreleasemanagementsystem.domian.State;
import com.example.newsreleasemanagementsystem.domian.User;
import com.example.newsreleasemanagementsystem.repository.StateRepository;
import com.example.newsreleasemanagementsystem.repository.UserRepository;
import com.example.newsreleasemanagementsystem.util.ResponseResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserRepository userRepository;
    private StateRepository stateRepository;
    public UserController(UserRepository userRepository, StateRepository stateRepository) {
        this.userRepository = userRepository;
        this.stateRepository = stateRepository;
    }
    /**
     * 用户列表
     */
    @GetMapping("/userList")
    public ResponseResult<?> userList() {
        return ResponseResult.success(userRepository.findAll());
    }
    /**
     * 单个用户
     */
    @PostMapping("/userDetail")
    public ResponseResult<?> userDetail(@RequestParam Long id) {
        if(! userRepository.existsById(id)) {
            return ResponseResult.fail("user id is not exist");
        } else {
            return ResponseResult.success(userRepository.findById(id));
        }
    }
    /**
     * 封禁用户（状态）
     */
    @PostMapping("/disableUser")
    public ResponseResult<?> disableUser(@RequestParam Long id) {
        if( ! userRepository.existsById(id)) {
            return ResponseResult.fail("user id is not exist");
        } else {
            User user = userRepository.findById(id).get();
            State state = stateRepository.findByName(EState.STATE_DisEnable).get();
            user.setState(state);
            return ResponseResult.success(userRepository.findById(id));
        }
    }
    /**
     * 修改用户
     */
    @PostMapping("/changeUser")
    public ResponseResult<?> changeUser(@RequestBody User user) {
        if( ! userRepository.existsById(user.getId())) {
            return ResponseResult.fail("user id is not exist");
        } else {
            User newUser = userRepository.save(user);
            return ResponseResult.success(newUser);
        }
    }
}
