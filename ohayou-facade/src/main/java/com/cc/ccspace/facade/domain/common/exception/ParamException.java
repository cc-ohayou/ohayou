package com.cc.ccspace.facade.domain.common.exception;

import com.cc.ccspace.facade.domain.common.constants.ExceptionCode;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/9/20 19:54.
 */
public class ParamException extends Exception {

    private String errorCode;
    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ParamException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    public ParamException(String errorMessage) {
        super(errorMessage);
        this.errorCode = ExceptionCode.PARAM_ERROR;
        this.errorMessage = errorMessage;
    }

}
