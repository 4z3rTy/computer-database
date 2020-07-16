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
import com.excilys.dto.ComputerDTO;
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
	public ModelAndView doGet(ComputerDTO dto) {

		ModelAndView mv = new ModelAndView("editComputer");

		String compId = dto.getId();
		mv.getModel().put("id", compId);
		try {
			List<CompanyDTO> compList = CS.getAllCompanies();
			mv.getModel().put("compList", compList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mv;
	}

	@PostMapping("/editComputer")
	public ModelAndView doPost(ComputerDTO dto) {

		ModelAndView mv = new ModelAndView("editComputer");
		Map<String, String> messages = new HashMap<String, String>();

		
		String computerId = dto.getId();
		String name = dto.getcomputerName();
		String intro = dto.getintroduced();
		String disco = dto.getdiscontinued();
		String companyId = dto.getcompanyId();

		CompanyDTO anyDto = new CompanyDTO.CompanyDTOBuilder().setId(companyId).build();
		ComputerDTO compDto = new ComputerDTO.ComputerDTOBuilder().setId(computerId).setcomputerName(name)
				.setintroduced(intro).setdiscontinued(disco).setAny(anyDto).build();

		messages = ComputerValidator.validate(compDto, messages);

		try {

			if (messages.isEmpty()) {
				messages.put("success", "Updated completed successfully!!!!");

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
