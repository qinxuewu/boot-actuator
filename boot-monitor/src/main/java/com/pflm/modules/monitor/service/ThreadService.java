package com.pflm.modules.monitor.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pflm.common.utils.JvmUtils;
import com.pflm.modules.monitor.dao.ThreadMapper;
import com.pflm.modules.monitor.entity.ThreadEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ThreadService {
    @Autowired
    private ThreadMapper threadMapper;

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ThreadEntity> findAllByName(String name) {
    	Map<String, Object> paramMap=new HashMap<>();
    	paramMap.put("name", name);
        return threadMapper.selectList(new QueryWrapper(paramMap));
    }

    /**
     * 写入线程相关信息
     * @param name
     * @param date
     * @param jstatk
     */
    public void save(ThreadEntity entity) {
    	entity.setDate(JvmUtils.time());
    	threadMapper.insert(entity);
    }


    @Async
    public void clearAll() {
    	threadMapper.deleteAll();
    }
}
