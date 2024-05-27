package com.hodubam.sinder.controller;

import com.hodubam.sinder.utils.BasicUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController("/view")
public class ViewController {

    @GetMapping("/{page}")
    public String viewPage(@PathVariable String page) {
        return page;
    }

}
