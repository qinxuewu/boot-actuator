package com.pflm.common.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pflm.common.utils.Res;



/**
 * @Description:    异常处理器
 * @Author:         qinxuewu
 * @Email 870439570@qq.com
 */
@RestControllerAdvice
public class MyExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(MyException.class)
    public Res handleMyException(MyException e) {
    	Res r = new Res();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());

        return r;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Res handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return Res.error("数据库中已存在该记录");
    }

 
    @ExceptionHandler(Exception.class)
    public Res handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return Res.error();
    }
}
