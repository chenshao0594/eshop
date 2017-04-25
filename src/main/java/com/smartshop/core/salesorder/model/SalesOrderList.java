package com.smartshop.core.salesorder.model;

import java.util.List;

import com.smartshop.eshop.domain.SalesOrder;
import com.smartshop.eshop.temp.EntityList;

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
