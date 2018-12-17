package com.pflm.api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * tomcat监控    https://docs.spring.io/spring-boot/docs/2.0.x/actuator-api/html/
 * @author qinxuewu
 * @version 1.00
 * @time  29/11/2018 下午 1:34
 * @email 870439570@qq.com
 */
@Component
public class TomcatApi {
    public Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * tomcat已创建session数
     */
    private static final String TOMCAT_SESSION_CREATED="metrics/tomcat.sessions.created";
    /**
     * tomcat已过期session数
     */
    private static final String TOMCAT_SESSION_EXPIRED="metrics/tomcat.sessions.expired";
    /**
     * tomcat活跃session数
     */
    private static final String TOMCAT_SESSION_CURRENT="metrics/tomcat.sessions.active.current";

    /**
     * tomcat最多活跃session数  显示在监控页面，超过阈值可报警或者进行动态扩容
     */
    private static final String TOMCAT_SESSION_ACTIVE_MAX="metrics/tomcat.sessions.active.max";

    /**
     * tomcat最多活跃session数持续时间
     */
    private static final String ALIVE_MAX_SECOND="metrics/tomcat.sessions.alive.max.second";
    /**
     * 超过session最大配置后，拒绝的session个数 显示在监控页面，方便分析问题
     */
    private static final String TOMCAT_SESSION_REJECTED="metrics/tomcat.sessions.rejected";
    /**
     * 错误总数 显示在监控页面，方便分析问题
     */
    private static final String TOMCAT_GLOBAL_ERROR="metrics/tomcat.global.error";

    /**
     * 发送的字节数
     */
    private static final String TOMCAT_GLOBAL_SENT="metrics/tomcat.global.sent";

    /**
     * request最长时间
     */
    private static final String TOMCAT_GLOBAL_REQUEST_MAX="metrics/tomcat.global.request.max";

    /**
     * 全局request次数和时间
     */
    private static final String TOMCAT_GLOBAL_REQUEST="metrics/tomcat.global.request";

    /**
     * 全局received次数和时间
     */
    private static final String TOMCAT_GLOBAL_RECEIVED="metrics/tomcat.global.received";

    /**
     * servlet的请求次数和时间
     */
    private static final String TOMCAT_SERVLET_REQUEST="metrics/tomcat.servlet.request";
    /**
     * servlet发生错误总数
     */
    private static final String TOMCAT_SERVLET_ERROR="metrics/tomcat.servlet.error";

    /**
     * servlet请求最长时间
     */
    private static final String TOMCAT_SERVLET_REQUEST_MAX="metrics/tomcat.servlet.request.max";

    /**
     * tomcat繁忙线程 显示在监控页面，据此检查是否有线程夯住
     */
    private static final String TOMCAT_THREADS_BUSY="metrics/tomcat.threads.busy";
    /**
     * tomcat当前线程数（包括守护线程） 显示在监控页面
     */
    private static final String TOMCAT_THREADS_CURRENT="metrics/tomcat.threads.current";

    /**
     * tomcat配置的线程最大数 显示在监控页面
     */
    private static final String TOMCAT_THREADS_CONFIG_MAX="metrics/tomcat.threads.config.max";
    /**
     * tomcat读取缓存次数
     */
    private static final String TOMCAT_THREADS_CACHE_ACCESS="metrics/tomcat.cache.access";
    /**
     * tomcat缓存命中次数
     */
    private static final String TOMCAT_THREADS_CACHE_HIT="metrics/tomcat.cache.hit";
}
