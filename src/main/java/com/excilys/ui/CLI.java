package com.excilys.ui;


import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyS;
import com.excilys.service.ComputerS;

public class CLI {

	  /**
  	 * The main method.
  	 *
  	 * @param args the arguments
  	 * @throws IOException Signals that an I/O exception has occurred.
  	 * @throws SQLException the SQL exception
  	 * @throws ParseException the parse exception
	 * @throws ClassNotFoundException 
  	 */
  	public static void main(String[] args) throws IOException, SQLException ,ParseException, ClassNotFoundException {
	  
	    Scanner sc = new Scanner(System.in);
	    boolean running=true;
		int option = 0;
		int id = 0;
		int c_id=0;
		String name=null;
		String intr = null;
		String disc = null;
		
		CompanyS anyS=new CompanyS();
		ComputerS compS=new ComputerS();
		Logger logger = LoggerFactory.getLogger(CLI.class);
		logger.info("Log4j Enabled");
		
		
		    
		

	    while (running)
	    {
	    	// Display menu graphics
	    	System.out.println("===============================================");
		    System.out.println("|   CDB WONDERFUL CLI (Much options such wow) |");
		    System.out.println("===============================================");
		    System.out.println("| Available Options:                          |");
		    System.out.println("|        1. List all computers                |");
		    System.out.println("|        2. List all companies                |");
		    System.out.println("|        3. Show computer details             |");
		    System.out.println("|        4. Create a computer                 |");
		    System.out.println("|        5. Update a computer                 |");
		    System.out.println("|        6. Delete a computer                 |");
		    System.out.println("|        7. Exit                              |");
		    System.out.println("|        8. List some or all of the computers |");
		    System.out.println("===============================================");
		    System.out.println("");
		    System.out.println(" Please select the (next) option you are interested in:  ");
		    	try {
		    			option=sc.nextInt();
		    		}

			    catch(InputMismatchException e)
				{
			    	sc.next();
			        System.out.print("That’s not an integer => ");
			        System.out.print("");
				}
			    

		   
	    
		
	    
	    switch (option) {
	    
	    
	    case 1:
	      System.out.println("'List all computers' selected ->");
	      System.out.println("");
	      ArrayList <Computer> c=(ArrayList<Computer>) compS.getAllComputer();
	      for(int i=0; i<c.size();i++)
          {
	    	  System.out.println(c.get(i).toString());
          
          }
	 
	      break;
	    
	    
	    case 2:
	    	System.out.println("'List all companies' selected ->");
	    	System.out.println("");
	    	 ArrayList <Company> co=(ArrayList<Company>) anyS.getAllCompanies();
		      for(int i=0; i<co.size();i++)
	          {
		    	  System.out.println(co.get(i).toString());
	          
	          }
	      break;
	    
	    
	    case 3:
	      System.out.println("'Show computer details' selected:");
	      System.out.println("Please indicate which computer you are interested in using its id ->");
	      Scanner three = new Scanner(System.in);
	  	try {
	  		id=three.nextInt();
	  		//three.close();
	  		System.out.println("Attempting to fetch computer details for computer ID="+id);
		    compS.getCompDetails(id); 
		    }

		    catch(InputMismatchException e)
			{
		    	three.next();
		        System.out.println("That’s not an integer => ");
		        System.out.println();
			} 
	      break;
	    
	    
	    case 4:
		      System.out.println("'Create a computer' selected:");
		      System.out.println("Please indicate the desired attributes of the computer you wish to create (name, date introduced (yyyy-MM-dd), date discontinued (yyyy-MM-dd) & company id) ->");
		      Scanner four = new Scanner(System.in);
		      try {
		      name=four.next();
		      intr=four.next(); 
		      disc=four.next();
		      c_id=four.nextInt();
		      }
		      catch(InputMismatchException e)
		      {
		    	  System.out.println("Sorry,there was an issue with the number of inputs. Creation aborted.");
		      }
		      System.out.println("Attempting to create computer with following attributes name=" +name);
		      System.out.println("date introduced=" +intr);
		      System.out.println("date discontinued=" +disc);
		      System.out.println("company ID=" +c_id);
	    	  DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
	    	  try {
	    	  LocalDate date=LocalDate.parse(disc, formatter);
	    	  Date sqlDate=Date.valueOf(date);
	    	  LocalDate date2=LocalDate.parse(intr, formatter);
	    	  Date sqlDate2=Date.valueOf(date2);
	    	  compS.insertComputer(name, c_id, sqlDate2, sqlDate);
	    	  }
	    	  catch(DateTimeParseException e )
	    	  {
	    		  System.out.println("Sorry,there was an issue with the format of either or both of the dates input.");
			        System.out.println();
	    	  }
	    	
		/*      else {
		    	  System.out.println("Sorry,too many arguments. Insertion has failed");
		      }
	    	  */
		      break;
	    
	   
	    case 5:
		      System.out.println("'Update a computer' selected:");
		      System.out.println("Please indicate which computer you wish to update using it's id ->");
		      Scanner five = new Scanner(System.in);
		  	try {
		  		 id=five.nextInt();
		  		}
		  		catch(InputMismatchException e)
				{
			    	five.next();
			        System.out.println("That’s not a valid ID (integer required) => Update Failed");
			        System.out.println();
			        break;
				}
			    	
			    
		  		
		      System.out.println("Please input '1' if you wish to update "+id +"'s Computer name or '2' if you wish to update "+id +"'s discontinued date ->");
		      int subc;
		      subc=five.nextInt();
		      switch(subc)
		      {
		      case 1:
		    	  System.out.println("Please input a new name for Computer id="+id+":");
		    	  Scanner subfive= new Scanner(System.in);
		    	  
		    	  try {
		    	  name=subfive.next();
		    	  compS.updateComputerName(name, id);   
		    	  System.out.println("Your modification has been carried out (hopefully, maybe, probably, definitely...unless "+id+ " didn't even exist to begin with)");
		    	  System.out.println("");
		    	  }
		    	  catch(NoSuchElementException e)
		    	  {
		    		  five.next();
		    		  five.close();
		    		  System.out.println("This computer is not present in the database");
		    	  }
		    		  
		    	  break;
		      case 2:
		    	  System.out.println("Please input a new introduced & discontinued date for Computer "+id+":");
		    	  Scanner five2= new Scanner(System.in);
		    	  intr=five2.next();
		    	  disc=five2.next();
		    	  DateTimeFormatter formatter1= DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		    	  try {
			    	  LocalDate date1=LocalDate.parse(disc, formatter1);
			    	  Date sqlDate1=Date.valueOf(date1);	
			    	  LocalDate date2=LocalDate.parse(intr, formatter1);
			    	  Date sqlDate2=Date.valueOf(date2);
			    	  
			    	  //five2.close();
			    	  if(compS.updateComputerDisc(sqlDate2,sqlDate1, id)==1)
			    	  {
			    	  System.out.println("Your modification has been carried out (hopefully, maybe, probably, definitely)");
			    	  System.out.println("");
			    	  }
		    	  }

		    		  catch(DateTimeParseException e )
			    	  {
			    		  System.out.println("Sorry,there was an issue with the format of your discontinued date input. Update failed");
					        System.out.println();
			    	  }
		    	  
		    	  break;
		      }
		      break;
	   
	    
	    case 6:
		      System.out.println("'Delete a computer' selected:");
		      System.out.println("Please indicate which computer you wish to delete using it's id ->");
		      Scanner six=new Scanner(System.in);
		      try {
		    	  id=six.nextInt();
		    	  //six.close();
			      compS.deleteComputer(id);		
			      System.out.println("Computer " +id+" has been deleted (hopefully, maybe, probably, definitely...unless " +id+" didn't even exist to begin with)");
			      
			  		}
			  		catch(InputMismatchException e)
					{
				    	six.next();
				        System.out.println("That’s not a valid ID (integer required) => Deletion Failed");
				        System.out.println();
				        break;
					}      
		      break;
	   
	    
	    case 7:
	      System.out.println("'Exit' selected...bye bye!");
	      sc.close();
	      
	      running=false;
	      break; 
	      
	    case 8:
	    	boolean notdone=true;
	    	System.out.println("List some or all of the computers in the db ->");
	    	Page p=new Page("computer");
	    	p.calcPages();
	    	Scanner eight=new Scanner(System.in);
	    	while(notdone)
	    	{
	    	System.out.println("Please indicate which page you wish to see:");
		      try {
			  		id=eight.nextInt();
			  		//three.close();
			  		System.out.println("Attempting to display page "+id);
			  		p.setPage(id);
			  		ArrayList <ComputerDTO> com=(ArrayList<ComputerDTO>) compS.viewSomeComputers(p);
				      for(int i=0; i<com.size();i++)
			          {
				    	  System.out.println(com.get(i).toString());
			          
			          }
				    System.out.println();
				    System.out.println("If you wish to exit please input 'exit'");
				    }

				    catch(InputMismatchException e)
		      {
				    	if(eight.next().equals("exit"))
				    	{
				    		notdone=false;
				    		System.out.println("Option exited succesfully.");
				    	}
				    	else
				    	{
				        System.out.println("That’s not an integer => ");
				        System.out.println();
				    	}
					} 
	    	}
		   break;
	    default:
	    	System.out.println("The option you selected is not currently available, please try again :) ");
	    	break;  
	    }
		    	

	    }}
}


