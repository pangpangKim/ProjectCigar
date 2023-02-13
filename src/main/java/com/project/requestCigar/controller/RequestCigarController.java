package com.project.requestCigar.controller;

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

import com.project.requestCigar.dao.RequestCigarDAO;
import com.project.requestCigar.to.RequestCigarTO;

@Controller
public class RequestCigarController {
	
	@Autowired
	private RequestCigarDAO dao;
	
	@RequestMapping(value = "api/requestSearch", method = {RequestMethod.GET, RequestMethod.POST})
	public JSONArray requestSearch(@RequestBody Map<String, Object>paramMap, HttpServletRequest request, HttpServletResponse response) {
		RequestCigarTO to = new RequestCigarTO();
		to.setRequest_cigar_brand((String)paramMap.get("Request_cigar_brand"));
		to.setRequest_cigar_name((String)paramMap.get("Request_cigar_name"));
		to.setRequest_content((String)paramMap.get("Request_content"));
		to.setRequest_writer((String)paramMap.get("Request_writer"));
		to.setRequest_subject((String)paramMap.get("Request_subject"));
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
