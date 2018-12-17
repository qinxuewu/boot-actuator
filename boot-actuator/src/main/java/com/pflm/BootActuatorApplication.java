package com.pflm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 * @author qinxuewu
 * @version 1.00
 * @time  29/11/2018 下午 1:33
 * @email 870439570@qq.com
 */
@SpringBootApplication
@ComponentScan("com.pflm.**,com.github.qinxuewu.core")
public class BootActuatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootActuatorApplication.class, args);
    }

}
