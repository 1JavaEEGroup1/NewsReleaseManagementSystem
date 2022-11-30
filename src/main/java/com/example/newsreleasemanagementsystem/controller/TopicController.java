package com.example.newsreleasemanagementsystem.controller;

import com.example.newsreleasemanagementsystem.domian.Topic;
import com.example.newsreleasemanagementsystem.repository.StateRepository;
import com.example.newsreleasemanagementsystem.repository.TopicRepository;
import com.example.newsreleasemanagementsystem.util.ResponseResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/topic")
public class TopicController {
    private TopicRepository topicRepository;

    public TopicController(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * 添加标签
     */
    @PostMapping("/add")
    public ResponseResult<?> addTopic(@RequestBody Topic topic) {
        return ResponseResult.success(topicRepository.save(topic));
    }
    /**
     * 标签列表
     */
    @GetMapping("/all")
    public ResponseResult<?> topicList() {
        return ResponseResult.success(topicRepository.findAll());
    }
    /**
     * 单个标签
     */
    @PostMapping("/detail")
    public ResponseResult<?> topicDetail(@RequestParam Long id) {
        if (!topicRepository.existsById(id)) {
            return ResponseResult.fail("topic id is not exist");
        } else {
            return ResponseResult.success(topicRepository.findById(id));
        }
    }
    /**
     * 修改标签
     */
    @PostMapping("/change")
    public ResponseResult<?> change(@RequestBody Topic topic) {
        if (!topicRepository.existsById(topic.getId())) {
            return ResponseResult.fail("topic id is not exist");
        } else {
            return ResponseResult.success(topicRepository.save(topic));
        }
    }
    /**
     * 删除标签
     */
    @DeleteMapping("/delete")
    public ResponseResult<?> delete(@RequestParam Long id) {
        if(! topicRepository.existsById(id)) {
            return ResponseResult.fail("topic id is not exist");
        } else {
            topicRepository.deleteById(id);
            return ResponseResult.success();
        }
    }
}
