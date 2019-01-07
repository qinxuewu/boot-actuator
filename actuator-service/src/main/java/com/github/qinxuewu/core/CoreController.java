package com.github.qinxuewu.core;
import com.github.qinxuewu.entity.*;
import com.github.qinxuewu.jvm.Jstack;
import com.github.qinxuewu.jvm.Jstat;
import com.github.qinxuewu.jvm.Server;
import com.github.qinxuewu.utils.ExecuteCmd;
import com.github.qinxuewu.utils.LogReaderUtils;
import com.github.qinxuewu.utils.Res;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 *  jvm 性能监控
 * @author qinxuewu
 * @version 1.00
 * @time  11/12/2018 上午 11:52
 * @email 870439570@qq.com
 */
@RestController
@RequestMapping("/actuator/info")
public class CoreController {
    private Logger logger = LoggerFactory.getLogger(getClass().getName());
    @Value("${spring.application.name}")
    private String name;
	@Value("${actuator.log.path}")
	private String logUrl;	
	


    /**
     * 线程信息
     * @return
     */
    @RequestMapping("/thread")
    public Res thread(){
        String date = time();
        try {
            JstackEntity info = Jstack.jstack();
            ThreadEntity entity = new ThreadEntity();
            entity.setId(Integer.valueOf(info.getId()));
            entity.setName(name);
            entity.setDate(date);
            entity.setTotal(info.getTotal());
            entity.setRUNNABLE(info.getRUNNABLE());
            entity.setTIMED_WAITING(info.getTIMED_WAITING());
            entity.setWAITING(info.getWAITING());
            return Res.ok().put("thread",entity);
        }catch (Exception e){
            e.printStackTrace();
            return  Res.error();
        }
    }


    /**
     * 类加载信息
     * @return
     */
    @RequestMapping("/classload")
    public Res classload(){
        String date = time();
        try {
            List<KVEntity> jstatClass = Jstat.jstatClass();
            ClassLoadEntity entity = new ClassLoadEntity();
            entity.setId(Integer.valueOf(getPid()));
            entity.setName(name);
            entity.setDate(date);
            entity.setLoaded(jstatClass.get(0).getValue());
            entity.setBytes1(jstatClass.get(1).getValue());
            entity.setUnloaded(jstatClass.get(2).getValue());
            entity.setBytes2(jstatClass.get(3).getValue());
            entity.setTime1(jstatClass.get(4).getValue());
            entity.setCompiled(jstatClass.get(5).getValue());
            entity.setFailed(jstatClass.get(6).getValue());
            entity.setInvalid(jstatClass.get(7).getValue());
            entity.setTime2(jstatClass.get(8).getValue());
            return Res.ok().put("classload",entity);
        }catch (Exception e){
            e.printStackTrace();
            return  Res.error();
        }
    }



    /**
     * gc信息  堆内存信息
     * @return
     */
    @RequestMapping("/gc")
    public Res gc(){
        String date = time();
        try {
            List<KVEntity> kvEntities = Jstat.jstatGc();
            GcEntity entity = new GcEntity();
            entity.setId(Integer.valueOf(getPid()));
            entity.setName(name);
            entity.setDate(date);
            entity.setS0C(kvEntities.get(0).getValue());
            entity.setS1C(kvEntities.get(1).getValue());
            entity.setS0U(kvEntities.get(2).getValue());
            entity.setS1U(kvEntities.get(3).getValue());
            entity.setEC(kvEntities.get(4).getValue());
            entity.setEU(kvEntities.get(5).getValue());
            entity.setOC(kvEntities.get(6).getValue());
            entity.setOU(kvEntities.get(7).getValue());
            entity.setMC(kvEntities.get(8).getValue());
            entity.setMU(kvEntities.get(9).getValue());
            entity.setCCSC(kvEntities.get(10).getValue());
            entity.setCCSU(kvEntities.get(11).getValue());
            entity.setYGC(kvEntities.get(12).getValue());
            entity.setYGCT(kvEntities.get(13).getValue());
            entity.setFGC(kvEntities.get(14).getValue());
            entity.setFGCT(kvEntities.get(15).getValue());
            entity.setGCT(kvEntities.get(16).getValue());
            return Res.ok().put("gc",entity);
        }catch (Exception e){
            e.printStackTrace();
            return  Res.error();
        }
    }


    /**
     * 执行jvm linux命令
     * @return
     */
    @RequestMapping("/runexec")
    public  Res getRuntimeExec(@RequestParam Map<String, String> params){
        try{
            ArrayList<String> list=new ArrayList<>();
            for(String s:params.keySet()){
                list.add(params.get(s));
            }
            String[] array = (String[])list.toArray(new String[list.size()]);
            logger.debug("runexec:{}", Arrays.toString(array));
            String result = ExecuteCmd.execute(array);
            return Res.ok().put("result",result);
        }catch (Exception e){
            logger.debug("RuntimeExec异常：{}",e);
            return  Res.error("RuntimeExec异常");
        }
    }
    
    @RequestMapping("/logReader")
    public  Res logred(String url){
        try{

        	String msg=LogReaderUtils.poll(logUrl);

            return Res.ok().put("result",msg);
        }catch (Exception e){
            logger.debug("RuntimeExec异常：{}",e);
            return  Res.error("RuntimeExec异常");
        }
    }

    /**
     * 系统物理内存 
     * @return
     */
    @RequestMapping("/systemInfo")
    public Res systemInfo() {
		try {
			 Server server = new Server();
	         server.copyTo();
			return Res.ok().put("server", server);
		} catch (Exception e) {
			 logger.debug("systemInfo异常：{}",e);
	         return  Res.error("systemInfo异常");
		}
	
      
    }
    
    /**
     * 现在时间
     * @return
     */
    public static String time(){
        SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 获取当前应用进程id
     * @return
     */
    public static String  getPid(){
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        return  pid;
    }


}
