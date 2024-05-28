package com.hodubam.sinder.controller;

import org.springframework.web.bind.annotation.*;

@RestController("/view")
public class ViewController {

    @GetMapping("/{page}")
    public String viewPage(@PathVariable String page) {
        return page;
    }

}
