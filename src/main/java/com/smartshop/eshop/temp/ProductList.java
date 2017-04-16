package com.smartshop.eshop.temp;

import java.util.ArrayList;
import java.util.List;

import com.smartshop.eshop.domain.Product;

public class ProductList extends EntityList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7267292601646149482L;
	private List<Product> products = new ArrayList<Product>();

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
