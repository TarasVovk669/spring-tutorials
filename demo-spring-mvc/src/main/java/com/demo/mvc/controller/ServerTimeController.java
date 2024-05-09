package com.demo.mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class ServerTimeController {

    @GetMapping("/server-time")
    public String getServerTime(Model theModel){
        theModel.addAttribute("theDate", LocalDateTime.now());
        return "serverTime";
    }

    @GetMapping("/show-form")
    public String showForm(){
        return "show-form";
    }


    @GetMapping("/submit-form")
    public String submitForm(){
        return "submit-form";
    }

    @GetMapping("/v2/submit-form")
    public String submitForm2(HttpServletRequest request, Model model){
        var name = request.getParameter("student_name").toUpperCase();

        var message = "Yo! dude " + name;

        model.addAttribute("message", message);
        return "submit-form";
    }

    @PostMapping("/v3/submit-form")
    public String submitForm3(@RequestParam("student_name") String name, Model model){
        var message = "Yo! dude v3 " + name;

        model.addAttribute("message", message);
        return "submit-form";
    }
}
