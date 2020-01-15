package com.tomato.nacos.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description:
 *
 * @author: caoyintong
 * Version: 1.0
 * Create Date Time: 2020-01-14 17:09.
 * Update Date Time:
 */

@Controller
public class DemoController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello world";
    }

}
