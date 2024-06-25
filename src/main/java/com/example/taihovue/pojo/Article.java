package com.example.taihovue.pojo;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.URL;

import com.example.taihovue.anno.State;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Article {
    private Integer id;
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String title;
    @NotEmpty
    private String content;
    @URL
    @NotEmpty
    private String coverImg;
    // @Pattern(regexp = "已发布|草稿", message = "发布状态必须为'已发布'或'草稿'")
    @State
    private String state;
    @NonNull
    private Integer categoryId;
    private Integer createUser;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
