package com.smartshop.eshop.service;

import com.smartshop.core.salesorder.model.OrderTotalVariation;
import com.smartshop.core.salesorder.model.SalesOrderSummary;
import com.smartshop.eshop.domain.Customer;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.OrderTotal;

/**
 * Service Interface for managing OrderTotal.
 */
public interface OrderTotalService extends AbstractDomainService<OrderTotal, Long> {
	OrderTotalVariation findOrderTotalVariation(final SalesOrderSummary summary, final Customer customer,
			final MerchantStore store, final Language language) throws Exception;

}