package com.hodubam.sinder.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController("/")
public class MainController {

    @PostMapping("/signUp")
    public String signUp(@RequestBody Map<String,Object> formData){
        return "폼 데이터가 정상적으로 처리되었습니다.";
    }

    @PostMapping("/")
    public String index(@RequestBody String data){
        System.out.println("스트링 데이터: " + data);
        return "스트링 데이터가 정상적으로 처리되었습니다.";
    }
}
