package com.smartshop.core.salesorder.model;

import java.util.List;

import com.smartshop.eshop.domain.OrderTotal;

/**
 * Contains a list of negative OrderTotal variation that will be shown in the
 * order summary
 * 
 * @author carlsamson
 *
 */
public abstract class OrderTotalVariation {

	List<OrderTotal> variations = null;

	public List<OrderTotal> getVariations() {
		return variations;
	}

	public void setVariations(List<OrderTotal> variations) {
		this.variations = variations;
	}

}
