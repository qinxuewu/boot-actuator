package com.github.qinxuewu.jvm;
import com.github.qinxuewu.entity.KVEntity;
import com.github.qinxuewu.utils.ArrayUtil;
import com.github.qinxuewu.utils.ExecuteCmd;
import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
public class Jstat {

    /**
     * Jstat 模板方法
     *
     * @param strings 命令
     * @return 集合
     */
    private static List<KVEntity> jstat(String[] strings) throws Exception {
        List<KVEntity> list = new ArrayList<>();
        String s = ExecuteCmd.execute(strings);
        assert s != null;
        BufferedReader reader = new BufferedReader(new StringReader(s));
        String[] keys = ArrayUtil.trim(reader.readLine().split("\\s+|\t"));
        String[] values = ArrayUtil.trim(reader.readLine().split("\\s+|\t"));
        // 特殊情况
        if (strings[1].equals("-compiler")) {
            for (int i = 0; i < 4; i++) {
                list.add(new KVEntity(keys[i], values[i]));
            }
            return list;
        }
        // 正常流程
        for (int i = 0; i < keys.length; i++) {
            list.add(new KVEntity(keys[i], values[i]));
        }
        return list;
    }

    /**
     * 类加载信息
     * X轴为时间，Y轴为值的变化
     * @return
     */
    public static List<KVEntity> jstatClass() throws Exception {
        String id=getPid();
        List<KVEntity> jstatClass = jstat(new String[]{"jstat", "-class", id});
        List<KVEntity> jstatCompiler = jstat(new String[]{"jstat", "-compiler", id});
        jstatClass.addAll(jstatCompiler);
        return jstatClass;
    }

    /**
     * 堆内存信息
     * X轴为时间，Y轴为值的变化
     * @return
     */
    public static List<KVEntity> jstatGc() throws Exception {
        String id=getPid();
        return jstat(new String[]{"jstat", "-gc", id});
    }

    /**
     * 堆内存百分比
     * 实时监控
     * @return
     */
    public static List<KVEntity> jstatUtil() throws Exception {
        String id=getPid();
        return jstat(new String[]{"jstat", "-gcutil", id});
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
