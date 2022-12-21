package com.itrjp.im.api.handler;

import com.itrjp.common.exception.BizException;
import com.itrjp.common.result.ErrorCode;
import com.itrjp.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalHandler {
    /**
     * 所有业务异常
     *
     * @param e 异常
     * @return
     */
    @ExceptionHandler(value = {BizException.class})
    public Result<Void> handlerBizException(BizException e) {
        log.warn("BizException, code: {}, message: {}", e.getCode(), e.getMessage());
        return Result.error(e);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result<Void> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("MethodArgumentNotValidException, message: {}", e.getMessage());
        FieldError fieldError = e.getBindingResult().getFieldError();
        if (fieldError == null) {
            return Result.error(ErrorCode.PARAM_ERROR);
        }
        return Result.error(ErrorCode.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public Result<Void> handlerException(Exception e) {
        log.error("Exception", e);
        return Result.error();
    }
}
