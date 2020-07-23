package com.excilys.dto;


public class CompanyDTO {

	public CompanyDTO() {}
	private String name;
	private String cId;

	public String getName() {
		return name;
	}


	public String getcId() {
		return cId;
	}


	@Override
	public String toString() {
		return " [name=" + name + ", Id=" + cId + "]";
	}

	
	private CompanyDTO(CompanyDTOBuilder builder) {
		this.cId = builder.cId;
		this.name = builder.name;
	}
	
	public static class CompanyDTOBuilder {

		private String cId;
		private String name;

	
		public CompanyDTOBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public CompanyDTOBuilder setcId(String cId) {
			this.cId = cId;
			return this;
		}
		
		public CompanyDTO build() {
			return new CompanyDTO(this);
		}
	}
}
