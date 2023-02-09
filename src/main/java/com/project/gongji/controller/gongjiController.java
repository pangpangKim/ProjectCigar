package com.project.gongji.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.project.gongji.dao.GongjiDAO;

@Controller
public class gongjiController {
	
	@Autowired
	private GongjiDAO dao;
}
