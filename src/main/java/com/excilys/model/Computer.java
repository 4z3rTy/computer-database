package com.excilys.model;

import java.time.LocalDate;

/**
 * The Class Computer.
 */
public class Computer {

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

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the intro.
	 *
	 * @return the intro
	 */
	public LocalDate getIntro() {
		return this.introduced;
	}

	/**
	 * Gets the disco.
	 *
	 * @return the disco
	 */
	public LocalDate getDisco() {
		return this.discontinued;
	}

	/**
	 * Gets the c id.
	 *
	 * @return the c id
	 */
	public String getCompany() {
		// return this.company_id;
		return this.company.getName();
	}

	public int getCompanyId() {
		// return this.company_id;
		return this.company.getId();
	}

	public String toString() {
		return "id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company=" + (this.company.getName());
	}

}