package com.smartshop.eshop.temp;

import java.util.List;

import com.smartshop.eshop.domain.Customer;

public class CustomerList extends EntityList {
	private static final long serialVersionUID = -3108842276158069739L;
	private List<Customer> customers;

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

}
