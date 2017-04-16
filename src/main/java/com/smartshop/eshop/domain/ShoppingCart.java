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
 * A ShoppingCart.
 */
@Entity
@Table(name = "shopping_cart")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "shoppingcart")
public class ShoppingCart extends BusinessDomain<Long, ShoppingCart> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "shopping_cart_code")
	private String shoppingCartCode;

	@OneToMany(mappedBy = "shoppingCart")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ShoppingCartItem> lineItems = new HashSet<>();

	@ManyToOne
	private MerchantStore merchantStore;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public ShoppingCart customerId(Long customerId) {
		this.customerId = customerId;
		return this;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getShoppingCartCode() {
		return shoppingCartCode;
	}

	public ShoppingCart shoppingCartCode(String shoppingCartCode) {
		this.shoppingCartCode = shoppingCartCode;
		return this;
	}

	public void setShoppingCartCode(String shoppingCartCode) {
		this.shoppingCartCode = shoppingCartCode;
	}

	public Set<ShoppingCartItem> getLineItems() {
		return lineItems;
	}

	public ShoppingCart lineItems(Set<ShoppingCartItem> shoppingCartItems) {
		this.lineItems = shoppingCartItems;
		return this;
	}

	public ShoppingCart addLineItems(ShoppingCartItem shoppingCartItem) {
		this.lineItems.add(shoppingCartItem);
		shoppingCartItem.setShoppingCart(this);
		return this;
	}

	public ShoppingCart removeLineItems(ShoppingCartItem shoppingCartItem) {
		this.lineItems.remove(shoppingCartItem);
		shoppingCartItem.setShoppingCart(null);
		return this;
	}

	public void setLineItems(Set<ShoppingCartItem> shoppingCartItems) {
		this.lineItems = shoppingCartItems;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public ShoppingCart merchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
		return this;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

}
