package com.example.taihovue.service;

import java.util.List;

import com.example.taihovue.pojo.Category;

public interface CategoryService {

    void add(Category category);

    List<Category> list();

    void update(Category category);

    void delete(Category category);

}
