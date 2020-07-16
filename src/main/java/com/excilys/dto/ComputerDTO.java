package com.excilys.dto;


public class ComputerDTO {

	private String Id;
	private String computerName;
	private String introduced;
	private String discontinued;
	private CompanyDTO company;
	/*
	 * private String company_id; private String company_name;
	 */

	public ComputerDTO () {}
	@Override
	public String toString() {
		return " [Id=" + Id + ", computerName=" + computerName + ", introduced=" + introduced + ", discontinued=" + discontinued + ", company_name="
				+ company.getName() + "]";
	}

	public String getcomputerName() {
		return computerName;
	}

	public String getintroduced() {
		return introduced;
	}

	public String getdiscontinued() {
		return discontinued;
	}

	public String getcompanyId() {
		return company.getId();	
	}

	public String getCompany_name() {
		return company.getName();
	}

	public String getId() {
		return Id;
	}

	private ComputerDTO(ComputerDTOBuilder builder) {
		this.Id = builder.Id;
		this.computerName = builder.computerName;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company=builder.company;
	}

	public static class ComputerDTOBuilder {

		private String Id;
		private String computerName;
		private String introduced;
		private String discontinued;
		private CompanyDTO company;

		public ComputerDTOBuilder setcomputerName(String computerName) {
			this.computerName = computerName;
			return this;
		}

		public ComputerDTOBuilder setId(String Id) {
			this.Id = Id;
			return this;
		}

		public ComputerDTOBuilder setintroduced(String introduced) {

			this.introduced = introduced;
			return this;
		}

		public ComputerDTOBuilder setdiscontinued(String discontinued) {

			this.discontinued = discontinued;
			return this;
		}

		public ComputerDTOBuilder setAny(CompanyDTO company) {
			this.company = company;
			return this;
		}


		public ComputerDTO build() {
			return new ComputerDTO(this);
		}
	}

}
