package com.demo.security.springsecuritydemo.jwt.cookie;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerController {

    @GetMapping("manager")
    public String getManagerPage() {
        return "public/manager";
    }
}
