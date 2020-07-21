package com.excilys.service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
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
		List<ComputerDTO> res = temp.stream().map(computer -> ComputerMapper.toDto(computer))
				.collect(Collectors.toList());
		return res;
	}

	/**
	 * View some computer.
	 *
	 * @param computerID the computer ID
	 * @return the list
	 */

	public ComputerDTO getCompDetails(int computerID) {
		Computer temp = computerDao.viewCompDetails(computerID);
		if (temp.getCompany() == null) {
			Company tempany = new Company.CompanyBuilder().setId(0).setName(null).build();
			temp = new Computer.ComputerBuilder().setId(temp.getId()).setName(temp.getName()).setAny(tempany)
					.setIntro(temp.getIntroduced()).setDisco(temp.getDiscontinued()).build();
		}
		ComputerDTO res = ComputerMapper.toDto(temp);
		return res;
	}

	/**
	 * View some computer.
	 *
	 * @param p the page
	 * @return the computer
	 */
	public List<ComputerDTO> viewSomeComputers(Page p) {
		logger.debug("Page object initialized", p);
		// page.setMax(count("computer"));
		// page.calcPages();
		List<Computer> temp = computerDao.viewSomeComputers(p);
		List<ComputerDTO> res = temp.stream().map(computer -> ComputerMapper.toDto(computer))
				.collect(Collectors.toList());

		return res;
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
	 * @param intr       the intr
	 * @param disc       the disc
	 * @param computerID the computer ID
	 * @return the int
	 */
	public boolean updateComputerDisc(Date intr, Date disc, int computerID) {
		return computerDao.updateComputerDisc(intr, disc, computerID);
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
		List<ComputerDTO> res = temp.stream().map(computer -> ComputerMapper.toDto(computer))
				.collect(Collectors.toList());
		return res;
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
		List<ComputerDTO> res = temp.stream().map(computer -> ComputerMapper.toDto(computer))
				.collect(Collectors.toList());
		return res;
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
		List<ComputerDTO> res = temp.stream().map(computer -> ComputerMapper.toDto(computer))
				.collect(Collectors.toList());
		return res;
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
