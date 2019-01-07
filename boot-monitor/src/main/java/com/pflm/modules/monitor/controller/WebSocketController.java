package com.pflm.modules.monitor.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.pflm.common.utils.HttpUtil;
import com.pflm.modules.monitor.entity.ClassLoadEntity;
import com.pflm.modules.monitor.entity.GcEntity;
import com.pflm.modules.monitor.entity.ThreadEntity;
import com.pflm.modules.monitor.service.ClassService;
import com.pflm.modules.monitor.service.GcService;
import com.pflm.modules.monitor.service.ThreadService;
import com.pflm.modules.sys.dao.SysActuatorMapper;

import java.util.List;


@Controller
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private GcService gcService;
    @Autowired
    private ClassService classService;
    @Autowired
    private ThreadService threadService;
	@Autowired
	private SysActuatorMapper sysActuatorMapper;
	
    /**
     * gc 内存信息
     * MessageMapping注解和我们之前使用的@RequestMapping类似  前端主动发送消息到后台时的地址
     * SendTo注解表示当服务器有消息需要推送的时候，会对订阅了@SendTo中路径的浏览器发送消息。
     * @return
     */
    @MessageMapping("/gc")
    @SendTo("/topic/gc")
    public List<GcEntity> socketGc(String name){
        return gcService.findAllByName(name);
    }

    /**
     * 类加载相关信息
     * @param name
     * @return
     */
    @MessageMapping("/cl")
    @SendTo("/topic/cl")
    public List<ClassLoadEntity> socketCl(String name){
        return classService.findAllByName(name);
    }

    /**
     * 线程相关信息
     * @param name
     * @return
     */
    @MessageMapping("/thread")
    @SendTo("/topic/thread")
    public List<ThreadEntity> socketThread(String name){
        return threadService.findAllByName(name);
    }


    /**
     * 日志
     * @param name
     * @return
     */
    @MessageMapping("/weblog")
    @SendTo("/topic/weblog")
    public String weblog(String url){
    	 JSONObject info=JSONObject.parseObject(HttpUtil.URLGet(url+"/actuator/info/logReader"));
    	 if(info.containsKey("result")){
    		 System.err.println(info.getString("result"));
    		 return info.getString("result");
    	 }else{
    		 return "没有最新日志.......................";
    	 }
       
    }
}
