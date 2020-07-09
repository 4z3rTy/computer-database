package com.excilys.ui;


public class Page {
	int itemsNb = 10;
	int maxItems;
	int pageTotal;
	int currentPage = 1;

	
	public Page(int userChoice,int total) {
		this.currentPage = userChoice;
		this.maxItems = total;		
	}
	
	public Page(int total)
	{
		this.maxItems =total;
	}

	public int getAmount() {
		return itemsNb;
	}

	public void setAmount(int amount) {
		this.itemsNb = amount;
	}

	public int getMax() {
		return maxItems;
	}

	public void setMax(int db_max) {
		this.maxItems = db_max;
	}

	public int getTotal() {
		return pageTotal;
	}

	public void setTotal(int p) {
		this.pageTotal = p;
	}

	public int getPage() {
		return currentPage;
	}

	public void setPage(int p) {
		this.currentPage = p;
	}

	public void calcPages() {
		int result = getMax() % getAmount();
		if (result > 0) {
			result = 1;
		} else {
			result = 0;
		}
		this.pageTotal = (getMax() / getAmount()) + result;
	}
}
