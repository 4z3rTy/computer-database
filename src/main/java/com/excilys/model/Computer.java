package com.excilys.model;

import java.time.LocalDate;

/**
 * The Class Computer.
 */
public class Computer {

	public int getId() {
		return id;
	}

	public Company getCompany() {
		return company;
	}

	public String getCompanyName() {
		return company.getName();
	}
	
	public int getCompanyId() {
		return company.getId();
	}
	
	public String getName() {
		return name;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setName(String name) {
		this.name = name;
	}

	/** The id. */
	private int id;

	/** The company id. */
	// private int company_id;
	private Company company;

	/** The name. */
	private String name;

	/** The introduced. */
	private LocalDate introduced;

	/** The discontinued. */
	private LocalDate discontinued;

	private Computer(ComputerBuilder builder) {
		this.id=builder.id;
		this.company = builder.company;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}

	public static class ComputerBuilder {
		/** The id. */
		private int id;

		/** The company id. */
		// private int company_id;
		private Company company;

		/** The name. */
		private String name;

		/** The introduced. */
		private LocalDate introduced;

		/** The discontinued. */
		private LocalDate discontinued;	
		


		public ComputerBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public ComputerBuilder setId(int id) {
			this.id = id;
			return this;
		}
		
		public ComputerBuilder setIntro(LocalDate intro) {
			this.introduced = intro;
			return this;
		}

		public ComputerBuilder setDisco(LocalDate disco) {
			this.discontinued = disco;
			return this;
		}

		public ComputerBuilder setAny(Company any) {
			this.company = any;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}
	}

	public String toString() {
		return "id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company=" + (this.company.getName());
	}

}