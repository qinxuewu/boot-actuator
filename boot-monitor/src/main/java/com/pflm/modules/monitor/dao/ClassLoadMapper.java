package com.pflm.modules.monitor.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pflm.modules.monitor.entity.ClassLoadEntity;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ClassLoadMapper extends BaseMapper<ClassLoadEntity> {
	
	@Delete("delete from class_table")
	void deleteAll();
 
  
}
