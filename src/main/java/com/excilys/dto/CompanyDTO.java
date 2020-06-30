package com.excilys.dto;

import com.excilys.model.Company;

public class CompanyDTO {

	private String name;
	private String id;
	
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "CompanyDTO [name=" + name + ", id=" + id + "]";
	}
	
	public CompanyDTO(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}
	public CompanyDTO(Company company) {
		this.name=company.getName();
		this.id=String.valueOf(company.getId());
		}
}
