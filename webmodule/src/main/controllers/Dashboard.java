package com.excilys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.dto.ComputerDTO;
import com.excilys.dto.MyLittleDTO;
import com.excilys.model.Page;
import com.excilys.service.ComputerService;

@RequestMapping("/dashboard")
@Controller
public class Dashboard {

	@Autowired
	private ComputerService ComputerService;
	private int sum;

	@GetMapping()
	public ModelAndView doGet(MyLittleDTO dto) {
		ModelAndView mv = new ModelAndView("dashboard");
		List<ComputerDTO> computers = null;

		Page p = updatePage(dto);
		String search = dto.getsearch();
		String searchType = dto.getsearchType();
		if (searchType == null) {

			computers = ifNoSearch(computers, p, search);

			if (dto.getsearchName() != null) {
				computers = ComputerService.getSearchName(search, p);
				searchType = "searchName";
			} else if (dto.getsearchIntro() != null) {
				computers = ComputerService.getSearchIntro(search, p);
				searchType = "searchIntro";
			}

		} else {
			switch (searchType) {
			case "":
				computers = continueCurrentSearch(p);
				break;
			case "searchName":
				computers = newSearchName(p, search);
				break;
			case "searchIntro":
				computers = newSearchIntro(p, search);
				break;
			}
		}
		updateModelView(mv, computers, p, search, searchType);
		return mv;
	}

	@PostMapping()
	public ModelAndView doPost(@RequestParam List<String> selection) {
		ModelAndView mv = new ModelAndView("redirect:dashboard");
		selection.forEach(computer -> ComputerService.deleteComputer(Integer.parseInt(computer)));

		return mv;
	}

	private Page updatePage(MyLittleDTO dto) {
		Page p = new Page(sum);
		if (dto.getcurrentPage() != null) {
			p.setPage(Integer.parseInt(dto.getcurrentPage()));
		}

		if (dto.getpageAmount() != null && dto.getpageAmount() != "") {
			p.setAmount(Integer.parseInt(dto.getpageAmount()));
		}
		p.calcPages();
		return p;
	}

	private void updateModelView(ModelAndView mv, List<ComputerDTO> computers, Page p, String search,
			String searchType) {
		mv.getModel().put("sum", sum);
		mv.getModel().put("compList", computers);
		mv.getModel().put("pageTotal", p.getTotal());
		mv.getModel().put("currentPage", p.getPage());
		mv.getModel().put("items", p.getAmount());
		mv.getModel().put("search", search);
		mv.getModel().put("searchType", searchType);
		mv.getModel().put("pageAmount", p.getAmount());
	}

	private List<ComputerDTO> newSearchIntro(Page p, String search) {
		List<ComputerDTO> computers;
		p.setMax(sum);
		p.calcPages();
		computers = ComputerService.getSearchIntro(search, p);
		return computers;
	}

	private List<ComputerDTO> newSearchName(Page p, String search) {
		List<ComputerDTO> computers;
		p.setMax(sum);
		p.calcPages();
		computers = ComputerService.getSearchName(search, p);
		return computers;
	}

	private List<ComputerDTO> continueCurrentSearch(Page p) {
		List<ComputerDTO> computers;
		p.setMax(sum);
		p.calcPages();
		computers = ComputerService.viewSomeComputers(p);
		return computers;
	}

	private List<ComputerDTO> ifNoSearch(List<ComputerDTO> computers, Page p, String search) {
		if (search == null) {
			sum = ComputerService.count("computer");
			computers = continueCurrentSearch(p);
		} else {
			sum = ComputerService.searchCount(search);
			p.setMax(sum);
			p.calcPages();
		}
		return computers;
	}

}
