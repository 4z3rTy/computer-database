package com.excilys;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
 
import com.excilys.persistence.ComputerDAO;
import com.excilys.service.ComputerS;
import com.excilys.sqlShenanigans.SqlConnector;
import com.excilys.ui.Page;
import com.excilys.model.Computer;
 
public class ComputerDAOTest 
{
	
	  @Mock
	   ComputerDAO mockDAO;
	    
	    @InjectMocks
	    ComputerS mockComputerS;
	    
	    @Mock
	    Statement stmt;
	    
	    @Mock
	    Computer computer;
	    
	    @Mock
	    private Connection c;
	    
	    @Mock
	    ResultSet mockRS;
	    
	    @Mock
	    Page mockPage;
	    
	    @Mock
	    SqlConnector sqlCo;
	
	    
	
	@SuppressWarnings("static-access")
	@Before
	public void setup() throws SQLException, ClassNotFoundException, IOException {
	    MockitoAnnotations.initMocks(this);
	    Mockito.when(sqlCo.getInstance()).thenReturn(c);
    	assertNotNull(c);
    	Mockito.when(c.createStatement()).thenReturn(stmt);
    	Mockito.when(stmt.executeQuery(Matchers.any(String.class))).thenReturn(mockRS);
	}
	 
     
  
    
    
    @Test
    public void getAllComptTest() throws SQLException, ClassNotFoundException, IOException
    {
    	
      List<Computer> all= mockComputerS.getAllComputer();
        try {
        	verify(mockDAO).viewComputer();
        	assertEquals(580,all.size());
        }
        catch (Exception e)
        {
        	fail("Test failed");
        }
    	

    }
}