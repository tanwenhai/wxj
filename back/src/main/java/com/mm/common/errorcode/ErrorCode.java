package com.mm.common.errorcode;

/**
 * 错误码
 * @author tanwenhai@gusoftware.com
 */
public final class ErrorCode {
    private final Integer code;
    private final String message;

    public static final ErrorCode EMPTY_DATA = new ErrorCode(1001, "暂无数据");

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 固定的状态码 消息不同
     * @param message
     * @return
     */
    public static ErrorCode of(String message) {
        return new ErrorCode(999, message);
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
