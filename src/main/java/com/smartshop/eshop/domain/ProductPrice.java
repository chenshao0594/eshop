package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import com.smartshop.eshop.domain.enumeration.ProductPriceEnum;

/**
 * A ProductPrice.
 */
@Entity
@Table(name = "product_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productprice")
public class ProductPrice extends BusinessDomain<Long, ProductPrice> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "default_price")
	private Boolean defaultPrice;

	@Column(name = "d_efaultpricecode")
	private String dEFAULTPRICECODE;

	@NotNull
	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "product_price_special_end_date")
	private Date productPriceSpecialEndDate;

	@Column(name = "product_price_amount", precision = 10, scale = 2)
	private BigDecimal productPriceAmount;

	@Column(name = "product_price_special_amount", precision = 10, scale = 2)
	private BigDecimal productPriceSpecialAmount;

	@Enumerated(EnumType.STRING)
	@Column(name = "product_price_type")
	private ProductPriceEnum productPriceType;

	@Column(name = "product_price_special_start_date")
	private Date productPriceSpecialStartDate;

	@OneToMany(mappedBy = "productPrice")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ProductPriceDescription> descriptions = new HashSet<>();

	@ManyToOne
	private ProductAvailability productAvailability;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Boolean isDefaultPrice() {
		return defaultPrice;
	}

	public ProductPrice defaultPrice(Boolean defaultPrice) {
		this.defaultPrice = defaultPrice;
		return this;
	}

	public void setDefaultPrice(Boolean defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public String getdEFAULTPRICECODE() {
		return dEFAULTPRICECODE;
	}

	public ProductPrice dEFAULTPRICECODE(String dEFAULTPRICECODE) {
		this.dEFAULTPRICECODE = dEFAULTPRICECODE;
		return this;
	}

	public void setdEFAULTPRICECODE(String dEFAULTPRICECODE) {
		this.dEFAULTPRICECODE = dEFAULTPRICECODE;
	}

	public String getCode() {
		return code;
	}

	public ProductPrice code(String code) {
		this.code = code;
		return this;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getProductPriceSpecialEndDate() {
		return productPriceSpecialEndDate;
	}

	public ProductPrice productPriceSpecialEndDate(Date productPriceSpecialEndDate) {
		this.productPriceSpecialEndDate = productPriceSpecialEndDate;
		return this;
	}

	public void setProductPriceSpecialEndDate(Date productPriceSpecialEndDate) {
		this.productPriceSpecialEndDate = productPriceSpecialEndDate;
	}

	public BigDecimal getProductPriceAmount() {
		return productPriceAmount;
	}

	public ProductPrice productPriceAmount(BigDecimal productPriceAmount) {
		this.productPriceAmount = productPriceAmount;
		return this;
	}

	public void setProductPriceAmount(BigDecimal productPriceAmount) {
		this.productPriceAmount = productPriceAmount;
	}

	public BigDecimal getProductPriceSpecialAmount() {
		return productPriceSpecialAmount;
	}

	public ProductPrice productPriceSpecialAmount(BigDecimal productPriceSpecialAmount) {
		this.productPriceSpecialAmount = productPriceSpecialAmount;
		return this;
	}

	public void setProductPriceSpecialAmount(BigDecimal productPriceSpecialAmount) {
		this.productPriceSpecialAmount = productPriceSpecialAmount;
	}

	public ProductPriceEnum getProductPriceType() {
		return productPriceType;
	}

	public ProductPrice productPriceType(ProductPriceEnum productPriceType) {
		this.productPriceType = productPriceType;
		return this;
	}

	public void setProductPriceType(ProductPriceEnum productPriceType) {
		this.productPriceType = productPriceType;
	}

	public Date getProductPriceSpecialStartDate() {
		return productPriceSpecialStartDate;
	}

	public ProductPrice productPriceSpecialStartDate(Date productPriceSpecialStartDate) {
		this.productPriceSpecialStartDate = productPriceSpecialStartDate;
		return this;
	}

	public void setProductPriceSpecialStartDate(Date productPriceSpecialStartDate) {
		this.productPriceSpecialStartDate = productPriceSpecialStartDate;
	}

	public Set<ProductPriceDescription> getDescriptions() {
		return descriptions;
	}

	public ProductPrice descriptions(Set<ProductPriceDescription> productPriceDescriptions) {
		this.descriptions = productPriceDescriptions;
		return this;
	}

	public ProductPrice addDescriptions(ProductPriceDescription productPriceDescription) {
		this.descriptions.add(productPriceDescription);
		productPriceDescription.setProductPrice(this);
		return this;
	}

	public ProductPrice removeDescriptions(ProductPriceDescription productPriceDescription) {
		this.descriptions.remove(productPriceDescription);
		productPriceDescription.setProductPrice(null);
		return this;
	}

	public void setDescriptions(Set<ProductPriceDescription> productPriceDescriptions) {
		this.descriptions = productPriceDescriptions;
	}

	public ProductAvailability getProductAvailability() {
		return productAvailability;
	}

	public ProductPrice productAvailability(ProductAvailability productAvailability) {
		this.productAvailability = productAvailability;
		return this;
	}

	public void setProductAvailability(ProductAvailability productAvailability) {
		this.productAvailability = productAvailability;
	}

}
