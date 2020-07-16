package com.excilys.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.dto.ComputerDTO;
import com.excilys.dto.MyLittleDTO;
import com.excilys.model.Page;
import com.excilys.service.ComputerService;

@Controller
public class Dashboard {
	
	@Autowired
	private ComputerService service;
	private int sum;
	
	@GetMapping("/dashboard")
	public ModelAndView doGet(MyLittleDTO dto) {

		ModelAndView mv=new ModelAndView("dashboard");
		List<ComputerDTO> compList = null;
		
			Page p = new Page(sum);
			//p.calcPages();
		if (dto.getcurrentPage()!= null) {
			p.setPage(Integer.parseInt(dto.getcurrentPage()));
		}

		if (dto.getpageAmount() != null && dto.getpageAmount() != "") {
			p.setAmount(Integer.parseInt(dto.getpageAmount()));
		}
		p.calcPages();

		try {

			String search = dto.getsearch();
			String searchType = dto.getsearchType();
			if (searchType == null) {

				if (search == null) {
					sum = service.count("computer");
					p.setMax(sum);
					p.calcPages();
					compList = service.viewSomeComputers(p);
				} else {
					sum = service.searchCount(search);
					p.setMax(sum);
					p.calcPages();
				}

				try {
					if (dto.getsearchName() != null) {
						compList = service.getSearchName(search, p);
						searchType = "searchName";
					} else if (dto.getsearchIntro() != null) {
						compList = service.getSearchIntro(search, p);
						searchType = "searchIntro";
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {

				p.setMax(sum);
				p.calcPages();

				switch (searchType) {

				case "":
					compList = service.viewSomeComputers(p);
					break;

				case "searchName":
					compList = service.getSearchName(search, p);
					break;

				case "searchIntro":
					compList = service.getSearchIntro(search, p);
					break;
				}
			}

			mv.getModel().put("sum", sum);
			mv.getModel().put("compList", compList);
			mv.getModel().put("pageTotal", p.getTotal());
			mv.getModel().put("currentPage", p.getPage());
			mv.getModel().put("items", p.getAmount());
			mv.getModel().put("search", search);
			mv.getModel().put("searchType", searchType);
			mv.getModel().put("pageAmount", p.getAmount());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return mv;

	}
	
	@PostMapping("/dashboard")
	public ModelAndView doPost(@RequestParam List<String> selection) {

		ModelAndView mv=new ModelAndView("redirect:dashboard");

		for (String i : selection) {
			try {
				service.deleteComputer(Integer.parseInt(i));
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		}

		return mv;
	}

}
