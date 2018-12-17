package com.github.qinxuewu.jvm;
import com.github.qinxuewu.entity.JstackEntity;
import com.github.qinxuewu.utils.ArrayUtil;
import com.github.qinxuewu.utils.ExecuteCmd;
import com.github.qinxuewu.utils.PathUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;

/**
 * Create by yster@foxmail.com 2018/11/10 0010 17:59
 */
public class Jstack {
    private final static String prefix = "java.lang.Thread.State: ";
    /**
     * 该进程的线程信息
     * X轴为时间，Y轴为值的变化
     * @return
     */
    public static JstackEntity jstack() {
        String id=getPid();
        String s = ExecuteCmd.execute(new String[]{"jstack",id });
        int total= ArrayUtil.appearNumber(s, "nid=");
        int RUNNABLE = ArrayUtil.appearNumber(s, prefix+"RUNNABLE");
        int TIMED_WAITING = ArrayUtil.appearNumber(s,prefix+"TIMED_WAITING");
        int WAITING = ArrayUtil.appearNumber(s,prefix+"WAITING");
        return new JstackEntity(id,total,RUNNABLE,TIMED_WAITING,WAITING);
    }

    /**
     * 导出线程快照
     * @return
     */
    public static String dump() throws IOException {
        String id=getPid();
        String path = PathUtil.getRootPath("dump/"+id+"_thread.txt");
        String s = ExecuteCmd.execute(new String[]{"jstack", id});
        File file = new File(path);
        FileUtils.write(file,s,Charset.forName("UTF-8"));
        return path;
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
