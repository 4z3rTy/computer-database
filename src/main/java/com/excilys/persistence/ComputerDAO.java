package com.excilys.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
	static String tbName="computer";
	private static final String SELECT_ALL="select id, name, company_id, introduced, discontinued from "+ ComputerDAO.tbName;
	private static final String SELECT_SOME="SELECT * FROM "+ComputerDAO.tbName+ " ORDER BY id LIMIT ? OFFSET ?";
	private static final String UPDATE="UPDATE computer SET name=? WHERE id=?";
	private static final String DELETE="DELETE FROM computer WHERE id =?";
	private static final String SELECT_WHERE="SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id=? ";
	
	/**
	 * View computer.
	 *
	 * @param con the con
	 * @return the list
	 * @throws SQLException the SQL exception
	 */
	public List<Computer> viewComputer() throws SQLException {
		
		    Statement stmt = null;
		    Computer computer=null;
		    List<Computer> computers = new ArrayList<Computer>();

		    try(Connection con=SqlConnector.getInstance()){
		    	
		        stmt = con.createStatement();
		        ResultSet rs = stmt.executeQuery(SELECT_ALL);
		        while (rs.next()) {
		        	computer=Mapper.map(rs);
		            computers.add(computer);
		
		    }}
		     catch (SQLException e ) {
		        Xeptions.printSQLException(e);
		    } finally {
		        if (stmt != null) { stmt.close(); }
		    }
		    return computers;
	}
	
	
	
	/**
	 * View some computer.
	 *
	 * @param con the con
	 * @param page the page
	 * @return the list
	 * @throws SQLException the SQL exception
	 */
	public Computer viewSomeComputer(Page page) throws SQLException {
		
		 PreparedStatement pstmt = null;   
	    
	    Computer computer=new Computer();

	    try(Connection con=SqlConnector.getInstance()) {
	        pstmt = con.prepareStatement(SELECT_SOME);
	        
	        int limit=page.getAmount();
	        int offset=(page.getPage()-1)*page.getAmount();
	        pstmt.setInt(1, limit);
	        pstmt.setInt(2, offset);
	        
	        
	        ResultSet rs = pstmt.executeQuery();
	        computer=new Computer();
	        while (rs.next()) {
	        	computer=Mapper.map(rs);
	        }
	    } catch (SQLException e ) {
	        Xeptions.printSQLException(e);
	    } finally {
	        if (pstmt != null) { pstmt.close(); }
	    }
	    return computer;
}
	
	
    /**
     * Update computer name.
     *
     * @param con the con
     * @param newName the new name
     * @param computerID the computer ID
     * @throws SQLException the SQL exception
     */
    public void updateComputerName(String newName, int computerID)
        throws SQLException {


        PreparedStatement pstmt = null;   
      
        try{
         
        	Connection con=SqlConnector.getInstance();
            pstmt = con.prepareStatement(
                        "UPDATE computer SET name=? WHERE id=?");

            pstmt.setString(1, newName);
            pstmt.setInt(2, computerID);
            pstmt.executeUpdate();
            
            
         
        }
        finally {
            if (pstmt != null) pstmt.close();
        }
    }
    
    /**
     * Update computer disc.
     *
     * @param con the con
     * @param intr the intr
     * @param disc the disc
     * @param computerID the computer ID
     * @return the int
     * @throws SQLException the SQL exception
     */
    public int updateComputerDisc(Date intr, Date disc, int computerID)
            throws SQLException {
    		int bool=0;
            PreparedStatement pstmt = null;   
          
            try(Connection con=SqlConnector.getInstance()){
             
                pstmt = con.prepareStatement(UPDATE);

                if(disc.compareTo(intr)>0)
                {
                pstmt.setDate(1, intr);
                pstmt.setDate(2, disc);
                pstmt.setInt(3, computerID);
                pstmt.executeUpdate();
                bool=1;
                
                }
                else
                {
                	System.out.println("Sorry there seems to be an incoherence with your dates input. Creation aborted");
                }
            }
            finally {
                if (pstmt != null) pstmt.close();
            }
            return bool;
        }
    
    
    /**
     * Insert computer.
     *
     * @param con the con
     * @param computerName the computer name
     * @param companyID the company ID
     * @param intro the intro
     * @param disco the disco
     * @throws SQLException the SQL exception
     */
    public  Computer insertComputer(String computerName, int companyID, Date intro, Date disco)
    		throws SQLException {
    		Computer comp = new Computer();
    		Statement stmt=null;
    		try(Connection con=SqlConnector.getInstance()){
    			
	    		stmt = con.createStatement(
	    		ResultSet.TYPE_SCROLL_SENSITIVE,
	    		ResultSet.CONCUR_UPDATABLE);
	
	    		ResultSet uprs = stmt.executeQuery(SELECT_ALL);

    		if(disco.compareTo(intro)>0)
    		{
    		uprs.moveToInsertRow();
    		uprs.updateString("NAME", computerName);
    		uprs.updateInt("COMPANY_ID", companyID);
    		uprs.updateDate("INTRODUCED", intro);
    		uprs.updateDate("DISCONTINUED", disco);
    		comp=Mapper.map(uprs);
    		//uprs.updateInt("ID", computerID);

    		uprs.insertRow();
    		uprs.beforeFirst();
    		} 
    		else
    			{
    			System.out.println("Sorry there seems to be an incoherence with your dates input. Creation aborted");

    			}
    		}
    			catch (SQLException e ) {
    		Xeptions.printSQLException(e);
    		} finally {
    		if (stmt != null) { stmt.close(); 
    					}}
			return comp;}
    
    /**
     * Delete computer.
     *
     * @param con the con
     * @param computerID the computer ID
     * @throws SQLException the SQL exception
     */
    public void deleteComputer(int computerID)
            throws SQLException {
      
            PreparedStatement pstmt = null;   
          
            try(Connection con=SqlConnector.getInstance()) {
                
                pstmt = con.prepareStatement(DELETE);
            		//		"DELETE FROM "+tbName+ "WHERE id =?");

                pstmt.setInt(1, computerID);
                pstmt.executeUpdate();
            }
            finally {
                if (pstmt != null) pstmt.close();
            }
        }
    

    /**
     * View comp details.
     *
     * @param con the con
     * @param computerID the computer ID
     * @return the computer
     * @throws SQLException the SQL exception
     */
    public Computer viewCompDetails(int computerID) throws SQLException {

       
        PreparedStatement pstmt = null;   
        Computer computer = new Computer();
        try(Connection con=SqlConnector.getInstance()) {

            	pstmt = con.prepareStatement(SELECT_WHERE);
                   //     "SELECT id, name, introduced, discontinued, company_id FROM "+tbName +"WHERE id=? ");

            	pstmt.setInt(1, computerID); 
    	        ResultSet rs = pstmt.executeQuery();
    	        
    	        while (rs.next()) {
    	        	computer=Mapper.map(rs);
    	        	System.out.println(computer.toString());
    	        				}
        }
        finally {
            if (pstmt != null) pstmt.close();
        }
        return computer;
 }
    
}