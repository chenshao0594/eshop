package com.smartshop.core.salesorder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.smartshop.core.shipping.model.ShippingSummary;
import com.smartshop.eshop.domain.ShoppingCartItem;

/**
 * This object is used as input object for many services such as order total
 * calculation and tax calculation
 * 
 * @author Carl Samson
 *
 */
public class SalesOrderSummary implements Serializable {

	/**
	 * 
	 */
	private OrderSummaryEnum orderSummaryType = OrderSummaryEnum.ORDERTOTAL;
	private static final long serialVersionUID = 1L;
	private ShippingSummary shippingSummary;
	private List<ShoppingCartItem> products = new ArrayList<ShoppingCartItem>();

	public void setProducts(List<ShoppingCartItem> products) {
		this.products = products;
	}

	public List<ShoppingCartItem> getProducts() {
		return products;
	}

	public void setShippingSummary(ShippingSummary shippingSummary) {
		this.shippingSummary = shippingSummary;
	}

	public ShippingSummary getShippingSummary() {
		return shippingSummary;
	}

	public OrderSummaryEnum getOrderSummaryType() {
		return orderSummaryType;
	}

	public void setOrderSummaryType(OrderSummaryEnum orderSummaryType) {
		this.orderSummaryType = orderSummaryType;
	}

}
