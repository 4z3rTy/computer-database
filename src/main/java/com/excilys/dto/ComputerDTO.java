package com.excilys.dto;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.excilys.model.Computer;

public class ComputerDTO {
	private String id;
	@Override
	public String toString() {
		return " [id=" + id + ", name=" + name + ", intro=" + intro + ", disco=" + disco + ", company_name=" + company_name + "]";
	}



	private String name;
	private String intro;
	private String disco;
	private String company_id;
	private String company_name;

	
	
	public ComputerDTO(Computer computer)
	{
		this.setId(String.valueOf(computer.getId()));
		this.name=computer.getName();
		DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		if(computer.getIntro()!=null)
		{
		this.intro=computer.getIntro().format(formatter);
		}
		if(computer.getDisco()!=null)
		{
		this.disco=computer.getDisco().format(formatter);
		}
		this.company_id=String.valueOf(computer.getCompanyId());
		this.setCompany_name(computer.getCompany());
	}
	
	
	
	public ComputerDTO(String name2, String intro2, String disco2, String company_id2) {
		this.name=name2;
		this.intro=intro2;
		this.disco=disco2;
		this.company_id=company_id2;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getDisco() {
		return disco;
	}

	public void setDisco(String disco) {
		this.disco = disco;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
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

}
