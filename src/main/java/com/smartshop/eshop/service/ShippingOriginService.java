package com.smartshop.eshop.service;

import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.ShippingOrigin;

/**
 * Service Interface for managing ShippingOrigin.
 */
public interface ShippingOriginService extends AbstractDomainService<ShippingOrigin, Long> {
	ShippingOrigin getByStore(MerchantStore store);

}