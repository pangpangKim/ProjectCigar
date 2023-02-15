package com.project.gongji.dao;

import java.io.File;
import java.util.ArrayList;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.cigar.to.CigarTO;
import com.project.gongji.mapper.GongjiMapperInter;
import com.project.gongji.to.GongjiTO;
@Repository
@MapperScan("com.project.gongji.mapper")
public class GongjiDAO {
	
	@Autowired
	private GongjiMapperInter gongjiMapperInter;
	
	private String filePath = "C:/eGovFrameDev-4.0.0-64bit/workspace/Project_Cigar/src/main/webapp/uploads/gongjiUpload/";
	
	public ArrayList<GongjiTO> gongjiList() {
		ArrayList<GongjiTO> gongjiLists = gongjiMapperInter.gongjiList();
		return gongjiLists;
	}
	
	public GongjiTO gongjiView(GongjiTO to) {
		int res = gongjiMapperInter.gongjiView_hit(to);
		//System.out.println("조회수 상승 값 : " + res);
		//System.out.println(to.getGongji_seq());
		to = gongjiMapperInter.gongjiView(to);
		return to;
	}
	
	public void gongjiWrite() {
		
	}
	
	public int gongjiWriteOk(GongjiTO to) {
		int result = gongjiMapperInter.gongjiWirte_ok(to);
		int flag = 1;
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public GongjiTO gongjiModify(GongjiTO to) {
		to = gongjiMapperInter.gongjiModify(to);
		return to;
	}
	
	public int gongjiModifyOk(GongjiTO to, String oldfilename) {
		int flag = 2;
		int result = 0;
//		System.out.println("new: " + to.getGongji_file_name().trim());
//		System.out.println("old: " + oldfilename.trim());
		if(to.getGongji_file_name() != null) {
			result = gongjiMapperInter.gongjiModify_ok(to);
		} else {
			result = gongjiMapperInter.gongjiModify_Ok_NoImage(to);
		}
		
		if(result == 0){
			// 비밀번호가 잘못된경우
			flag = 1;
			if( to.getGongji_file_name() != null ) {
				File file = new File( filePath.trim(), oldfilename.trim() );
				file.delete();
			}
		} else if(result == 1){
			// 정상 작동
			flag = 0;
			if( to.getGongji_file_name() != null && oldfilename != null ) {
				File file = new File( filePath.trim(), oldfilename.trim() );
				//System.out.println(file.toString().trim());
				file.delete();
			}
		}
		return flag;
	}
	
	public GongjiTO gongjiDelete(GongjiTO to) {
		to = gongjiMapperInter.gongjiDelete(to);
		return to;
	}
	
	public int gongjiDeleteOk(GongjiTO to) {
		int flag = 2;

		int result = gongjiMapperInter.gongjiDelete_ok(to);
		
		if(result == 0){
			// 비밀번호가 잘못된경우
			flag = 1;
		} else if(result == 1){
			// 정상 작동
			flag = 0;
			if( to.getGongji_file_name() != null) {
				File file = new File( filePath.trim(), to.getGongji_file_name().trim());
				//System.out.println(file.toString().trim());
				//System.out.println(file);
				file.delete();
			}
		}
		return flag;
	}
	
	public ArrayList<GongjiTO> gongjiSearch(GongjiTO to) {
		ArrayList<GongjiTO> gongjiSearchList = gongjiMapperInter.GongjiBoardSearch(to);
		return gongjiSearchList;
	}
}
