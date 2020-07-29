package com.excilys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.dto.CompanyDTO;
import com.excilys.service.CompanyService;

@RequestMapping("/company")
@RestController
public class CompanyController {
	public CompanyController(CompanyService anyService) {
		this.anyService = anyService;
	}

	@Autowired
	private CompanyService anyService;


	@GetMapping()
	public List<CompanyDTO> getCompanies() {

		List<CompanyDTO> companies = anyService.getAllCompanies();

		return companies;
	}
	
	@DeleteMapping()
	public void deleteCompany(int companyId) {
		
		anyService.deleteCompany(companyId);
	}


}
