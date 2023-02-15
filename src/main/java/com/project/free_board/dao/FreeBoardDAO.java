package com.project.free_board.dao;

import java.util.ArrayList;
import java.io.File;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.free_board.mapper.FreeBoardMapperInter;
import com.project.free_board.to.FreeBoardTO;

@Repository
@MapperScan("com.project.free_board.mapper")
public class FreeBoardDAO {
	
	@Autowired
	private FreeBoardMapperInter freeBoardMapperInter;
	
//	private String filePath = "C:/eGovFrameDev-4.0.0-64bit/workspace/Project_Cigar/src/main/webapp/uploads/freeUpload/";
	private String filePath = System.getProperty("user.dir") + "/src/main/webapp/uploads/freeUpload/";
	
	public ArrayList<FreeBoardTO> freeList() {
		ArrayList<FreeBoardTO> freeLists = freeBoardMapperInter.freeList();
		return freeLists;
	}
	
	public FreeBoardTO freeView(FreeBoardTO to) {
		to = freeBoardMapperInter.freeView(to);
		freeBoardMapperInter.freeViewHit(to);
		//System.out.println(result);
		return to;
	}
	
	public int freeWrite_Ok(FreeBoardTO to) {
		int result = freeBoardMapperInter.freeWrite_Ok(to);
		int flag = 1;
		if(result == 1) {
			flag = 0;
		}
		return flag;
	}
	
	public FreeBoardTO freeModify(FreeBoardTO to) {
		to = freeBoardMapperInter.freeModify(to);
		return to;
	}
	
	public int freeModifyOk(FreeBoardTO to, String oldfilename) {
		
		int flag = 2;
		int result = 0;
//		System.out.println("new: " + to.getFree_file_name().trim());
//		System.out.println("old: " + oldfilename.trim());
		if(to.getFree_file_name() != null) {
			result = freeBoardMapperInter.freeModify_Ok(to);
		} else {
			result = freeBoardMapperInter.freeModify_Ok_NoImage(to);
		}
		
		if(result == 0){
			// 비밀번호가 잘못된경우
			flag = 1;
			if( to.getFree_file_name() != null ) {
				File file = new File( filePath.trim(), oldfilename.trim() );
				file.delete();
			}
		} else if(result == 1){
			// 정상 작동
			flag = 0;
			if( to.getFree_file_name() != null && oldfilename != null ) {
				File file = new File( filePath.trim(), oldfilename.trim() );
				//System.out.println(file.toString().trim());
				file.delete();
			}
		}
		return flag;
	}
	
	public FreeBoardTO freeDelete(FreeBoardTO to) {
		to = freeBoardMapperInter.freeDelete(to);
		return to;
	}
	
	
	public int freeDeleteOk(FreeBoardTO to) {

		int flag = 2;

		int result = freeBoardMapperInter.freeDelete_Ok(to);
		freeBoardMapperInter.freeDeleteAllComment(to);
		
		if(result == 0){
			// 비밀번호가 잘못된경우
			flag = 1;
		} else if(result == 1){
			// 정상 작동
			flag = 0;
			if( to.getFree_file_name() != null) {
				File file = new File( filePath.trim(), to.getFree_file_name().trim());
				//System.out.println(file.toString().trim());
				//System.out.println(file);
				file.delete();
			}
		}
		return flag;
	}
	
	public ArrayList<FreeBoardTO> freeSearch(FreeBoardTO to) {
		ArrayList<FreeBoardTO> freeSearchList = freeBoardMapperInter.FreeBoardSearch(to);
		return freeSearchList;
	}
	
}
