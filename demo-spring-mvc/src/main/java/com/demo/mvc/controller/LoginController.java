package com.demo.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/showMyCustomLoginForm")
    public String showMyCustomLoginForm(){
        return "plain-login";
    }

}
