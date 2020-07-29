package com.excilys.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.dto.CompanyDTO;
import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerDtoMapper;
import com.excilys.model.Page;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

@RestController
public class ComputerController {

	@Autowired
	private ComputerService ComputerService;
	private int sum;

	
	@GetMapping("/computer/{Id}")
	public ComputerDTO getComputer(@PathVariable int Id) {

		ComputerDTO computerDto = ComputerService.getCompDetails(Id);

		return computerDto;
	}
	
	@GetMapping("/computer")
	public List<ComputerDTO> getComputers(@RequestParam(value = "currentPage", required = false) String currentPage,
			@RequestParam(value = "pageAmount", required = false) String pageAmount,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "searchName", required = false) String searchName,
			@RequestParam(value = "searchIntro", required = false) String searchIntro,
			@RequestParam(value = "searchType", required = false) String searchType) {

		List<ComputerDTO> computers = null;

		Page p = updatePage(currentPage, pageAmount);

		if (searchType == null) {
			computers = ifNoSearch(computers, p, search);

			if (searchName != null) {
				computers = ComputerService.getSearchName(search, p);
				searchType = "searchName";
			} else if (searchIntro != null) {
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
		return computers;
	}


	@PostMapping("/computer/update/{id}")
	public void updateComputer(@RequestBody ComputerDTO computerDto,HttpServletResponse response) {
		Map<String, String> messages = new HashMap<String, String>();

		String computerId = computerDto.getId();
		String name = computerDto.getComputerName();
		String intro = computerDto.getIntroduced();
		String disco = computerDto.getDiscontinued();
		String companyId = computerDto.getCompanyId();

		CompanyDTO anyDto = new CompanyDTO.CompanyDTOBuilder().setcId(companyId).build();
		ComputerDTO compDto = new ComputerDTO.ComputerDTOBuilder().setId(computerId).setComputerName(name)
				.setIntroduced(intro).setDiscontinued(disco).setCompany(anyDto).build();

		messages = ComputerValidator.validate(compDto, messages);

		if (messages.isEmpty()) {
			messages.put("success", "Update completed successfully!!!!");
			ComputerService.updateComputer(ComputerDtoMapper.toComputerEdit(compDto));
		}

		/*
		 * mv.getModel().put("messages", messages); 
		 * mv.getModel().put("id", computerId);
		 * mv.getModel().put("companies", anyService.getAllCompanies());
		 */
	}
	
	@PostMapping("/computer/delete/{id}")
	public void deleteComputer(@RequestParam List<String> selection, HttpServletResponse response)
			throws IOException {
		selection.forEach(computer -> ComputerService.deleteComputer(Integer.parseInt(computer)));
		response.sendRedirect("dashboard");
	}
	
	@PostMapping("/computer/add")
	public void addComputer(@RequestBody ComputerDTO computerDto, HttpServletResponse response) {
		Map<String, String> messages = new HashMap<String, String>();
		// ModelAndView mv = new ModelAndView("addComputer");

		String name = computerDto.getComputerName();
		String intro = computerDto.getIntroduced();
		String disco = computerDto.getDiscontinued();
		String companyId = computerDto.getCompanyId();

		ComputerDTO uterDto;
		if (!companyId.equals("0")) {
			CompanyDTO anyDto = new CompanyDTO.CompanyDTOBuilder().setcId(companyId).build();
			uterDto = new ComputerDTO.ComputerDTOBuilder().setComputerName(name).setIntroduced(intro)
					.setDiscontinued(disco).setCompany(anyDto).build();
		} else {
			uterDto = new ComputerDTO.ComputerDTOBuilder().setComputerName(name).setIntroduced(intro)
					.setDiscontinued(disco).build();
		}
		messages = ComputerValidator.validate(uterDto, messages);

		if (messages.isEmpty()) {
			messages.put("success", "Insertion completed successfully!!!!");
			ComputerService.insertComputer(ComputerDtoMapper.toComputerAdd(uterDto));
		}

		/*  mv.getModel().put("messages", messages); */
	}

	/*
	 * private void updateModelView(ModelAndView mv, List<ComputerDTO> computers,
	 * Page p, String search, String searchType) { mv.getModel().put("sum", sum);
	 * mv.getModel().put("compList", computers); mv.getModel().put("pageTotal",
	 * p.getTotal()); mv.getModel().put("currentPage", p.getPage());
	 * mv.getModel().put("items", p.getAmount()); mv.getModel().put("search",
	 * search); mv.getModel().put("searchType", searchType);
	 * mv.getModel().put("pageAmount", p.getAmount()); }
	 */
	
	private Page updatePage(String currentPage, String pageAmount) {
		Page p = new Page(sum);
		if (currentPage != null) {
			p.setPage(Integer.parseInt(currentPage));
		}

		if (pageAmount != null && pageAmount != "") {
			p.setAmount(Integer.parseInt(pageAmount));
		}
		p.calcPages();
		return p;
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
