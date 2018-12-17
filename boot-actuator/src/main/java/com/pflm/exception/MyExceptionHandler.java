package com.pflm.exception;

/**
 * @author qinxuewu
 * @create 18/11/30上午7:39
 * @since 1.0.0
 */

import com.pflm.utils.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 通用异常处理器
 * @author qxw
 * @data 2018年11月20日上午11:22:52
 */
@RestControllerAdvice
public class MyExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(MyException.class)
    public Res handleRRException(MyException e) {
        Res r = new Res();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());
        logger.error("自定义RRException异常：{}", e);
        return r;
    }

    @ExceptionHandler(Exception.class)
    public Res handleException(Exception e) {
        logger.error("全局Exception异常：{}", e);
        return Res.error();
    }
}
