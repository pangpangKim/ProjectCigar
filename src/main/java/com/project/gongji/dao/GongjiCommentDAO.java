package com.project.gongji.dao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.gongji.mapper.GongjiCommentMapperInter;

@Repository
@MapperScan("com.project.gongji.mapper")
public class GongjiCommentDAO {
	
	@Autowired
	private GongjiCommentMapperInter gongjiCommentMapperInter;
}
