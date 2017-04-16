package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A ProductAttribute.
 */
@Entity
@Table(name = "product_attribute",uniqueConstraints={
		@UniqueConstraint(columnNames={
				"OPTION_ID",
				"OPTION_VALUE_ID",
				"PRODUCT_ID"
			})
	})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productattribute")
public class ProductAttribute extends BusinessDomain<Long, ProductAttribute> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_attribute_weight", precision=10, scale=2)
    private BigDecimal productAttributeWeight;

    @Column(name = "product_attribute_price", precision=10, scale=2)
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
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="OPTION_ID", nullable=false)
	private ProductOption productOption;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="OPTION_VALUE_ID", nullable=false)
	private ProductOptionValue productOptionValue;
	
	/**
	 * This transient object property
	 * is a utility used only to submit from a free text
	 */
	@Transient
	private String attributePrice = "0";
	
	/**
	 * This transient object property
	 * is a utility used only to submit from a free text
	 */
	@Transient
	private String attributeSortOrder = "0";

	/**
	 * This transient object property
	 * is a utility used only to submit from a free text
	 */
	@Transient
	private String attributeAdditionalWeight = "0";
	

    public Long getId() {
        return id;
    }

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

	public ProductOption getProductOption() {
		return productOption;
	}

	public void setProductOption(ProductOption productOption) {
		this.productOption = productOption;
	}

	public ProductOptionValue getProductOptionValue() {
		return productOptionValue;
	}

	public void setProductOptionValue(ProductOptionValue productOptionValue) {
		this.productOptionValue = productOptionValue;
	}

	public String getAttributePrice() {
		return attributePrice;
	}

	public void setAttributePrice(String attributePrice) {
		this.attributePrice = attributePrice;
	}

	public String getAttributeSortOrder() {
		return attributeSortOrder;
	}

	public void setAttributeSortOrder(String attributeSortOrder) {
		this.attributeSortOrder = attributeSortOrder;
	}

	public String getAttributeAdditionalWeight() {
		return attributeAdditionalWeight;
	}

	public void setAttributeAdditionalWeight(String attributeAdditionalWeight) {
		this.attributeAdditionalWeight = attributeAdditionalWeight;
	}

	public Boolean getAttributeRequired() {
		return attributeRequired;
	}

	public Boolean getAttributeDefault() {
		return attributeDefault;
	}

	public Boolean getAttributeDisplayOnly() {
		return attributeDisplayOnly;
	}

	public Boolean getProductAttributeIsFree() {
		return productAttributeIsFree;
	}

	public Boolean getAttributeDiscounted() {
		return attributeDiscounted;
	}
}
