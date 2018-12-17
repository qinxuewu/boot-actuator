package com.pflm.modules.job.service;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pflm.modules.job.entity.ScheduleJobLogEntity;


/**
* @Description:    定时任务日志
* @Author:         qinxuewu
* @CreateDate:     26/11/2018 下午 3:37
* @Email 870439570@qq.com
**/
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	List<ScheduleJobLogEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
}
