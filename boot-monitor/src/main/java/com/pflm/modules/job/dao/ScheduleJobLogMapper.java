package com.pflm.modules.job.dao;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pflm.modules.job.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ScheduleJobLogMapper extends BaseMapper<ScheduleJobLogEntity> {

	List<ScheduleJobLogEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

}
