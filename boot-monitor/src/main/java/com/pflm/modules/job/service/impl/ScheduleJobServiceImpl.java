package com.pflm.modules.job.service.impl;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pflm.common.utils.Constant;
import com.pflm.modules.job.dao.ScheduleJobMapper;
import com.pflm.modules.job.entity.ScheduleJobEntity;
import com.pflm.modules.job.service.ScheduleJobService;
import com.pflm.modules.job.utils.ScheduleUtils;


@Service("scheduleJobService")
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobMapper, ScheduleJobEntity> implements ScheduleJobService {
	@Autowired
	private ScheduleJobMapper scheduleJobMapper;
    @Autowired
    private Scheduler scheduler;	

	 /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
    	scheduleJobMapper.selectList(null);
        List<ScheduleJobEntity> scheduleJobList =scheduleJobMapper.selectList(null);
        for (ScheduleJobEntity scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

	@Override
	public List<ScheduleJobEntity> queryList(Map<String, Object> map) {
		return scheduleJobMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {

		return scheduleJobMapper.queryTotal(map);
	}

	@Override
	public void run(Long jobId) {
		  ScheduleUtils.run(scheduler,scheduleJobMapper.selectById(jobId));
		
	}

	@Override
	public void pause(Long jobId) {
		  ScheduleUtils.pauseJob(scheduler, jobId);
		  scheduleJobMapper.updateStatus(Constant.ScheduleStatus.PAUSE.getValue(), jobId);
	}

	@Override
	public void resume(Long jobId) {
		ScheduleUtils.resumeJob(scheduler, jobId);
		 scheduleJobMapper.updateStatus(Constant.ScheduleStatus.NORMAL.getValue(), jobId);
	}

}
