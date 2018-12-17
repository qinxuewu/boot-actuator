package com.pflm.common.xss;
import org.apache.commons.lang3.StringUtils;
import com.pflm.common.exception.MyException;
/**
 * @author SQL过滤
 * @version 1.00
 * @time  26/11/2018 下午 6:26
 * @email 870439570@qq.com
 */
public class SQLFilter {

    /**
     * SQL注入过滤
     * @param str  待验证的字符串
     */
    public static String sqlInject(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        //去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        //转换成小写
        str = str.toLowerCase();

        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop"};

        //判断是否包含非法字符
        for(String keyword : keywords){
            if(str.indexOf(keyword) != -1){
                throw new MyException("包含非法字符");
            }
        }

        return str;
    }
}
