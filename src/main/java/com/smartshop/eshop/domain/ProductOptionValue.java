package com.smartshop.eshop.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A ProductOptionValue.
 */
@Entity
@Table(name = "product_option_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productoptionvalue")
public class ProductOptionValue extends BusinessDomain<Long, ProductOption> implements BusinessDomainInterface {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "product_option_value_image")
	private String productOptionValueImage;

	@NotNull
	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "product_option_value_sort_order")
	private Integer productOptionValueSortOrder;

	@Column(name = "product_option_display_only")
	private Boolean productOptionDisplayOnly;

	@OneToMany(mappedBy = "productOptionValue")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ProductOptionValueDescription> descriptions = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	private MerchantStore merchantStore;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private ProductOption productOption;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductOptionValueImage() {
		return productOptionValueImage;
	}

	public ProductOptionValue productOptionValueImage(String productOptionValueImage) {
		this.productOptionValueImage = productOptionValueImage;
		return this;
	}

	public void setProductOptionValueImage(String productOptionValueImage) {
		this.productOptionValueImage = productOptionValueImage;
	}

	public String getCode() {
		return code;
	}

	public ProductOptionValue code(String code) {
		this.code = code;
		return this;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getProductOptionValueSortOrder() {
		return productOptionValueSortOrder;
	}

	public ProductOptionValue productOptionValueSortOrder(Integer productOptionValueSortOrder) {
		this.productOptionValueSortOrder = productOptionValueSortOrder;
		return this;
	}

	public void setProductOptionValueSortOrder(Integer productOptionValueSortOrder) {
		this.productOptionValueSortOrder = productOptionValueSortOrder;
	}

	public Boolean isProductOptionDisplayOnly() {
		return productOptionDisplayOnly;
	}

	public ProductOptionValue productOptionDisplayOnly(Boolean productOptionDisplayOnly) {
		this.productOptionDisplayOnly = productOptionDisplayOnly;
		return this;
	}

	public void setProductOptionDisplayOnly(Boolean productOptionDisplayOnly) {
		this.productOptionDisplayOnly = productOptionDisplayOnly;
	}

	public Set<ProductOptionValueDescription> getDescriptions() {
		return descriptions;
	}

	public ProductOptionValue descriptions(Set<ProductOptionValueDescription> productOptionValueDescriptions) {
		this.descriptions = productOptionValueDescriptions;
		return this;
	}

	public ProductOptionValue addDescriptions(ProductOptionValueDescription productOptionValueDescription) {
		this.descriptions.add(productOptionValueDescription);
		productOptionValueDescription.setProductOptionValue(this);
		return this;
	}

	public ProductOptionValue removeDescriptions(ProductOptionValueDescription productOptionValueDescription) {
		this.descriptions.remove(productOptionValueDescription);
		productOptionValueDescription.setProductOptionValue(null);
		return this;
	}

	public void setDescriptions(Set<ProductOptionValueDescription> productOptionValueDescriptions) {
		this.descriptions = productOptionValueDescriptions;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public ProductOptionValue merchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
		return this;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public ProductOption getProductOption() {
		return productOption;
	}

	public ProductOptionValue productOption(ProductOption productOption) {
		this.productOption = productOption;
		return this;
	}

	public void setProductOption(ProductOption productOption) {
		this.productOption = productOption;
	}

}
