package com.github.qinxuewu.core.listener;
import javax.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * 拦截器注册
 * @author qxw
 * @data 2018年7月17日上午10:16:32
 */
@Configuration
public class WebAppConfiguer implements   WebMvcConfigurer {
	@Resource
	private LoginInterceptor  loginInterceptor;
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor).addPathPatterns("/actuator/**");
    }

}
