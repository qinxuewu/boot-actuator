package com.pflm.modules.job.task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
* @Description:   测试定时任务
*  testTask为spring bean的名称
* @Author:         qinxuewu
* @CreateDate:     26/11/2018 下午 3:37
* @Email 870439570@qq.com
**/
@Component("testTask")
public class TestTask {
    private Logger logger = LoggerFactory.getLogger(getClass());



    //定时任务只能接受一个参数；如果有多个参数，使用json数据即可
    public void test(String params) {
        logger.info("我是带参数的test方法，正在被执行，参数为：" + params);

    }


    public void test2() {
        logger.info("我是不带参数的test2方法，正在被执行");
    }
}
