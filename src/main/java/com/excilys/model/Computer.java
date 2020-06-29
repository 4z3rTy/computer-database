package com.excilys.model;


import java.time.LocalDate;

/**
 * The Class Computer.
 */
public class Computer {

		/** The id. */
		private int id;
		
		/** The company id. */
		//private int company_id;
		private Company company;
		
		/** The name. */
		private String name;
		
		/** The introduced. */
		private LocalDate introduced;
		
		/** The discontinued. */
		private LocalDate discontinued;
		
		
	
		
	/**
	 * Instantiates a new computer.
	 */
	public Computer() {}
	
	/**
	 * Instantiates a new computer.
	 *
	 * @param id the id
	 */
	public Computer(int id) {
		this.id=id;
		introduced =LocalDate.now();
	}		
	
	/**
	 * Instantiates a new computer.
	 *
	 * @param id the id
	 * @param name the name
	 */
	public Computer(int id, String name, LocalDate intro, LocalDate disco, Company company )
	{
		this.id=id;
		this.name=name;
		this.introduced=intro;
		this.discontinued=disco;
		//this.company_id=c_id;
		this.company=company;
	}
	
	
	
	
	public Computer(int i, String string, LocalDate intro2, LocalDate disco2) {
		this.id=i;
		this.name=string;
		this.introduced=intro2;
		this.discontinued=disco2;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId()
	{
		return this.id;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Gets the intro.
	 *
	 * @return the intro
	 */
	public LocalDate getIntro()
	{
		return this.introduced;
	}
	
	/**
	 * Gets the disco.
	 *
	 * @return the disco
	 */
	public LocalDate getDisco()
	{
		return this.discontinued;
	}
	
	/**
	 * Gets the c id.
	 *
	 * @return the c id
	 */
	public String getCompany()
	{
		//return this.company_id;
		return this.company.getName();
	}
	
	public int getCompanyId()
	{
		//return this.company_id;
		return this.company.getId();
	}
	
	
	
	
	/**
	 * Sets the id.
	 *
	 * @param newId the new id
	 */
	public void setId(int newId)
	{
		// Add Exception to prevent setting the id to NULL
		this.id=newId;
		
	}
	
	/**
	 * Sets the c id.
	 *
	 * @param newId the new c id
	 */
	public void setCompany(Company company)
	{
		// Add Exception to prevent setting the id to NULL
		//this.company_id=newId;
		this.company=company;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param newName the new name
	 */
	public void setName(String newName)
	{
		this.name=newName;
		
	}
	
	/**
	 * Sets the disco.
	 *
	 * @param newDate the new disco
	 */
	public void setDisco(LocalDate newDate)
	{
		this.discontinued=newDate;
	}
	
	/**
	 * Sets the intro.
	 *
	 * @param newDate the new intro
	 */
	public void setIntro(LocalDate newDate)
	{
		this.introduced=newDate;
	}
	
	public String toString()
	{
		return "id=" + id + ", name=" + name + ", introduced="
				+ introduced + ", discontinued=" + discontinued + ", company="
				+(this.company.getName());
	}
}