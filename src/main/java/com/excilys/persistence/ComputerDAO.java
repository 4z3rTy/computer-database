package com.excilys.persistence;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mapper.Mapper;
import com.excilys.model.Computer;
import com.excilys.sqlShenanigans.SqlConnector;
import com.excilys.sqlShenanigans.Xeptions;
import com.excilys.ui.Page;

/**
 * The Class ComputerDAO.
 */
public class ComputerDAO {

	/** The table name. */
	static String tbName = "computer";
	private static final String SELECT_ALL = "select computer.id, computer.name, computer.company_id, introduced, discontinued, company.name from computer LEFT JOIN company ON computer.company_id=company.id ";
	private static final String SELECT_SOME = "SELECT computer.id, computer.name, computer.company_id, introduced, discontinued, company.name from computer LEFT JOIN company ON computer.company_id=company.id ORDER BY id LIMIT ? OFFSET ?";
	private static final String UPDATE_NAME = "UPDATE computer SET name=? WHERE id=?";
	private static final String UPDATE_DATE = "UPDATE computer SET introduced=? , discontinued=? WHERE id=?";
	private static final String DELETE = "DELETE FROM computer WHERE id =?";
	private static final String SELECT_WHERE = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id=? ";
	private static final String COUNT = "SELECT COUNT(*) from " + tbName;
	public static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	public int countDb(String tbName) {
		int count = -2;
		try {
			Connection con = SqlConnector.getInstance();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(COUNT);
			rs.next();
			count = rs.getInt(1);
			stmt.close();
			logger.debug("Connection to the database was terminated");
		} catch (SQLException e) {
			logger.error("Connection to the database could not be established", e);
			Xeptions.printSQLException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * View computer.
	 *
	 * @param con the con
	 * @return the list
	 * @throws SQLException           the SQL exception
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public List<Computer> viewComputer() {

		Statement stmt = null;
		Computer computer = null;
		List<Computer> computers = new ArrayList<Computer>();

		try (Connection con = SqlConnector.getInstance()) {

			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT_ALL);
			while (rs.next()) {
				computer = Mapper.map(rs);
				computers.add(computer);

			}
			stmt.close();
			logger.debug("Connection to the database was terminated");
		} catch (SQLException e) {
			logger.error("Connection to the database could not be established", e);
			Xeptions.printSQLException(e);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return computers;
	}

	/**
	 * View some computer.
	 *
	 * @param con  the con
	 * @param page the page
	 * @return the list
	 * @throws SQLException           the SQL exception
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public List<Computer> viewSomeComputers(Page page) throws SQLException, ClassNotFoundException, IOException {

		PreparedStatement pstmt = null;

		Computer computer = null;
		List<Computer> computers = new ArrayList<Computer>();

		try (Connection con = SqlConnector.getInstance()) {
			pstmt = con.prepareStatement(SELECT_SOME);

			int limit = page.getAmount();
			int offset = (page.getPage() - 1) * page.getAmount();
			pstmt.setInt(1, limit);
			pstmt.setInt(2, offset);

			ResultSet rs = pstmt.executeQuery();
			computer = new Computer();
			while (rs.next()) {
				computer = Mapper.map(rs);
				computers.add(computer);
			}
		} catch (SQLException e) {
			logger.error("Connection to the database could not be established", e);
			Xeptions.printSQLException(e);
		} finally {
			if (pstmt != null) {
				pstmt.close();
				logger.debug("Connection to the database was terminated");
			}
		}
		return computers;
	}

	/**
	 * Update computer name.
	 *
	 * @param con        the con
	 * @param newName    the new name
	 * @param computerID the computer ID
	 * @throws SQLException           the SQL exception
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void updateComputerName(String newName, int computerID)
			throws SQLException, ClassNotFoundException, IOException {

		PreparedStatement pstmt = null;

		try {

			Connection con = SqlConnector.getInstance();
			pstmt = con.prepareStatement(UPDATE_NAME);

			pstmt.setString(1, newName);
			pstmt.setInt(2, computerID);
			pstmt.executeUpdate();

		} finally {
			if (pstmt != null) {
				pstmt.close();
				logger.debug("Connection to the database was terminated");
			}
		}
	}

	/**
	 * Update computer disc.
	 *
	 * @param con        the con
	 * @param intr       the intr
	 * @param disc       the disc
	 * @param computerID the computer ID
	 * @return the int
	 * @throws SQLException           the SQL exception
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public int updateComputerDisc(Date intr, Date disc, int computerID)
			throws SQLException, ClassNotFoundException, IOException {
		int bool = 0;
		PreparedStatement pstmt = null;

		try (Connection con = SqlConnector.getInstance()) {

			pstmt = con.prepareStatement(UPDATE_DATE);
			if (disc.compareTo(intr) > 0) {
				pstmt.setDate(1, intr);
				pstmt.setDate(2, disc);
				pstmt.setInt(3, computerID);
				pstmt.executeUpdate();
				bool = 1;

			} else {
				logger.info(
						"Sorry there seems to be an incoherence with your date format input. Update failed to resolve");
			}
		} finally {
			if (pstmt != null) {
				pstmt.close();
				logger.debug("Connection to the database was terminated");
			}
		}
		return bool;
	}

	/**
	 * Insert computer.
	 *
	 * @param con          the con
	 * @param computerName the computer name
	 * @param companyID    the company ID
	 * @param intro        the intro
	 * @param disco        the disco
	 * @throws SQLException           the SQL exception
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Computer insertComputer(String computerName, int companyID, Date intro, Date disco)
			throws SQLException, ClassNotFoundException, IOException {
		Computer comp = new Computer();
		Statement stmt = null;
		try (Connection con = SqlConnector.getInstance()) {

			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			ResultSet uprs = stmt.executeQuery(SELECT_ALL);

			if (disco.compareTo(intro) > 0) {
				uprs.moveToInsertRow();
				uprs.updateString("NAME", computerName);
				uprs.updateInt("COMPANY_ID", companyID);
				uprs.updateDate("INTRODUCED", intro);
				uprs.updateDate("DISCONTINUED", disco);
				comp = Mapper.map(uprs);
				// uprs.updateInt("ID", computerID);

				uprs.insertRow();
				uprs.beforeFirst();
			} else {
				logger.info("Sorry there seems to be an incoherence with your date format input. Creation was aborted");

			}
		} catch (SQLException e) {
			Xeptions.printSQLException(e);
		} finally {
			if (stmt != null) {
				stmt.close();
				logger.debug("Connection to the database was terminated");
			}
		}
		return comp;
	}

	/**
	 * Delete computer.
	 *
	 * @param con        the con
	 * @param computerID the computer ID
	 * @throws SQLException           the SQL exception
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void deleteComputer(int computerID) throws SQLException, ClassNotFoundException, IOException {

		PreparedStatement pstmt = null;

		try (Connection con = SqlConnector.getInstance()) {

			pstmt = con.prepareStatement(DELETE);
			// "DELETE FROM "+tbName+ "WHERE id =?");

			pstmt.setInt(1, computerID);
			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
				logger.debug("Connection to the database was terminated");
			}
		}
	}

	/**
	 * View comp details.
	 *
	 * @param con        the con
	 * @param computerID the computer ID
	 * @return the computer
	 * @throws SQLException           the SQL exception
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Computer viewCompDetails(int computerID) throws SQLException, ClassNotFoundException, IOException {

		PreparedStatement pstmt = null;
		Computer computer = new Computer();
		try (Connection con = SqlConnector.getInstance()) {

			pstmt = con.prepareStatement(SELECT_WHERE);
			// "SELECT id, name, introduced, discontinued, company_id FROM "+tbName +"WHERE
			// id=? ");

			pstmt.setInt(1, computerID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				computer = Mapper.map(rs);
				// System.out.println(computer.toString());
				logger.info(computer.toString());
			}
		} finally {
			if (pstmt != null) {
				pstmt.close();
				logger.debug("Connection to the database was terminated");
			}
		}
		return computer;
	}

}