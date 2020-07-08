package com.excilys.dto;


public class ComputerDTO {

	private String id;
	private String name;
	private String intro;
	private String disco;
	private CompanyDTO company;
	/*
	 * private String company_id; private String company_name;
	 */

	@Override
	public String toString() {
		return " [id=" + id + ", name=" + name + ", intro=" + intro + ", disco=" + disco + ", company_name="
				+ company.getName() + "]";
	}

	public String getName() {
		return name;
	}

	public String getIntro() {
		return intro;
	}

	public String getDisco() {
		return disco;
	}

	public String getCompany_id() {
		return company.getId();	
	}

	public String getCompany_name() {
		return company.getName();
	}

	public String getId() {
		return id;
	}

	private ComputerDTO(ComputerDTOBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.intro = builder.intro;
		this.disco = builder.disco;
		this.company=builder.company;
	}

	public static class ComputerDTOBuilder {

		private String id;
		private String name;
		private String intro;
		private String disco;
		private CompanyDTO company;

		public ComputerDTOBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public ComputerDTOBuilder setId(String id) {
			this.id = id;
			return this;
		}

		public ComputerDTOBuilder setIntro(String intro) {

			this.intro = intro;
			return this;
		}

		public ComputerDTOBuilder setDisco(String disco) {

			this.disco = disco;
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
