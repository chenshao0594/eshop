package com.smartshop.core.shipping.integration.model;

import java.util.List;

import com.smartshop.core.shipping.model.PackageDetails;
import com.smartshop.core.shipping.model.ShippingProduct;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.exception.BusinessException;

public interface Packaging {

	public List<PackageDetails> getBoxPackagesDetails(List<ShippingProduct> products, MerchantStore store)
			throws BusinessException;

	public List<PackageDetails> getItemPackagesDetails(List<ShippingProduct> products, MerchantStore store)
			throws BusinessException;

}
