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
import com.excilys.mapper.ComputerMapper;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

@RequestMapping("/editComputer")
@Controller
public class EditComp {
	@Autowired
	private CompanyService CS;

	@Autowired
	private ComputerService C;


	@GetMapping()
	public ModelAndView doGet(ComputerDTOBuilder dto) {

		ModelAndView mv = new ModelAndView("editComputer");

		String compId = dto.build().getId();
		mv.getModel().put("id", compId);
		List<CompanyDTO> companies = CS.getAllCompanies();
		mv.getModel().put("companies", companies);

		return mv;
	}

	@PostMapping()
	public ModelAndView doPost(ComputerDTOBuilder uterBuilder, CompanyDTOBuilder anyBuilder) {

		ComputerDTO computerDto = uterBuilder.build();
		CompanyDTO companyDto = anyBuilder.build();

		// ModelAndView mv = new ModelAndView("redirect:editComputer?Id="+temp.getId());
		ModelAndView mv = new ModelAndView("editComputer");
		Map<String, String> messages = new HashMap<String, String>();

		String computerId = computerDto.getId();
		String name = computerDto.getComputerName();
		String intro = computerDto.getIntroduced();
		String disco = computerDto.getDiscontinued();
		String companyId = companyDto.getcId();

		CompanyDTO anyDto = new CompanyDTO.CompanyDTOBuilder().setcId(companyId).build();
		ComputerDTO compDto = new ComputerDTO.ComputerDTOBuilder().setId(computerId).setComputerName(name)
				.setIntroduced(intro).setDiscontinued(disco).setCompany(anyDto).build();

		messages = ComputerValidator.validate(compDto, messages);

		if (messages.isEmpty()) {
			messages.put("success", "Update completed successfully!!!!");

			C.updateComputer(ComputerMapper.toComputer(compDto));
		}
		mv.getModel().put("messages", messages);

		return mv;
	}
}
