package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A ProductRelationship.
 */
@Entity
@Table(name = "product_relationship")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productrelationship")
public class ProductRelationship extends BusinessDomain<Long, ProductRelationship> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "active")
	private Boolean active;

	@ManyToOne
	private Product relatedProduct;

	@ManyToOne
	private MerchantStore store;

	@ManyToOne
	private Product product;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public ProductRelationship code(String code) {
		this.code = code;
		return this;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean isActive() {
		return active;
	}

	public ProductRelationship active(Boolean active) {
		this.active = active;
		return this;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Product getRelatedProduct() {
		return relatedProduct;
	}

	public ProductRelationship relatedProduct(Product product) {
		this.relatedProduct = product;
		return this;
	}

	public void setRelatedProduct(Product product) {
		this.relatedProduct = product;
	}

	public MerchantStore getStore() {
		return store;
	}

	public ProductRelationship store(MerchantStore merchantStore) {
		this.store = merchantStore;
		return this;
	}

	public void setStore(MerchantStore merchantStore) {
		this.store = merchantStore;
	}

	public Product getProduct() {
		return product;
	}

	public ProductRelationship product(Product product) {
		this.product = product;
		return this;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
