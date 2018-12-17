package com.pflm.modules.job.service;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pflm.modules.job.entity.ScheduleJobEntity;


/**
* @Description:    定时任务
* @Author:         qinxuewu
* @CreateDate:     26/11/2018 下午 3:36
* @Email 870439570@qq.com
**/
public interface ScheduleJobService extends IService<ScheduleJobEntity> {

	
	List<ScheduleJobEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	 /**
     * 立即执行
     */
    void run(Long jobId);

    /**
     * 暂停运行
     */
    void pause(Long jobId);

    /**
     * 恢复运行
     */
    void resume(Long jobId);
}
