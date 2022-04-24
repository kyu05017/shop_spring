package com.shop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class HelloController {

    @GetMapping("hello")
    public List<String> hello() {
        return Arrays.asList("김규석 스프링 공부", "리이액트 연동","2022-04-24");

    }
}
