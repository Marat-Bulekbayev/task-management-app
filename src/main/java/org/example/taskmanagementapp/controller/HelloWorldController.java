package org.example.taskmanagementapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class HelloWorldController {

    @GetMapping("/hello-world-admin")
    public String getAdmin() {
        return "hello world - admin";
    }

    @GetMapping("/hello-world-user")
    public String getUser() {
        return "hello world - user";
    }
}
