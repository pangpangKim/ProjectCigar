package com.project.gongji.dao;

import java.util.ArrayList;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.project.gongji.mapper.GongjiMapperInter;
import com.project.gongji.to.GongjiTO;

@Repository
@MapperScan("com.project.gongji.mapper")
public class GongjiDAO {
	
	@Autowired
	private GongjiMapperInter gongjiMapperInter;
	
	public ArrayList<GongjiTO> gongjiSearch(GongjiTO to) {
		ArrayList<GongjiTO> gongjiSearchList = gongjiMapperInter.GongjiBoardSearch(to);
		return gongjiSearchList;
	}
}
