/***********************************************************************
 * Module:  Stock.java
 * Author:  Elodie NGUYEN THANH NHAN, Laura OLLIVIER & Chloé VASSEUR
 * Purpose: Defines the Class Stock
 ***********************************************************************/
package fr.epita.sigl.miwa.application.data;

public class Stock {
	private String productRef;
	private Boolean ordered;
	private Integer stockQty;
	private Integer maxQty;
	private String store;
	
	public Stock() {
	}
	
	public Stock(String productRef, Boolean ordered, Integer stockQty, Integer maxQty,
			String store) {
		this.productRef = productRef;
		this.ordered = ordered;
		this.stockQty = stockQty;
		this.maxQty = maxQty;
		this.store = store;
	}

	public String getProductRef() {
		return productRef;
	}

	public void setProductRef(String productRef) {
		this.productRef = productRef;
	}

	public Boolean getOrdered() {
		return ordered;
	}

	public void setOrdered(Boolean ordered) {
		this.ordered = ordered;
	}

	public Integer getStockQty() {
		return stockQty;
	}

	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}

	public Integer getMaxQty() {
		return maxQty;
	}

	public void setMaxQty(Integer maxQty) {
		this.maxQty = maxQty;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}
}