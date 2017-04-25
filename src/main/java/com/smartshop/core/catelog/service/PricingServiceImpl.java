package com.smartshop.core.catelog.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.smartshop.eshop.core.catalog.product.FinalPrice;
import com.smartshop.eshop.domain.Currency;
import com.smartshop.eshop.domain.Customer;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.domain.ProductAttribute;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.utils.ProductPriceUtils;

/**
 * Contains all the logic required to calculate product price
 *
 * @author Carl Samson
 *
 */
@Service("pricingService")
public class PricingServiceImpl implements PricingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PricingServiceImpl.class);

	@Inject
	private ProductPriceUtils priceUtil;

	@Override
	public FinalPrice calculateProductPrice(Product product) throws BusinessException {
		return priceUtil.getFinalPrice(product);
	}

	@Override
	public FinalPrice calculateProductPrice(Product product, Customer customer) throws BusinessException {
		/** TODO add rules for price calculation **/
		return priceUtil.getFinalPrice(product);
	}

	@Override
	public FinalPrice calculateProductPrice(Product product, List<ProductAttribute> attributes)
			throws BusinessException {
		return priceUtil.getFinalProductPrice(product, attributes);
	}

	@Override
	public FinalPrice calculateProductPrice(Product product, List<ProductAttribute> attributes, Customer customer)
			throws BusinessException {
		/** TODO add rules for price calculation **/
		return priceUtil.getFinalProductPrice(product, attributes);
	}

	@Override
	public String getDisplayAmount(BigDecimal amount, MerchantStore store) throws BusinessException {
		try {
			String price = priceUtil.getStoreFormatedAmountWithCurrency(store, amount);
			return price;
		} catch (Exception e) {
			LOGGER.error("An error occured when trying to format an amount " + amount.toString());
			throw new BusinessException(e);
		}
	}

	@Override
	public String getDisplayAmount(BigDecimal amount, Locale locale, Currency currency, MerchantStore store)
			throws BusinessException {
		try {
			String price = priceUtil.getFormatedAmountWithCurrency(locale, currency, amount);
			return price;
		} catch (Exception e) {
			LOGGER.error("An error occured when trying to format an amunt " + amount.toString() + " using locale "
					+ locale.toString() + " and currency " + currency.toString());
			throw new BusinessException(e);
		}
	}

	@Override
	public String getStringAmount(BigDecimal amount, MerchantStore store) throws BusinessException {
		try {
			String price = priceUtil.getAdminFormatedAmount(store, amount);
			return price;
		} catch (Exception e) {
			LOGGER.error("An error occured when trying to format an amount " + amount.toString());
			throw new BusinessException(e);
		}
	}

}
