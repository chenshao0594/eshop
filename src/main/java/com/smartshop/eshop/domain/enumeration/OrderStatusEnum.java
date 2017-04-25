package com.smartshop.eshop.domain.enumeration;

/**
 * The OrderStatus enumeration.
 */
public enum OrderStatusEnum {
	ORDERED("ordered"), PROCESSED("processed"), DELIVERED("delivered"), REFUNDED("refunded"), CANCELED("canceled"),;

	private String value;

	private OrderStatusEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
