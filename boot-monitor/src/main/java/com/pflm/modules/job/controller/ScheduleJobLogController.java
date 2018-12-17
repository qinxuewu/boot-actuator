package com.pflm.modules.job.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pflm.common.utils.PageUtils;
import com.pflm.common.utils.Query;
import com.pflm.common.utils.Res;
import com.pflm.modules.job.entity.ScheduleJobLogEntity;
import com.pflm.modules.job.service.ScheduleJobLogService;

/**
 * 定时任务日志
 * @author qinxeuw
 *
 */
@Controller
@RequestMapping("/scheduleLog")
public class ScheduleJobLogController {
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;
    
    
	 /**
     * 定时任务日志列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public Res list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ScheduleJobLogEntity> jobList = scheduleJobLogService.queryList(query);
        int total = scheduleJobLogService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(jobList, total, query.getLimit(), query.getPage());

        return Res.ok().put("page", pageUtil);
    }
    
    /**
     * 定时任务日志信息
     */
    @RequestMapping("/info/{logId}")
    public Res info(@PathVariable("logId") Long logId) {
        ScheduleJobLogEntity log = scheduleJobLogService.getById(logId);

        return Res.ok().put("log", log);
    }
}
