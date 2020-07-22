package com.excilys.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.dto.CompanyDTO;
import com.excilys.dto.CompanyDTO.CompanyDTOBuilder;
import com.excilys.dto.ComputerDTO;
import com.excilys.dto.ComputerDTO.ComputerDTOBuilder;
import com.excilys.dto.MyLittleDTO;
import com.excilys.mapper.ComputerDtoMapper;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

@RequestMapping("/addComputer")
@Controller
public class AddComp {

	@Autowired
	private CompanyService anyService;
	@Autowired
	private ComputerService uterService;

	@GetMapping()
	public ModelAndView doGet(MyLittleDTO dto) {

		ModelAndView mv = new ModelAndView("addComputer");
		List<CompanyDTO> companies = anyService.getAllCompanies();
		mv.getModel().put("companies", companies);

		return mv;
	}

	@PostMapping()
	public ModelAndView doPost(ComputerDTOBuilder uterBuilder, CompanyDTOBuilder anyBuilder) {
		Map<String, String> messages = new HashMap<String, String>();
		ModelAndView mv = new ModelAndView("addComputer");

		ComputerDTO computerDto = uterBuilder.build();
		CompanyDTO companyDto = anyBuilder.build();

		String name = computerDto.getComputerName();
		String intro = computerDto.getIntroduced();
		String disco = computerDto.getDiscontinued();
		String companyId = companyDto.getcId();

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

			uterService.insertComputer(ComputerDtoMapper.toComputerAdd(uterDto));
		}

		mv.getModel().put("messages", messages);

		return mv;
	}
}
