package com.example.taihovue.service;

import com.example.taihovue.pojo.Article;
import com.example.taihovue.pojo.PageBean;

public interface ArticleService {

    void add(Article article);

    PageBean<Article> list(Integer pagaNum, Integer pageSize, Integer categoryId, String state);
}
