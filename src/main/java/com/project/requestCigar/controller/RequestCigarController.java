package com.project.requestCigar.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.project.requestCigar.dao.RequestCigarCommentDAO;
import com.project.requestCigar.dao.RequestCigarDAO;
import com.project.requestCigar.to.RequestCigarCommentTO;
import com.project.requestCigar.to.RequestCigarTO;

@Controller
public class RequestCigarController {
	
	@Autowired
	private RequestCigarDAO dao;
	
	@Autowired
	private RequestCigarCommentDAO cmtDAO;

//	private String filePath = "C:/eGovFrameDev-4.0.0-64bit/workspace/Project_Cigar/src/main/webapp/uploads/requestUpload/";
//	private String backupFilePath = "C:/eGovFrameDev-4.0.0-64bit/workspace/Project_Cigar/src/main/webapp/uploads/requestUpload/requestBackup/";

	private String filePath = System.getProperty("user.dir") + "/src/main/webapp/uploads/requestUpload/";
	private String backupFilePath = System.getProperty("user.dir") + "/src/main/webapp/uploads/requestUpload/requestBackup/";
	
	@RequestMapping("requestCigar/list.do")
	public ModelAndView requestList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		ArrayList<RequestCigarTO> listTO = dao.requestList();
		JSONArray requestLists = new JSONArray();
		for(RequestCigarTO to : listTO) {
			JSONObject obj = new JSONObject();
			obj.put("request_seq", to.getRequest_seq());
			obj.put("request_writer_seq", to.getRequest_writer_seq());
			obj.put("request_suject", to.getRequest_subject());
			obj.put("request_writer", to.getRequest_writer());
			obj.put("request_reg_date", to.getRequest_reg_date());
			obj.put("request_content", to.getRequest_content());
			obj.put("request_hit", to.getRequest_hit());
			obj.put("request_cmt_count", to.getRequest_cmt_count());
			obj.put("request_cigar_brand", to.getRequest_cigar_brand());
			obj.put("request_cigar_name", to.getRequest_cigar_name());
			obj.put("request_tar", to.getRequest_tar());
			obj.put("request_nicotine", to.getRequest_nicotine());
			obj.put("request_file_name", to.getRequest_file_name());
			obj.put("request_file_size", to.getRequest_file_size());
			obj.put("request_smoke_years", to.getRequest_smoke_years());
			
			requestLists.add(obj);
		}
		
		mav.addObject("requestLists", requestLists);
		mav.setViewName("requestViews/requestList");
		return mav;
	}
	
	@RequestMapping("requestCigar/view.do")
	public ModelAndView requestView(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		RequestCigarTO to = new RequestCigarTO();
		RequestCigarCommentTO cmtTO = new RequestCigarCommentTO();
		//to.setRequest_cigar_seq(Integer.parseInt(request.getParameter("request_cigar_seq")));
		to.setRequest_seq(1);
		cmtTO.setRequest_pseq(1);
		//HttpSession session = request.getSession();
		//System.out.println((int)session.getAttribute("member_seq"));
		to = dao.requestView(to);
		JSONObject requestViewObj = new JSONObject();
		requestViewObj.put("request_seq", to.getRequest_seq());
		requestViewObj.put("request_writer_seq", to.getRequest_writer_seq());
		requestViewObj.put("request_suject", to.getRequest_subject());
		requestViewObj.put("request_writer", to.getRequest_writer());
		requestViewObj.put("request_reg_date", to.getRequest_reg_date());
		requestViewObj.put("request_content", to.getRequest_content());
		requestViewObj.put("request_hit", to.getRequest_hit());
		requestViewObj.put("request_cmt_count", to.getRequest_cmt_count());
		requestViewObj.put("request_cigar_brand", to.getRequest_cigar_brand());
		requestViewObj.put("request_cigar_name", to.getRequest_cigar_name());
		requestViewObj.put("request_tar", to.getRequest_tar());
		requestViewObj.put("request_nicotine", to.getRequest_nicotine());
		requestViewObj.put("request_file_name", to.getRequest_file_name());
		requestViewObj.put("request_file_size", to.getRequest_file_size());
		requestViewObj.put("request_smoke_years", to.getRequest_smoke_years());
		
		ArrayList<RequestCigarCommentTO> CommentListTO = cmtDAO.requestCommentList(cmtTO);
		JSONArray requestCommentLists = new JSONArray();
		for(RequestCigarCommentTO cmtTO2 : CommentListTO) {
			JSONObject obj = new JSONObject();
			obj.put("request_cmt_seq", cmtTO2.getRequest_cmt_seq());
			obj.put("request_pseq", cmtTO2.getRequest_pseq());
			obj.put("request_cmt_writer_seq", cmtTO2.getRequest_cmt_writer_seq());
			obj.put("request_grp", cmtTO2.getRequest_grp());
			obj.put("request_grps", cmtTO2.getRequest_grps());
			obj.put("request_grpl", cmtTO2.getRequest_grpl());
			obj.put("request_cmt_writer", cmtTO2.getRequest_cmt_writer());
			obj.put("request_cmt_content", cmtTO2.getRequest_cmt_content());
			obj.put("request_cmt_reg_date", cmtTO2.getRequest_cmt_reg_date());
			
			requestCommentLists.add(obj);
		}
		//System.out.println(requestCommentLists.size());
		mav.addObject("requestViewObj", requestViewObj);
		mav.addObject("requestCommentLists", requestCommentLists);
		mav.setViewName("requestViews/requestView2");
		return mav;
	}
	
	@RequestMapping("requestCigar/write.do")
	public ModelAndView requestWrite(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("requestViews/requestWrite");
		return mav;
	}
	
	@RequestMapping("requestCigar/write_ok.do")
	public ModelAndView requestWriteOk(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		RequestCigarTO to = new RequestCigarTO();
		
		try {
			to.setRequest_writer_seq(Integer.parseInt(request.getParameter("request_writer_seq")));
			to.setRequest_subject(request.getParameter("request_subject"));
			to.setRequest_writer(request.getParameter("request_writer"));
			to.setRequest_content(request.getParameter("request_content"));
			to.setRequest_cigar_brand(request.getParameter("request_cigar_brand"));
			to.setRequest_cigar_name(request.getParameter("request_cigar_name"));
			to.setRequest_tar(Double.parseDouble(request.getParameter("request_tar")));
			to.setRequest_nicotine(Double.parseDouble(request.getParameter("request_nicotine")));
			//to.setRequest_writer_seq(Integer.parseInt(request.getParameter("request_smoke_years")));
			if(request.getParameter("Request_public").trim().equals("public")) {
				to.setRequest_public(true);
				//System.out.println("공개글 값" + request.getParameter("free_public"));
			} else {
				to.setRequest_public(false);
				//System.out.println("비공개글 값" + request.getParameter("free_public"));
			}
			
			if( !upload.isEmpty() ) {
				String extention = upload.getOriginalFilename().substring(upload.getOriginalFilename().indexOf("."));
				to.setRequest_file_name(UUID.randomUUID().toString() + extention);
				to.setRequest_file_size((int)upload.getSize());
				byte[] bytes = upload.getBytes();
				Path path = Paths.get((filePath + to.getRequest_file_name()).trim());
	            Files.write(path, bytes);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("[에러] : " + e.getMessage());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			System.out.println("[에러] : " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("[에러] : " + e.getMessage());
		}
		int flag = dao.requestWriteOk(to);
		mav.addObject("flag", flag);
		mav.setViewName("requestViews/requestWrite_ok");
		return mav;
	}
	
	@RequestMapping("requestCigar/modify.do")
	public ModelAndView requestModify(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		RequestCigarTO to = new RequestCigarTO();
		to.setRequest_seq(Integer.parseInt(request.getParameter("request_seq")));
		to = dao.requestModify(to);
		JSONObject requestModifyObj = new JSONObject();
		requestModifyObj.put("request_seq", to.getRequest_seq());
		requestModifyObj.put("request_writer_seq", to.getRequest_writer_seq());
		requestModifyObj.put("request_suject", to.getRequest_subject());
		requestModifyObj.put("request_writer", to.getRequest_writer());
		requestModifyObj.put("request_reg_date", to.getRequest_reg_date());
		requestModifyObj.put("request_content", to.getRequest_content());
		requestModifyObj.put("request_hit", to.getRequest_hit());
		requestModifyObj.put("request_cmt_count", to.getRequest_cmt_count());
		requestModifyObj.put("request_cigar_brand", to.getRequest_cigar_brand());
		requestModifyObj.put("request_cigar_name", to.getRequest_cigar_name());
		requestModifyObj.put("request_tar", to.getRequest_tar());
		requestModifyObj.put("request_nicotine", to.getRequest_nicotine());
		requestModifyObj.put("request_file_name", to.getRequest_file_name());
		requestModifyObj.put("request_file_size", to.getRequest_file_size());
		requestModifyObj.put("request_smoke_years", to.getRequest_smoke_years());
		mav.addObject("requestModifyObj", requestModifyObj);
		mav.setViewName("requestViews/requestModify");
		return mav;
	}
	
	@RequestMapping("requestCigar/modify_ok.do")
	public ModelAndView requestModifyOk(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) {
		ModelAndView mav = new ModelAndView();
		RequestCigarTO to = new RequestCigarTO();
		String oldfilename = request.getParameter("request_file_name");
		try {
			to.setRequest_seq(Integer.parseInt(request.getParameter("request_seq")));
			to.setRequest_subject(request.getParameter("request_subject"));
			to.setRequest_content(request.getParameter("request_content"));
			to.setRequest_cigar_brand(request.getParameter("request_cigar_brand"));
			to.setRequest_cigar_name(request.getParameter("request_cigar_name"));
			to.setRequest_tar(Double.parseDouble(request.getParameter("request_tar")));
			to.setRequest_nicotine(Double.parseDouble(request.getParameter("request_nicotine")));
			if( !upload.isEmpty() ) {
				byte[] bytes = upload.getBytes();
				String extention = upload.getOriginalFilename().substring(upload.getOriginalFilename().indexOf("."));
				to.setRequest_file_name(UUID.randomUUID().toString() + extention);
				to.setRequest_file_size((int) upload.getSize());
				
			    Path targetPath = Paths.get(filePath.trim() + oldfilename.trim());
			    Path backuppath = Paths.get(backupFilePath.trim() + oldfilename.trim());			
				Path path = Paths.get(filePath.trim() + to.getRequest_file_name().trim());
				
				Files.write(path, bytes);
			    Files.copy(targetPath, backuppath);
	            
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("[에러] : " + e.getMessage());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			System.out.println("[에러] : " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("[에러] : " + e.getMessage());
		}
		int flag = dao.requestModifyOk(to, oldfilename);
		mav.addObject("flag", flag);
		mav.setViewName("requestViews/requestModify_ok");
		return mav;
	}
	
	@RequestMapping("requestCigar/delete.do")
	public ModelAndView requestDelete(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		RequestCigarTO to = new RequestCigarTO();
		to.setRequest_seq(Integer.parseInt(request.getParameter("request_seq")));
		to = dao.requestDelete(to);
		JSONObject requestDeleteObj = new JSONObject();
		requestDeleteObj.put("request_seq", to.getRequest_seq());
		requestDeleteObj.put("request_writer_seq", to.getRequest_writer_seq());
		requestDeleteObj.put("request_suject", to.getRequest_subject());
		requestDeleteObj.put("request_writer", to.getRequest_writer());
		requestDeleteObj.put("request_reg_date", to.getRequest_reg_date());
		requestDeleteObj.put("request_content", to.getRequest_content());
		requestDeleteObj.put("request_hit", to.getRequest_hit());
		requestDeleteObj.put("request_cmt_count", to.getRequest_cmt_count());
		requestDeleteObj.put("request_cigar_brand", to.getRequest_cigar_brand());
		requestDeleteObj.put("request_cigar_name", to.getRequest_cigar_name());
		requestDeleteObj.put("request_tar", to.getRequest_tar());
		requestDeleteObj.put("request_nicotine", to.getRequest_nicotine());
		requestDeleteObj.put("request_file_name", to.getRequest_file_name());
		requestDeleteObj.put("request_file_size", to.getRequest_file_size());
		requestDeleteObj.put("request_smoke_years", to.getRequest_smoke_years());
		mav.addObject("requestDeleteObj", requestDeleteObj);
		mav.setViewName("requestViews/requestDelete");
		return mav;
	}
	
	@RequestMapping("requestCigar/delete_ok.do")
	public ModelAndView requestdeleteOk(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		RequestCigarTO to = new RequestCigarTO();
		to.setRequest_seq(Integer.parseInt(request.getParameter("request_seq")));
		try {
			to = dao.requestDelete(to);
			Path targetPath = Paths.get(filePath.trim() + to.getRequest_file_name());
			Path backuppath = Paths.get(backupFilePath.trim() + to.getRequest_file_name());		
			Files.copy(targetPath, backuppath);
			System.out.println(to.getRequest_file_name());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		int flag = dao.requestDeleteOk(to);
		mav.addObject("flag", flag);
		mav.setViewName("requestViews/requestDelete_ok");
		return mav;
	}
	
	@RequestMapping("requestCigar/parent_cmt_write.do")
	public ModelAndView requestParentCmtWrite(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		JSONObject requestCmtWriteObj = new JSONObject();
		RequestCigarCommentTO to = new RequestCigarCommentTO();
		to.setRequest_pseq(Integer.parseInt(request.getParameter("request_pseq")));
		to = cmtDAO.requestParentCommentWrite(to);
		requestCmtWriteObj.put("request_cmt_seq", 0);
		requestCmtWriteObj.put("request_pseq", to.getRequest_pseq());
		requestCmtWriteObj.put("request_grp", to.getRequest_grp()+1);
		requestCmtWriteObj.put("request_grps", 0);
		requestCmtWriteObj.put("request_grpl", 0);
		requestCmtWriteObj.put("request_cmt_writer", (String)session.getAttribute("nickname"));
		
		mav.addObject("requestCmtWriteObj", requestCmtWriteObj);
		mav.setViewName("requestViews/requestCmtWrite");
		return mav;
	}
	
	
	@RequestMapping("requestCigar/cmt_write.do")
	public ModelAndView requestCmtWrite(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		RequestCigarCommentTO to = new RequestCigarCommentTO();
		to.setRequest_cmt_seq(Integer.parseInt(request.getParameter("request_cmt_seq")));
		to = cmtDAO.requestCommentWrite(to);
		JSONObject requestCmtWriteObj = new JSONObject();
		requestCmtWriteObj.put("request_cmt_seq", to.getRequest_cmt_seq());
		requestCmtWriteObj.put("request_pseq", to.getRequest_pseq());
		requestCmtWriteObj.put("request_grp", to.getRequest_grp());
		requestCmtWriteObj.put("request_grps", to.getRequest_grps());
		requestCmtWriteObj.put("request_grpl", to.getRequest_grpl());
		requestCmtWriteObj.put("request_cmt_writer", (String)session.getAttribute("nickname"));
		mav.addObject("requestCmtWriteObj", requestCmtWriteObj);
		mav.setViewName("requestViews/requestCmtWrite");
		return mav;
	}
	
	@RequestMapping("requestCigar/cmt_write_ok.do")
	public ModelAndView requestCmtWriteOk(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		RequestCigarCommentTO to = new RequestCigarCommentTO();
		HttpSession session = request.getSession();
		to.setRequest_cmt_seq(Integer.parseInt(request.getParameter("request_cmt_seq")));
		to.setRequest_pseq(Integer.parseInt(request.getParameter("request_pseq")));
		to.setRequest_cmt_writer_seq((int)session.getAttribute("member_seq"));
		to.setRequest_grp(Integer.parseInt(request.getParameter("request_grp")));
		to.setRequest_grps(Integer.parseInt(request.getParameter("request_grps")));
		to.setRequest_grpl(Integer.parseInt(request.getParameter("request_grpl")));
		to.setRequest_cmt_writer((String)session.getAttribute("nickname"));
		to.setRequest_cmt_content(request.getParameter("request_cmt_content"));
		int flag = cmtDAO.requestCmtWriteOk(to);
		mav.addObject("flag", flag);
		mav.setViewName("requestViews/requestCmtWrite_ok");
		return mav;
	}
	
	@RequestMapping("requestCigar/cmt_modify.do")
	public ModelAndView requestCmtModify(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		RequestCigarCommentTO to = new RequestCigarCommentTO();
		to.setRequest_cmt_seq(Integer.parseInt(request.getParameter("request_cmt_seq")));
		to = cmtDAO.requestCommentModify(to);
		JSONObject requestCmtModifyObj = new JSONObject();
		requestCmtModifyObj.put("request_cmt_seq", to.getRequest_cmt_seq());
		requestCmtModifyObj.put("request_pseq", to.getRequest_pseq());
		requestCmtModifyObj.put("request_cmt_writer_seq", to.getRequest_cmt_writer_seq());
		requestCmtModifyObj.put("request_grp", to.getRequest_grp());
		requestCmtModifyObj.put("request_grps", to.getRequest_grps());
		requestCmtModifyObj.put("request_grpl", to.getRequest_grpl());
		requestCmtModifyObj.put("request_cmt_writer", to.getRequest_cmt_writer());
		requestCmtModifyObj.put("request_cmt_content", to.getRequest_cmt_content());
		requestCmtModifyObj.put("request_cmt_reg_date", to.getRequest_cmt_reg_date());
		mav.addObject("requestCmtModifyObj", requestCmtModifyObj);
		mav.setViewName("requestViews/requestCmtModify");
		return mav;
	}
	
	@RequestMapping("requestCigar/cmt_modify_ok.do")
	public ModelAndView requestCmtModifyOk(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		RequestCigarCommentTO to = new RequestCigarCommentTO();
		to.setRequest_cmt_seq(Integer.parseInt(request.getParameter("request_cmt_seq")));
		to.setRequest_pseq(Integer.parseInt(request.getParameter("request_pseq")));
		to.setRequest_cmt_writer_seq(Integer.parseInt(request.getParameter("request_cmt_writer_seq")));
		to.setRequest_grp(Integer.parseInt(request.getParameter("request_grp")));
		to.setRequest_grps(Integer.parseInt(request.getParameter("request_grps")));
		to.setRequest_grpl(Integer.parseInt(request.getParameter("request_grpl")));
		to.setRequest_cmt_writer(request.getParameter("request_cmt_writer"));
		to.setRequest_cmt_content(request.getParameter("request_cmt_content"));
		to.setRequest_cmt_reg_date(Date.valueOf(request.getParameter("request_cmt_reg_date")));
		int flag = cmtDAO.requestCmtWriteOk(to);
		mav.addObject("flag", flag);
		mav.setViewName("requestViews/requestCmtModify_ok");
		return mav;
	}
	
	@RequestMapping("requestCigar/cmt_delete.do")
	public ModelAndView requestCmtDelete(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		RequestCigarCommentTO to = new RequestCigarCommentTO();
		to.setRequest_cmt_seq(Integer.parseInt(request.getParameter("request_cmt_seq")));
		to = cmtDAO.requestCommentDelete(to);
		JSONObject requestCmtDeleteObj = new JSONObject();
		requestCmtDeleteObj.put("request_cmt_seq", to.getRequest_cmt_seq());
		requestCmtDeleteObj.put("request_pseq", to.getRequest_pseq());
		requestCmtDeleteObj.put("request_cmt_writer_seq", to.getRequest_cmt_writer_seq());
		requestCmtDeleteObj.put("request_grp", to.getRequest_grp());
		requestCmtDeleteObj.put("request_grps", to.getRequest_grps());
		requestCmtDeleteObj.put("request_grpl", to.getRequest_grpl());
		requestCmtDeleteObj.put("request_cmt_writer", to.getRequest_cmt_writer());
		requestCmtDeleteObj.put("request_cmt_content", to.getRequest_cmt_content());
		requestCmtDeleteObj.put("request_cmt_reg_date", to.getRequest_cmt_reg_date());
		mav.addObject("requestCmtDeleteObj", requestCmtDeleteObj);
		mav.setViewName("requestViews/requestCmtDelete");
		return mav;
	}
	
	@RequestMapping("requestCigar/cmt_delete_ok.do")
	public ModelAndView requestCmtDeleteOk(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		RequestCigarCommentTO to = new RequestCigarCommentTO();
		to.setRequest_cmt_seq(Integer.parseInt(request.getParameter("request_cmt_seq")));
		int flag = cmtDAO.requestCommentDeleteOk(to);
		mav.addObject("flag", flag);
		mav.setViewName("requestViews/requestCmtDelete_ok");
		return mav;
	}
	
	@RequestMapping("requestCigar/Search")
	public JSONArray requestSearch(HttpServletRequest request, HttpServletResponse response) {
		RequestCigarTO to = new RequestCigarTO();
		to.setRequest_cigar_brand(request.getParameter("Request_cigar_brand"));
		to.setRequest_cigar_name(request.getParameter("Request_cigar_name"));
		to.setRequest_content(request.getParameter("Request_content"));
		to.setRequest_writer(request.getParameter("Request_writer"));
		to.setRequest_subject(request.getParameter("Request_subject"));
		ArrayList<RequestCigarTO> requestSearchtLists = dao.requestSearch(to);
		
		JSONArray reqeustSearchArray = new JSONArray();
		for(RequestCigarTO to2 : requestSearchtLists) {
			JSONObject obj = new JSONObject();
			obj.put("request_seq", to2.getRequest_seq());
			obj.put("request_wirter", to2.getRequest_writer());
			obj.put("request_wirter_seq", to2.getRequest_writer_seq());
			obj.put("request_subject", to2.getRequest_subject());
			obj.put("request_content", to2.getRequest_content());
			obj.put("request_smoke_years", to2.getRequest_smoke_years());
			obj.put("request_cmt_count", to2.getRequest_cmt_count());
			obj.put("request_cigar_brand", to2.getRequest_cigar_brand());
			obj.put("request_cigar_name", to2.getRequest_cigar_name());
			obj.put("request_request_tar", to2.getRequest_tar());
			obj.put("request_nicotine", to2.getRequest_nicotine());
			obj.put("request_file_name_", to2.getRequest_file_name());
			obj.put("request_file_size", to2.getRequest_file_size());
			obj.put("request_reg_date", to2.getRequest_reg_date());
			obj.put("request_hit", to2.getRequest_hit());
			reqeustSearchArray.add(obj);
		}
		
		System.out.println(reqeustSearchArray);
		return reqeustSearchArray;
	}
}
