package com.hodubam.sinder.controller;

import com.hodubam.sinder.utils.BasicUtil;
import com.hodubam.sinder.utils.TokenUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("/user")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

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
            String hashedPassword = passwordEncoder.encode(formData.get("password").toString());
            formData.put("password_hash", hashedPassword);

            // 사용자 추가
            formData.put("USER_KEY", BasicUtil.getUUID());
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

    @PostMapping("/signIn")
    public ResponseEntity<Object> signIn(@RequestBody Map<String, Object> formData) {
        Map<String, Object> response = new HashMap<>();

        // 필수 필드 확인
        if (!formData.containsKey("email") || !formData.containsKey("password")) {
            response.put("success", false);
            response.put("message", "Email and Password are required.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        String email = formData.get("email").toString();
        String password = formData.get("password").toString();

        // 사용자 확인
        Map<String, Object> user = template.selectOne("USER.checkUserByEmail", formData);
        if (user == null || !passwordEncoder.matches(password, user.get("PASSWORD_HASH").toString())) {
            response.put("success", false);
            response.put("message", "Invalid email or password.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // JWT 토큰 생성
        String accessToken = TokenUtil.generateAccessToken(user.get("USERNAME").toString());
        String refreshToken = TokenUtil.generateRefreshToken(user.get("USERNAME").toString());

        // 성공 응답
        response.put("success", true);
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<Object> refreshToken(@RequestBody Map<String, Object> formData) {
        Map<String, Object> response = new HashMap<>();

        // 필수 필드 확인
        if (!formData.containsKey("refreshToken")) {
            response.put("success", false);
            response.put("message", "Refresh Token is required.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        String refreshToken = formData.get("refreshToken").toString();

        // Refresh Token 유효성 검사
        if (!TokenUtil.validateToken(refreshToken)) {
            response.put("success", false);
            response.put("message", "Invalid Refresh Token.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String username = TokenUtil.getUsernameFromToken(refreshToken);
        String newAccessToken = TokenUtil.generateAccessToken(username);

        // 성공 응답
        response.put("success", true);
        response.put("accessToken", newAccessToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
