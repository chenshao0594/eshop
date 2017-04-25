package com.smartshop.eshop.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.smartshop.core.salesorder.model.OrderTotalSummary;
import com.smartshop.core.salesorder.model.SalesOrderList;
import com.smartshop.core.salesorder.model.SalesOrderSummary;
import com.smartshop.eshop.domain.Customer;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.OrderStatusHistory;
import com.smartshop.eshop.domain.SalesOrder;
import com.smartshop.eshop.domain.ShoppingCart;
import com.smartshop.eshop.domain.ShoppingCartItem;
import com.smartshop.eshop.domain.Transaction;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.temp.SalesOrderCriteria;
import com.smartshop.payment.dto.PaymentDTO;

/**
 * Service Interface for managing SalesOrder.
 */
public interface SalesOrderService extends AbstractDomainService<SalesOrder, Long> {
	void addOrderStatusHistory(SalesOrder order, OrderStatusHistory history) throws BusinessException;

	/**
	 * Can be used to calculates the final prices of all items contained in
	 * checkout page
	 *
	 * @param orderSummary
	 * @param customer
	 * @param store
	 * @param language
	 * @return
	 * @throws BusinessException
	 */
	OrderTotalSummary caculateOrderTotal(SalesOrderSummary orderSummary, Customer customer, MerchantStore store,
			Language language) throws BusinessException;

	/**
	 * Can be used to calculates the final prices of all items contained in a
	 * ShoppingCart
	 *
	 * @param orderSummary
	 * @param store
	 * @param language
	 * @return
	 * @throws BusinessException
	 */
	OrderTotalSummary caculateOrderTotal(SalesOrderSummary orderSummary, MerchantStore store, Language language)
			throws BusinessException;

	/**
	 * Can be used to calculates the final prices of all items contained in
	 * checkout page
	 *
	 * @param shoppingCart
	 * @param customer
	 * @param store
	 * @param language
	 * @return @return {@link OrderTotalSummary}
	 * @throws BusinessException
	 */
	OrderTotalSummary calculateShoppingCartTotal(final ShoppingCart shoppingCart, final Customer customer,
			final MerchantStore store, final Language language) throws BusinessException;

	/**
	 * Can be used to calculates the final prices of all items contained in a
	 * ShoppingCart
	 *
	 * @param shoppingCart
	 * @param store
	 * @param language
	 * @return {@link OrderTotalSummary}
	 * @throws BusinessException
	 */
	OrderTotalSummary calculateShoppingCartTotal(final ShoppingCart shoppingCart, final MerchantStore store,
			final Language language) throws BusinessException;

	ByteArrayOutputStream generateInvoice(MerchantStore store, SalesOrder order, Language language)
			throws BusinessException;

	SalesOrder getOrder(Long id);

	void saveOrUpdate(SalesOrder order) throws BusinessException;

	SalesOrder processOrder(SalesOrder order, Customer customer, List<ShoppingCartItem> items,
			OrderTotalSummary summary, PaymentDTO payment, MerchantStore store) throws BusinessException;

	SalesOrder processOrder(SalesOrder order, Customer customer, List<ShoppingCartItem> items,
			OrderTotalSummary summary, PaymentDTO payment, Transaction transaction, MerchantStore store)
			throws BusinessException;

	/**
	 * Determines if an Order has download files
	 *
	 * @param order
	 * @return
	 * @throws BusinessException
	 */
	boolean hasDownloadFiles(SalesOrder order) throws BusinessException;

	SalesOrderList listByStore(MerchantStore store, SalesOrderCriteria criteria);
}