package com.shop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class HelloController {

    @GetMapping("hello")
    public List<String> hello() {
        return Arrays.asList("강사님 성공했습니다.", "ㅜㅜㅠㅜㅠㅜㅠㅠ");
    }
}
