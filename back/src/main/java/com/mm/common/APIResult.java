package com.mm.common;

import com.mm.common.errorcode.ErrorCode;
import com.github.pagehelper.Page;

import java.util.Collection;
import java.util.Objects;

import static com.mm.common.errorcode.ErrorCode.EMPTY_DATA;

/**
 * 接口返回对象
 * @author tanwenhai@gusoftware.com
 * @param <T>
 */
public class APIResult<T> {
    private final Integer code;
    private final T value;
    private final String message;
    private final Boolean status;

    public APIResult(Integer code, T value, String message, Boolean status) {
        this.code = code;
        this.value = value;
        this.message = message;
        this.status = status;
    }

    public static <T> APIResultBuilder<T> withSuccess(T o) {
        return new APIResultBuilder<T>().code(0).value(o).status(true);
    }

    public static <T> APIResultBuilder<T> withFail(ErrorCode errorCode) {
        return new APIResultBuilder<T>().code(errorCode.getCode()).message(errorCode.getMessage()).status(false);
    }

    public static APIResultBuilder<String> withBoolean(boolean flag, ErrorCode errorCode) {
        if (flag) {
            return withSuccess("操作成功");
        }
        return withFail(errorCode);
    }
    public static APIResultBuilder withCollectionIfEmpty(Collection c) {
        return withCollectionIfEmpty(c, null);
    }

    public static APIResultBuilder withCollectionIfEmpty(Collection c, ErrorCode errorCode) {
        if (c == null || c.size() == 0) {
            errorCode = errorCode == null ? EMPTY_DATA : errorCode;
            return withFail(errorCode);
        } else if (c instanceof Page) {
            return withSuccess(((Page) c).toPageInfo());
        }

        return withSuccess(c);
    }

    public static <T> APIResultBuilder withNonNull(T o, ErrorCode errorCode) {
        if (Objects.nonNull(o)) {
            return withSuccess(o);
        }

        return withFail(errorCode);
    }

    public Integer getCode() {
        return code;
    }

    public T getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }

    public static class APIResultBuilder<T> {
        private Integer code;
        private T value;
        private String message;
        private Boolean status;

        public APIResultBuilder code(Integer code) {
            this.code = code;
            return this;
        }

        public APIResultBuilder value(T value) {
            this.value = value;
            return this;
        }

        public APIResultBuilder message(String message) {
            this.message = message;
            return this;
        }

        public APIResultBuilder status(Boolean status) {
            this.status = status;
            return this;
        }

        public APIResult<T> build() {
            return new APIResult<>(code, value, message, status);
        }
    }
}
