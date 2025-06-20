package com.rays.pro4.Bean;

import java.util.Date;

public class ProductBean extends BaseBean {

	private String customerName;
	private String productName;
	private int productPrice;
	private Date pruchaseDate;

	public Date getPruchaseDate() {
		return pruchaseDate;
	}

	public void setPruchaseDate(Date pruchaseDate) {
		this.pruchaseDate = pruchaseDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public String getkey() {
		// TODO Auto-generated method stub
		return id + "";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return pruchaseDate + "";
	}

}
