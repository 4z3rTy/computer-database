package com.excilys.controllers;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.dto.CompanyDTO;
import com.excilys.dto.CompanyDTO.CompanyDTOBuilder;
import com.excilys.dto.ComputerDTO;
import com.excilys.dto.ComputerDTO.ComputerDTOBuilder;
import com.excilys.mapper.ComputerMapper;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

@Controller
public class EditComp {
	@Autowired
	private CompanyService CS;

	@Autowired
	private ComputerService C;

	private static final Logger logger = LoggerFactory.getLogger(EditComp.class);

	@GetMapping("/editComputer")
	public ModelAndView doGet(ComputerDTOBuilder dto) {

		ModelAndView mv = new ModelAndView("editComputer");

		String compId = dto.build().getId();
		mv.getModel().put("id", compId);
		try {
			List<CompanyDTO> companies = CS.getAllCompanies();
			mv.getModel().put("companies", companies);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mv;
	}

	@PostMapping("/editComputer")
	public ModelAndView doPost(ComputerDTOBuilder dto, CompanyDTOBuilder fyoudto) {

		ComputerDTO temp=dto.build();
		CompanyDTO fyoutemp=fyoudto.build();
		
		//ModelAndView mv = new ModelAndView("redirect:editComputer?Id="+temp.getId());
		ModelAndView mv = new ModelAndView("editComputer");
		Map<String, String> messages = new HashMap<String, String>();

		String computerId = temp.getId();
		String name = temp.getComputerName();
		String intro = temp.getIntroduced();
		String disco = temp.getDiscontinued();
		//String companyId = temp.getCompanyId();
		String companyId=fyoutemp.getcId();

		CompanyDTO anyDto = new CompanyDTO.CompanyDTOBuilder().setcId(companyId).build();
		ComputerDTO compDto = new ComputerDTO.ComputerDTOBuilder().setId(computerId).setComputerName(name)
				.setIntroduced(intro).setDiscontinued(disco).setCompany(anyDto).build();

		messages = ComputerValidator.validate(compDto, messages);

		try {

			if (messages.isEmpty()) {
				messages.put("success", "Update completed successfully!!!!");

				C.updateComputer(ComputerMapper.toComputer(compDto));
			}
		} catch (SQLException e) {
			logger.error("Update could not go through.");
			e.printStackTrace();
		}
		mv.getModel().put("messages", messages);

		return mv;
	}
}
