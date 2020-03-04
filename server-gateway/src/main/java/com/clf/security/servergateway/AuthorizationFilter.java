package com.clf.security.servergateway;

import com.alibaba.fastjson.JSONObject;
import com.clf.backendutils.common.vo.BaseResponseVO;
import com.clf.backendutils.enums.ErrorEnum;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: clf
 * @Date: 2020-03-04
 * @Description: TODO
 */
@Slf4j
@Component
public class AuthorizationFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("authorization start...");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if(isNeedAuth(request)) {
            TokenInfo tokenInfo = (TokenInfo) request.getAttribute("tokenInfo");
            if(tokenInfo != null && tokenInfo.isActive()) {
                if(!hashPermission(tokenInfo, request)) {
                    log.info("audit log update fail 403");
                    handlerError(403, requestContext);
                }
                //在header头中添加其他服务需要的用户信息
                requestContext.addZuulRequestHeader("username", tokenInfo.getUser_name());
                log.error("tokenInfo: {}", tokenInfo);
            } else {
                if(!StringUtils.startsWith(request.getRequestURI(), "/token")) {
                    log.info("audit log update fail 401");
                    handlerError(401, requestContext);
                }
            }
        }
        return null;
    }

    private boolean hashPermission(TokenInfo tokenInfo, HttpServletRequest request) {
        //TODO, 判断是否有对应的权限
        return true;
    }

    private void handlerError(int status, RequestContext requestContext) {
        requestContext.getResponse().setContentType("application/json");
        requestContext.setResponseStatusCode(status);
        requestContext.setResponseBody(JSONObject.toJSONString(BaseResponseVO.noLogin()));
        requestContext.setSendZuulResponse(false);
    }

    private boolean isNeedAuth(HttpServletRequest request) {
        //TODO,查询数据库来判断是否需要认证
        return true;
    }
}
