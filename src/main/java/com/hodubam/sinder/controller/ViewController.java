package com.hodubam.sinder.controller;

import com.hodubam.sinder.utils.BasicUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("/view")
public class ViewController {

    private String prefix;
    @PostMapping("/{path}")
    public String url(@RequestBody Map<String,Object> formData){

    }

}
