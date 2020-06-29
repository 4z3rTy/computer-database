package com.excilys.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.model.Company;
import com.excilys.model.Computer;

// TODO: Auto-generated Javadoc
/**
 * The Class Mapper.
 */
public class Mapper {
	
	/** The logger. */
	public static Logger logger = LoggerFactory.getLogger(Mapper.class);
	
	
	/**
	 * Computer map.
	 *
	 * @param rs the rs
	 * @return the computer
	 * @throws SQLException the SQL exception
	 */
	public static Computer computerMap(ResultSet rs) throws SQLException
	{
		Computer c=new Computer();
		logger.debug("New Computer Object initialized",c);
        	c.setId(rs.getInt("id"));
        	c.setName(rs.getString("name"));
        	LocalDate intro;
			if(( rs.getDate("introduced")) == null)
        	{
        		intro=null;
        		logger.info("Introduced date is set as NULL inside the database");
        	}
			else
			{

				Date sqlDate=(rs.getDate("introduced"));
				intro=sqlDate.toLocalDate();
			}
			
			LocalDate disc;
			if((rs.getDate("discontinued")) == null)
        	{
        		disc=null;
        		logger.info("Discontinued date is set as NULL inside the database");
        	}
			else
			{
				Date sqlDate2=(rs.getDate("discontinued"));
				disc=sqlDate2.toLocalDate();
			}
			Company temp= companyMap(rs);
        	c.setCompany(temp);
	    	c.setIntro(intro);
            c.setDisco(disc);
		return c;
}
	
	
	/**
	 * Pretty map.
	 *
	 * @param rs the rs
	 * @return the computer
	 * @throws SQLException the SQL exception
	 */
	public static Computer prettyMap(ResultSet rs) throws SQLException
	{
		Computer c=new Computer();
		logger.debug("New Computer Object initialized",c);
        	c.setId(rs.getInt("id"));
        	c.setName(rs.getString("name"));
        	LocalDate intro;
			if(( rs.getDate("introduced")) == null)
        	{
        		intro=null;
        		logger.info("Introduced date is set as NULL inside the database");
        	}
			else
			{

				Date sqlDate=(rs.getDate("introduced"));
				intro=sqlDate.toLocalDate();
			}
			
			LocalDate disc;
			if((rs.getDate("discontinued")) == null)
        	{
        		disc=null;
        		logger.info("Discontinued date is set as NULL inside the database");
        	}
			else
			{
				Date sqlDate2=(rs.getDate("discontinued"));
				disc=sqlDate2.toLocalDate();
			}
			Company temp= prettyCompanyMap(rs);
        	c.setCompany(temp);
	    	c.setIntro(intro);
            c.setDisco(disc);
		return c;
}
		
		
	
		/**
		 * Company map.
		 *
		 * @param rs the rs
		 * @return the company
		 * @throws SQLException the SQL exception
		 */
		public static Company companyMap(ResultSet rs) throws SQLException
		{
			Company c=new Company();
			logger.debug("New Company Object initialized",c);
	        	c.setId(rs.getInt("id"));
	        	c.setName(rs.getString("name"));
	        
			return c;
		}
		
		/**
		 * Pretty company map.
		 *
		 * @param rs the rs
		 * @return the company
		 * @throws SQLException the SQL exception
		 */
		public static Company prettyCompanyMap(ResultSet rs) throws SQLException
		{
			Company c=new Company();
			logger.debug("New Company Object initialized",c);
	        	c.setId(rs.getInt("computer.company_id"));
	        	c.setName(rs.getString("company.name"));
	        
			return c;
		}
		
		
		public static Date localToSql(LocalDate d)
		{
			Date sqld=Date.valueOf(d);
			return sqld;
		}
		public static Computer toComputer(ComputerDTO dto)
		{
			int c_id=Integer.parseInt(dto.getCompany_id());		
			DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
			LocalDate i=LocalDate.parse(dto.getIntro(),formatter);
			LocalDate d=LocalDate.parse(dto.getDisco(),formatter);

					
			Computer c=new Computer(c_id, dto.getName(),i,d);
			return c;
		}
		
		public static LocalDate stringToLocal(String d)
		{
			DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
			LocalDate i=LocalDate.parse(d,formatter);
			return i;
		}
}