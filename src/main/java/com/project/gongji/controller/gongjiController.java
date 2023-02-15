package com.project.gongji.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.project.gongji.dao.GongjiCommentDAO;
import com.project.gongji.dao.GongjiDAO;
import com.project.gongji.to.GongjiCommentTO;
import com.project.gongji.to.GongjiTO;

@Controller
public class GongjiController {
	
//	private String filePath = "C:/eGovFrameDev-4.0.0-64bit/workspace/Project_Cigar/src/main/webapp/uploads/gongjiUpload/";
//	private String backupFilePath = "C:/eGovFrameDev-4.0.0-64bit/workspace/Project_Cigar/src/main/webapp/uploads/gongjiUpload/gongjiBackup/";

	private String filePath = System.getProperty("user.dir") + "/src/main/webapp/uploads/gongjiUpload/";
	private String backupFilePath = System.getProperty("user.dir") + "/src/main/webapp/uploads/gongjiUpload/gongjiBackup/";
	
	@Autowired
	private GongjiDAO dao;
	@Autowired
	private GongjiCommentDAO cmtDAO;
	
	@RequestMapping(value = "gongji/list.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView gongjiList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();

		ArrayList<GongjiTO> listTO = dao.gongjiList();
		JSONArray gongjiLists = new JSONArray();
		for(GongjiTO to2 : listTO) {
			JSONObject obj = new JSONObject();
			obj.put("gongji_seq", to2.getGongji_seq());
			obj.put("gongji_writer_seq", to2.getGongji_writer_seq());
			obj.put("gongji_subject", to2.getGongji_subject());
			obj.put("gongji_writer", to2.getGongji_writer());
			obj.put("gongji_reg_date", to2.getGongji_reg_date().toString());
			obj.put("gongji_content", to2.getGongji_content());
			obj.put("gongji_hit", to2.getGongji_hit());
			obj.put("gongji_cmt_count", to2.getGongji_cmt_count());
			obj.put("gongji_file_name", to2.getGongji_file_name());
			obj.put("gongji_file_size", to2.getGongji_file_size());
			obj.put("gongji_smoke_years", to2.getGongji_smoke_years().toString());
			obj.put("gongji_public", to2.isGongji_public());
			
			gongjiLists.add(obj);
		}
		JSONObject obj2 = new JSONObject();
		obj2.put("gongjiLists", gongjiLists);
		mav.addObject("obj2", obj2);
		mav.setViewName("gongjiViews/gongjiList");
		return mav;
	}
	
	@RequestMapping(value = "gongji/view.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView gongjiView(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		GongjiTO to = new GongjiTO();
		GongjiCommentTO cmtTO = new GongjiCommentTO();
		//to.setGongji_cigar_seq(Integer.parseInt(request.getParameter("gongji_cigar_seq")));
		to.setGongji_seq(1);
		cmtTO.setGongji_pseq(1);
		//HttpSession session = request.getSession();
		//System.out.println((int)session.getAttribute("member_seq"));
		to = dao.gongjiView(to);
		JSONObject gongjiViewObj = new JSONObject();
		gongjiViewObj.put("gongji_seq", to.getGongji_seq());
		gongjiViewObj.put("gongji_writer_seq", to.getGongji_writer_seq());
		gongjiViewObj.put("gongji_suject", to.getGongji_subject());
		gongjiViewObj.put("gongji_writer", to.getGongji_writer());
		gongjiViewObj.put("gongji_reg_date", to.getGongji_reg_date());
		gongjiViewObj.put("gongji_content", to.getGongji_content());
		gongjiViewObj.put("gongji_hit", to.getGongji_hit());
		gongjiViewObj.put("gongji_cmt_count", to.getGongji_cmt_count());
		gongjiViewObj.put("gongji_file_name", to.getGongji_file_name());
		gongjiViewObj.put("gongji_file_size", to.getGongji_file_size());
		gongjiViewObj.put("gongji_smoke_years", to.getGongji_smoke_years());
		gongjiViewObj.put("gongji_public", to.isGongji_public());
		
		ArrayList<GongjiCommentTO> CommentListTO = cmtDAO.gongjiCommentList(cmtTO);
		JSONArray gongjiCommentLists = new JSONArray();
		for(GongjiCommentTO cmtTO2 : CommentListTO) {
			JSONObject obj = new JSONObject();
			obj.put("gongji_cmt_seq", cmtTO2.getGongji_cmt_seq());
			obj.put("gongji_pseq", cmtTO2.getGongji_pseq());
			obj.put("gongji_cmt_writer_seq", cmtTO2.getGongji_cmt_writer_seq());
			obj.put("gongji_grp", cmtTO2.getGongji_grp());
			obj.put("gongji_grps", cmtTO2.getGongji_grps());
			obj.put("gongji_grpl", cmtTO2.getGongji_grpl());
			obj.put("gongji_cmt_writer", cmtTO2.getGongji_cmt_writer());
			obj.put("gongji_cmt_content", cmtTO2.getGongji_cmt_content());
			obj.put("gongji_cmt_reg_date", cmtTO2.getGongji_cmt_reg_date());
			
			gongjiCommentLists.add(obj);
		}
		
		mav.addObject("gongjiViewObj", gongjiViewObj);
		mav.addObject("gongjiCommentLists", gongjiCommentLists);
		
		mav.setViewName("gongjiViews/gongjiView2");
		
		return mav;
	}

	@RequestMapping(value = "gongji/write.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView gongjiWrite(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("gongjiViews/gongjiWrite");
		return mav;
	}
	
	@RequestMapping(value = "gongji/write_ok.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView gongjiWriteOk(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) {
		ModelAndView mav = new ModelAndView();
		GongjiTO to = new GongjiTO();
		HttpSession session = request.getSession();
		
		to.setGongji_writer_seq((int)session.getAttribute("member_seq"));
		to.setGongji_subject(request.getParameter("gongji_subject"));
		to.setGongji_writer((String)session.getAttribute("nickname"));
		to.setGongji_content(request.getParameter("gongji_content"));
		to.setGongji_smoke_years((Date)session.getAttribute("smoke_years"));

		if(request.getParameter("gongji_public").equals("public")) {
			to.setGongji_public(true);
		} else {
			to.setGongji_public(false);
		}
		
		try {
			if( !upload.isEmpty() ) {
				String extention = upload.getOriginalFilename().substring(upload.getOriginalFilename().indexOf("."));
				to.setGongji_file_name(UUID.randomUUID().toString() + extention);
				to.setGongji_file_size((int)upload.getSize());
				byte[] bytes = upload.getBytes();
				Path path = Paths.get((filePath + to.getGongji_file_name()).trim());
			    Files.write(path, bytes);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int flag = dao.gongjiWriteOk(to);
		mav.addObject("flag", flag);
		mav.setViewName("gongjiViews/gongjiWrite_ok");
		return mav;
	}
	
	@RequestMapping(value = "gongji/modify.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView gongjiModify(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		GongjiTO to = new GongjiTO();
		//to.setGongji_seq(Integer.parseInt(request.getParameter("gongji_seq")));
		to.setGongji_seq(1);
		to = dao.gongjiModify(to);
		JSONObject gongjiModifyObj = new JSONObject();
		gongjiModifyObj.put("gongji_seq", to.getGongji_seq());
		gongjiModifyObj.put("gongji_writer_seq", to.getGongji_writer_seq());
		gongjiModifyObj.put("gongji_suject", to.getGongji_subject());
		gongjiModifyObj.put("gongji_writer", to.getGongji_writer());
		gongjiModifyObj.put("gongji_reg_date", to.getGongji_reg_date());
		gongjiModifyObj.put("gongji_content", to.getGongji_content());
		gongjiModifyObj.put("gongji_hit", to.getGongji_hit());
		gongjiModifyObj.put("gongji_cmt_count", to.getGongji_cmt_count());
		gongjiModifyObj.put("gongji_file_name", to.getGongji_file_name());
		gongjiModifyObj.put("gongji_file_size", to.getGongji_file_size());
		gongjiModifyObj.put("gongji_smoke_years", to.getGongji_smoke_years());
		gongjiModifyObj.put("gongji_public", to.isGongji_public());
		mav.addObject("gongjiModifyObj", gongjiModifyObj);
		mav.setViewName("gongjiViews/gongjiModify");
		return mav;
	}
	
	@RequestMapping(value = "gongji/modify_ok.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView gongjiModifyOk(HttpServletRequest request, HttpServletResponse response,  MultipartFile upload) {
		ModelAndView mav = new ModelAndView();
		GongjiTO to = new GongjiTO();
		String oldfilename = request.getParameter("gongji_file_name");
		try {
		to.setGongji_seq(Integer.parseInt(request.getParameter("gongji_seq")));
		to.setGongji_subject(request.getParameter("gongji_subject"));
		to.setGongji_content(request.getParameter("gongji_content"));			
			if( !upload.isEmpty() ) {
				byte[] bytes = upload.getBytes();
				String extention = upload.getOriginalFilename().substring(upload.getOriginalFilename().indexOf("."));
				to.setGongji_file_name(UUID.randomUUID().toString() + extention);
				to.setGongji_file_size((int) upload.getSize());
				
			    Path targetPath = Paths.get(filePath.trim() + oldfilename.trim());
			    Path backuppath = Paths.get(backupFilePath.trim() + oldfilename.trim());			
				Path path = Paths.get(filePath.trim() + to.getGongji_file_name().trim());
				
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
		
		if(request.getParameter("gongji_public").equals("public")) {
			to.setGongji_public(true);
		} else {
			to.setGongji_public(false);
		}
		int flag = dao.gongjiModifyOk(to, oldfilename);
		mav.addObject("flag", flag);
		mav.setViewName("gongjiViews/gongjiModify_ok");
		return mav;
	}
	
	@RequestMapping(value = "gongji/delete.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView gongjiDelete(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		GongjiTO to = new GongjiTO();
		//to.setGongji_seq(Integer.parseInt(request.getParameter("gongji_seq")));
		to.setGongji_seq(1);
		to = dao.gongjiDelete(to);
		JSONObject gongjiDeleteObj = new JSONObject();
		gongjiDeleteObj.put("gongji_seq", to.getGongji_seq());
		gongjiDeleteObj.put("gongji_writer_seq", to.getGongji_writer_seq());
		gongjiDeleteObj.put("gongji_suject", to.getGongji_subject());
		gongjiDeleteObj.put("gongji_writer", to.getGongji_writer());
		gongjiDeleteObj.put("gongji_reg_date", to.getGongji_reg_date());
		gongjiDeleteObj.put("gongji_content", to.getGongji_content());
		gongjiDeleteObj.put("gongji_hit", to.getGongji_hit());
		gongjiDeleteObj.put("gongji_cmt_count", to.getGongji_cmt_count());
		gongjiDeleteObj.put("gongji_file_name", to.getGongji_file_name());
		gongjiDeleteObj.put("gongji_file_size", to.getGongji_file_size());
		gongjiDeleteObj.put("gongji_smoke_years", to.getGongji_smoke_years());
		gongjiDeleteObj.put("gongji_public", to.isGongji_public());
		
		mav.addObject("gongjiDeleteObj", gongjiDeleteObj);
		mav.setViewName("gongjiViews/gongjiDelete");
		return mav;
	}
	
	@RequestMapping(value = "/gongji/delete_ok.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView gongjideleteOk(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		GongjiTO to = new GongjiTO();
		
		to.setGongji_seq(Integer.parseInt(request.getParameter("gongji_seq")));
		try {
			to = dao.gongjiDelete(to);
			Path targetPath = Paths.get(filePath.trim() + to.getGongji_file_name());
			Path backuppath = Paths.get(backupFilePath.trim() + to.getGongji_file_name());		
			Files.copy(targetPath, backuppath);
			System.out.println(to.getGongji_file_name());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	    
		to.setGongji_file_name(to.getGongji_file_name());
		
		int flag = dao.gongjiDeleteOk(to);
		mav.addObject("flag", flag);
		mav.setViewName("gongjiViews/gongjiDelete_ok");
		return mav;
	}
	
	@RequestMapping(value = "gongjiCommentLists.do", method = {RequestMethod.GET, RequestMethod.POST} )
	public JSONArray gongjiCommentList(HttpServletRequest request, HttpServletResponse response) {
		JSONArray gongjiCommentLists = new JSONArray();
		GongjiCommentTO cmtTO = new GongjiCommentTO();
		cmtTO.setGongji_pseq(Integer.parseInt(request.getParameter("gongji_pseq")));
		ArrayList<GongjiCommentTO> listCmtTO = cmtDAO.gongjiCommentList(cmtTO);
		for(GongjiCommentTO cmtTO2 : listCmtTO) {
			JSONObject obj = new JSONObject();
			obj.put("gongji_cmt_seq", cmtTO2.getGongji_cmt_seq());
			obj.put("gongji_pseq", cmtTO2.getGongji_pseq());
			obj.put("gongji_cmt_writer_seq", cmtTO2.getGongji_cmt_writer_seq());
			obj.put("gongji_grp", cmtTO2.getGongji_grp());
			obj.put("gongji_grps", cmtTO2.getGongji_grps());
			obj.put("gongji_grpl", cmtTO2.getGongji_grpl());
			obj.put("gongji_cmt_writer", cmtTO2.getGongji_cmt_writer());
			obj.put("gongji_cmt_content", cmtTO2.getGongji_cmt_content());
			obj.put("gongji_cmt_reg_date", cmtTO2.getGongji_cmt_reg_date());
			
			gongjiCommentLists.add(obj);
		}
		
		return gongjiCommentLists;
	}
	
	@RequestMapping(value = "gongji/parent_cmt_write.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView gongjiParentCmtWrite(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		JSONObject gongjiCmtWriteObj = new JSONObject();
		GongjiCommentTO to = new GongjiCommentTO();
		to.setGongji_pseq(Integer.parseInt(request.getParameter("gongji_pseq")));
		to = cmtDAO.gongjiParentCommentWrite(to);
		gongjiCmtWriteObj.put("gongji_cmt_seq", 0);
		gongjiCmtWriteObj.put("gongji_pseq", to.getGongji_pseq());
		gongjiCmtWriteObj.put("gongji_grp", to.getGongji_grp()+1);
		gongjiCmtWriteObj.put("gongji_grps", 0);
		gongjiCmtWriteObj.put("gongji_grpl", 0);
		gongjiCmtWriteObj.put("gongji_cmt_writer", (String)session.getAttribute("nickname"));
		
		mav.addObject("gongjiCmtWriteObj", gongjiCmtWriteObj);
		mav.setViewName("gongjiViews/gongjiCmtWrite");
		return mav;
	}
	
	
	@RequestMapping(value = "gongji/cmt_write.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView gongjiCmtWrite(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		GongjiCommentTO to = new GongjiCommentTO();
		to.setGongji_cmt_seq(Integer.parseInt(request.getParameter("gongji_cmt_seq")));
		to = cmtDAO.gongjiCommentWrite(to);
		JSONObject gongjiCmtWriteObj = new JSONObject();
		gongjiCmtWriteObj.put("gongji_cmt_seq", to.getGongji_cmt_seq());
		gongjiCmtWriteObj.put("gongji_pseq", to.getGongji_pseq());
		gongjiCmtWriteObj.put("gongji_grp", to.getGongji_grp());
		gongjiCmtWriteObj.put("gongji_grps", to.getGongji_grps());
		gongjiCmtWriteObj.put("gongji_grpl", to.getGongji_grpl());
		gongjiCmtWriteObj.put("gongji_cmt_writer", (String)session.getAttribute("nickname"));
		mav.addObject("gongjiCmtWriteObj", gongjiCmtWriteObj);
		mav.setViewName("gongjiViews/gongjiCmtWrite");
		return mav;
	}
	
	@RequestMapping(value = "gongji/cmt_write_ok.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView gongjiCmtWriteOk(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		GongjiCommentTO to = new GongjiCommentTO();
		HttpSession session = request.getSession();
		to.setGongji_cmt_seq(Integer.parseInt(request.getParameter("gongji_cmt_seq")));
		to.setGongji_pseq(Integer.parseInt(request.getParameter("gongji_pseq")));
		to.setGongji_cmt_writer_seq((int)session.getAttribute("member_seq"));
		to.setGongji_grp(Integer.parseInt(request.getParameter("gongji_grp")));
		to.setGongji_grps(Integer.parseInt(request.getParameter("gongji_grps")));
		to.setGongji_grpl(Integer.parseInt(request.getParameter("gongji_grpl")));
		to.setGongji_cmt_writer((String)session.getAttribute("nickname"));
		to.setGongji_cmt_content(request.getParameter("gongji_cmt_content"));
		int flag = cmtDAO.gongjiCmtWriteOk(to);
		mav.addObject("flag", flag);
		mav.setViewName("gongjiViews/gongjiCmtWrite_ok");
		return mav;
	}
	
	@RequestMapping(value = "gongji/cmt_modify.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView gongjiCmtModify(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		GongjiCommentTO to = new GongjiCommentTO();
		to.setGongji_cmt_seq(Integer.parseInt(request.getParameter("gongji_cmt_seq")));
		to = cmtDAO.gongjiCommentModify(to);
		JSONObject gongjiCmtModifyObj = new JSONObject();
		gongjiCmtModifyObj.put("gongji_cmt_seq", to.getGongji_cmt_seq());
		gongjiCmtModifyObj.put("gongji_pseq", to.getGongji_pseq());
		gongjiCmtModifyObj.put("gongji_cmt_writer_seq", to.getGongji_cmt_writer_seq());
		gongjiCmtModifyObj.put("gongji_grp", to.getGongji_grp());
		gongjiCmtModifyObj.put("gongji_grps", to.getGongji_grps());
		gongjiCmtModifyObj.put("gongji_grpl", to.getGongji_grpl());
		gongjiCmtModifyObj.put("gongji_cmt_writer", to.getGongji_cmt_writer());
		gongjiCmtModifyObj.put("gongji_cmt_content", to.getGongji_cmt_content());
		gongjiCmtModifyObj.put("gongji_cmt_reg_date", to.getGongji_cmt_reg_date());
		mav.addObject("gongjiCmtModifyObj", gongjiCmtModifyObj);
		mav.setViewName("gongjiViews/gongjiCmtModify");
		return mav;
	}
	
	@RequestMapping(value = "gongji/cmt_modify_ok.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView gongjiCmtModifyOk(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		GongjiCommentTO to = new GongjiCommentTO();
		to.setGongji_cmt_seq(Integer.parseInt(request.getParameter("gongji_cmt_seq")));
		to.setGongji_pseq(Integer.parseInt(request.getParameter("gongji_pseq")));
		to.setGongji_cmt_writer_seq(Integer.parseInt(request.getParameter("gongji_cmt_writer_seq")));
		to.setGongji_grp(Integer.parseInt(request.getParameter("gongji_grp")));
		to.setGongji_grps(Integer.parseInt(request.getParameter("gongji_grps")));
		to.setGongji_grpl(Integer.parseInt(request.getParameter("gongji_grpl")));
		to.setGongji_cmt_writer(request.getParameter("gongji_cmt_writer"));
		to.setGongji_cmt_content(request.getParameter("gongji_cmt_content"));
		to.setGongji_cmt_reg_date(Date.valueOf(request.getParameter("gongji_cmt_reg_date")));
		int flag = cmtDAO.gongjiCmtWriteOk(to);
		mav.addObject("flag", flag);
		mav.setViewName("gongjiViews/gongjiCmtModify_ok");
		return mav;
	}
	
	@RequestMapping(value = "gongji/cmt_delete.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView gongjiCmtDelete(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		GongjiCommentTO to = new GongjiCommentTO();
		to.setGongji_cmt_seq(Integer.parseInt(request.getParameter("gongji_cmt_seq")));
		to = cmtDAO.gongjiCommentDelete(to);
		JSONObject gongjiCmtDeleteObj = new JSONObject();
		gongjiCmtDeleteObj.put("gongji_cmt_seq", to.getGongji_cmt_seq());
		gongjiCmtDeleteObj.put("gongji_pseq", to.getGongji_pseq());
		gongjiCmtDeleteObj.put("gongji_cmt_writer_seq", to.getGongji_cmt_writer_seq());
		gongjiCmtDeleteObj.put("gongji_grp", to.getGongji_grp());
		gongjiCmtDeleteObj.put("gongji_grps", to.getGongji_grps());
		gongjiCmtDeleteObj.put("gongji_grpl", to.getGongji_grpl());
		gongjiCmtDeleteObj.put("gongji_cmt_writer", to.getGongji_cmt_writer());
		gongjiCmtDeleteObj.put("gongji_cmt_content", to.getGongji_cmt_content());
		gongjiCmtDeleteObj.put("gongji_cmt_reg_date", to.getGongji_cmt_reg_date());
		mav.addObject("gongjiCmtDeleteObj", gongjiCmtDeleteObj);
		mav.setViewName("gongjiViews/gongjiCmtDelete");
		return mav;
	}
	
	@RequestMapping(value = "gongji/cmt_delete_ok.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView gongjiCmtDeleteOk(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		GongjiCommentTO to = new GongjiCommentTO();
		to.setGongji_cmt_seq(Integer.parseInt(request.getParameter("gongji_cmt_seq")));
		int flag = cmtDAO.gongjiCommentDeleteOk(to);
		mav.addObject("flag", flag);
		mav.setViewName("gongjiViews/gongjiCmtDelete_ok");
		return mav;
	}
	
	@RequestMapping(value = "/gongjiSearch", method = {RequestMethod.GET, RequestMethod.POST})
	public JSONArray gongjiSearch(HttpServletRequest request, HttpServletResponse response){
		GongjiTO to = new GongjiTO();
		
		to.setGongji_content(request.getParameter("Gongji_content"));
		to.setGongji_writer(request.getParameter("Gongji_writer"));
		to.setGongji_subject(request.getParameter("Gongji_subject"));
		
		ArrayList<GongjiTO> gongjiSearchLists = dao.gongjiSearch(to);
		JSONArray gongjiSearchArray = new JSONArray();
		
		for(GongjiTO to2 : gongjiSearchLists) {
			JSONObject obj = new JSONObject();
			obj.put("gongji_seq", to2.getGongji_seq());
			obj.put("gongji_writer_seq", to2.getGongji_writer_seq());
			obj.put("gongji_subject", to2.getGongji_subject());
			obj.put("gongji_writer", to2.getGongji_writer());
			obj.put("gongji_content", to2.getGongji_content());
			obj.put("gongji_hit", to2.getGongji_hit());
			obj.put("gongji_cmt_count", to2.getGongji_cmt_count());
			obj.put("gongji_file_size", to2.getGongji_file_size());
			obj.put("gongji_file_name", to2.getGongji_file_name());
			obj.put("gongji_smoke_years", to2.getGongji_smoke_years());
			obj.put("gongji_public", to2.isGongji_public());
			obj.put("gongji_reg_date", to2.getGongji_reg_date());
			gongjiSearchArray.add(obj);
		}
		
		System.out.println(gongjiSearchArray);
		return gongjiSearchArray;
	}
}
