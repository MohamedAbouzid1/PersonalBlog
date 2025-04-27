package com.mohamed.blog.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class DebugController {

    @ModelAttribute
    public void logRequest(HttpServletRequest request) {
        System.out.println("==== DEBUG REQUEST ====");
        System.out.println("Request URL: " + request.getRequestURL());
        System.out.println("Request Method: " + request.getMethod());
        System.out.println("======================");
    }
}