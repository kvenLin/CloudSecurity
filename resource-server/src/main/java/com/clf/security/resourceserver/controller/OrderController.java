package com.clf.security.resourceserver.controller;

import com.clf.security.resourceserver.service.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String order(String orderId, @AuthenticationPrincipal User user) {
        log.info("user: {}", user);
        return "do order service";
    }

    @PostMapping("/create")
    public String create(String orderId, @AuthenticationPrincipal(expression = "#this.id") Long userId ) {
        log.info("userId: {}", userId);
        return "do order service";
    }


}
