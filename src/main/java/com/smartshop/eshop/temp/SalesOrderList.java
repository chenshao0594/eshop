package com.smartshop.eshop.temp;

import java.util.List;

import com.smartshop.eshop.domain.SalesOrder;

public class SalesOrderList extends EntityList {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6645927228659963628L;
	private List<SalesOrder> orders;

	public void setOrders(List<SalesOrder> orders) {
		this.orders = orders;
	}

	public List<SalesOrder> getOrders() {
		return orders;
	}

}
