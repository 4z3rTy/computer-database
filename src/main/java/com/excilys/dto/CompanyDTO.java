package com.excilys.dto;


public class CompanyDTO {

	private String name;
	private String id;

	public String getName() {
		return name;
	}


	public String getId() {
		return id;
	}


	@Override
	public String toString() {
		return "CompanyDTO [name=" + name + ", id=" + id + "]";
	}

	
	private CompanyDTO(CompanyDTOBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
	}
	
	public static class CompanyDTOBuilder {

		private String id;
		private String name;

	
		public CompanyDTOBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public CompanyDTOBuilder setId(String id) {
			this.id = id;
			return this;
		}
		
		public CompanyDTO build() {
			return new CompanyDTO(this);
		}
	}
}
