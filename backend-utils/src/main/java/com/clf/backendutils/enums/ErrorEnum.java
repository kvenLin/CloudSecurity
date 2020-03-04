package com.clf.backendutils.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @Author: clf
 * @Date: 2020-02-29
 * @Description: 错误枚举
 */
@Getter
public enum  ErrorEnum {
    SERVER_ERROR(500,"服务器未知错误:%s" ),
    REQUEST_METHOD_ERROR(550,"不支持%s的请求方式" ),
    BIND_ERROR(511,"参数校验错误:%s"),
    USER_NOT_FOUND(501, "用户不存在"),
    PASSWORD_WRONG(502, "用户密码错误"),
    SAVE_FILM_FAILED(503,"添加影片失败"),
    PARSE_ERROR(504, "时间转换错误"),
    ROLE_MATCH_ACT_ERROR(505, "演员和角色名数量不匹配"),
    SAVE_CINEMA_ERROR(506, "添加影院失败"),
    PAGE_TOO_BIG(507, "nowPage太大了，不支持此处理"),
    SAVE_HALL_ERROR(508, "添加影厅失败"),
    FILM_NOT_FOUND(509, "未能找到对应影片信息"),
    SAVE_HALL_FILM_INFO_ERROR(510, "添加影厅电影信息失败"),
    NOT_FOUND(404, "请求有问题，请联系管理员");
    private Integer code;
    private String msg;

    ErrorEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
