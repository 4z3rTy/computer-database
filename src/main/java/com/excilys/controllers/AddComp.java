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
import com.excilys.dto.MyLittleDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

@Controller
public class AddComp {

	@Autowired
	private CompanyService CS;
	@Autowired
	private ComputerService C;
	private static final Logger logger = LoggerFactory.getLogger(AddComp.class);
	
	@GetMapping("/addComputer")
	public ModelAndView doGet(MyLittleDTO dto){

		ModelAndView mv=new ModelAndView("addComputer");
		try {
			List<CompanyDTO> compList = CS.getAllCompanies();
			mv.getModel().put("compList", compList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	
	@PostMapping("/addComputer")
	public ModelAndView doPost(ComputerDTO dto) {
		Map<String, String> messages = new HashMap<String, String>();
		ModelAndView mv=new ModelAndView("addComputer");
		
		String name = dto.getcomputerName();
		String intro = dto.getintroduced();
		String disco = dto.getdiscontinued();
		String companyId = dto.getcompanyId();

		CompanyDTO anyDto = new CompanyDTO.CompanyDTOBuilder().setId(companyId).build();
		ComputerDTO compDto = new ComputerDTO.ComputerDTOBuilder().setcomputerName(name).setintroduced(intro).setdiscontinued(disco)
				.setAny(anyDto).build();

		messages = ComputerValidator.validate(compDto, messages);
		
		try {
			if (messages.isEmpty()) {
				messages.put("success", "Insertion completed successfully!!!!");
			
			C.insertComputer(ComputerMapper.toComputer(compDto));
			}

		} catch (SQLException e) {
			logger.error("Insertion did not go through.", e);
			e.printStackTrace();
		}

		mv.getModel().put("messages", messages);
		
		return mv;
	}
}
