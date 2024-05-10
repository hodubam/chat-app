package com.hodubam.sinder.controller;

import com.hodubam.sinder.utils.BasicUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/")
public class SignUpCotroller {


    @Autowired
    private SqlSessionTemplate sst;
    @PostMapping("/signUp")
    public String signUp(@RequestBody Map<String,Object> formData){

        if(!formData.containsKey("email") || !formData.containsKey("username") ||
                !formData.containsKey("password") || !formData.containsKey("confirm_password")) {
            return "Empty Field Exist";
        }

        Map<String,Object> user = new HashMap<>();

        user = sst.selectOne("USER.checkUserByEmail", formData);
        if(user != null){
            return "The email address is already registered. Please use a different email address.";
        }

        user = sst.selectOne("USER.checkUserByUsername", formData);
        if(user != null){
            return "The Username is already registered. Please use a different Username.";
        }

        formData.put("USER_KEY", BasicUtils.createUUID());
        sst.insert("USER.insertNewUser", formData);
        return "success";
    }

}
