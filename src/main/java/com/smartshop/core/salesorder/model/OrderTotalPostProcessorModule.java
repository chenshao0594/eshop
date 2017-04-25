package com.smartshop.core.salesorder.model;

import org.elasticsearch.common.inject.Module;

import com.smartshop.eshop.domain.Customer;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.OrderTotal;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.domain.ShoppingCartItem;

/**
 * Calculates order total based on specific modules implementation
 *
 * @author carlsamson
 *
 */
public interface OrderTotalPostProcessorModule extends Module {

	/**
	 * Uses the OrderSummary and external tools for applying if necessary
	 * variations on the OrderTotal calculation.
	 *
	 * @param orderSummary
	 * @param shoppingCartItem
	 * @param product
	 * @param customer
	 * @param store
	 * @return
	 * @throws Exception
	 */
	OrderTotal caculateProductPiceVariation(final SalesOrderSummary summary, final ShoppingCartItem shoppingCartItem,
			final Product product, final Customer customer, final MerchantStore store) throws Exception;

}
