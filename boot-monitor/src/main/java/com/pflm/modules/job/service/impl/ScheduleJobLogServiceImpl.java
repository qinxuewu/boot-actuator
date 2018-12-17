package com.pflm.modules.job.service.impl;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pflm.modules.job.dao.ScheduleJobLogMapper;
import com.pflm.modules.job.entity.ScheduleJobLogEntity;
import com.pflm.modules.job.service.ScheduleJobLogService;



@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogMapper, ScheduleJobLogEntity> implements ScheduleJobLogService {
	@Autowired
 	private ScheduleJobLogMapper scheduleJobLogMapper;

	@Override
	public List<ScheduleJobLogEntity> queryList(Map<String, Object> map) {
		return scheduleJobLogMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return scheduleJobLogMapper.queryTotal(map);
	}



}
