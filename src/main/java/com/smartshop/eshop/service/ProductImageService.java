package com.smartshop.eshop.service;

import java.util.List;

import com.smartshop.eshop.catalog.product.ProductImageSize;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.domain.ProductImage;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.type.ImageContentFile;
import com.smartshop.eshop.type.OutputContentFile;

/**
 * Service Interface for managing ProductImage.
 */
public interface ProductImageService extends AbstractDomainService<ProductImage, Long> {
	/**
	 * Add a ProductImage to the persistence and an entry to the CMS
	 * 
	 * @param product
	 * @param productImage
	 * @param file
	 * @throws BusinessException
	 */
	void addProductImage(Product product, ProductImage productImage, ImageContentFile inputImage)
			throws BusinessException;

	/**
	 * Get the image ByteArrayOutputStream and content description from CMS
	 * 
	 * @param productImage
	 * @return
	 * @throws BusinessException
	 */
	OutputContentFile getProductImage(ProductImage productImage, ProductImageSize size) throws BusinessException;

	/**
	 * Returns all Images for a given product
	 * 
	 * @param product
	 * @return
	 * @throws BusinessException
	 */
	List<OutputContentFile> getProductImages(Product product) throws BusinessException;

	void removeProductImage(ProductImage productImage) throws BusinessException;

	void saveOrUpdate(ProductImage productImage) throws BusinessException;

	/**
	 * Returns an image file from required identifier. This method is used by
	 * the image servlet
	 * 
	 * @param store
	 * @param product
	 * @param fileName
	 * @param size
	 * @return
	 * @throws BusinessException
	 */
	OutputContentFile getProductImage(String storeCode, String productCode, String fileName,
			final ProductImageSize size) throws BusinessException;

	void addProductImages(Product product, List<ProductImage> productImages) throws BusinessException;
}