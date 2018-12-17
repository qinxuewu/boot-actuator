package com.pflm.modules.sys.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pflm.modules.sys.entity.SysUserEntity;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表
 * 
 * @author qinxuewu
 * @email 
 * @date 2018-12-13 11:14:48
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserEntity> {
	
}
