package com.smartshop.eshop.temp;

import java.util.List;

import com.smartshop.eshop.catalog.product.ProductImageSize;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.domain.ProductImage;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.type.OutputContentFile;

public interface ProductImageGet extends ImageGet {

	/**
	 * Used for accessing the path directly
	 * 
	 * @param merchantStoreCode
	 * @param product
	 * @param imageName
	 * @return
	 * @throws BusinessException
	 */
	public OutputContentFile getProductImage(final String merchantStoreCode, final String productCode,
			final String imageName) throws BusinessException;

	public OutputContentFile getProductImage(final String merchantStoreCode, final String productCode,
			final String imageName, final ProductImageSize size) throws BusinessException;

	public OutputContentFile getProductImage(ProductImage productImage) throws BusinessException;

	public List<OutputContentFile> getImages(Product product) throws BusinessException;

}
