package com.github.qinxuewu.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应参数
 * @author qxw
 * @data 2018年11月21日上午11:14:52
 */
public class Res extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public Res() {
        put("code", 0);
    }

    public static Res error() {
        return error(500, "服务器繁忙");
    }

    public static Res error(String msg) {
        return error(500, msg);
    }

    public static Res error(int code, String msg) {
        Res r = new Res();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static Res ok(String msg) {
        Res r = new Res();
        r.put("msg", msg);
        return r;
    }

    public static Res ok(Map<String, Object> map) {
        Res r = new Res();
        r.putAll(map);
        return r;
    }

    public static Res ok() {
        return new Res();
    }
    @Override
    public Res put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
