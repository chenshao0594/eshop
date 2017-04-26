package com.smartshop.eshop.service.impl;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.smartshop.core.payment.service.PaymentService;
import com.smartshop.core.salesorder.model.OrderSummaryEnum;
import com.smartshop.core.salesorder.model.OrderTotalSummary;
import com.smartshop.core.salesorder.model.OrderTotalVariation;
import com.smartshop.core.salesorder.model.SalesOrderList;
import com.smartshop.core.salesorder.model.SalesOrderSummary;
import com.smartshop.core.shipping.model.ShippingConfiguration;
import com.smartshop.core.shipping.service.ShippingService;
import com.smartshop.core.tax.model.TaxItem;
import com.smartshop.core.tax.service.TaxService;
import com.smartshop.eshop.common.BusinessConstants;
import com.smartshop.eshop.core.catalog.product.FinalPrice;
import com.smartshop.eshop.domain.Customer;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.OrderProduct;
import com.smartshop.eshop.domain.OrderStatusHistory;
import com.smartshop.eshop.domain.OrderTotal;
import com.smartshop.eshop.domain.SalesOrder;
import com.smartshop.eshop.domain.ShoppingCart;
import com.smartshop.eshop.domain.ShoppingCartItem;
import com.smartshop.eshop.domain.Transaction;
import com.smartshop.eshop.domain.enumeration.OrderStatusEnum;
import com.smartshop.eshop.domain.enumeration.OrderTotalEnum;
import com.smartshop.eshop.domain.enumeration.OrderValueEnum;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.repository.SalesOrderRepository;
import com.smartshop.eshop.repository.search.SalesOrderSearchRepository;
import com.smartshop.eshop.service.CustomerService;
import com.smartshop.eshop.service.OrderTotalService;
import com.smartshop.eshop.service.SalesOrderService;
import com.smartshop.eshop.service.TransactionService;
import com.smartshop.eshop.temp.SalesOrderCriteria;
import com.smartshop.payment.dto.PaymentDTO;

/**
 * Service Implementation for managing SalesOrder.
 */
@Service
@Transactional
public class SalesOrderServiceImpl extends AbstractDomainServiceImpl<SalesOrder, Long> implements SalesOrderService {

	private final Logger LOGGER = LoggerFactory.getLogger(SalesOrderServiceImpl.class);
	private final SalesOrderRepository salesOrderRepository;
	private final SalesOrderSearchRepository salesOrderSearchRepository;

	/*
	 * @Inject private InvoiceModule invoiceModule;
	 */

	@Inject
	private ShippingService shippingService;

	@Inject
	private PaymentService paymentService;

	@Inject
	private TaxService taxService;

	@Inject
	private CustomerService customerService;

	@Inject
	private TransactionService transactionService;

	@Inject
	private OrderTotalService orderTotalService;

	public SalesOrderServiceImpl(SalesOrderRepository salesOrderRepository,
			SalesOrderSearchRepository salesOrderSearchRepository) {
		super(salesOrderRepository, salesOrderSearchRepository);
		this.salesOrderRepository = salesOrderRepository;
		this.salesOrderSearchRepository = salesOrderSearchRepository;
	}

	@Override
	public void addOrderStatusHistory(SalesOrder order, OrderStatusHistory history) throws BusinessException {
		order.getOrderHistories().add(history);
		history.setOrder(order);
		update(order);
	}

	@Override
	public SalesOrder processOrder(SalesOrder order, Customer customer, List<ShoppingCartItem> items,
			OrderTotalSummary summary, PaymentDTO payment, MerchantStore store) throws BusinessException {

		return this.process(order, customer, items, summary, payment, null, store);
	}

	@Override
	public SalesOrder processOrder(SalesOrder order, Customer customer, List<ShoppingCartItem> items,
			OrderTotalSummary summary, PaymentDTO payment, Transaction transaction, MerchantStore store)
			throws BusinessException {

		return this.process(order, customer, items, summary, payment, transaction, store);
	}

	private SalesOrder process(SalesOrder order, Customer customer, List<ShoppingCartItem> items,
			OrderTotalSummary summary, PaymentDTO payment, Transaction transaction, MerchantStore store)
			throws BusinessException {

		Validate.notNull(order, "Order cannot be null");
		Validate.notNull(customer, "Customer cannot be null (even if anonymous order)");
		Validate.notEmpty(items, "ShoppingCart items cannot be null");
		Validate.notNull(payment, "Payment cannot be null");
		Validate.notNull(store, "MerchantStore cannot be null");
		Validate.notNull(summary, "Order total Summary cannot be null");

		// first process payment
		Transaction processTransaction = paymentService.processPayment(customer, store, payment, items, order);
		// transactionService.save(processTransaction);

		if (order.getOrderHistories() == null || order.getOrderHistories().size() == 0 || order.getStatus() == null) {
			OrderStatusEnum status = order.getStatus();
			if (status == null) {
				status = OrderStatusEnum.ORDERED;
				order.setStatus(status);
			}
			Set<OrderStatusHistory> statusHistorySet = new HashSet<OrderStatusHistory>();
			OrderStatusHistory statusHistory = new OrderStatusHistory();
			statusHistory.setStatus(status);
			statusHistory.setDateAdded(new Date());
			statusHistory.setOrder(order);
			statusHistorySet.add(statusHistory);
			order.setOrderHistories(statusHistorySet);

		}

		if (customer.getId() == null || customer.getId() == 0) {
			customerService.save(customer);
		}

		order.setCustomerId(customer.getId());

		this.save(order);

		if (transaction != null) {
			transaction.setOrder(order);
			if (transaction.getId() == null || transaction.getId() == 0) {
				transactionService.save(transaction);
			} else {
				transactionService.update(transaction);
			}
		}

		if (processTransaction != null) {
			processTransaction.setOrder(order);
			if (processTransaction.getId() == null || processTransaction.getId() == 0) {
				transactionService.save(processTransaction);
			} else {
				transactionService.update(processTransaction);
			}
		}

		return order;

	}

	private OrderTotalSummary caculateOrder(SalesOrderSummary summary, Customer customer, final MerchantStore store,
			final Language language) throws Exception {

		OrderTotalSummary totalSummary = new OrderTotalSummary();
		List<OrderTotal> orderTotals = new ArrayList<OrderTotal>();
		Map<String, OrderTotal> otherPricesTotals = new HashMap<String, OrderTotal>();

		ShippingConfiguration shippingConfiguration = null;

		BigDecimal grandTotal = new BigDecimal(0);
		grandTotal.setScale(2, RoundingMode.HALF_UP);

		// price by item
		/**
		 * qty * price subtotal
		 */
		BigDecimal subTotal = new BigDecimal(0);
		subTotal.setScale(2, RoundingMode.HALF_UP);
		for (ShoppingCartItem item : summary.getProducts()) {

			BigDecimal st = item.getItemPrice().multiply(new BigDecimal(item.getQuantity()));
			item.setSubTotal(st);
			subTotal = subTotal.add(st);
			// Other prices
			FinalPrice finalPrice = item.getFinalPrice();
			if (finalPrice != null) {
				List<FinalPrice> otherPrices = finalPrice.getAdditionalPrices();
				if (otherPrices != null) {
					for (FinalPrice price : otherPrices) {
						if (!price.isDefaultPrice()) {
							OrderTotal itemSubTotal = otherPricesTotals.get(price.getProductPrice().getCode());

							if (itemSubTotal == null) {
								itemSubTotal = new OrderTotal();
								itemSubTotal.setModule(BusinessConstants.OT_ITEM_PRICE_MODULE_CODE);
								// itemSubTotal.setText(Constants.OT_ITEM_PRICE_MODULE_CODE);
								itemSubTotal.setTitle(BusinessConstants.OT_ITEM_PRICE_MODULE_CODE);
								itemSubTotal.setOrderTotalCode(price.getProductPrice().getCode());
								itemSubTotal.setOrderTotalType(OrderTotalEnum.PRODUCT);
								itemSubTotal.setSortOrder(0);
								otherPricesTotals.put(price.getProductPrice().getCode(), itemSubTotal);
							}

							BigDecimal orderTotalValue = itemSubTotal.getValue();
							if (orderTotalValue == null) {
								orderTotalValue = new BigDecimal(0);
								orderTotalValue.setScale(2, RoundingMode.HALF_UP);
							}

							orderTotalValue = orderTotalValue.add(price.getFinalPrice());
							itemSubTotal.setValue(orderTotalValue);
							if (price.getProductPrice().getProductPriceType().name().equals(OrderValueEnum.ONE_TIME)) {
								subTotal = subTotal.add(price.getFinalPrice());
							}
						}
					}
				}
			}

		}

		// only in order page, otherwise invokes too many processing
		if (OrderSummaryEnum.ORDERTOTAL.name().equals(summary.getOrderSummaryType().name())) {

			// Post processing order total variation modules for sub total
			// calculation - drools, custom modules
			// may affect the sub total
			OrderTotalVariation orderTotalVariation = orderTotalService.findOrderTotalVariation(summary, customer,
					store, language);

			int currentCount = 10;

			if (!CollectionUtils.isEmpty(orderTotalVariation.getVariations())) {
				for (OrderTotal variation : orderTotalVariation.getVariations()) {
					variation.setSortOrder(currentCount++);
					orderTotals.add(variation);
					subTotal = subTotal.subtract(variation.getValue());
				}
			}

		}

		totalSummary.setSubTotal(subTotal);
		grandTotal = grandTotal.add(subTotal);

		OrderTotal orderTotalSubTotal = new OrderTotal();
		orderTotalSubTotal.setModule(BusinessConstants.OT_SUBTOTAL_MODULE_CODE);
		orderTotalSubTotal.setOrderTotalType(OrderTotalEnum.SUBTOTAL);
		orderTotalSubTotal.setOrderTotalCode("order.total.subtotal");
		orderTotalSubTotal.setTitle(BusinessConstants.OT_SUBTOTAL_MODULE_CODE);
		// orderTotalSubTotal.setText("order.total.subtotal");
		orderTotalSubTotal.setSortOrder(5);
		orderTotalSubTotal.setValue(subTotal);

		orderTotals.add(orderTotalSubTotal);

		// shipping
		if (summary.getShippingSummary() != null) {

			OrderTotal shippingSubTotal = new OrderTotal();
			shippingSubTotal.setModule(BusinessConstants.OT_SHIPPING_MODULE_CODE);
			shippingSubTotal.setOrderTotalType(OrderTotalEnum.SHIPPING);
			shippingSubTotal.setOrderTotalCode("order.total.shipping");
			shippingSubTotal.setTitle(BusinessConstants.OT_SHIPPING_MODULE_CODE);
			// shippingSubTotal.setText("order.total.shipping");
			shippingSubTotal.setSortOrder(100);

			orderTotals.add(shippingSubTotal);

			if (!summary.getShippingSummary().isFreeShipping()) {
				shippingSubTotal.setValue(summary.getShippingSummary().getShipping());
				grandTotal = grandTotal.add(summary.getShippingSummary().getShipping());
			} else {
				shippingSubTotal.setValue(new BigDecimal(0));
				grandTotal = grandTotal.add(new BigDecimal(0));
			}

			// check handling fees
			shippingConfiguration = shippingService.getShippingConfiguration(store);
			if (summary.getShippingSummary().getHandling() != null
					&& summary.getShippingSummary().getHandling().doubleValue() > 0) {
				if (shippingConfiguration.getHandlingFees() != null
						&& shippingConfiguration.getHandlingFees().doubleValue() > 0) {
					OrderTotal handlingubTotal = new OrderTotal();
					handlingubTotal.setModule(BusinessConstants.OT_HANDLING_MODULE_CODE);
					handlingubTotal.setOrderTotalType(OrderTotalEnum.HANDLING);
					handlingubTotal.setOrderTotalCode("order.total.handling");
					handlingubTotal.setTitle(BusinessConstants.OT_HANDLING_MODULE_CODE);
					// handlingubTotal.setText("order.total.handling");
					handlingubTotal.setSortOrder(120);
					handlingubTotal.setValue(summary.getShippingSummary().getHandling());
					orderTotals.add(handlingubTotal);
					grandTotal = grandTotal.add(summary.getShippingSummary().getHandling());
				}
			}
		}

		// tax
		List<TaxItem> taxes = taxService.calculateTax(summary, customer, store, language);
		if (taxes != null && taxes.size() > 0) {
			BigDecimal totalTaxes = new BigDecimal(0);
			totalTaxes.setScale(2, RoundingMode.HALF_UP);
			int taxCount = 200;
			for (TaxItem tax : taxes) {

				OrderTotal taxLine = new OrderTotal();
				taxLine.setModule(BusinessConstants.OT_TAX_MODULE_CODE);
				taxLine.setOrderTotalType(OrderTotalEnum.TAX);
				taxLine.setOrderTotalCode(tax.getLabel());
				taxLine.setSortOrder(taxCount);
				taxLine.setTitle(BusinessConstants.OT_TAX_MODULE_CODE);
				taxLine.setText(tax.getLabel());
				taxLine.setValue(tax.getItemPrice());

				totalTaxes = totalTaxes.add(tax.getItemPrice());
				orderTotals.add(taxLine);
				// grandTotal=grandTotal.add(tax.getItemPrice());

				taxCount++;

			}
			grandTotal = grandTotal.add(totalTaxes);
			totalSummary.setTaxTotal(totalTaxes);
		}

		// grand total
		OrderTotal orderTotal = new OrderTotal();
		orderTotal.setModule(BusinessConstants.OT_TOTAL_MODULE_CODE);
		orderTotal.setOrderTotalType(OrderTotalEnum.TOTAL);
		orderTotal.setOrderTotalCode("order.total.total");
		orderTotal.setTitle(BusinessConstants.OT_TOTAL_MODULE_CODE);
		// orderTotal.setText("order.total.total");
		orderTotal.setSortOrder(500);
		orderTotal.setValue(grandTotal);
		orderTotals.add(orderTotal);

		totalSummary.setTotal(grandTotal);
		totalSummary.setTotals(orderTotals);
		return totalSummary;

	}

	@Override
	public OrderTotalSummary caculateOrderTotal(final SalesOrderSummary orderSummary, final Customer customer,
			final MerchantStore store, final Language language) throws BusinessException {
		Validate.notNull(orderSummary, "Order summary cannot be null");
		Validate.notNull(orderSummary.getProducts(), "Order summary.products cannot be null");
		Validate.notNull(store, "MerchantStore cannot be null");
		Validate.notNull(customer, "Customer cannot be null");

		try {
			return caculateOrder(orderSummary, customer, store, language);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public OrderTotalSummary caculateOrderTotal(final SalesOrderSummary orderSummary, final MerchantStore store,
			final Language language) throws BusinessException {
		Validate.notNull(orderSummary, "Order summary cannot be null");
		Validate.notNull(orderSummary.getProducts(), "Order summary.products cannot be null");
		Validate.notNull(store, "MerchantStore cannot be null");

		try {
			return caculateOrder(orderSummary, null, store, language);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	private OrderTotalSummary caculateShoppingCart(final ShoppingCart shoppingCart, final Customer customer,
			final MerchantStore store, final Language language) throws Exception {

		SalesOrderSummary orderSummary = new SalesOrderSummary();
		orderSummary.setOrderSummaryType(OrderSummaryEnum.SHOPPINGCART);

		List<ShoppingCartItem> itemsSet = new ArrayList<ShoppingCartItem>(shoppingCart.getLineItems());
		orderSummary.setProducts(itemsSet);

		return this.caculateOrder(orderSummary, customer, store, language);

	}

	/**
	 * <p>
	 * Method will be used to calculate Shopping cart total as well will update
	 * price for each line items.
	 * </p>
	 *
	 * @param shoppingCart
	 * @param customer
	 * @param store
	 * @param language
	 * @return {@link OrderTotalSummary}
	 * @throws BusinessException
	 *
	 */
	@Override
	public OrderTotalSummary calculateShoppingCartTotal(final ShoppingCart shoppingCart, final Customer customer,
			final MerchantStore store, final Language language) throws BusinessException {
		Validate.notNull(shoppingCart, "Order summary cannot be null");
		Validate.notNull(customer, "Customery cannot be null");
		Validate.notNull(store, "MerchantStore cannot be null.");
		try {
			return caculateShoppingCart(shoppingCart, customer, store, language);
		} catch (Exception e) {
			LOGGER.error("Error while calculating shopping cart total" + e);
			throw new BusinessException(e);
		}

	}

	/**
	 * <p>
	 * Method will be used to calculate Shopping cart total as well will update
	 * price for each line items.
	 * </p>
	 *
	 * @param shoppingCart
	 * @param store
	 * @param language
	 * @return {@link OrderTotalSummary}
	 * @throws BusinessException
	 *
	 */
	@Override
	public OrderTotalSummary calculateShoppingCartTotal(final ShoppingCart shoppingCart, final MerchantStore store,
			final Language language) throws BusinessException {
		Validate.notNull(shoppingCart, "Order summary cannot be null");
		Validate.notNull(store, "MerchantStore cannot be null");

		try {
			return caculateShoppingCart(shoppingCart, null, store, language);
		} catch (Exception e) {
			LOGGER.error("Error while calculating shopping cart total" + e);
			throw new BusinessException(e);
		}
	}

	@Override
	public ByteArrayOutputStream generateInvoice(final MerchantStore store, final SalesOrder order,
			final Language language) throws BusinessException {

		Validate.notNull(order.getOrderProducts(), "Order products cannot be null");
		Validate.notNull(order.getOrderTotals(), "Order totals cannot be null");

		try {
			ByteArrayOutputStream stream = null;// invoiceModule.createInvoice(store,
												// order, language);
			return stream;
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public SalesOrder getOrder(final Long orderId) {
		return getById(orderId);
	}

	/*
	 * @Override public List<Order> listByStore(final MerchantStore
	 * merchantStore) { return listByField(Order_.merchant, merchantStore); }
	 */

	@Override
	public SalesOrderList listByStore(final MerchantStore store, final SalesOrderCriteria criteria) {

		return salesOrderRepository.listByStore(store, criteria);
	}

	@Override
	public void saveOrUpdate(final SalesOrder order) throws BusinessException {

		if (order.getId() != null && order.getId() > 0) {
			LOGGER.debug("Updating Order");
			super.update(order);
		} else {
			LOGGER.debug("Creating Order");
			super.save(order);
		}
	}

	@Override
	public boolean hasDownloadFiles(SalesOrder order) throws BusinessException {
		Validate.notNull(order, "Order cannot be null");
		Validate.notNull(order.getOrderProducts(), "Order products cannot be null");
		Validate.notEmpty(order.getOrderProducts(), "Order products cannot be empty");

		boolean hasDownloads = false;
		for (OrderProduct orderProduct : order.getOrderProducts()) {
			if (!CollectionUtils.isEmpty(orderProduct.getDownloads())) {
				hasDownloads = true;
				break;
			}
		}
		return hasDownloads;
	}

}
