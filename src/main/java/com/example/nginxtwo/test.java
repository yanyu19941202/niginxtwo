package com.example.nginxtwo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class test {

    @RequestMapping("orderTest")
    public String orderTest() {

        System.out.println("twoaaaaaaaaaaa.............");
        return "this is test2";
    }

}
