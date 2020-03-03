package com.clf.security.serverauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author: clf
 * @Date: 2020-03-03
 * @Description: TODO
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    //TODO, 这里需要注入自己的UserService来读数据库

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //TODO, 这里分别是根据传入的username来查询数据库获取用户的加密密码和权限

        return User.withUsername(username)
                .password(passwordEncoder.encode("123456"))
                .authorities("ROLE_ADMIN")
                .build();
    }
}
