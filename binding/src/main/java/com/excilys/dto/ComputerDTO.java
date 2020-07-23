package com.excilys.dto;


public class ComputerDTO {

	private String id;
	private String computerName;
	private String introduced;
	private String discontinued;
	private CompanyDTO company;


	public ComputerDTO () {}
	@Override
	public String toString() {
		return " [id=" + id + ", computerName=" + computerName + ", introduced=" + introduced + ", discontinued=" + discontinued + ", companyName="
				+ company.getName() + "]";
	}

	public String getComputerName() {
		return computerName;
	}

	public String getIntroduced() {
		return introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public String getCompanyId() {
		return company.getcId();	
	}

	public String getCompanyName() {
		return company.getName();
	}

	public String getId() {
		return id;
	}
	
	public CompanyDTO getCompany() {
		return company;
	}

	private ComputerDTO(ComputerDTOBuilder builder) {
		this.id = builder.id;
		this.computerName = builder.computerName;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company=builder.company;
	}

	public static class ComputerDTOBuilder {

		private String id;
		private String computerName;
		private String introduced;
		private String discontinued;
		private CompanyDTO company;

		public ComputerDTOBuilder setComputerName(String computerName) {
			this.computerName = computerName;
			return this;
		}

		public ComputerDTOBuilder setId(String id) {
			this.id = id;
			return this;
		}

		public ComputerDTOBuilder setIntroduced(String introduced) {

			this.introduced = introduced;
			return this;
		}

		public ComputerDTOBuilder setDiscontinued(String discontinued) {

			this.discontinued = discontinued;
			return this;
		}

		public ComputerDTOBuilder setCompany(CompanyDTO company) {
			this.company = company;
			return this;
		}


		public ComputerDTO build() {
			return new ComputerDTO(this);
		}
	}

}
