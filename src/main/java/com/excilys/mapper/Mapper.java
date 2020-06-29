package com.excilys.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public class Mapper {
	
	public static Logger logger = LoggerFactory.getLogger(Mapper.class);
	
	
	public static Computer map(ResultSet rs) throws SQLException
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
			Company temp= map1(rs);
        	c.setCompany(temp);
	    	c.setIntro(intro);
            c.setDisco(disc);
		return c;
}
	
	
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
			Company temp= map2(rs);
        	c.setCompany(temp);
	    	c.setIntro(intro);
            c.setDisco(disc);
		return c;
}
		
		
	
		public static Company map1(ResultSet rs) throws SQLException
		{
			Company c=new Company();
			logger.debug("New Company Object initialized",c);
	        	c.setId(rs.getInt("id"));
	        	c.setName(rs.getString("name"));
	        
			return c;
		}
		
		public static Company map2(ResultSet rs) throws SQLException
		{
			Company c=new Company();
			logger.debug("New Company Object initialized",c);
	        	c.setId(rs.getInt("computer.company_id"));
	        	c.setName(rs.getString("company.name"));
	        
			return c;
		}
}