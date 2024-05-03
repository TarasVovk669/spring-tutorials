package com.shop.manager.managerservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;
import java.util.NoSuchElementException;

@ControllerAdvice
@AllArgsConstructor
public class AppExceptionHandler {


    private final MessageSource messageSource;

    @ExceptionHandler(NoSuchElementException.class)
    public String noSuchElementException(NoSuchElementException e, Model model,
                                         HttpServletResponse response, Locale locale) {
        var message = messageSource.getMessage(e.getMessage(), new Object[0], e.getMessage(), locale);
        response.setStatus(404);
        model.addAttribute("error", message);

        System.out.println(message);
        return "error/404";
    }
}
