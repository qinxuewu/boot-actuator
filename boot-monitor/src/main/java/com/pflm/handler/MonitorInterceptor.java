package com.pflm.handler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截器
 * @author qxw
 * @data 2018年6月15日下午5:11:13
 */
@Component
public class MonitorInterceptor implements HandlerInterceptor{

	 private Logger logger = LoggerFactory.getLogger(getClass());
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(true);  
		Object obj=session.getAttribute("username");
	    if (obj == null || "".equals(obj.toString())) {
	    	logger.info("*****************************非法访问，还未登录***********************");
	    	   response.sendRedirect("/login");
	    	return false;
	    }
        return true;  
	}


	 @Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {}
	

	 @Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {}



}
