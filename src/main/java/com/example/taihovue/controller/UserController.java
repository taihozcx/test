package com.example.taihovue.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.StringUtils;

import com.example.taihovue.pojo.Result;
import com.example.taihovue.pojo.User;
import com.example.taihovue.service.UserService;
import com.example.taihovue.utils.JwtUtil;
import com.example.taihovue.utils.Md5Util;
import com.example.taihovue.utils.ThreadLocalUtil;

import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result<String> register(@Pattern(regexp = "^\\S{5,16}$") String username,
            @Pattern(regexp = "^\\S{5,16}$") String password) {
        // 查询用户
        User u = userService.findByUserName(username);
        if (u == null) {
            // 注册
            userService.register(username, password);
            return Result.success();
        } else {
            // 占有
            return Result.error("用户名已被占用！");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,
            @Pattern(regexp = "^\\S{5,16}$") String password) {
        // 根据用户名查询用户
        User loginUser = userService.findByUserName(username);
        // 判断用户是否存在
        if (loginUser == null) {
            return Result.error("用户名或密码错误！");

        }
        // 判断密码是否正确
        if (Md5Util.md5(password).equals(loginUser.getPassword())) {
            // 登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            // 把token存入redis
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);
            return Result.success(token);
        }
        return Result.error("用户名或密码错误！");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name = "Authorization") String token) {
        // 根据用户名查询用户
        // Map<String, Object> map = JwtUtil.parseToken(token);
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result<String> updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result<String> updatePwd(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String token) {
        // 1.参数校验
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要的参数！");
        }

        if (!rePwd.equals(newPwd)) {
            return Result.error("两次密码输入不一致！");
        }

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);
        if (!loginUser.getPassword().equals(Md5Util.md5(oldPwd))) {
            return Result.error("原密码不正确！");
        }

        // 2.调用Service完成密码更新
        userService.updatePwd(newPwd);
        // 删除redis中原来的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }

}
