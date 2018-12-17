package com.pflm.modules.job.dao;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pflm.modules.job.entity.ScheduleJobEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface ScheduleJobMapper extends BaseMapper<ScheduleJobEntity> {

	List<ScheduleJobEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	@Update("update schedule_job set status = #{status} where job_id=#{jobId}")
	int updateStatus(@Param("status") int status,@Param("jobId") Long jobId);
}
