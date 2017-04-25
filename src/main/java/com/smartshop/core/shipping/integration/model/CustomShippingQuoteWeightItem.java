package com.smartshop.core.shipping.integration.model;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class CustomShippingQuoteWeightItem extends CustomShippingQuoteItem implements JSONAware {

	private int maximumWeight;

	private String priceText;

	@Override
	public String getPriceText() {
		return priceText;
	}

	@Override
	public void setPriceText(String priceText) {
		this.priceText = priceText;
	}

	public void setMaximumWeight(int maximumWeight) {
		this.maximumWeight = maximumWeight;
	}

	public int getMaximumWeight() {
		return maximumWeight;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String toJSONString() {
		JSONObject data = new JSONObject();
		data.put("price", super.getPrice());
		data.put("maximumWeight", this.getMaximumWeight());

		return data.toJSONString();
	}

}
