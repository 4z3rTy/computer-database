package com.excilys.dto;

public class MyLittleDTO {

	private String items = "10";
	private String sum;
	private String pageTotal;
	private String currentPage = "1";
	private String search;
	private String searchType;
	private String searchName;
	private String searchIntro;
	private String pageAmount;

	public String getitems() {
		return items;
	}

	public void setitems(String itemsNb) {
		this.items = itemsNb;
	}

	public String getsum() {
		return sum;
	}

	public void setsum(String maxItems) {
		this.sum = maxItems;
	}

	public String getpageTotal() {
		return pageTotal;
	}

	public void setpageTotal(String pageTotal) {
		this.pageTotal = pageTotal;
	}

	public String getcurrentPage() {
		return currentPage;
	}

	public void setcurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getsearch() {
		return search;
	}

	public void setsearch(String search) {
		this.search = search;
	}

	public String getsearchType() {
		return searchType;
	}

	public void setsearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getsearchIntro() {
		return searchIntro;
	}

	public void setsearchIntro(String searchIntro) {
		this.searchIntro = searchIntro;
	}

	public String getsearchName() {
		return searchName;
	}

	public void setsearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getpageAmount() {
		return pageAmount;
	}

	public void setpageAmount(String searchAmount) {
		this.pageAmount = searchAmount;
	}

}
