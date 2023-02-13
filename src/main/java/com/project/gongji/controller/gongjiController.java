package com.project.gongji.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.project.gongji.dao.GongjiDAO;
import com.project.gongji.to.GongjiTO;

@Controller
public class gongjiController {
	
	@Autowired
	private GongjiDAO dao;
	
	@RequestMapping(value = "api/gongjiSearch", method = {RequestMethod.GET, RequestMethod.POST})
	public JSONArray gongjiSearch(@RequestBody Map<String, Object> paramMap ,HttpServletRequest request, HttpServletResponse response){
		GongjiTO to = new GongjiTO();
		
		to.setGongji_content((String)paramMap.get("Free_content"));
		to.setGongji_writer((String)paramMap.get("Free_writer"));
		to.setGongji_subject((String)paramMap.get("Free_subject"));
		
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
