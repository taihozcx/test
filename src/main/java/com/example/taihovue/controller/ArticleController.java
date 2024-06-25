package com.example.taihovue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taihovue.pojo.Article;
import com.example.taihovue.pojo.Result;
import com.example.taihovue.service.ArticleService;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    // public Result<String> list(@RequestHeader(name = "Authorization") String
    // token, HttpServletResponse response) {
    // // 验证token
    // try {
    // Map<String, Object> claims = JwtUtil.parseToken(token);
    // return Result.success("所有的文章数据...");
    // } catch (Exception e) {
    // response.setStatus(401);
    // return Result.error("未登录");
    // }
    public Result<String> list() {
        return Result.success("所有的文章数据...");

    }

    @PostMapping
    public Result<String> add(@RequestBody Article article) {
        articleService.add(article);
        return Result.success();
    }

}
