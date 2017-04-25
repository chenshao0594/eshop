package com.smartshop.core.shipping.model;

import java.math.BigDecimal;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

/**
 * Object saved in the database maintaining various shipping options
 *
 * @author casams1
 *
 */
public class ShippingConfiguration implements JSONAware {

	// enums
	private ShippingEnum shippingType = ShippingEnum.NATIONAL;
	private ShippingBasisEnum shippingBasisType = ShippingBasisEnum.SHIPPING;
	private ShippingOptionPriceEnum shippingOptionPriceType = ShippingOptionPriceEnum.ALL;
	private ShippingPackageEnum shippingPackageType = ShippingPackageEnum.ITEM;
	private ShippingDescriptionEnum shippingDescription = ShippingDescriptionEnum.SHORT_DESCRIPTION;
	private ShippingEnum freeShippingType = null;

	private int boxWidth = 0;
	private int boxHeight = 0;
	private int boxLength = 0;
	private double boxWeight = 0;
	private double maxWeight = 0;

	// free shipping
	private boolean freeShippingEnabled = false;
	private BigDecimal orderTotalFreeShipping = null;

	private BigDecimal handlingFees = null;
	private boolean taxOnShipping = false;

	// JSON bindings
	private String shipType;
	private String shipBaseType;
	private String shipOptionPriceType = ShippingOptionPriceEnum.ALL.name();
	private String shipPackageType;
	private String shipDescription;
	private String shipFreeType;

	// Transient
	private String orderTotalFreeShippingText = null;
	private String handlingFeesText = null;

	public String getShipType() {
		return shipType;
	}

	public String getShipBaseType() {
		return shipBaseType;
	}

	public String getShipOptionPriceType() {
		return shipOptionPriceType;
	}

	public void setShippingOptionPriceType(ShippingOptionPriceEnum shippingOptionPriceType) {
		this.shippingOptionPriceType = shippingOptionPriceType;
		this.shipOptionPriceType = this.shippingOptionPriceType.name();
	}

	public ShippingOptionPriceEnum getShippingOptionPriceType() {
		return shippingOptionPriceType;
	}

	public void setShippingBasisType(ShippingBasisEnum shippingBasisType) {
		this.shippingBasisType = shippingBasisType;
		this.shipBaseType = this.shippingBasisType.name();
	}

	public ShippingBasisEnum getShippingBasisType() {
		return shippingBasisType;
	}

	public void setShippingType(ShippingEnum shippingType) {
		this.shippingType = shippingType;
		this.shipType = this.shippingType.name();
	}

	public ShippingEnum getShippingType() {
		return shippingType;
	}

	public ShippingPackageEnum getShippingPackageType() {
		return shippingPackageType;
	}

	public void setShippingPackageType(ShippingPackageEnum shippingPackageType) {
		this.shippingPackageType = shippingPackageType;
		this.shipPackageType = shippingPackageType.name();
	}

	public String getShipPackageType() {
		return shipPackageType;
	}

	/** JSON bindding **/
	public void setShipType(String shipType) {
		this.shipType = shipType;
		ShippingEnum sType = ShippingEnum.NATIONAL;
		if (shipType.equals(ShippingEnum.INTERNATIONAL.name())) {
			sType = ShippingEnum.INTERNATIONAL;
		}
		setShippingType(sType);
	}

	public void setShipOptionPriceType(String shipOptionPriceType) {
		this.shipOptionPriceType = shipOptionPriceType;
		ShippingOptionPriceEnum sType = ShippingOptionPriceEnum.ALL;
		if (shipOptionPriceType.equals(ShippingOptionPriceEnum.HIGHEST.name())) {
			sType = ShippingOptionPriceEnum.HIGHEST;
		}
		if (shipOptionPriceType.equals(ShippingOptionPriceEnum.LEAST.name())) {
			sType = ShippingOptionPriceEnum.LEAST;
		}
		setShippingOptionPriceType(sType);
	}

	public void setShipBaseType(String shipBaseType) {
		this.shipBaseType = shipBaseType;
		ShippingBasisEnum sType = ShippingBasisEnum.SHIPPING;
		if (shipBaseType.equals(ShippingBasisEnum.BILLING.name())) {
			sType = ShippingBasisEnum.BILLING;
		}
		setShippingBasisType(sType);
	}

	public void setShipPackageType(String shipPackageType) {
		this.shipPackageType = shipPackageType;
		ShippingPackageEnum sType = ShippingPackageEnum.ITEM;
		if (shipPackageType.equals(ShippingPackageEnum.BOX.name())) {
			sType = ShippingPackageEnum.BOX;
		}
		this.setShippingPackageType(sType);
	}

	public void setShipDescription(String shipDescription) {
		this.shipDescription = shipDescription;
		ShippingDescriptionEnum sType = ShippingDescriptionEnum.SHORT_DESCRIPTION;
		if (shipDescription.equals(ShippingDescriptionEnum.LONG_DESCRIPTION.name())) {
			sType = ShippingDescriptionEnum.LONG_DESCRIPTION;
		}
		this.setShippingDescription(sType);
	}

	public void setShipFreeType(String shipFreeType) {
		this.shipFreeType = shipFreeType;
		ShippingEnum sType = ShippingEnum.NATIONAL;
		if (shipFreeType.equals(ShippingEnum.INTERNATIONAL.name())) {
			sType = ShippingEnum.INTERNATIONAL;
		}
		setFreeShippingType(sType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String toJSONString() {
		JSONObject data = new JSONObject();
		data.put("shipBaseType", this.getShippingBasisType().name());
		data.put("shipOptionPriceType", this.getShippingOptionPriceType().name());
		data.put("shipType", this.getShippingType().name());
		data.put("shipPackageType", this.getShippingPackageType().name());
		if (shipFreeType != null) {
			data.put("shipFreeType", this.getFreeShippingType().name());
		}
		data.put("shipDescription", this.getShippingDescription().name());

		data.put("boxWidth", this.getBoxWidth());
		data.put("boxHeight", this.getBoxHeight());
		data.put("boxLength", this.getBoxLength());
		data.put("boxWeight", this.getBoxWeight());
		data.put("maxWeight", this.getMaxWeight());
		data.put("freeShippingEnabled", this.freeShippingEnabled);
		data.put("orderTotalFreeShipping", this.orderTotalFreeShipping);
		data.put("handlingFees", this.handlingFees);
		data.put("taxOnShipping", this.taxOnShipping);

		return data.toJSONString();
	}

	public int getBoxWidth() {
		return boxWidth;
	}

	public void setBoxWidth(int boxWidth) {
		this.boxWidth = boxWidth;
	}

	public int getBoxHeight() {
		return boxHeight;
	}

	public void setBoxHeight(int boxHeight) {
		this.boxHeight = boxHeight;
	}

	public int getBoxLength() {
		return boxLength;
	}

	public void setBoxLength(int boxLength) {
		this.boxLength = boxLength;
	}

	public double getBoxWeight() {
		return boxWeight;
	}

	public void setBoxWeight(double boxWeight) {
		this.boxWeight = boxWeight;
	}

	public double getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public boolean isFreeShippingEnabled() {
		return freeShippingEnabled;
	}

	public void setFreeShippingEnabled(boolean freeShippingEnabled) {
		this.freeShippingEnabled = freeShippingEnabled;
	}

	public BigDecimal getOrderTotalFreeShipping() {
		return orderTotalFreeShipping;
	}

	public void setOrderTotalFreeShipping(BigDecimal orderTotalFreeShipping) {
		this.orderTotalFreeShipping = orderTotalFreeShipping;
	}

	public void setHandlingFees(BigDecimal handlingFees) {
		this.handlingFees = handlingFees;
	}

	public BigDecimal getHandlingFees() {
		return handlingFees;
	}

	public void setTaxOnShipping(boolean taxOnShipping) {
		this.taxOnShipping = taxOnShipping;
	}

	public boolean isTaxOnShipping() {
		return taxOnShipping;
	}

	public String getShipDescription() {
		return shipDescription;
	}

	public void setShippingDescription(ShippingDescriptionEnum shippingDescription) {
		this.shippingDescription = shippingDescription;
	}

	public ShippingDescriptionEnum getShippingDescription() {
		return shippingDescription;
	}

	public void setFreeShippingType(ShippingEnum freeShippingType) {
		this.freeShippingType = freeShippingType;
	}

	public ShippingEnum getFreeShippingType() {
		return freeShippingType;
	}

	public String getShipFreeType() {
		return shipFreeType;
	}

	public void setOrderTotalFreeShippingText(String orderTotalFreeShippingText) {
		this.orderTotalFreeShippingText = orderTotalFreeShippingText;
	}

	public String getOrderTotalFreeShippingText() {
		return orderTotalFreeShippingText;
	}

	public void setHandlingFeesText(String handlingFeesText) {
		this.handlingFeesText = handlingFeesText;
	}

	public String getHandlingFeesText() {
		return handlingFeesText;
	}

}
