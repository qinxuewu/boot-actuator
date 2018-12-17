package com.pflm.modules.monitor.dao;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pflm.modules.monitor.entity.ThreadEntity;




@Mapper
public interface ThreadMapper extends BaseMapper<ThreadEntity> {
   
    
	@Delete("delete from thread_table")
	void deleteAll();
}
