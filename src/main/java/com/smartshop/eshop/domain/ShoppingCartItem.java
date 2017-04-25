package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartshop.eshop.core.catalog.product.FinalPrice;

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

	@Transient
	private boolean productVirtual;
	@Transient
	private BigDecimal itemPrice;// item final price including all rebates

	@Transient
	private BigDecimal subTotal;// item final price * quantity

	@Transient
	private FinalPrice finalPrice;// contains price details (raw prices)

	@Transient
	private Product product;

	@Transient
	private boolean obsolete = false;

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

	public boolean isProductVirtual() {
		return productVirtual;
	}

	public void setProductVirtual(boolean productVirtual) {
		this.productVirtual = productVirtual;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public FinalPrice getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(FinalPrice finalPrice) {
		this.finalPrice = finalPrice;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public boolean isObsolete() {
		return obsolete;
	}

	public void setObsolete(boolean obsolete) {
		this.obsolete = obsolete;
	}

}
