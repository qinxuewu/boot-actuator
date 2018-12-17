package com.pflm.common.utils;
import org.apache.commons.lang3.StringUtils;
import com.pflm.common.exception.MyException;



/**
* @Description:   数据校验
* @Author:         qinxuewu
* @CreateDate:     26/11/2018 下午 3:31
* @Email 870439570@qq.com
**/
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new MyException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new MyException(message);
        }
    }
}
