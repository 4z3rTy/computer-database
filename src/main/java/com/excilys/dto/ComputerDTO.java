package com.excilys.dto;


public class ComputerDTO {

	private String id;
	private String name;
	private String intro;
	private String disco;
	private String company_id;
	private String company_name;

	@Override
	public String toString() {
		return " [id=" + id + ", name=" + name + ", intro=" + intro + ", disco=" + disco + ", company_name="
				+ company_name + "]";
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
		return company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/*
	 * public ComputerDTO(Computer computer) {
	 * this.setId(String.valueOf(computer.getId())); this.name=computer.getName();
	 * DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd",
	 * Locale.ENGLISH); if(computer.getIntro()!=null) {
	 * this.intro=computer.getIntro().format(formatter); }
	 * if(computer.getDisco()!=null) {
	 * this.disco=computer.getDisco().format(formatter); }
	 * this.company_id=String.valueOf(computer.getCompanyId());
	 * this.setCompany_name(computer.getCompany()); }
	 */

	private ComputerDTO(ComputerDTOBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.intro = builder.intro;
		this.disco = builder.disco;
		this.company_id = builder.company_id;
		this.company_name = builder.company_name;
	}

	public static class ComputerDTOBuilder {

		private String id;
		private String name;
		private String intro;
		private String disco;
		private String company_id;
		private String company_name;

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

		public ComputerDTOBuilder setAnyId(int any) {
			this.company_id = String.valueOf(any);
			return this;
		}

		public ComputerDTOBuilder setAnyName(String any) {
			this.company_name = any;
			return this;
		}

		public ComputerDTO build() {
			return new ComputerDTO(this);
		}
	}

}
