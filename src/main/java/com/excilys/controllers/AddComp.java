package com.excilys.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AddComp {
	@RequestMapping("/addComputer")
	public String lol() {
		return "addComputer";
	}
	

}