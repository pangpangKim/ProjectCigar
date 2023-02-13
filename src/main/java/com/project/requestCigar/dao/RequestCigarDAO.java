package com.project.requestCigar.dao;

import java.util.ArrayList;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.free_board.to.FreeBoardTO;
import com.project.requestCigar.mapper.RequestCigarMapperInter;
import com.project.requestCigar.to.RequestCigarTO;

@Repository
@MapperScan("com.project.requestCigar.mapper")
public class RequestCigarDAO {
	
	@Autowired
	private RequestCigarMapperInter requestCigarMapperInter;
	
	public ArrayList<RequestCigarTO> requestSearch(RequestCigarTO to) {
		ArrayList<RequestCigarTO> requestSearchList = requestCigarMapperInter.requestBoardSearch(to);
		return requestSearchList;
	}
}
