package com.smartshop.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ShoppingCartItem.
 */
@Entity
@Table(name = "shopping_cart_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "shoppingcartitem")
public class ShoppingCartItem extends BusinessDomain<Long, ShoppingCartItem> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "product_id")
	private Long productId;

	@Column(name = "quantity")
	private Integer quantity;

	@OneToMany(mappedBy = "shoppingCartItem")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ShoppingCartAttributeItem> attributes = new HashSet<>();

	@ManyToOne
	private ShoppingCart shoppingCart;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public ShoppingCartItem productId(Long productId) {
		this.productId = productId;
		return this;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public ShoppingCartItem quantity(Integer quantity) {
		this.quantity = quantity;
		return this;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Set<ShoppingCartAttributeItem> getAttributes() {
		return attributes;
	}

	public ShoppingCartItem attributes(Set<ShoppingCartAttributeItem> shoppingCartAttributeItems) {
		this.attributes = shoppingCartAttributeItems;
		return this;
	}

	public ShoppingCartItem addAttributes(ShoppingCartAttributeItem shoppingCartAttributeItem) {
		this.attributes.add(shoppingCartAttributeItem);
		shoppingCartAttributeItem.setShoppingCartItem(this);
		return this;
	}

	public ShoppingCartItem removeAttributes(ShoppingCartAttributeItem shoppingCartAttributeItem) {
		this.attributes.remove(shoppingCartAttributeItem);
		shoppingCartAttributeItem.setShoppingCartItem(null);
		return this;
	}

	public void setAttributes(Set<ShoppingCartAttributeItem> shoppingCartAttributeItems) {
		this.attributes = shoppingCartAttributeItems;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public ShoppingCartItem shoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
		return this;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

}
