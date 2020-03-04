package com.clf.security.resourceserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: clf
 * @Date: 2020-03-03
 * @Description: TODO
 */
@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    @GetMapping("/order")
    public String order(String orderId, @RequestHeader String username) {
        log.info("username: {}", username);
        return "do order service";
    }

}
