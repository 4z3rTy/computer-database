package com.excilys.ui;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.excilys.sqlShenanigans.SqlConnector;
import com.excilys.sqlShenanigans.Xeptions;

public class Page {
	 int itemsNb=10;
	 int maxItems;
	 int pageTotal;
	 int currentPage;
	 
	 public Page(int userChoice)
	 {
		 this.currentPage=userChoice;
	 }
	 
	 
	 public int countDb(String tbName) throws SQLException
		{
			Statement stmt =null;
			int count=-1;
			String query =
			        "select COUNT(*) from "+tbName;
			try {
			Connection con=SqlConnector.getInstance();
	        stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        rs.next();
	        count=rs.getInt(1);
			}
			 catch (SQLException e ) {
			    Xeptions.printSQLException(e);
			} finally {
			    if (stmt != null) { stmt.close(); }
			}
	    return count;
	}
	 
	 
	 
	
	public int getAmount()
	{
		return itemsNb;
	}
	
	public void setAmount(int amount)
	{
		this.itemsNb=amount;
	}

	public int getMax()
	{
		return maxItems;
	}
	
	public void setMax(int db_max)
	{
		this.maxItems=db_max;
	}
	
	public int getTotal()
	{
		return pageTotal;
	}
	
	public void setTotal(int p)
	{
		this.pageTotal=p;
	}
	
	public int getPage()
	{
		return currentPage;
	}
	
	public void setPage(int p)
	{
		 this.currentPage=p;
	}
	
	public void calcPages(int items,int max)
	{
		int result=max%items;
		if(result>0)
		{
			result=1;
		}
		else
		{
			result=0;
		}
		this.pageTotal=(max/items)+result;
	}
}
