package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A ProductType.
 */
@Entity
@Table(name = "product_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "producttype")
public class ProductType extends BusinessDomain<Long, ProductType> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "allow_add_to_cart")
	private Boolean allowAddToCart;

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

	public ProductType code(String code) {
		this.code = code;
		return this;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean isAllowAddToCart() {
		return allowAddToCart;
	}

	public ProductType allowAddToCart(Boolean allowAddToCart) {
		this.allowAddToCart = allowAddToCart;
		return this;
	}

	public void setAllowAddToCart(Boolean allowAddToCart) {
		this.allowAddToCart = allowAddToCart;
	}

}
