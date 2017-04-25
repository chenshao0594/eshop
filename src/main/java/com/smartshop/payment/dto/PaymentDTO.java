package com.smartshop.payment.dto;
import java.util.Map;

import com.smartshop.eshop.domain.Currency;
import com.smartshop.eshop.domain.enumeration.TransactionEnum;

public class PaymentDTO {
	
	private PaymentEnum paymentType;
	private TransactionEnum transactionType = TransactionEnum.AUTHORIZECAPTURE;
	private String moduleName;
	private Currency currency;
	private Map<String,String> paymentMetaData = null;

	public void setPaymentType(PaymentEnum paymentType) {
		this.paymentType = paymentType;
	}

	public PaymentEnum getPaymentType() {
		return paymentType;
	}

	public void setTransactionType(TransactionEnum transactionType) {
		this.transactionType = transactionType;
	}

	public TransactionEnum getTransactionType() {
		return transactionType;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Map<String,String> getPaymentMetaData() {
		return paymentMetaData;
	}

	public void setPaymentMetaData(Map<String,String> paymentMetaData) {
		this.paymentMetaData = paymentMetaData;
	}

}
