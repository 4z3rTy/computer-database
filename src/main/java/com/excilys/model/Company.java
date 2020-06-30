package com.excilys.model;


// TODO: Auto-generated Javadoc
/**
 * The Class Company.
 */
public class Company {
	
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public int getId()
	{
		return this.id;
	}
	

	public String getName()
	{
		return this.name;
	}


	private int id;
	private String name;
	
	
	private Company(CompanyBuilder builder) {
		this.id=builder.id;
		this.name = builder.name;

	}
	
	public static class CompanyBuilder{
		private int id;
		private String name;
		
		public CompanyBuilder setId(int id)
		{
			this.id=id;
			return this;
		}
		
		public CompanyBuilder setName(String name)
		{
			this.name=name;
			return this;
		
		}
		
		public Company build() {
			return new Company(this);
		}
		
	}		
	
	public String toString()
	{
		return "id=" + id + ", name=" + name;
	}
}
