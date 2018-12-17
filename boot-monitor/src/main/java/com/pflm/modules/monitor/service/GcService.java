package com.pflm.modules.monitor.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pflm.common.utils.JvmUtils;
import com.pflm.modules.monitor.dao.GcMapper;
import com.pflm.modules.monitor.entity.GcEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class GcService {
    @Autowired
    private GcMapper gcMapper;

    /**
     * 写入gc信息
     * @param name
     * @param date
     * @param kvEntities
     */
    public void save( GcEntity entity) {
    	entity.setDate(JvmUtils.time());
    	gcMapper.insert(entity);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<GcEntity> findAllByName(String name) {
    	Map<String, Object> paramMap=new HashMap<>();
    	paramMap.put("name", name);
        return gcMapper.selectList(new QueryWrapper(paramMap));
    }
    
    @Async
    public void clearAll() {
    	gcMapper.deleteAll();
    }
}
