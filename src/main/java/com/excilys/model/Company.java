package com.excilys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {

	public Company() {}
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	private Company(CompanyBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;

	}

	public static class CompanyBuilder {
		private int id;
		private String name;

		public CompanyBuilder setId(int id) {
			this.id = id;
			return this;
		}

		public CompanyBuilder setName(String name) {
			this.name = name;
			return this;

		}

		public Company build() {
			return new Company(this);
		}

	}

	public String toString() {
		return "id=" + id + ", name=" + name;
	}
}
