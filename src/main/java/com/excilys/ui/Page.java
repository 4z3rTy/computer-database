package com.excilys.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.service.ComputerS;
import com.excilys.service.CompanyS;

public class Page {
	int itemsNb = 10;
	int maxItems;
	int pageTotal;
	int currentPage = 1;
	private static final Logger logger = LoggerFactory.getLogger(Page.class);

	public Page(int userChoice, String tbName) {
		this.currentPage = userChoice;
		switch (tbName) {
		case "computer":
			CompanyS CS = new CompanyS();
			this.maxItems = CS.count(tbName);
		case "company":
			ComputerS C = new ComputerS();
			this.maxItems = C.count(tbName);
		default:
			logger.debug("The table name in your input is not valid. maxItems value remained unchanged");
		}
	}

	public Page(String tbName) {
		switch (tbName) {
		case "computer":
			CompanyS CS = new CompanyS();
			this.maxItems = CS.count(tbName);
		case "company":
			ComputerS C = new ComputerS();
			this.maxItems = C.count(tbName);
		default:
			logger.debug("The table name in your input is not valid. maxItems value remained unchanged");
		}
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
