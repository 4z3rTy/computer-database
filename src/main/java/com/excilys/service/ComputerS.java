package com.excilys.service;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.ui.Page;

// 
/**
 * The Class ComputerS.
 */
public class ComputerS {

	/** The comp DAO. */
	private static ComputerDAO compDAO = new ComputerDAO();
	
	/** The logger. */
	public static Logger logger = LoggerFactory.getLogger(ComputerS.class);

	/**
	 * Count.
	 *
	 * @param tbName the tb name
	 * @return the int
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public int count(String tbName) {
				return compDAO.countDb(tbName);
	}

	/**
	 * Gets the all computer.
	 *
	 * @return the all computer
	 */
	public List<ComputerDTO> getAllComputer()  {
		List <Computer> temp=compDAO.viewComputer();
		List <ComputerDTO> res= new ArrayList <ComputerDTO>();
		for(int i=0;i<temp.size();i++)
		{
			ComputerDTO t= new ComputerDTO(temp.get(i));
			res.add(t);
		}
		return res;
	}

	/**
	 * View some computer.
	 *
	 * @param computerID the computer ID
	 * @return the list
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */

	public Computer getCompDetails(int computerID) throws SQLException, ClassNotFoundException, IOException {
		return compDAO.viewCompDetails(computerID);
	}

	/**
	 * View some computer.
	 *
	 * @param con the con
	 * @param pageNumber the page number
	 * @return the computer
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public List <ComputerDTO> viewSomeComputers(Page p)
			throws SQLException, ClassNotFoundException, IOException {
		logger.debug("Page object initialized", p);
		//page.setMax(count("computer"));
		//page.calcPages();
		List <Computer> temp=compDAO.viewSomeComputers(p);
		List <ComputerDTO> res= new ArrayList <ComputerDTO>();
		for(int i=0;i<temp.size();i++)
		{
			ComputerDTO t= new ComputerDTO(temp.get(i));
			res.add(t);
		}
		return res;
	}

	/**
	 * Update computer name.
	 *
	 * @param newName the new name
	 * @param computerID the computer ID
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateComputerName(String newName, int computerID)
			throws SQLException, ClassNotFoundException, IOException {
		compDAO.updateComputerName(newName, computerID);
	}

	/**
	 * Update computer disc.
	 *
	 * @param intr the intr
	 * @param disc the disc
	 * @param computerID the computer ID
	 * @return the int
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public int updateComputerDisc(Date intr, Date disc, int computerID)
			throws SQLException, ClassNotFoundException, IOException {
		return compDAO.updateComputerDisc(intr, disc, computerID);
	}

	/**
	 * Insert computer.
	 *
	 * @param computerName the computer name
	 * @param companyID the company ID
	 * @param intro the intro
	 * @param disco the disco
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void insertComputer(String computerName, int companyID, Date intro, Date disco)
			throws SQLException, ClassNotFoundException, IOException {
		compDAO.insertComputer(computerName, companyID, intro, disco);
	}

	/**
	 * Delete computer.
	 *
	 * @param computerID the computer ID
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void deleteComputer(int computerID) throws SQLException, ClassNotFoundException, IOException {
		compDAO.deleteComputer(computerID);
	}

}
