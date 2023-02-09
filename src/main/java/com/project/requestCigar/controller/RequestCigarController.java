package com.project.requestCigar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.project.requestCigar.dao.RequestCigarDAO;

@Controller
public class RequestCigarController {
	
	@Autowired
	private RequestCigarDAO dao;
}
