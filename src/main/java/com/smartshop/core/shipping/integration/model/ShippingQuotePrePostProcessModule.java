package com.smartshop.core.shipping.integration.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import com.smartshop.core.model.system.IntegrationConfiguration;
import com.smartshop.core.shipping.model.PackageDetails;
import com.smartshop.core.shipping.model.ShippingConfiguration;
import com.smartshop.core.shipping.model.ShippingQuote;
import com.smartshop.eshop.domain.IntegrationModule;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.ShippingOrigin;
import com.smartshop.eshop.domain.common.Delivery;
import com.smartshop.eshop.exception.IntegrationException;

/**
 * Invoked before or after quote processing
 *
 * @author carlsamson
 *
 */
public interface ShippingQuotePrePostProcessModule {

	public String getModuleCode();

	public void prePostProcessShippingQuotes(ShippingQuote quote, List<PackageDetails> packages, BigDecimal orderTotal,
			Delivery delivery, ShippingOrigin origin, MerchantStore store,
			IntegrationConfiguration globalShippingConfiguration, IntegrationModule currentModule,
			ShippingConfiguration shippingConfiguration, List<IntegrationModule> allModules, Locale locale)
			throws IntegrationException;

}
