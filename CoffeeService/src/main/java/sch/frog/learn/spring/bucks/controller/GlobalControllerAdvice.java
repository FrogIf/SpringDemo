package sch.frog.learn.spring.bucks.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerAdvice {

    /**
     * 可以看下@RestControllerAdvice
     * 就是一个controller中的方法, 当其他controller中方法抛出异常之后, 会调用这里的方法, 根据抛出的异常, 找到响应的处理该异常的方法
     * 相当于目标controller处理失败, 用这个方法处理
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> validationExceptionHandler(ValidationException validationException){
        Map<String, String> map = new HashMap<>(1);
        map.put("message", validationException.getMessage());
        return map;
    }

}
