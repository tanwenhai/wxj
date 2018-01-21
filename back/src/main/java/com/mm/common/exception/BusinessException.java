package com.mm.common.exception;

import com.mm.common.errorcode.ErrorCode;

/**
 * 业务异常
 * @author tanwenhai@gusoftware.com
 */
public class BusinessException extends RuntimeException {
    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(Exception e) {
        super(e);
        this.errorCode = ErrorCode.of(e.getMessage());
    }

    public BusinessException(String message) {
        super(message);
        this.errorCode = ErrorCode.of(message);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
