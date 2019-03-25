package com.pflm;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.github.qinxuewu.utils.IPUtils;



@SpringBootApplication
@EnableScheduling
@EnableAsync
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
@ComponentScan({"com.pflm.**","com.github.qinxuewu.core"})
public class MonitorApplication {


    public static void main(String[] args) {
    	IPUtils.getHostIp();
        SpringApplication.run(MonitorApplication.class, args);
    }
}
