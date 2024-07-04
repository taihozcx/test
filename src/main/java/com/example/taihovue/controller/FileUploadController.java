package com.example.taihovue.controller;

import com.example.taihovue.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        // 把文件的内容输出到磁盘上
        String originalFilename = file.getOriginalFilename();
        // 保证文件的名字唯一，从而防止文件覆盖
        String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        file.transferTo(new File("D:\\Project\\files\\" + filename));
        return Result.success("url访问地址...");
    }
}
