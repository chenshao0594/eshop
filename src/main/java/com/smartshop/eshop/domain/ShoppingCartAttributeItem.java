package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A ShoppingCartAttributeItem.
 */
@Entity
@Table(name = "shopping_cart_attribute_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "shoppingcartattributeitem")
public class ShoppingCartAttributeItem extends BusinessDomain<Long, ShoppingCartAttributeItem> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "product_attribute_id")
	private Long productAttributeId;

	@ManyToOne
	private ShoppingCartItem shoppingCartItem;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductAttributeId() {
		return productAttributeId;
	}

	public ShoppingCartAttributeItem productAttributeId(Long productAttributeId) {
		this.productAttributeId = productAttributeId;
		return this;
	}

	public void setProductAttributeId(Long productAttributeId) {
		this.productAttributeId = productAttributeId;
	}

	public ShoppingCartItem getShoppingCartItem() {
		return shoppingCartItem;
	}

	public ShoppingCartAttributeItem shoppingCartItem(ShoppingCartItem shoppingCartItem) {
		this.shoppingCartItem = shoppingCartItem;
		return this;
	}

	public void setShoppingCartItem(ShoppingCartItem shoppingCartItem) {
		this.shoppingCartItem = shoppingCartItem;
	}

}
