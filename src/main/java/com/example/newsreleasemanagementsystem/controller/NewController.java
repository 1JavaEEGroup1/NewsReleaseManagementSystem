package com.example.newsreleasemanagementsystem.controller;

import com.example.newsreleasemanagementsystem.domian.EState;
import com.example.newsreleasemanagementsystem.domian.New;
import com.example.newsreleasemanagementsystem.domian.State;
import com.example.newsreleasemanagementsystem.domian.User;
import com.example.newsreleasemanagementsystem.repository.NewRepository;
import com.example.newsreleasemanagementsystem.repository.StateRepository;
import com.example.newsreleasemanagementsystem.util.ResponseResult;
import com.example.newsreleasemanagementsystem.websocket.WebSocketServer;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/new")
public class NewController {
    private NewRepository newRepository;
    private StateRepository stateRepository;

    public NewController(NewRepository newRepository, StateRepository stateRepository) {
        this.newRepository = newRepository;
        this.stateRepository = stateRepository;
    }

    /**
     * 添加新闻
     */
    @PostMapping("/add")
    public ResponseResult<?> addNew(@RequestBody New news) {
        New aNew = newRepository.save(news);
        User author = aNew.getAuthor();
        List<String> fans = new ArrayList<>();
        for(User fan : author.getFans()) {
            fans.add(String.valueOf(fan.getId()));
        }
        try {
            WebSocketServer.pushInfo(String.valueOf(author.getId()), fans);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseResult.success(aNew);
    }

    /**
     * 新闻列表
     */
    @GetMapping("/all")
    public ResponseResult<?> newList() {
        return ResponseResult.success(newRepository.findAll());
    }

    /**
     * 单个新闻
     */
    @PostMapping("/detail")
    public ResponseResult<?> newDetail(@RequestParam Long id) {
        if (!newRepository.existsById(id)) {
            return ResponseResult.fail("new id is not exist");
        } else {
            return ResponseResult.success(newRepository.findById(id));
        }
    }

    /**
     * 下架新闻（状态）
     */
    @PostMapping("/disableNew")
    public ResponseResult<?> disableNew(@RequestParam Long id) {
        if (!newRepository.existsById(id)) {
            return ResponseResult.fail("new id is not exist");
        } else {
            State state = stateRepository.findByName(EState.STATE_DisEnable).get();
            New news = newRepository.findById(id).get();
            news.setState(state);
            return ResponseResult.success(newRepository.save(news));
        }
    }

    /**
     * 修改新闻
     */
    @PostMapping("/change")
    public ResponseResult<?> change(@RequestBody New news) {
        if (!newRepository.existsById(news.getId())) {
            return ResponseResult.fail("new id is not exist");
        } else {
            return ResponseResult.success(newRepository.save(news));
        }
    }

    /**
     * 删除新闻
     */
    @DeleteMapping("/delete")
    public ResponseResult<?> delete(@RequestParam Long id) {
        if(! newRepository.existsById(id)) {
            return ResponseResult.fail("new id is not exist");
        } else {
            newRepository.deleteById(id);
            return ResponseResult.success();
        }
    }
}
