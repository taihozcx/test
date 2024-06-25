package com.example.taihovue.pojo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

@Data
public class Category {
    @NotNull(groups = { Update.class, Delete.class })
    private Integer id;
    @NotEmpty
    private String categoryName; // 分类名称
    @NotEmpty
    private String categoryAlias;// 分类别名
    private Integer createUser; // 创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime; // 更新时间

    public interface Add extends Default {

    }

    public interface Update extends Default {

    }

    public interface Delete {

    }

}
