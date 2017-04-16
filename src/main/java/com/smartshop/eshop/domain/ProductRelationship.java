package com.smartshop.eshop.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

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


	@ManyToOne(targetEntity = MerchantStore.class)
	@JoinColumn(name="MERCHANT_ID",nullable=false)  
	private MerchantStore store;
	
	@ManyToOne(targetEntity = Product.class)
	@JoinColumn(name="PRODUCT_ID",updatable=false,nullable=true) 
	private Product product = null;
	
	@ManyToOne(targetEntity = Product.class)
	@JoinColumn(name="RELATED_PRODUCT_ID",updatable=false,nullable=true) 
	private Product relatedProduct = null;
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="ACTIVE")
	private boolean active = true;

    public Long getId() {
        return id;
    }

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

	public MerchantStore getStore() {
		return store;
	}

	public void setStore(MerchantStore store) {
		this.store = store;
	}

	public Product getRelatedProduct() {
		return relatedProduct;
	}

	public void setRelatedProduct(Product relatedProduct) {
		this.relatedProduct = relatedProduct;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
