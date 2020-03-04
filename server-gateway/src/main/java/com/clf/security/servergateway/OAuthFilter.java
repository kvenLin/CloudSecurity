package com.clf.security.servergateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: clf
 * @Date: 2020-03-04
 * @Description: TODO
 */
@Slf4j
@Component
public class OAuthFilter extends ZuulFilter  {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("OAuth Start ...");
        // ThreadLocal
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if (StringUtils.startsWith(request.getRequestURI(), "/token")) {
            return null;
        }

        String authHeader = request.getHeader("Authorization");
        if(StringUtils.isBlank(authHeader)) {
            return null;
        }
        if (!StringUtils.startsWithIgnoreCase(authHeader, "bearer ")) {
            return null;
        }

        try {
            TokenInfo info = getTokenInfo(authHeader);
            request.setAttribute("tokenInfo", info);//认证成功后将token放入请求中
        }catch (Exception e){
            log.error("get token info failed : {}", e);

        }
        return null;
    }

    private TokenInfo getTokenInfo(String authHeader) {
        String token = StringUtils.substringAfter(authHeader, "bearer ");
        String oauthServiceUrl = "http://localhost:9090/oauth/check_token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("gateway", "123456");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", token);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<TokenInfo> response = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, httpEntity, TokenInfo.class);
        log.info("token info : " + response.getBody().toString());
        return response.getBody();
    }
}
