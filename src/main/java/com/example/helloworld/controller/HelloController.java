package com.example.helloworld.controller;

import com.example.helloworld.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

//  http://localhost:8080/hello
//  http://localhost:8080/hello?username=zhangsan&phone=123
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    // @GetMapping("/hello")
    public String hello(@RequestParam(value = "username", required = false   ) String name, String phone){
        System.out.println(phone);
        return "你好" + name;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String post(){
        return "post 请求成功";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password){
        System.out.println(username);
        System.out.println(password);
        return "post 请求成功";
    }

    @RequestMapping(value = "/login1", method = RequestMethod.POST)
    public String login1(User user){
        System.out.println(user);
        return "post 请求成功";
    }

    @RequestMapping(value = "/login2", method = RequestMethod.POST)
    public String login2(@RequestBody User user){
        System.out.println(user);
        return "post 请求成功";
    }
}
