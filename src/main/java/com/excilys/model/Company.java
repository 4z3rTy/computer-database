package com.excilys.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Company.
 */
public class Company {
	
	/** The id. */
	private int id;
	
	/** The name. */
	private String name;
	
	
	/**
	 * Instantiates a new company.
	 */
	public Company() {}
	
	/**
	 * Instantiates a new company.
	 *
	 * @param id the id
	 */
	public Company(int id) {
		this.id=id;
	}		
	
	/**
	 * Instantiates a new company.
	 *
	 * @param id the id
	 * @param name the name
	 */
	public Company(int id, String name)
	{
		this.id=id;
		this.name=name;
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
	 * Sets the name.
	 *
	 * @param newName the new name
	 */
	public void setName(String newName)
	{
		this.name=newName;
		
	}
	
	public String toString()
	{
		return "id=" + id + ", name=" + name;
	}
}
