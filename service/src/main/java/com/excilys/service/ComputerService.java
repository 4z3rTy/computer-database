package com.excilys.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerDtoMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.persistence.ComputerDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class ComputerS.
 */
@Service
public class ComputerService {

	/** The computer dao. */
	@Autowired
	private ComputerDAO computerDao;

	/** The logger. */
	private static final Logger logger = LoggerFactory.getLogger(ComputerService.class);

	/**
	 * Count.
	 *
	 * @param tbName the tb name
	 * @return the int
	 */
	public int count(String tbName) {
		return computerDao.countDb(tbName);
	}

	/**
	 * Gets all the computers.
	 *
	 * @return the all computer
	 */
	public List<ComputerDTO> getAllComputers() {
		List<Computer> temp = computerDao.viewComputer();
		List<ComputerDTO> computers = temp.stream().map(computer -> ComputerDtoMapper.toDto(computer))
				.collect(Collectors.toList());
		return computers;
	}

	/**
	 * View some computer.
	 *
	 * @param computerID the computer ID
	 * @return the list
	 */

	public ComputerDTO getCompDetails(int computerID) {
		Computer tempUter = computerDao.viewCompDetails(computerID);
		ComputerDTO computer = null;
		if (tempUter != null) {
			if (tempUter.getCompany() == null) {
				Company tempAny = new Company.CompanyBuilder().setId(0).setName(null).build();
				tempUter = new Computer.ComputerBuilder().setId(tempUter.getId()).setName(tempUter.getName())
						.setAny(tempAny).setIntro(tempUter.getIntroduced()).setDisco(tempUter.getDiscontinued())
						.build();

			}
			computer = ComputerDtoMapper.toDto(tempUter);
		}
		return computer;
	}

	/**
	 * View some computer.
	 *
	 * @param p the page
	 * @return the computer
	 */
	public List<ComputerDTO> viewSomeComputers(Page p) {
		logger.debug("Page object initialized", p);
		List<Computer> temp = computerDao.viewSomeComputers(p);
		List<ComputerDTO> computers = temp.stream().map(computer -> ComputerDtoMapper.toDto(computer))
				.collect(Collectors.toList());

		return computers;
	}

	/**
	 * Update computer name.
	 *
	 * @param newName    the new name
	 * @param computerID the computer ID
	 */
	public void updateComputerName(String newName, int computerID) {
		computerDao.updateComputerName(newName, computerID);
	}

	/**
	 * Update computer disc.
	 *
	 * @param date2       the intr
	 * @param date1       the disc
	 * @param computerID the computer ID
	 * @return the int
	 */
	public boolean updateComputerDisc(LocalDate date2, LocalDate date1, int computerID) {
		return computerDao.updateComputerDisc(date2, date1, computerID);
	}

	/**
	 * Update computer.
	 *
	 * @param myComp the my comp
	 */
	public void updateComputer(Computer myComp) {
		computerDao.updateComputer(myComp);
	}

	/**
	 * Insert computer.
	 *
	 * @param myComp the my comp
	 */
	public void insertComputer(Computer myComp) {
		computerDao.insertComputer(myComp);
	}

	/**
	 * Delete computer.
	 *
	 * @param computerID the computer ID
	 */
	public void deleteComputer(int computerID) {
		computerDao.deleteComputer(computerID);
	}

	/**
	 * Gets the search id.
	 *
	 * @param search the search
	 * @param page   the page
	 * @return the search id
	 */
	public List<ComputerDTO> getSearchId(String search, Page page) {
		List<Computer> temp = computerDao.getSearchId(search, page);
		List<ComputerDTO> computerFound = temp.stream().map(computer -> ComputerDtoMapper.toDto(computer))
				.collect(Collectors.toList());
		return computerFound;
	}

	/**
	 * Gets the search intro.
	 *
	 * @param search the search
	 * @param page   the page
	 * @return the search intro
	 */
	public List<ComputerDTO> getSearchIntro(String search, Page page) {
		List<Computer> temp = computerDao.getSearchIntro(search, page);
		List<ComputerDTO> computerFound = temp.stream().map(computer -> ComputerDtoMapper.toDto(computer))
				.collect(Collectors.toList());
		return computerFound;
	}

	/**
	 * Gets the search name.
	 *
	 * @param search the search
	 * @param page   the page
	 * @return the search name
	 */
	public List<ComputerDTO> getSearchName(String search, Page page) {
		List<Computer> temp = computerDao.getSearchName(search, page);
		List<ComputerDTO> computerFound = temp.stream().map(computer -> ComputerDtoMapper.toDto(computer))
				.collect(Collectors.toList());
		return computerFound;
	}

	/**
	 * Search count.
	 *
	 * @param search the search
	 * @return the int
	 */
	public int searchCount(String search) {
		return computerDao.searchCount(search);
	}

}
