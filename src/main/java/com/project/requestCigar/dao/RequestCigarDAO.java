package com.project.requestCigar.dao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.requestCigar.mapper.RequestCigarMapperInter;

@Repository
@MapperScan("com.project.requestCigar.mapper")
public class RequestCigarDAO {
	
	@Autowired
	private RequestCigarMapperInter requestCigarMapperInter;
}
