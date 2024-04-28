package com.hodubam.sinder.controller;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController("/")
public class SignUpCotroller {


    @Autowired
    private SqlSessionTemplate sst;
    @PostMapping("/signUp")
    public String signUp(@RequestBody Map<String,Object> formData){


        Map<String,Object> user = sst.selectOne("USER.testQuery");

        List<Map<String,Object>> list = sst.selectOne("USER.testQuery");
        System.out.println(list);
        return "jj";
    }

}
