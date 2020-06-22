package com.excilys;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
 
import com.excilys.persistence.ComputerDAO;
import com.excilys.service.ComputerS;
import com.excilys.model.Computer;
 
public class ComputerDAOTest 
{
	@Before
	public void setup() {
	    MockitoAnnotations.initMocks(this);
	}
	 
     
    @Mock
    ComputerDAO mockDAO;
    
    @InjectMocks
    ComputerS mockComputerS;
     
     
    @Test
    public void getAllComptTest() throws SQLException
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