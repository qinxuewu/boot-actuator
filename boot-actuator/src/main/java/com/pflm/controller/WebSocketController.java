package com.pflm.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pflm.api.JvmApi;
import com.pflm.api.SystemCpuApi;
import com.pflm.utils.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WebSocketController
 * @author qinxuewu
 * @create 18/12/1上午9:27
 * @since 1.0.0
 */
@RestController
public class WebSocketController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private JvmApi jvmApi;
    @Autowired
    private SystemCpuApi systemCpuApi;


    /**
     * jvm内存监控 堆内存
     * MessageMapping注解和我们之前使用的@RequestMapping类似  前端主动发送消息到后台时的地址
     * SendTo注解表示当服务器有消息需要推送的时候，会对订阅了@SendTo中路径的浏览器发送消息。
     * @return
     */
    @MessageMapping("/jvmMemory")
    @SendTo("/topic/jvmMemory")
    public Res jvmMemory(String key){
        JSONArray jvmInfo= jvmApi.getJvmMemory(key);
        JSONObject nonHeapinfo=jvmApi.getJvmMemoryNonHeap(key);
        JSONObject heapinfo=jvmApi.getJvmMemoryHeap(key);
        nonHeapinfo.put("metaspace",jvmApi.getMetaspace(key));
        JSONObject classinfo=jvmApi.getClasssinfo(key);
        return Res.ok().put("jvmInfo",jvmInfo).put("heapinfo",heapinfo).put("nonHeapinfo",nonHeapinfo).put("classinfo",classinfo);
    }



    /**
     * 系统cpu数量 应用允许最大句柄数
     * @param url
     * @return
     */
    @RequestMapping("/getCpuAndfilesMax")
    public Res getCpuCount(String url){
        try {
            int cpuCount=systemCpuApi.getSystemCput(url);
            int filesMax=systemCpuApi.getfilesMax(url);
            return  Res.ok().put("cpuCount",cpuCount).put("filesMax",filesMax);
        }catch (Exception e){
            e.printStackTrace();
            return  Res.error();
        }
    }

    /**
     * 应用基本信息
     * param url
     * @return
     */
    @MessageMapping("/processInfo")
    @SendTo("/topic/processInfo")
    public Res getProcessInfo(String url){
        try{
            JSONObject info=systemCpuApi.getProcessnfo(url);
            /**
             * load average  系统平均负载
             * 假设我们的系统是单CPU单内核的，把它比喻成是一条单向马路，把CPU任务比作汽车。当车不多的时候，load <1；
             * 当车占满整个马路的时候 load=1；当马路都站满了，而且马路外还堆满了汽车的时候，load>1
             *
             * 我们经常会发现服务器Load > 1但是运行仍然不错，那是因为服务器是多核处理器（Multi-core）。
             * 假设我们服务器CPU是2核，那么将意味我们拥有2条马路，我们的Load = 2时，所有马路都跑满车辆。
             */
            info.put("loadAverage",systemCpuApi.getSystemLoadAvg(url));
            return Res.ok().put("info",info);
        }catch (Exception e){
            e.printStackTrace();
            return  Res.error();
        }
    }


}
