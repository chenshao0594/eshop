package com.smartshop.payment.dto;

import com.smartshop.eshop.domain.enumeration.PaymentEnum;

/**
 * When the user performs a payment using paypal
 * @author Carl Samson
 *
 */
public class PaypalPaymentDTO extends PaymentDTO {
	
	//express checkout
	private String payerId;
	private String paymentToken;
	
	public PaypalPaymentDTO() {
		super.setPaymentType(PaymentEnum.PAYPAL);
	}
	
	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}
	public String getPayerId() {
		return payerId;
	}
	public void setPaymentToken(String paymentToken) {
		this.paymentToken = paymentToken;
	}
	public String getPaymentToken() {
		return paymentToken;
	}

}
