package com.hui.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IndexController {
    // 公共资源
    @RequestMapping("index")
    public String index() {
        return "index";
    }
}
