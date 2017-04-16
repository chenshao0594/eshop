package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A ProductAttribute.
 */
@Entity
@Table(name = "product_attribute")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productattribute")
public class ProductAttribute extends BusinessDomain<Long, ProductAttribute> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "product_attribute_weight", precision = 10, scale = 2)
	private BigDecimal productAttributeWeight;

	@Column(name = "product_attribute_price", precision = 10, scale = 2)
	private BigDecimal productAttributePrice;

	@Column(name = "attribute_required")
	private Boolean attributeRequired;

	@Column(name = "attribute_default")
	private Boolean attributeDefault;

	@Column(name = "attribute_display_only")
	private Boolean attributeDisplayOnly;

	@Column(name = "product_option_sort_order")
	private Integer productOptionSortOrder;

	@Column(name = "product_attribute_is_free")
	private Boolean productAttributeIsFree;

	@Column(name = "attribute_discounted")
	private Boolean attributeDiscounted;

	@ManyToOne
	private ProductOption productOption;

	@ManyToOne
	private Product product;

	@ManyToOne
	private ProductOptionValue productOptionValue;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getProductAttributeWeight() {
		return productAttributeWeight;
	}

	public ProductAttribute productAttributeWeight(BigDecimal productAttributeWeight) {
		this.productAttributeWeight = productAttributeWeight;
		return this;
	}

	public void setProductAttributeWeight(BigDecimal productAttributeWeight) {
		this.productAttributeWeight = productAttributeWeight;
	}

	public BigDecimal getProductAttributePrice() {
		return productAttributePrice;
	}

	public ProductAttribute productAttributePrice(BigDecimal productAttributePrice) {
		this.productAttributePrice = productAttributePrice;
		return this;
	}

	public void setProductAttributePrice(BigDecimal productAttributePrice) {
		this.productAttributePrice = productAttributePrice;
	}

	public Boolean isAttributeRequired() {
		return attributeRequired;
	}

	public ProductAttribute attributeRequired(Boolean attributeRequired) {
		this.attributeRequired = attributeRequired;
		return this;
	}

	public void setAttributeRequired(Boolean attributeRequired) {
		this.attributeRequired = attributeRequired;
	}

	public Boolean isAttributeDefault() {
		return attributeDefault;
	}

	public ProductAttribute attributeDefault(Boolean attributeDefault) {
		this.attributeDefault = attributeDefault;
		return this;
	}

	public void setAttributeDefault(Boolean attributeDefault) {
		this.attributeDefault = attributeDefault;
	}

	public Boolean isAttributeDisplayOnly() {
		return attributeDisplayOnly;
	}

	public ProductAttribute attributeDisplayOnly(Boolean attributeDisplayOnly) {
		this.attributeDisplayOnly = attributeDisplayOnly;
		return this;
	}

	public void setAttributeDisplayOnly(Boolean attributeDisplayOnly) {
		this.attributeDisplayOnly = attributeDisplayOnly;
	}

	public Integer getProductOptionSortOrder() {
		return productOptionSortOrder;
	}

	public ProductAttribute productOptionSortOrder(Integer productOptionSortOrder) {
		this.productOptionSortOrder = productOptionSortOrder;
		return this;
	}

	public void setProductOptionSortOrder(Integer productOptionSortOrder) {
		this.productOptionSortOrder = productOptionSortOrder;
	}

	public Boolean isProductAttributeIsFree() {
		return productAttributeIsFree;
	}

	public ProductAttribute productAttributeIsFree(Boolean productAttributeIsFree) {
		this.productAttributeIsFree = productAttributeIsFree;
		return this;
	}

	public void setProductAttributeIsFree(Boolean productAttributeIsFree) {
		this.productAttributeIsFree = productAttributeIsFree;
	}

	public Boolean isAttributeDiscounted() {
		return attributeDiscounted;
	}

	public ProductAttribute attributeDiscounted(Boolean attributeDiscounted) {
		this.attributeDiscounted = attributeDiscounted;
		return this;
	}

	public void setAttributeDiscounted(Boolean attributeDiscounted) {
		this.attributeDiscounted = attributeDiscounted;
	}

	public ProductOption getProductOption() {
		return productOption;
	}

	public ProductAttribute productOption(ProductOption productOption) {
		this.productOption = productOption;
		return this;
	}

	public void setProductOption(ProductOption productOption) {
		this.productOption = productOption;
	}

	public ProductOptionValue getProductOptionValue() {
		return productOptionValue;
	}

	public ProductAttribute productOptionValue(ProductOptionValue productOptionValue) {
		this.productOptionValue = productOptionValue;
		return this;
	}

	public void setProductOptionValue(ProductOptionValue productOptionValue) {
		this.productOptionValue = productOptionValue;
	}

	public Product getProduct() {
		return product;
	}

	public ProductAttribute product(Product product) {
		this.product = product;
		return this;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
