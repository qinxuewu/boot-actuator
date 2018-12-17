package com.pflm.api;
import com.alibaba.fastjson.JSONObject;
import com.pflm.utils.DateUtils;
import com.pflm.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;

/**
 * CPU 监控    https://docs.spring.io/spring-boot/docs/2.0.x/actuator-api/html/
 * @author qinxuewu
 * @version 1.00
 * @time  29/11/2018 下午 1:34
 * @email 870439570@qq.com
 */
@Component
public class SystemCpuApi {
    public Logger logger = LoggerFactory.getLogger(getClass());
   /**
     * CPU数量
     */
    private static final String CPU_COUNT="metrics/system.cpu.count";
    /**
     * load average（load average） 超过阈值报警
     */
    private static final String LOAD_AVERAGE="metrics/system.load.average.1m";
    /**
     * 系统CPU使用率
     */
    private static final String CPU_USAGE="metrics/system.cpu.usage";
    /**
     * 当前进程CPU使用率  超过阈值报警
     */
    private static final String PROCESS_CPU_USAGE="metrics/process.cpu.usage";
    /**
     * 应用已运行时间
     */
    private static final String PROCESS_UPTIME="metrics/process.uptime";
    /**
     * 允许最大句柄数 配合当前打开句柄数使用
     */
    private static final String PROCESS_FILE_MAX="metrics/process.files.max";
    /**
     * 当前打开句柄数 监控文件句柄使用率，超过阈值后报警
     */
    private static final String PROCESS_FILE_OPEN="metrics/process.files.open";
    /**
     * 应用启动时间点
     */
    private static final String PROCESS_START_TIME="metrics/process.start.time";



   /**
    * 获取系统  cpu数量
    * @param url
    * @return
    */
   public int getSystemCput(String url){
      JSONObject info=new JSONObject();
      String cpuCountStr= HttpUtil.URLGet(url+CPU_COUNT);
      JSONObject cpuCount=JSONObject.parseObject(cpuCountStr);
      return  cpuCount.getJSONArray("measurements").getJSONObject(0).getIntValue("value");
   }

    /**
     *  获取系统 load average（load average） 超过阈值报警
     * @param url
     * @return
     */
    public double getSystemLoadAvg(String url){
        String loadAverageStr= HttpUtil.URLGet(url+LOAD_AVERAGE);
        JSONObject loadAverage=JSONObject.parseObject(loadAverageStr);
        double value = loadAverage.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value").setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return value;
    }


    /**
     * 当前应用允许最大句柄数 配合当前打开句柄数使用
     * @param url
     * @return
     */
    public int getfilesMax(String url){
        // 允许最大句柄数 配合当前打开句柄数使用
        String filesMaxStr= HttpUtil.URLGet(url+PROCESS_FILE_MAX);
        JSONObject filesMax=JSONObject.parseObject(filesMaxStr);
        return filesMax.getJSONArray("measurements").getJSONObject(0).getIntValue("value");

    }

    /**
     * 获取当前应用信息
     * @param url
     * @return
     */
    public JSONObject getProcessnfo(String url){
        JSONObject info=new JSONObject();
        //应用已运行时间
        String uptimeStr= HttpUtil.URLGet(url+PROCESS_UPTIME);
        if(!StringUtils.isEmpty(uptimeStr)){
            JSONObject uptime=JSONObject.parseObject(uptimeStr);
            String time= DateUtils.secondToTime((uptime.getJSONArray("measurements").getJSONObject(0).getLongValue("value")));
            info.put("uptime",time);
        }
        //当前引用打开句柄数 监控文件句柄使用率，超过阈值后报警
        String fileOpenStr= HttpUtil.URLGet(url+PROCESS_FILE_OPEN);
        if(!StringUtils.isEmpty(fileOpenStr)){
            JSONObject fileOpen=JSONObject.parseObject(fileOpenStr);
            info.put("fileOpen",fileOpen.getJSONArray("measurements").getJSONObject(0).getIntValue("value"));
        }
        //当前进程应用CPU使用率  超过阈值报警
        String proCpuUsaStr= HttpUtil.URLGet(url+PROCESS_CPU_USAGE);
        if(!StringUtils.isEmpty(proCpuUsaStr)){
            JSONObject proCpuUsage=JSONObject.parseObject(proCpuUsaStr);
            info.put("proCpuUsage",proCpuUsage.getJSONArray("measurements").getJSONObject(0).getIntValue("value"));
        }
        return  info;
    }
}
