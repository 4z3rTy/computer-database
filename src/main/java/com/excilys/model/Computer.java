package com.excilys.model;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
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


	private int id;

	private Company company;

	private String name;

	private LocalDate introduced;

	private LocalDate discontinued;

	private Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.company = builder.company;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}

	public static class ComputerBuilder {

		private int id;

		private Company company;

		private String name;

		private LocalDate introduced;

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