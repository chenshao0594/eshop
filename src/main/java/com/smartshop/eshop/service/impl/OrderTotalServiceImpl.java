package com.smartshop.eshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.core.salesorder.model.OrderTotalPostProcessorModule;
import com.smartshop.core.salesorder.model.OrderTotalVariation;
import com.smartshop.core.salesorder.model.RebatesOrderTotalVariation;
import com.smartshop.core.salesorder.model.SalesOrderSummary;
import com.smartshop.eshop.domain.Customer;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.OrderTotal;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.domain.ShoppingCartItem;
import com.smartshop.eshop.repository.OrderTotalRepository;
import com.smartshop.eshop.repository.search.OrderTotalSearchRepository;
import com.smartshop.eshop.service.LanguageService;
import com.smartshop.eshop.service.OrderTotalService;
import com.smartshop.eshop.service.ProductService;

/**
 * Service Implementation for managing OrderTotal.
 */
@Service
@Transactional
public class OrderTotalServiceImpl extends AbstractDomainServiceImpl<OrderTotal, Long> implements OrderTotalService {

	private final Logger LOGGER = LoggerFactory.getLogger(OrderTotalServiceImpl.class);
	private final OrderTotalRepository orderTotalRepository;
	private final OrderTotalSearchRepository orderTotalSearchRepository;

	@Autowired
	@Resource(name = "orderTotalsPostProcessors")
	List<OrderTotalPostProcessorModule> orderTotalPostProcessors;
	@Inject
	private ProductService productService;

	@Inject
	private LanguageService languageService;

	public OrderTotalServiceImpl(OrderTotalRepository orderTotalRepository,
			OrderTotalSearchRepository orderTotalSearchRepository) {
		super(orderTotalRepository, orderTotalSearchRepository);
		this.orderTotalRepository = orderTotalRepository;
		this.orderTotalSearchRepository = orderTotalSearchRepository;
	}

	@Override
	public OrderTotalVariation findOrderTotalVariation(SalesOrderSummary summary, Customer customer,
			MerchantStore store, Language language) throws Exception {

		RebatesOrderTotalVariation variation = new RebatesOrderTotalVariation();

		List<OrderTotal> totals = null;

		if (orderTotalPostProcessors != null) {
			for (OrderTotalPostProcessorModule module : orderTotalPostProcessors) {
				// TODO check if the module is enabled from the Admin

				List<ShoppingCartItem> items = summary.getProducts();
				for (ShoppingCartItem item : items) {

					Long productId = item.getProductId();
					Product product = productService.getProductForLocale(productId, language,
							languageService.toLocale(language));

					OrderTotal orderTotal = module.caculateProductPiceVariation(summary, item, product, customer,
							store);
					if (orderTotal == null) {
						continue;
					}
					if (totals == null) {
						totals = new ArrayList<OrderTotal>();
						variation.setVariations(totals);
					}
					// if product is null it will be catched when invoking the
					// module
					orderTotal.setText(product.getProductDescription().getName());
					variation.getVariations().add(orderTotal);
				}
			}
		}

		return variation;
	}

}
