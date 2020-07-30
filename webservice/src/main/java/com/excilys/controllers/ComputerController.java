package com.excilys.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.dto.CompanyDTO;
import com.excilys.dto.ComputerDTO;
import com.excilys.dto.MyLittleDTO;
import com.excilys.mapper.ComputerDtoMapper;
import com.excilys.model.Page;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

@RequestMapping("/computers")
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
	public List<ComputerDTO> getComputers(@Valid @RequestBody MyLittleDTO dto) {
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
		return computers;
	}

	@PutMapping("/computer")
	public void updateComputer(@Valid @RequestBody ComputerDTO computerDto, HttpServletResponse response)
			throws IOException {
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
		} else {
			response.sendError(304, "whoopsie");
		}
	}

	@DeleteMapping("/computer/{selection}")
	public void deleteComputer(@PathVariable List<String> selection, HttpServletResponse response) {
		selection.forEach(computer -> ComputerService.deleteComputer(Integer.parseInt(computer)));
		// response.sendRedirect("/computers/computer");
	}

	@PostMapping("/computer")
	public void addComputer(@RequestBody ComputerDTO computerDto, HttpServletResponse response) throws IOException {
		Map<String, String> messages = new HashMap<String, String>();

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
		} else {
			response.sendError(304, "whoopsie");
		}

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
