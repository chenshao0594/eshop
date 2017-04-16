package com.smartshop.eshop.utils;

import java.util.Set;

import com.smartshop.eshop.domain.OrderProduct;
import com.smartshop.eshop.domain.OrderProductAttribute;

public class ProductUtils {
	
	public static String buildOrderProductDisplayName(OrderProduct orderProduct) {
		
		String pName = orderProduct.getProductName();
		Set<OrderProductAttribute> oAttributes = orderProduct.getOrderAttributes();
		StringBuilder attributeName = null;
		for(OrderProductAttribute oProductAttribute : oAttributes) {
			if(attributeName == null) {
				attributeName = new StringBuilder();
				attributeName.append("[");
			} else {
				attributeName.append(", ");
			}
			attributeName.append(oProductAttribute.getProductAttributeName())
			.append(": ")
			.append(oProductAttribute.getProductAttributeValueName());
			
		}
		
		
		StringBuilder productName = new StringBuilder();
		productName.append(pName);
		
		if(attributeName!=null) {
			attributeName.append("]");
			productName.append(" ").append(attributeName.toString());
		}
		
		return productName.toString();
		
		
	}

}
