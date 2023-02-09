package com.project.gongji.dao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.gongji.mapper.GongjiMapperInter;

@Repository
@MapperScan("com.project.gongji.mapper")
public class GongjiDAO {
	
	@Autowired
	private GongjiMapperInter gongjiMapperInter;
}
