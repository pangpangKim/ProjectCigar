package com.project.gongji.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.project.gongji.to.GongjiTO;

@Mapper
public interface GongjiMapperInter {

	@Select("select gongji_seq, gongji_subject, gongji_writer, gongji_content, gongji_reg_date from gongji_board"
			+ " where gongji_subject like CONCAT('%', #{gongji_subject}, '%')"
			+ " or gongji_content like CONCAT('%', #{gongji_content}, '%')"
			+ " or gongji_writer like CONCAT('%', #{gongji_writer}, '%')")
	ArrayList<GongjiTO> GongjiBoardSearch(GongjiTO to);
}
