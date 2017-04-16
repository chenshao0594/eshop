package com.smartshop.eshop.service.impl;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.smartshop.eshop.catalog.product.ProductImageSize;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.domain.ProductImage;
import com.smartshop.eshop.domain.ProductImageDescription;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.repository.ProductImageRepository;
import com.smartshop.eshop.repository.search.ProductImageSearchRepository;
import com.smartshop.eshop.service.ProductImageService;
import com.smartshop.eshop.temp.ProductFileManager;
import com.smartshop.eshop.type.FileContentType;
import com.smartshop.eshop.type.ImageContentFile;
import com.smartshop.eshop.type.OutputContentFile;

/**
 * Service Implementation for managing ProductImage.
 */
@Service
@Transactional
public class ProductImageServiceImpl extends AbstractDomainServiceImpl<ProductImage, Long>
		implements ProductImageService {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductImageServiceImpl.class);
	private final ProductImageRepository productImageRepository;
	private final ProductImageSearchRepository productImageSearchRepository;

	public ProductImageServiceImpl(ProductImageRepository productImageRepository,
			ProductImageSearchRepository productImageSearchRepository) {
		super(productImageRepository, productImageSearchRepository);
		this.productImageRepository = productImageRepository;
		this.productImageSearchRepository = productImageSearchRepository;
	}

	@Inject
	private ProductFileManager productFileManager;

	@Override
	public ProductImage getById(Long id) {

		return productImageRepository.findOne(id);
	}

	@Override
	public void addProductImages(Product product, List<ProductImage> productImages) throws BusinessException {

		try {
			for (ProductImage productImage : productImages) {

				Assert.notNull(productImage.getImage());

				InputStream inputStream = productImage.getImage();
				ImageContentFile cmsContentImage = new ImageContentFile();
				cmsContentImage.setFileName(productImage.getProductImage());
				cmsContentImage.setFile(inputStream);
				cmsContentImage.setFileContentType(FileContentType.PRODUCT);

				addProductImage(product, productImage, cmsContentImage);
			}

		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public void addProductImage(Product product, ProductImage productImage, ImageContentFile inputImage)
			throws BusinessException {

		productImage.setProduct(product);

		try {

			Assert.notNull(inputImage.getFile(), "ImageContentFile.file cannot be null");

			productFileManager.addProductImage(productImage, inputImage);

			// insert ProductImage
			this.saveOrUpdate(productImage);

		} catch (Exception e) {
			throw new BusinessException(e);
		} finally {
			try {

				if (inputImage.getFile() != null) {
					inputImage.getFile().close();
				}

			} catch (Exception ignore) {

			}
		}

	}

	@Override
	public void saveOrUpdate(ProductImage productImage) throws BusinessException {

		super.save(productImage);

	}

	public void addProductImageDescription(ProductImage productImage, ProductImageDescription description)
			throws BusinessException {

		if (productImage.getDescriptions() == null) {
			productImage.setDescriptions(new HashSet<ProductImageDescription>());
		}

		productImage.getDescriptions().add(description);
		description.setProductImage(productImage);
		update(productImage);

	}

	// TODO get default product image

	@Override
	public OutputContentFile getProductImage(ProductImage productImage, ProductImageSize size)
			throws BusinessException {

		ProductImage pi = new ProductImage();
		String imageName = productImage.getProductImage();
		if (size == ProductImageSize.LARGE) {
			imageName = "L-" + imageName;
		}

		if (size == ProductImageSize.SMALL) {
			imageName = "S-" + imageName;
		}

		pi.setProductImage(imageName);
		pi.setProduct(productImage.getProduct());

		OutputContentFile outputImage = productFileManager.getProductImage(pi);

		return outputImage;

	}

	@Override
	public OutputContentFile getProductImage(final String storeCode, final String productCode, final String fileName,
			final ProductImageSize size) throws BusinessException {
		OutputContentFile outputImage = productFileManager.getProductImage(storeCode, productCode, fileName, size);
		return outputImage;

	}

	@Override
	public List<OutputContentFile> getProductImages(Product product) throws BusinessException {
		return productFileManager.getImages(product);
	}

	@Override
	public void removeProductImage(ProductImage productImage) throws BusinessException {

		if (!StringUtils.isBlank(productImage.getProductImage())) {
			productFileManager.removeProductImage(productImage);// managed
																// internally
		}

		ProductImage p = this.getById(productImage.getId());

		this.delete(p);

	}
}
