package com.github.qinxuewu.jvm;
import com.github.qinxuewu.utils.ExecuteCmd;
import com.github.qinxuewu.utils.PathUtil;
import java.io.File;
import java.lang.management.ManagementFactory;

/**
 * Create by yster@foxmail.com 2018/11/14 0014 22:21
 */
public class Jmap {

    /**
     * 导出堆快照
     * @return
     */
    public static String dump(){
        String id=getPid();
        String path = PathUtil.getRootPath("dump/"+id+"_heap.hprof");
        File file = new File(PathUtil.getRootPath("dump/"));
        if (!file.exists()){
            file.mkdirs();
        }
        ExecuteCmd.execute(new String[]{"jmap","-dump:format=b,file="+path, id});
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
