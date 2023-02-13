package com.project.requestCigar.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.project.requestCigar.to.RequestCigarTO;

@Mapper
public interface RequestCigarMapperInter {
	@Select("select request_seq, request_writer_seq, request_subject, request_writer, request_reg_date, "
			+ " request_hit, request_content, request_hit, request_cmt_count, request_cigar_brand, request_cigar_name,"
			+ " request_tar, request_nicotine, request_file_name, request_file_size, request_smoke_years, request_public from cigar_request"
			+ " where request_subject like CONCAT('%', #{request_subject}, '%')"
			+ " or request_writer like CONCAT('%', #{request_writer}, '%')"
			+ " or request_content like CONCAT('%', #{request_content}, '%')"
			+ " or request_cigar_brand like CONCAT('%', #{request_brand}, '%')"
			+ " or request_cigar_name like CONCAT('%', #{request_cigar_name}, '%')")
	ArrayList<RequestCigarTO> requestBoardSearch(RequestCigarTO to);
}
