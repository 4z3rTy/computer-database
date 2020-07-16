package com.excilys.service;

import java.io.IOException;

import java.sql.Date;
import java.sql.SQLException;
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
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 */

	public ComputerDTO getCompDetails(int computerID) throws SQLException {
		Computer temp = computerDao.viewCompDetails(computerID);
		if(temp.getCompany()==null)
		{
			Company tempany=new Company.CompanyBuilder().setId(0).setName(null).build();
			temp=new Computer.ComputerBuilder().setId(temp.getId()).setName(temp.getName()).setAny(tempany)
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
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 */
	public List<ComputerDTO> viewSomeComputers(Page p) throws SQLException {
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
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 */
	public void updateComputerName(String newName, int computerID)
			throws SQLException {
		computerDao.updateComputerName(newName, computerID);
	}

	/**
	 * Update computer disc.
	 *
	 * @param intr       the intr
	 * @param disc       the disc
	 * @param computerID the computer ID
	 * @return the int
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 */
	public boolean updateComputerDisc(Date intr, Date disc, int computerID)
			throws SQLException {
		return computerDao.updateComputerDisc(intr, disc, computerID);
	}

	/**
	 * Update computer.
	 *
	 * @param myComp the my comp
	 * @return 
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateComputer(Computer myComp) throws SQLException {
		computerDao.updateComputer(myComp);
	}

	/**
	 * Insert computer.
	 *
	 * @param myComp the my comp
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 */
	public void insertComputer(Computer myComp) throws SQLException {
		computerDao.insertComputer(myComp);
	}

	/**
	 * Delete computer.
	 *
	 * @param computerID the computer ID
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 */
	public void deleteComputer(int computerID) throws SQLException {
		computerDao.deleteComputer(computerID);
	}

	/**
	 * Gets the search id.
	 *
	 * @param search the search
	 * @param page the page
	 * @return the search id
	 * @throws SQLException the SQL exception
	 */
	public List<ComputerDTO> getSearchId(String search, Page page) throws SQLException {
		List<Computer> temp = computerDao.getSearchId(search, page);
		List<ComputerDTO> res = temp.stream().map(computer -> ComputerMapper.toDto(computer))
				.collect(Collectors.toList());
		return res;
	}

	/**
	 * Gets the search intro.
	 *
	 * @param search the search
	 * @param page the page
	 * @return the search intro
	 * @throws SQLException the SQL exception
	 */
	public List<ComputerDTO> getSearchIntro(String search, Page page) throws SQLException {
		List<Computer> temp = computerDao.getSearchIntro(search, page);
		List<ComputerDTO> res = temp.stream().map(computer -> ComputerMapper.toDto(computer))
				.collect(Collectors.toList());
		return res;
	}

	/**
	 * Gets the search name.
	 *
	 * @param search the search
	 * @param page the page
	 * @return the search name
	 * @throws SQLException the SQL exception
	 */
	public List<ComputerDTO> getSearchName(String search, Page page) throws SQLException {
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
