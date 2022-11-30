package com.example.newsreleasemanagementsystem.controller;

import com.example.newsreleasemanagementsystem.domian.EState;
import com.example.newsreleasemanagementsystem.domian.New;
import com.example.newsreleasemanagementsystem.domian.State;
import com.example.newsreleasemanagementsystem.repository.NewRepository;
import com.example.newsreleasemanagementsystem.repository.StateRepository;
import com.example.newsreleasemanagementsystem.util.ResponseResult;
import org.springframework.web.bind.annotation.*;

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
        return ResponseResult.success(newRepository.save(news));
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
