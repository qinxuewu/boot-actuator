package com.pflm.common.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.pflm.common.vo.FreeBean;
import com.pflm.common.vo.KvBean;

/**
 * linux  jvm操作
 * @author admin
 *
 */
public class JvmUtils {

	
	/**
	 * 获取哦系统实际占用内存
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static FreeBean free(String url) throws IOException {
		List<KvBean> list = new ArrayList<>();
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", "free");
		params.put("value", "-m");
		JSONObject info = JSONObject.parseObject(HttpUtil.URLGet(url+"/actuator/info/runexec", params,"utf-8"));
		String s = info.getString("result");
		BufferedReader reader = new BufferedReader(new StringReader(s));
		String[] keys = ArrayUtil.trim(reader.readLine().split("\\s+|\t"));
		String[] values = ArrayUtil.trim(reader.readLine().split("\\s+|\t"));
		for (int i = 0; i < keys.length; i++) {
			list.add(new KvBean(keys[i], values[i]));
		}
		FreeBean b=new FreeBean();
		b.setTotal(list.get(1).getValue());
		b.setUsed(list.get(2).getValue());
		b.setFree(list.get(3).getValue());
		return b;
	}
	


    /**
     * 现在时间
     * @return
     */
    public static String time(){
        SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm:ss");
        return format.format(new Date());
    }


}
