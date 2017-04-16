package com.smartshop.eshop.service;

import java.util.List;

import com.smartshop.eshop.domain.Customer;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.domain.ProductReview;

/**
 * Service Interface for managing ProductReview.
 */
public interface ProductReviewService extends AbstractDomainService< ProductReview, Long>{
	List<ProductReview> getByCustomer(Customer customer);
	List<ProductReview> getByProduct(Product product);
	List<ProductReview> getByProduct(Product product, Language language);
	ProductReview getByProductAndCustomer(Long productId, Long customerId);
	List<ProductReview> getByProductNoCustomers(Product product);

 }