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
import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.ui.Page;

/**
 * The Class ComputerS.
 */
public class ComputerS {

	/** The comp DAO. */
	private static ComputerDAO compDAO = new ComputerDAO();

	/** The logger. */
	private static final Logger logger = LoggerFactory.getLogger(ComputerS.class);

	/**
	 * Count.
	 *
	 * @param tbName the tb name
	 * @return the int
	 */
	public int count(String tbName) {
		return compDAO.countDb(tbName);
	}

	/**
	 * Gets the all computer.
	 *
	 * @return the all computer
	 */
	public List<ComputerDTO> getAllComputer() {
		List<Computer> temp = compDAO.viewComputer();
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

	public ComputerDTO getCompDetails(int computerID) throws SQLException, ClassNotFoundException, IOException {
		Computer temp = compDAO.viewCompDetails(computerID);
		ComputerDTO res = ComputerMapper.toDto(temp);
		return res;
	}

	/**
	 * View some computer.
	 *
	 * @param p the p
	 * @return the computer
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 */
	public List<ComputerDTO> viewSomeComputers(Page p) throws SQLException, ClassNotFoundException, IOException {
		logger.debug("Page object initialized", p);
		// page.setMax(count("computer"));
		// page.calcPages();
		List<Computer> temp = compDAO.viewSomeComputers(p);
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
			throws SQLException, ClassNotFoundException, IOException {
		compDAO.updateComputerName(newName, computerID);
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
			throws SQLException, ClassNotFoundException, IOException {
		return compDAO.updateComputerDisc(intr, disc, computerID);
	}

	/**
	 * Update computer.
	 *
	 * @param myComp the my comp
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateComputer(Computer myComp) throws SQLException, ClassNotFoundException, IOException {
		compDAO.updateComputer(myComp);
	}

	/**
	 * Insert computer.
	 *
	 * @param myComp the my comp
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 */
	public void insertComputer(Computer myComp) throws SQLException, ClassNotFoundException, IOException {
		compDAO.insertComputer(myComp);
	}

	/**
	 * Delete computer.
	 *
	 * @param computerID the computer ID
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 */
	public void deleteComputer(int computerID) throws SQLException, ClassNotFoundException, IOException {
		compDAO.deleteComputer(computerID);
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
		List<Computer> temp = compDAO.getSearchId(search, page);
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
		List<Computer> temp = compDAO.getSearchIntro(search, page);
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
		List<Computer> temp = compDAO.getSearchName(search, page);
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
		return compDAO.searchCount(search);
	}

}
