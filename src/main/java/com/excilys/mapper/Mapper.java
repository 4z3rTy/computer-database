package com.excilys.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public class Mapper {
	public static Computer map(ResultSet rs) throws SQLException
	{
		Computer c=new Computer();
        	c.setId(rs.getInt("id"));
        	c.setName(rs.getString("name"));
        	LocalDate intro;
			if(( rs.getDate("introduced")) == null)
        	{
        		intro=null;
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
        	}
			else
			{
				Date sqlDate2=(rs.getDate("discontinued"));
				disc=sqlDate2.toLocalDate();
			}
        	c.setC_Id(rs.getInt("company_id"));
	    	c.setIntro(intro);
            c.setDisco(disc);
		return c;
}
		
		public static Company map1(ResultSet rs) throws SQLException
		{
			Company c=new Company();
	        	c.setId(rs.getInt("id"));
	        	c.setName(rs.getString("name"));
	        
			return c;
		}
}