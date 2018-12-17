package com.pflm.api;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pflm.utils.ByteConvKbUtils;
import com.pflm.utils.DateUtils;
import com.pflm.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
/**
 * 监控api    https://docs.spring.io/spring-boot/docs/2.0.x/actuator-api/html/
 * @author qinxuewu
 * @version 1.00
 * @time  29/11/2018 下午 1:34
 * @email 870439570@qq.com
 */
@Component
public class JvmApi {
    public Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * jvm最大内存
     */
    private static final  String JVM_MEMORY_MAX="metrics/jvm.memory.max";
    /**
     * JVM已用内存
     */
    private static final  String JVM_MEMORY_USED="metrics/jvm.memory.used";
    /**
     * committed是当前可使用的内存大小(包括已使用的),committed >= used。
     * committed不足时jvm向系统申请,若超过max则发生OutOfMemoryError错误。
     */
    private static final  String JVM_MEMORY_COMMITTED="metrics/jvm.memory.committed";

    /**
     * GC时，老年代分配的内存空间
     */
    private static final  String JVM_GC_MEMORY_PROMOTED="metrics/jvm.gc.memory.promoted";

    /**
     * GC时，年轻代分配的内存空间
     */
    private static final  String JVM_GC_MEMORY_ALLOCATED="metrics/jvm.gc.memory.allocated";
    /**
     * GC时，老年代的最大内存空间
     */
    private static final  String JVM_GC_MAXSIZE="metrics/jvm.gc.max.data.size";
    /**
     * FullGC时(full GC 是清理整个堆空间—包括年轻代和老年代。)，老年代的内存空间
     */
    private static final  String JVM_GC_LIVE_MAXSIZE="metrics/jvm.gc.live.data.size";
   /**
      GC耗时 在GC暂停中花费的时间 单位秒
     */
    private static final  String JVM_GC_PAUSE="metrics/jvm.gc.pause";
   /**
     * JVM守护线程数
     */
    private static final String JVM_THREADS_DAEMON="metrics/jvm.threads.daemon";
   /**
     * JVM当前活跃线程数
     */
    private static final String JVM_THREADS_LIVE="metrics/jvm.threads.live";
   /**
     * JVM峰值线程数
     */
    private static final String JVM_THREADS_PEAK="metrics/jvm.threads.peak";
    /**
     * 2.1版本才有
     * jVM 线程的6种状态数量
     * NEW 状态是指线程刚创建, 尚未启动
     * RUNNABLE 状态是线程正在正常运行中
     * BLOCKED  这个状态下, 是在多个线程有同步操作的场景, 也就是这里是线程在等待进入临界区
     * WAITING 这个状态下是指线程拥有了某个锁之后, 调用了他的wait方法,
     * TIMED_WAITING  这个状态就是有限的(时间限制)的WAITING, 一般出现在调用wait(long), join(long)等情况下, 另外一个线程sleep后, 也会进入TIMED_WAITING状态
     * TERMINATED  这个状态下表示 该线程的run方法已经执行完毕了, 基本上就等于死亡了
     */
    private static final String JVM_THREADS_NEW="metrics/jvm.threads.states";
   /**
     * 加载堆class数
     */
    private static final String JVM_CLASSES_LOADED="metrics/jvm.classes.loaded";
   /**
     * 未加载堆class数
     */
    private static final String JVM_CLASSES_UNLOADED="metrics/jvm.classes.unloaded";
   /**
     * JVM缓冲区已用内存
     */
    private static final String JVM_BUFFER_MEMORY_USED="metrics/jvm.buffer.memory.used";
    /**
     * 当前缓冲区数
     */
    private static final String JVM_BUFFER_MEMORY_COUNT="metrics/jvm.buffer.count";
   
  

    /**
     * 监控指定URL地址的JVM分配最大内存,已用内存
     * @param url
     * @return
     */
    public JSONArray getJvmMemory(String url){
        JSONArray info=new JSONArray();
        try {
            //JVM分配最大内存
            JSONObject maxInfo=new JSONObject();

            JSONObject max=JSONObject.parseObject(HttpUtil.URLGet(url+JVM_MEMORY_MAX));
            BigDecimal jvmMax=max.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
            maxInfo.put("value", ByteConvKbUtils.convertDouble(jvmMax,ByteConvKbUtils.Units.MB));
            maxInfo.put("name","JVM最大内存");
            info.add(maxInfo);

            //jvm已用内存
            JSONObject usedInfo=new JSONObject();
            JSONObject used=JSONObject.parseObject(HttpUtil.URLGet(url+JVM_MEMORY_USED));
            BigDecimal jvmUsed=used.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
            usedInfo.put("value", ByteConvKbUtils.convertDouble(jvmUsed,ByteConvKbUtils.Units.MB));
            usedInfo.put("name","JVM已用内存");
            info.add(usedInfo);
        }catch (Exception e){
            logger.error("监控{},jvmMemory异常：{}",url,e);
        }
        return info;

    }


    /**
     * 监控指定URL地址的heap(堆区)分配内存情况
     * Eden Spac,Survivor Space(幸存者区),Old Gen（老年代）
     * @param url
     * @return
     */
    public JSONObject getJvmMemoryHeap(String url){
        JSONObject info=new JSONObject();
        info.put("heapMax",0);
        info.put("heapUsed", 0);
        info.put("survivorSpaceUsed", 0);
        info.put("oldGeUsed", 0);
        info.put("endeSpaceUsed", 0);
        try {
            //heap 最大堆内存
            String jvmMaxHeapStr=HttpUtil.URLGet(url+JVM_MEMORY_MAX+"?tag=area:heap");
            if(!StringUtils.isEmpty(jvmMaxHeapStr)){
                JSONObject heapMax=JSONObject.parseObject(jvmMaxHeapStr);
                BigDecimal jvmHeapMax=heapMax.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("heapMax", ByteConvKbUtils.convertDouble(jvmHeapMax,ByteConvKbUtils.Units.MB));
            }
            //heap 已使用堆内存
            String jvmUsedHeapStr=HttpUtil.URLGet(url+JVM_MEMORY_USED+"?tag=area:heap");
            if(!StringUtils.isEmpty(jvmUsedHeapStr)){
                JSONObject heapUsed=JSONObject.parseObject(jvmUsedHeapStr);
                BigDecimal jvmHeapUsed=heapUsed.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("heapUsed", ByteConvKbUtils.convertDouble(jvmHeapUsed,ByteConvKbUtils.Units.MB));
            }
            //堆区  Par Survivor Space 已使用内存
            String jvmHeapSurvivorUsedStr=HttpUtil.URLGet(url+JVM_MEMORY_USED+"?tag=area:heap&tag=id:Par Survivor Space");
            if(!StringUtils.isEmpty(jvmHeapSurvivorUsedStr)){
                JSONObject heapSurvivorUsed=JSONObject.parseObject(jvmHeapSurvivorUsedStr);
                BigDecimal jvmHeapSurvivorUsed=heapSurvivorUsed.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("survivorSpaceUsed", ByteConvKbUtils.convertDouble(jvmHeapSurvivorUsed,ByteConvKbUtils.Units.MB));
            }
            //CMS Old Ge(老年代)  已使用内存
            String jvmHeapOldGeUsedStr=HttpUtil.URLGet(url+JVM_MEMORY_USED+"?tag=area:heap&tag=id:CMS Old Gen");
            if(!StringUtils.isEmpty(jvmHeapOldGeUsedStr)){
                JSONObject heapOldGeUsed=JSONObject.parseObject(jvmHeapOldGeUsedStr);
                BigDecimal jvmHeapOldGeUsed=heapOldGeUsed.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("oldGeUsed", ByteConvKbUtils.convertDouble(jvmHeapOldGeUsed,ByteConvKbUtils.Units.MB));
            }
            //Par Eden Space区 使用内存
            String jvmHeapEdenSpaceUsedStr=HttpUtil.URLGet(url+JVM_MEMORY_MAX+"?tag=area:heap&tag=id:Par Eden Space");
            if(!StringUtils.isEmpty(jvmHeapEdenSpaceUsedStr)){
                JSONObject heapEdenSpaceUsed=JSONObject.parseObject(jvmHeapEdenSpaceUsedStr);
                BigDecimal jvmHeapEdenSpaceUsed=heapEdenSpaceUsed.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("endeSpaceUsed", ByteConvKbUtils.convertDouble(jvmHeapEdenSpaceUsed,ByteConvKbUtils.Units.MB));
            }
            info.put("url",url);
            info.put("time", DateUtils.getNowTiemStr());
        }catch (Exception e){
            logger.error("监控{},heap异常：{}",url,e);
        }
        return info;
    }

    /**
     * 监控指定URL地址的Survivor Space(幸存者区)内存情况
     * @param url
     * @return
     */
    public JSONObject getJvmSurvivor(String url){
        JSONObject info=new JSONObject();
        info.put("survivorSpaceMax", 0);
        info.put("survivorSpaceUsed", 0);
        try {
            //堆区  Par Survivor Space((幸存者区)) 分配最大内存
            String jvmHeapSurvivorMaxStr=HttpUtil.URLGet(url+JVM_MEMORY_MAX+"?tag=area:heap&tag=id:Par Survivor Space");
            if(!StringUtils.isEmpty(jvmHeapSurvivorMaxStr)){
                JSONObject heapSurvivorMax=JSONObject.parseObject(jvmHeapSurvivorMaxStr);
                BigDecimal jvmHeapSurvivorMax=heapSurvivorMax.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("survivorSpaceMax", ByteConvKbUtils.convertDouble(jvmHeapSurvivorMax,ByteConvKbUtils.Units.MB));
            }
            //堆区  Par Survivor Space 已使用内存
            String jvmHeapSurvivorUsedStr=HttpUtil.URLGet(url+JVM_MEMORY_USED+"?tag=area:heap&tag=id:Par Survivor Space");
            if(!StringUtils.isEmpty(jvmHeapSurvivorUsedStr)){
                JSONObject heapSurvivorUsed=JSONObject.parseObject(jvmHeapSurvivorUsedStr);
                BigDecimal jvmHeapSurvivorUsed=heapSurvivorUsed.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("survivorSpaceUsed", ByteConvKbUtils.convertDouble(jvmHeapSurvivorUsed,ByteConvKbUtils.Units.MB));
            }
            info.put("url",url);
            info.put("time", DateUtils.getNowTiemStr());
        }catch (Exception e){
            logger.error("监控{},heap异常：{}",url,e);
        }
        return info;
    }

    /**
     * 监控指定URL地址的CMS Old Ge(老年代) 分内存
     * @param url
     * @return
     */
    public JSONObject getOldGe(String url){
        JSONObject info=new JSONObject();
        info.put("oldGeMax", 0);
        info.put("oldGeUsed", 0);
        try {
            //CMS Old Ge(老年代) 分配内存
            String jvmHeapOldGeMaxStr=HttpUtil.URLGet(url+JVM_MEMORY_MAX+"?tag=area:heap&tag=id:CMS Old Gen");
            if(!StringUtils.isEmpty(jvmHeapOldGeMaxStr)){
                JSONObject heapOldGeMax=JSONObject.parseObject(jvmHeapOldGeMaxStr);
                BigDecimal jvmHeapOldGeMax=heapOldGeMax.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("oldGeMax", ByteConvKbUtils.convertDouble(jvmHeapOldGeMax,ByteConvKbUtils.Units.MB));
            }
            //CMS Old Ge(老年代)  已使用内存
            String jvmHeapOldGeUsedStr=HttpUtil.URLGet(url+JVM_MEMORY_USED+"?tag=area:heap&tag=id:CMS Old Gen");
            if(!StringUtils.isEmpty(jvmHeapOldGeUsedStr)){
                JSONObject heapOldGeUsed=JSONObject.parseObject(jvmHeapOldGeUsedStr);
                BigDecimal jvmHeapOldGeUsed=heapOldGeUsed.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("oldGeUsed", ByteConvKbUtils.convertDouble(jvmHeapOldGeUsed,ByteConvKbUtils.Units.MB));
            }
            info.put("url",url);
            info.put("time", DateUtils.getNowTiemStr());
        }catch (Exception e){
            logger.error("监控{},heap异常：{}",url,e);
        }
        return info;
    }


    /**
     * 监控指定URL地址的Par Eden Space区 内存
     * @param url
     * @return
     */
    public JSONObject getEnenSpac(String url){
        JSONObject info=new JSONObject();
        info.put("endeSpaceMax", 0);
        info.put("endeSpaceUsed", 0);
        try {
            //Par Eden Space区 分配内存
            String jvmHeapEdenSpaceMaxStr=HttpUtil.URLGet(url+JVM_MEMORY_MAX+"?tag=area:heap&tag=id:Par Eden Space");
            if(!StringUtils.isEmpty(jvmHeapEdenSpaceMaxStr)){
                JSONObject heapEdenSpaceMax=JSONObject.parseObject(jvmHeapEdenSpaceMaxStr);
                BigDecimal jvmHeapEdenSpacemMax=heapEdenSpaceMax.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("endeSpaceMax", ByteConvKbUtils.convertDouble(jvmHeapEdenSpacemMax,ByteConvKbUtils.Units.MB));
            }
            //Par Eden Space区 使用内存
            String jvmHeapEdenSpaceUsedStr=HttpUtil.URLGet(url+JVM_MEMORY_MAX+"?tag=area:heap&tag=id:Par Eden Space");
            if(!StringUtils.isEmpty(jvmHeapEdenSpaceUsedStr)){
                JSONObject heapEdenSpaceUsed=JSONObject.parseObject(jvmHeapEdenSpaceUsedStr);
                BigDecimal jvmHeapEdenSpaceUsed=heapEdenSpaceUsed.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("endeSpaceUsed", ByteConvKbUtils.convertDouble(jvmHeapEdenSpaceUsed,ByteConvKbUtils.Units.MB));
            }
            info.put("url",url);
            info.put("time", DateUtils.getNowTiemStr());
        }catch (Exception e){
            logger.error("监控{},heap异常：{}",url,e);
        }
        return info;
    }


    /**
     * 监控指定URL地址的nonheap(非堆区)内存情况
     * Metaspace(元空间 jdk8),Compressed Class Space(类指针压缩空间),Code Cache(代码缓存区)
     * @param url
     * @return
     */
    public JSONObject getJvmMemoryNonHeap(String url){
        JSONObject info=new JSONObject();
        info.put("nonHeapMax", 0);
        info.put("nonHeapUsed", 0);
        info.put("compressedClassSpaceUsed", 0);
        info.put("codeCacheUsed", 0);
        try {
            //分配最大堆内存
            String nonheapMaxStr=HttpUtil.URLGet(url+JVM_MEMORY_MAX+"?tag=area:nonheap");
            if(!StringUtils.isEmpty(nonheapMaxStr)){
                JSONObject noHeapMax=JSONObject.parseObject(nonheapMaxStr);
                BigDecimal jvmnonHeapMax=noHeapMax.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("nonHeapMax", ByteConvKbUtils.convertDouble(jvmnonHeapMax,ByteConvKbUtils.Units.MB));
            }
            //已使用堆内存
            String nonheapStr=HttpUtil.URLGet(url+JVM_MEMORY_USED+"?tag=area:nonheap");
            if(!StringUtils.isEmpty(nonheapStr)){
                JSONObject nonHeapUsed=JSONObject.parseObject(nonheapStr);
                BigDecimal jvmnonHeapUsed=nonHeapUsed.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("nonHeapUsed", ByteConvKbUtils.convertDouble(jvmnonHeapUsed,ByteConvKbUtils.Units.MB));
            }
            //Compressed Class Space 使用空间
            String compressedClassSpaceUsedStr=HttpUtil.URLGet(url+JVM_MEMORY_USED+"?tag=area:nonheap&tag=id:Compressed Class Space");
            if(!StringUtils.isEmpty(compressedClassSpaceUsedStr)){
                JSONObject classSpaceUsed=JSONObject.parseObject(compressedClassSpaceUsedStr);
                BigDecimal jvmClassSpaceUsed=classSpaceUsed.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("compressedClassSpaceUsed", ByteConvKbUtils.convertDouble(jvmClassSpaceUsed,ByteConvKbUtils.Units.MB));
            }
            //Code Cache 使用空间
            String codeCacheUsedStr=HttpUtil.URLGet(url+JVM_MEMORY_USED+"?tag=area:nonheap&tag=id:Code Cache");
            if(!StringUtils.isEmpty(codeCacheUsedStr)){
                JSONObject codeCacheUsed=JSONObject.parseObject(codeCacheUsedStr);
                BigDecimal jvmcodeCacheUsed=codeCacheUsed.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("codeCacheUsed", ByteConvKbUtils.convertDouble(jvmcodeCacheUsed,ByteConvKbUtils.Units.MB));
            }
            info.put("url",url);
            info.put("time", DateUtils.getNowTiemStr());
        }catch (Exception e){
            logger.error("监控{},nonheap异常：{}",url,e);
        }
        return info;
    }

    /**
     * 监控指定URL地址的非堆区 Metaspace(元空间 jdk8)使用内存情况
     * @param url
     * @return
     */
    public double getMetaspace(String url){
        String metaspaceStr=HttpUtil.URLGet(url+JVM_MEMORY_USED+"?tag=area:nonheap&tag=id:Metaspace");
        JSONObject metaspacepUsed=JSONObject.parseObject(metaspaceStr);
        BigDecimal jvmnMetaspaceUsed=metaspacepUsed.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
        return  ByteConvKbUtils.convertDouble(jvmnMetaspaceUsed,ByteConvKbUtils.Units.MB);
    }

    /**
     * 监控指定URL地址的非堆区 Compressed Class Space(类指针压缩空间)内存情况
     * @param url
     * @return
     */
    public JSONObject getCompressedCLassSpace(String url){
        JSONObject info=new JSONObject();
        info.put("compressedClassSpaceMax", 0);
        info.put("compressedClassSpaceUsed", 0);
        try {
            //Compressed Class Space 分配空间
            String compressedClassSpaceMaxStr=HttpUtil.URLGet(url+JVM_MEMORY_MAX+"?tag=area:nonheap&tag=id:Compressed Class Space");
            if(!StringUtils.isEmpty(compressedClassSpaceMaxStr)){
                JSONObject classSpaceMax=JSONObject.parseObject(compressedClassSpaceMaxStr);
                BigDecimal jvmClassSpaceMax=classSpaceMax.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("compressedClassSpaceMax", ByteConvKbUtils.convertDouble(jvmClassSpaceMax,ByteConvKbUtils.Units.MB));
            }
            //Compressed Class Space 使用空间
            String compressedClassSpaceUsedStr=HttpUtil.URLGet(url+JVM_MEMORY_USED+"?tag=area:nonheap&tag=id:Compressed Class Space");
            if(!StringUtils.isEmpty(compressedClassSpaceUsedStr)){
                JSONObject classSpaceUsed=JSONObject.parseObject(compressedClassSpaceUsedStr);
                BigDecimal jvmClassSpaceUsed=classSpaceUsed.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("compressedClassSpaceUsed", ByteConvKbUtils.convertDouble(jvmClassSpaceUsed,ByteConvKbUtils.Units.MB));
            }
            info.put("url",url);
            info.put("time", DateUtils.getNowTiemStr());
        }catch (Exception e){
            logger.error("监控{},nonheap异常：{}",url,e);
        }
        return info;
    }

    /**
     * 监控指定URL地址的nonheap(非堆区)内存情况
     * Metaspace(元空间 jdk8),Compressed Class Space(类指针压缩空间),Code Cache(代码缓存区)
     * @param url
     * @return
     */
    public JSONObject getCodeCache(String url){
        JSONObject info=new JSONObject();
        info.put("codeCacheMax", 0);
        info.put("codeCacheUsed", 0);
        try {
            //Code Cache 分配空间
            String codeCacheMaxStr=HttpUtil.URLGet(url+JVM_MEMORY_MAX+"?tag=area:nonheap&tag=id:Code Cache");
            if(!StringUtils.isEmpty(codeCacheMaxStr)){
                JSONObject codeCacheMax=JSONObject.parseObject(codeCacheMaxStr);
                BigDecimal jvmcodeCacheMax=codeCacheMax.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("codeCacheMax", ByteConvKbUtils.convertDouble(jvmcodeCacheMax,ByteConvKbUtils.Units.MB));
            }
            //Code Cache 使用空间
            String codeCacheUsedStr=HttpUtil.URLGet(url+JVM_MEMORY_USED+"?tag=area:nonheap&tag=id:Code Cache");
            if(!StringUtils.isEmpty(codeCacheUsedStr)){
                JSONObject codeCacheUsed=JSONObject.parseObject(codeCacheUsedStr);
                BigDecimal jvmcodeCacheUsed=codeCacheUsed.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("codeCacheUsed", ByteConvKbUtils.convertDouble(jvmcodeCacheUsed,ByteConvKbUtils.Units.MB));
            }
            info.put("url",url);
            info.put("time", DateUtils.getNowTiemStr());
        }catch (Exception e){
            logger.error("监控{},nonheap异常：{}",url,e);
        }
        return info;
    }

    /**
     * 监控指定URl的GC 情况
     * @param url
     * @return
     */
    public JSONObject getJvmGc(String url){
        JSONObject info=new JSONObject();
        info.put("gcPromoted",0);
        info.put("gcAllocated",0);
        try {
           // GC时，老年代分配的内存空间
            String gcPromoted=HttpUtil.URLGet(url+JVM_GC_MEMORY_PROMOTED);
            if(!StringUtils.isEmpty(gcPromoted)){
                JSONObject promoted=JSONObject.parseObject(gcPromoted);
                BigDecimal memoryPromoted=promoted.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("gcPromoted", ByteConvKbUtils.convertDouble(memoryPromoted,ByteConvKbUtils.Units.MB));
            }
            // GC时，年轻代分配的内存空间
            String allocatedStr=HttpUtil.URLGet(url+JVM_GC_MEMORY_ALLOCATED);
            if(!StringUtils.isEmpty(allocatedStr)){
                JSONObject allocated=JSONObject.parseObject(allocatedStr);
                BigDecimal gcAllocated=allocated.getJSONArray("measurements").getJSONObject(0).getBigDecimal("value");
                info.put("gcAllocated", ByteConvKbUtils.convertDouble(gcAllocated,ByteConvKbUtils.Units.MB));
            }
            info.put("url",url);
            info.put("time", DateUtils.getNowTiemStr());
        }catch (Exception e){
            logger.error("监控{}, GC异常：{}",url,e);
        }
        return info;
    }


    /**
     * 应用加载class数
     * @param url
     * @return
     */
    public JSONObject getClasssinfo(String url){
        JSONObject info=new JSONObject();
        try {
            JSONObject classLoaded=JSONObject.parseObject(HttpUtil.URLGet(url+JVM_CLASSES_LOADED));
            info.put("classLoaded", classLoaded.getJSONArray("measurements").getJSONObject(0).getIntValue("value"));

            JSONObject unclassLoaded=JSONObject.parseObject(HttpUtil.URLGet(url+JVM_CLASSES_UNLOADED));
            info.put("unclassLoaded",  unclassLoaded.getJSONArray("measurements").getJSONObject(0).getIntValue("value"));
        }catch (Exception e){
            logger.error("监控{}, 异常：{}",url,e);
        }
        return info;
    }

    /**
     * 监控应用线程活跃数 守护线程数  峰值数
     * @param url
     * @return
     */
    public JSONObject getThreadsInfo(String url){
        JSONObject info=new JSONObject();
        info.put("daemonThread",0);
        info.put("liveThread",0);
        info.put("peakThread",0);
        info.put("url",url);
        info.put("time", DateUtils.getNowTiemStr());
        info.put("hms", DateUtils.getNowHourMillisSecond());
        try {
            //JVM守护线程数
            JSONObject daemonThread=JSONObject.parseObject(HttpUtil.URLGet(url+JVM_THREADS_DAEMON));
            info.put("daemonThread", daemonThread.getJSONArray("measurements").getJSONObject(0).getIntValue("value"));

            //JVM活跃线程数
            JSONObject liveThread=JSONObject.parseObject(HttpUtil.URLGet(url+JVM_THREADS_LIVE));
            info.put("liveThread", liveThread.getJSONArray("measurements").getJSONObject(0).getIntValue("value"));

            //JVM峰值线程数
            JSONObject peakThread=JSONObject.parseObject(HttpUtil.URLGet(url+JVM_THREADS_PEAK));
            info.put("peakThread", peakThread.getJSONArray("measurements").getJSONObject(0).getIntValue("value"));
        }catch (Exception e){
            logger.error("线程监控{}, 异常：{}",url,e);
        }
        return info;
    }

    /**
     * 线程的几种状态数量检测
     * @param url
     * @return
     */
    public JSONObject getThreadsStateInfo(String url){
        JSONObject info=new JSONObject();
        info.put("daemonThread",0);
        info.put("url",url);
        info.put("time", DateUtils.getNowTiemStr());
        info.put("hms", DateUtils.getNowHourMillisSecond());
        try {
            //JVM守护线程数
            JSONObject daemonThread=JSONObject.parseObject(HttpUtil.URLGet(url+JVM_THREADS_DAEMON));
            info.put("daemonThread", daemonThread.getJSONArray("measurements").getJSONObject(0).getIntValue("value"));

        }catch (Exception e){
            logger.error("线程监控{}, 异常：{}",url,e);
        }
        return info;
    }

    /**
     * 监GC 耗时 单位秒
     * @param url
     * @return
     */
    public JSONObject getGcPause(String url){
        JSONObject info=new JSONObject();

        info.put("url",url);
        info.put("time", DateUtils.getNowTiemStr());
        info.put("hms", DateUtils.getNowHourMillisSecond());
        try {

            JSONObject daemonThread=JSONObject.parseObject(HttpUtil.URLGet(url+JVM_GC_PAUSE));
            info.put("count", daemonThread.getJSONArray("measurements").getJSONObject(0).getDoubleValue("value"));
            info.put("total_time", daemonThread.getJSONArray("measurements").getJSONObject(1).getDoubleValue("value"));
            info.put("max", daemonThread.getJSONArray("measurements").getJSONObject(2).getDoubleValue("value"));
        }catch (Exception e){
            logger.error("GC耗时监控{}, 异常：{}",url,e);
        }
        return info;
    }
}
