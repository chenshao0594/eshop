package com.smartshop.eshop.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.Customer;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.domain.ProductReview;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.repository.ProductReviewRepository;
import com.smartshop.eshop.repository.search.ProductReviewSearchRepository;
import com.smartshop.eshop.service.ProductReviewService;
import com.smartshop.eshop.service.ProductService;

/**
 * Service Implementation for managing ProductReview.
 */
@Service
@Transactional
public class ProductReviewServiceImpl extends AbstractDomainServiceImpl<ProductReview, Long>
		implements ProductReviewService {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductReviewServiceImpl.class);
	private final ProductReviewRepository productReviewRepository;
	private final ProductReviewSearchRepository productReviewSearchRepository;
	@Inject
	private ProductService productService;

	public ProductReviewServiceImpl(ProductReviewRepository productReviewRepository,
			ProductReviewSearchRepository productReviewSearchRepository) {
		super(productReviewRepository, productReviewSearchRepository);
		this.productReviewRepository = productReviewRepository;
		this.productReviewSearchRepository = productReviewSearchRepository;
	}

	@Override
	public List<ProductReview> getByCustomer(Customer customer) {
		return productReviewRepository.findByCustomer(customer.getId());
	}

	@Override
	public List<ProductReview> getByProduct(Product product) {
		return productReviewRepository.findByProduct(product.getId());
	}

	@Override
	public ProductReview getByProductAndCustomer(Long productId, Long customerId) {
		return productReviewRepository.findByProductAndCustomer(productId, customerId);
	}

	@Override
	public List<ProductReview> getByProduct(Product product, Language language) {
		return productReviewRepository.findByProduct(product.getId(), language.getId());
	}

	public void create(ProductReview review) throws BusinessException {
		// refresh product
		Product product = productService.getById(review.getProduct().getId());

		// ajust product rating
		Integer count = 0;
		if (product.getProductReviewCount() != null) {
			count = product.getProductReviewCount();
		}

		BigDecimal averageRating = product.getProductReviewAvg();
		if (averageRating == null) {
			averageRating = new BigDecimal(0);
		}
		// get reviews
		BigDecimal totalRating = averageRating.multiply(new BigDecimal(count));
		totalRating = totalRating.add(new BigDecimal(review.getReviewRating()));
		count = count + 1;
		double avg = totalRating.doubleValue() / count.intValue();
		product.setProductReviewAvg(new BigDecimal(avg));
		product.setProductReviewCount(count);
		super.save(review);
		productService.update(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesmanager.core.business.services.catalog.product.review.
	 * ProductReviewService#getByProductNoObjects(com.salesmanager.core.model.
	 * catalog.product.Product)
	 */
	@Override
	public List<ProductReview> getByProductNoCustomers(Product product) {
		return productReviewRepository.findByProductNoCustomers(product.getId());
	}

}
