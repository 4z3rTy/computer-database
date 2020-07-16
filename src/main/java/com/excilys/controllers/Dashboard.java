package com.excilys.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Dashboard {
	@RequestMapping("/dashboard")
	public String lol() {
		return "dashboard";
	}
}
