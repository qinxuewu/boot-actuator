package com.pflm.modules.sys.controller;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pflm.common.utils.JvmUtils;
import com.pflm.common.utils.Md5;
import com.pflm.common.utils.Res;
import com.pflm.common.vo.FreeBean;
import com.pflm.modules.sys.dao.SysActuatorMapper;
import com.pflm.modules.sys.dao.SysUserMapper;
import com.pflm.modules.sys.entity.SysActuatorEntity;
import com.pflm.modules.sys.entity.SysUserEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author qinxuewu
 * @version 1.00
 * @time 22/11/2018下午 5:46
 */
@Controller
public class ActuatorController {
    public Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysActuatorMapper sysActuatorMapper;
	  /**
     * 登录页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/")
    public String localhost(ModelMap model){
        return "login";
    }

    /**
     * 登录页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/login")
    public String tologin(ModelMap model){
        return "login";
    }

    /**
     * 登录页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/logiout")
    public String logiout(HttpServletRequest request){
    	request.getSession().removeAttribute("username");
        return "login";
    }
      
    /**
     * 登录检测
     * @param 
     * @return
     */
	@ResponseBody
    @RequestMapping("/checkLogin")
    public Res login(String uname,String pwd,HttpServletRequest request) {
    	if(StringUtils.isEmpty(uname)||StringUtils.isEmpty(pwd)){
    		return Res.error("账号密码不能为空");
    	}
    	Map<String, Object> paramMap=new HashMap<>();
    	paramMap.put("name", uname);
    	SysUserEntity user =sysUserMapper.selectOne(new QueryWrapper(paramMap));
    	//账号不存在、密码错误
        if (user == null || !user.getPassword().equals(Md5.encode(pwd))){
            return Res.error("账号或密码不正确");
        }else{
        	request.getSession().setAttribute("username", user.getName());
			return Res.ok();
        }
    }
	
	
	/**
	 * 首页
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/index")
    public String index(ModelMap model,HttpServletRequest request){
       List<JSONObject> list=getList(); 
    	model.addAttribute("list",list);
    	model.addAttribute("username",request.getSession().getAttribute("username"));
        return "index";
    }
    
   
    /**
     * 应用列表
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/main")
    public String main(ModelMap model) throws IOException{
        List<JSONObject> list= getList();
    	model.addAttribute("list",list);
        return "main";
    }
    
    /**
     * 添加应用
     * @param name
     * @param url
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveActuator")
    public Res saveActuator(String name,String url){
    	if(StringUtils.isEmpty(name)||StringUtils.isEmpty(url)){
    		return Res.error("名称和应用域名不能为空");
    	}
    	try {
    		SysActuatorEntity query=new SysActuatorEntity();
    		query.setUrl(url);
    		query.setUrl(name);
    		int count=sysActuatorMapper.selectCount(new QueryWrapper(query));
    		if(count>0){
    			return Res.error("该应用数据中已存在");
    		}
    		SysActuatorEntity s=new SysActuatorEntity();
    		s.setName(name);
    		s.setUrl(url);
    		s.setDate(new Date());
    		sysActuatorMapper.insert(s);
    		return Res.ok();
		} catch (Exception e) {
			logger.debug("添加监控应用异常：{},{}",name,e);
			return Res.error();
		}
    }
    
    /**
     * 删除应用
     * @param name
     * @param url
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
	public Res delete(String name){
    	if(StringUtils.isEmpty(name)){
    		return Res.error("删除应用名称不能为空");
    	}
    	try {
    		SysActuatorEntity s=new SysActuatorEntity();
    		s.setName(name);
    		sysActuatorMapper.delete(new QueryWrapper(s));
    		return Res.ok();
		} catch (Exception e){
			logger.debug("删除监控应用异常：{},{}",name,e);
			return Res.error();
		}
    }
    /**
     * 监控主页
     * @return
     */
    @RequestMapping(value = "/monitor")
    public String monitor(){
        return "monitor";
    }
    
    /**
     * 获取监控应用列表
     * @return
     */
    public  List<JSONObject>  getList(){
    	List<JSONObject> info=new ArrayList<>();
    	List<SysActuatorEntity> list=sysActuatorMapper.selectList(null);
    	list.forEach(i->{
    		JSONObject obj=new JSONObject();
    		 FreeBean free;
			try {
				free = JvmUtils.free(i.getUrl());
				 obj.put("name",i.getName());
	    		 obj.put("url",i.getUrl());
	    		 obj.put("total", free.getTotal());
	    		 obj.put("used", free.getUsed());
	    		 obj.put("free", free.getFree());
	    		 info.add(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	});
    	 return info;
    }



    
    /**
     * 用户列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/userList")
    public String userList(ModelMap model){
    	List<SysUserEntity> list=sysUserMapper.selectList(null);
    	model.addAttribute("list",list);
        return "userList";
    }
      
   /**
    * 添加用户
    * @param name
    * @param password
    * @return
    */
    @ResponseBody
    @RequestMapping("/saveUser")
    public Res saveUser(String name,String password){
    	try {
    		SysUserEntity u=new SysUserEntity();
    		u.setName(name);
    		int count=sysUserMapper.selectCount(new QueryWrapper(u));
    		if(count>0){
    			return Res.error("用户名已存在");
    		}
    		SysUserEntity user=new SysUserEntity();
    		user.setName(name);
    		user.setPassword(Md5.encode(password));
    		user.setDate(format(new Date()));
    		sysUserMapper.insert(user);
    		return Res.ok();
		} catch (Exception e) {
			logger.debug("添加用户异常：{},{}",name,e);
			return Res.error();
		}
    }
    

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(Date date) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.format(date);
        }
        return null;
    }
    /**
     * 删除
     * @param name
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping("/delUser")
     public Res delUser(int id,HttpServletRequest request){
     	try {
     		sysUserMapper.deleteById(id);
     		return Res.ok();
 		} catch (Exception e) {
 			logger.debug("删除用户异常：{}",e);
 			return Res.error();
 		}
     }
     
     

}
