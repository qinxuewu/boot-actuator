package com.github.qinxuewu.jvm;
import com.github.qinxuewu.entity.JinfoEntity;
import com.github.qinxuewu.utils.ArrayUtil;
import com.github.qinxuewu.utils.ExecuteCmd;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.stream.Collectors;
public class Jinfo {


    /**
     * JVM默认参数与指定参数
     * @return
     */
    public static JinfoEntity info(){
        String s = ExecuteCmd.execute(new String[]{"jinfo","-flags", getPid()});
        if (!s.contains("successfully")){
            return null;
        }
        String flags = "flags:";
        String command = "Command line:";
        //默认参数
        String[] noedefault = ArrayUtil.trim(s.substring(s.indexOf(flags)+flags.length(),s.indexOf(command)).split("\\s+"));
        String[] commandLine = null;
        s = s.substring(s.indexOf(command));
        if (!s.equals(command)){
            commandLine = s.substring(command.length()).split("\\s+");
        }
        commandLine = ArrayUtil.trim(commandLine);
        return new JinfoEntity(Arrays.stream(noedefault).collect(Collectors.toList()), Arrays.stream(commandLine).collect(Collectors.toList()) );
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
