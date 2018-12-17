package com.pflm.modules.job.utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import com.pflm.common.exception.MyException;
import com.pflm.common.utils.SpringContextUtils;

import java.lang.reflect.Method;



/**
* @Description:    执行定时任
* @Author:         qinxuewu
* @CreateDate:     26/11/2018 下午 3:38
* @Email 870439570@qq.com
**/
public class ScheduleRunnable implements Runnable {
    private Object target;
    private Method method;
    private String params;

    public ScheduleRunnable(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
        this.target = SpringContextUtils.getBean(beanName);
        this.params = params;

        if (StringUtils.isNotBlank(params)) {
            this.method = target.getClass().getDeclaredMethod(methodName, String.class);
        } else {
            this.method = target.getClass().getDeclaredMethod(methodName);
        }
    }

    @Override
    public void run() {
        try {
            ReflectionUtils.makeAccessible(method);
            if (StringUtils.isNotBlank(params)) {
                method.invoke(target, params);
            } else {
                method.invoke(target);
            }
        } catch (Exception e) {
            throw new MyException("执行定时任务失败", e);
        }
    }

}
