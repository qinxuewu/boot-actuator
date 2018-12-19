package com.pflm.modules.job.controller;
import java.util.List;
import java.util.Map;

import com.pflm.common.utils.Res;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pflm.common.utils.PageUtils;
import com.pflm.common.utils.Query;
import com.pflm.modules.job.entity.ScheduleJobEntity;
import com.pflm.modules.job.service.ScheduleJobService;


/**
 * @Description: 定时任务
 * @Author: qinxuewu
 * @CreateDate: 26/11/2018 下午 3:35
 * @Email 870439570@qq.com
 **/
@Controller
@RequestMapping("/schedule")
public class ScheduleJobController {
	@Autowired
	private ScheduleJobService scheduleJobService;
	@Autowired
	private Scheduler scheduler;
	
	@Value("${actuator.type}")
	private String actuatorTtype;
	
    @RequestMapping(value = "/index")
    public String localhost(ModelMap model){
        return "schedule/index";
    }
    
    
	/**
	 * 定时任务列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Res list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
	    System.err.println(params.toString());
		List<ScheduleJobEntity> jobList = scheduleJobService.queryList(query);
		int total = scheduleJobService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(jobList, total, query.getLimit(),query.getPage());
		return Res.ok("success").put("page", pageUtil);
	}
	
	/**
     * 定时任务信息
     */
    @RequestMapping("/info/{jobId}")
	@ResponseBody
    public Res info(@PathVariable("jobId") Long jobId) {
        ScheduleJobEntity schedule = scheduleJobService.getById(jobId);
        return Res.ok().put("schedule", schedule);
    }
    
    /**
     * 保存定时任务
     */

    @RequestMapping("/save")
    @ResponseBody
    public Res save(@RequestBody ScheduleJobEntity scheduleJob) {
    	if(actuatorTtype.equals("1")){
    		return Res.error("演示环境不能操作");
    	}
        scheduleJobService.save(scheduleJob);
        return Res.ok();
    }

    /**
     * 修改定时任务
     */
    
    @RequestMapping("/update")
    @ResponseBody
    public Res update(@RequestBody ScheduleJobEntity scheduleJob) {
    	if(actuatorTtype.equals("1")){
    		return Res.error("演示环境不能操作");
    	}
        scheduleJobService.updateById(scheduleJob);
        return Res.ok();
    }
    
    /**
     * 删除定时任务
     */

    @RequestMapping("/delete")
    @ResponseBody
    public Res delete(Long jobId) {
    	if(actuatorTtype.equals("1")){
    		return Res.error("演示环境不能操作");
    	}
        scheduleJobService.removeById(jobId);
        return Res.ok();
    }

    /**
     * 立即执行任务
     */

    @RequestMapping("/run")
    @ResponseBody
    public Res run(Long jobId){
        scheduleJobService.run(jobId);
        return Res.ok();
    }

    /**
     * 暂停定时任务
     */

    @RequestMapping("/pause")
    @ResponseBody
    public Res pause(Long jobId) {
    	if(actuatorTtype.equals("1")){
    		return Res.error("演示环境不能操作");
    	}
        scheduleJobService.pause(jobId);
        return Res.ok();
    }

    /**
     * 恢复定时任务
     */
    @RequestMapping("/resume")
    @ResponseBody
    public Res resume(Long jobId) {
    	if(actuatorTtype.equals("1")){
    		return Res.error("演示环境不能操作");
    	}
        scheduleJobService.resume(jobId);
        return Res.ok();
    }
}
