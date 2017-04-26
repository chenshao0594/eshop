package com.smartshop.eshop.domain.enumeration;
public enum PaymentEnum {
	
	
	
	CREDITCARD("creditcard"), FREE("creditcard"), COD("creditcard"), MONEYORDER("creditcard"), PAYPAL("creditcard"), STRIPE("creditcard"), WEPAY("creditcard");

	
	private String paymentType;
	
	PaymentEnum(String type) {
		paymentType = type;
	}
	
    public static PaymentEnum fromString(String text) {
		    if (text != null) {
		      for (PaymentEnum b : PaymentEnum.values()) {
		    	String payemntType = text.toUpperCase(); 
		        if (payemntType.equalsIgnoreCase(b.name())) {
		          return b;
		        }
		      }
		    }
		    return null;
	}
}
