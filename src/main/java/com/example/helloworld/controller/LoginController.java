package com.example.helloworld.controller;

import com.example.helloworld.entity.User;
import com.example.helloworld.mapper.LoginMapper;
import com.example.helloworld.utils.JwtUtils;
import com.example.helloworld.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:8088")

public class LoginController {

    @Autowired
    private LoginMapper loginMapper;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        System.out.println(user);
        List<User> res = loginMapper.selectUser(user.getName());
        if (res == null || res.size() == 0){
            return Result.error().message("未查询到用户");
        } else {
            List<User> userList = loginMapper.login(user);
            if (userList == null || userList.size() == 0){
                return Result.error().message("密码有误，请重试！");
            } else {
                String token = JwtUtils.generateToken(user.getName());
                return Result.ok().data("token", token).data("info", userList.get(0));
            }
        }
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        List<User> sel = loginMapper.selectUser(user.getName());
        if (sel.size() == 1) {
            return Result.error().message("此用户名已被注册！");
        } else {
            int res = loginMapper.register(user);
            if (res > 0){
                return Result.ok().data("result", true);
            } else {
                return Result.error().message("系统错误，请联系管理员！");
            }
        }
    }

    @ApiOperation("用户退出")
    @PostMapping("/logout")
    public Result logout(@RequestBody String token){
        return Result.ok();
    }

    @GetMapping("/info")
    public Result info(String token){
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        String url = "http://5b0988e595225.cdn.sohucs.com/images/20190801/707d9c30704541908057eb46c3b31986.jpeg";
        return Result.ok().data("name", username).data("avatar", url);
    }
}
