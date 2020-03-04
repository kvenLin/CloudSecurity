package com.clf.backendutils.exception;

import com.clf.backendutils.enums.ErrorEnum;
import lombok.Data;

/**
 * @Author: clf
 * @Date: 2020-02-29
 * @Description: TODO
 */
@Data
public class CommonException extends RuntimeException{
    private Integer code;
    private String msg;

    public CommonException(ErrorEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.msg = errorEnum.getMsg();
    }

}
