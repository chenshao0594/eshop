package com.smartshop.eshop.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.smartshop.core.shipping.module.ShippingQuoteModule;

@Configuration
public class ContextBeanConfig {

	@Bean("shippingModules")
	public Map<String, ShippingQuoteModule> shippingModules() {
		System.out.println("context bean config .....");
		return null;
	}

}
