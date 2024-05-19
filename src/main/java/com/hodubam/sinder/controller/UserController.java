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

@RestController("/user")
public class UserController {


    @Autowired
    private SqlSessionTemplate template;
    @PostMapping("/signUp")
    public ResponseEntity<Object> signUp(@RequestBody Map<String,Object> formData){
        Map<String, Object> response = new HashMap<>();

        // 필수 필드 확인
        if (!formData.containsKey("email") || !formData.containsKey("username") ||
                !formData.containsKey("password") || !formData.containsKey("confirmPassword")) {
            response.put("success", false);
            response.put("message", "Empty Field Exist");
        }

        Map<String, Object> user;

        // 이메일 중복 확인
        user = template.selectOne("USER.checkUserByEmail", formData);
        if (user != null) {
            response.put("success", false);
            response.put("message", "The email address is already registered. Please use a different email address.");
        }

        // 사용자 이름 중복 확인
        user = template.selectOne("USER.checkUserByUsername", formData);
        if (user != null) {
            response.put("success", false);
            response.put("message", "The Username is already registered. Please use a different Username.");
        }

        try {
            // 사용자 추가
            formData.put("USER_KEY", BasicUtils.getUUID());
            template.insert("USER.insertNewUser", formData);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Try again");
            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 성공 응답
        response.put("success", true);
        response.put("message", "Success");
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

}
