package com.clf.security.serverauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @Author: clf
 * @Date: 2020-03-03
 * @Description: 配置授权服务器
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("my-application") //允许访问的服务的名称
                .secret(passwordEncoder.encode("123456"))
                .scopes("read", "write") //权限的集合
                .accessTokenValiditySeconds(3600)
                .resourceIds("order-server") //允许访问的资源服务器的id
                .authorizedGrantTypes("password")
                .and()
                .withClient("orderService") //允许访问的服务的名称
                .secret(passwordEncoder.encode("123456"))
                .scopes("read") //权限的集合
                .accessTokenValiditySeconds(3600)
                .resourceIds("order-server") //允许访问的资源服务器的id
                .authorizedGrantTypes("password");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()"); // 来验证token的请求必须是经过身份认证的
    }
}
