package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "product")
public class Product extends BusinessDomain<Long, Product> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "metatag_description")
	private String metatagDescription;

	@Column(name = "search_url")
	private String searchUrl;

	@Column(name = "metatag_keywords")
	private String metatagKeywords;

	@Column(name = "product_highlight")
	private String productHighlight;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "metatag_title")
	private String metatagTitle;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "product_external_dl")
	private String productExternalDl;
	
	

	@Column(name = "product_height", precision = 10, scale = 2)
	private BigDecimal productHeight;

	@Column(name = "product_weight", precision = 10, scale = 2)
	private BigDecimal productWeight;

	@Column(name = "product_shipeable")
	private Boolean productShipeable;

	@Column(name = "product_ordered")
	private Integer productOrdered;

	@Column(name = "product_review_avg", precision = 10, scale = 2)
	private BigDecimal productReviewAvg;

	@Column(name = "date_available")
	private LocalDate dateAvailable;

	@Column(name = "sort_order")
	private Integer sortOrder;

	@Column(name = "product_is_free")
	private Boolean productIsFree;

	@Column(name = "available")
	private Boolean available;

	@Column(name = "product_review_count")
	private Integer productReviewCount;

	@Column(name = "ref_sku")
	private String refSku;

	@Column(name = "product_virtual")
	private Boolean productVirtual;

	@Column(name = "product_width", precision = 10, scale = 2)
	private BigDecimal productWidth;

	@Column(name = "pre_order")
	private Boolean preOrder=true;

	@Column(name = "product_length", precision = 10, scale = 2)
	private BigDecimal productLength;

	@NotNull
	@Column(name = "sku", nullable = false)
	private String sku;

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ProductImage> images = new HashSet<>();

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ProductAvailability> availabilities = new HashSet<>();

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ProductAttribute> attributes = new HashSet<>();

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ProductDescription> descriptions = new HashSet<>();

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ProductRelationship> relationships = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private Manufacturer manufacturer;

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private ProductType type;

	@ManyToOne(fetch = FetchType.LAZY)
	private MerchantStore merchantStore;

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private TaxClass taxClass;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	@JoinTable(name = "PRODUCT_CATEGORY", joinColumns = {
			@JoinColumn(name = "PRODUCT_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "CATEGORY_ID", nullable = false, updatable = false) })
	@Cascade({ org.hibernate.annotations.CascadeType.DETACH, org.hibernate.annotations.CascadeType.LOCK,
			org.hibernate.annotations.CascadeType.REFRESH, org.hibernate.annotations.CascadeType.REPLICATE

	})
	private Set<Category> categories = new HashSet<Category>();

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getProductHeight() {
		return productHeight;
	}

	public Product productHeight(BigDecimal productHeight) {
		this.productHeight = productHeight;
		return this;
	}

	public void setProductHeight(BigDecimal productHeight) {
		this.productHeight = productHeight;
	}

	public BigDecimal getProductWeight() {
		return productWeight;
	}

	public Product productWeight(BigDecimal productWeight) {
		this.productWeight = productWeight;
		return this;
	}

	public void setProductWeight(BigDecimal productWeight) {
		this.productWeight = productWeight;
	}

	public Boolean isProductShipeable() {
		return productShipeable;
	}

	public Product productShipeable(Boolean productShipeable) {
		this.productShipeable = productShipeable;
		return this;
	}

	public void setProductShipeable(Boolean productShipeable) {
		this.productShipeable = productShipeable;
	}

	public Integer getProductOrdered() {
		return productOrdered;
	}

	public Product productOrdered(Integer productOrdered) {
		this.productOrdered = productOrdered;
		return this;
	}

	public void setProductOrdered(Integer productOrdered) {
		this.productOrdered = productOrdered;
	}

	public BigDecimal getProductReviewAvg() {
		return productReviewAvg;
	}

	public Product productReviewAvg(BigDecimal productReviewAvg) {
		this.productReviewAvg = productReviewAvg;
		return this;
	}

	public void setProductReviewAvg(BigDecimal productReviewAvg) {
		this.productReviewAvg = productReviewAvg;
	}

	public LocalDate getDateAvailable() {
		return dateAvailable;
	}

	public Product dateAvailable(LocalDate dateAvailable) {
		this.dateAvailable = dateAvailable;
		return this;
	}

	public void setDateAvailable(LocalDate dateAvailable) {
		this.dateAvailable = dateAvailable;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public Product sortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
		return this;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Boolean isProductIsFree() {
		return productIsFree;
	}

	public Product productIsFree(Boolean productIsFree) {
		this.productIsFree = productIsFree;
		return this;
	}

	public void setProductIsFree(Boolean productIsFree) {
		this.productIsFree = productIsFree;
	}

	public Boolean isAvailable() {
		return available;
	}

	public Product available(Boolean available) {
		this.available = available;
		return this;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Integer getProductReviewCount() {
		return productReviewCount;
	}

	public Product productReviewCount(Integer productReviewCount) {
		this.productReviewCount = productReviewCount;
		return this;
	}

	public void setProductReviewCount(Integer productReviewCount) {
		this.productReviewCount = productReviewCount;
	}

	public String getRefSku() {
		return refSku;
	}

	public Product refSku(String refSku) {
		this.refSku = refSku;
		return this;
	}

	public void setRefSku(String refSku) {
		this.refSku = refSku;
	}

	public Boolean isProductVirtual() {
		return productVirtual;
	}

	public Product productVirtual(Boolean productVirtual) {
		this.productVirtual = productVirtual;
		return this;
	}

	public void setProductVirtual(Boolean productVirtual) {
		this.productVirtual = productVirtual;
	}

	public BigDecimal getProductWidth() {
		return productWidth;
	}

	public Product productWidth(BigDecimal productWidth) {
		this.productWidth = productWidth;
		return this;
	}

	public void setProductWidth(BigDecimal productWidth) {
		this.productWidth = productWidth;
	}

	public Boolean isPreOrder() {
		return preOrder;
	}

	public Product preOrder(Boolean preOrder) {
		this.preOrder = preOrder;
		return this;
	}

	public void setPreOrder(Boolean preOrder) {
		this.preOrder = preOrder;
	}

	public BigDecimal getProductLength() {
		return productLength;
	}

	public Product productLength(BigDecimal productLength) {
		this.productLength = productLength;
		return this;
	}

	public void setProductLength(BigDecimal productLength) {
		this.productLength = productLength;
	}

	public String getSku() {
		return sku;
	}

	public Product sku(String sku) {
		this.sku = sku;
		return this;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Set<ProductImage> getImages() {
		return images;
	}

	public Product images(Set<ProductImage> productImages) {
		this.images = productImages;
		return this;
	}

	public Product addImages(ProductImage productImage) {
		this.images.add(productImage);
		productImage.setProduct(this);
		return this;
	}

	public Product removeImages(ProductImage productImage) {
		this.images.remove(productImage);
		productImage.setProduct(null);
		return this;
	}

	public void setImages(Set<ProductImage> productImages) {
		this.images = productImages;
	}

	public Set<ProductAvailability> getAvailabilities() {
		return availabilities;
	}

	public Product availabilities(Set<ProductAvailability> productAvailabilities) {
		this.availabilities = productAvailabilities;
		return this;
	}

	public Product addAvailabilities(ProductAvailability productAvailability) {
		this.availabilities.add(productAvailability);
		productAvailability.setProduct(this);
		return this;
	}

	public Product removeAvailabilities(ProductAvailability productAvailability) {
		this.availabilities.remove(productAvailability);
		productAvailability.setProduct(null);
		return this;
	}

	public void setAvailabilities(Set<ProductAvailability> productAvailabilities) {
		this.availabilities = productAvailabilities;
	}

	public Set<ProductAttribute> getAttributes() {
		return attributes;
	}

	public Product attributes(Set<ProductAttribute> productAttributes) {
		this.attributes = productAttributes;
		return this;
	}

	public Product addAttributes(ProductAttribute productAttribute) {
		this.attributes.add(productAttribute);
		productAttribute.setProduct(this);
		return this;
	}

	public Product removeAttributes(ProductAttribute productAttribute) {
		this.attributes.remove(productAttribute);
		productAttribute.setProduct(null);
		return this;
	}

	public void setAttributes(Set<ProductAttribute> productAttributes) {
		this.attributes = productAttributes;
	}

	public Set<ProductDescription> getDescriptions() {
		return descriptions;
	}

	public Product descriptions(Set<ProductDescription> productDescriptions) {
		this.descriptions = productDescriptions;
		return this;
	}

	public Product addDescriptions(ProductDescription productDescription) {
		this.descriptions.add(productDescription);
		productDescription.setProduct(this);
		return this;
	}

	public Product removeDescriptions(ProductDescription productDescription) {
		this.descriptions.remove(productDescription);
		productDescription.setProduct(null);
		return this;
	}

	public void setDescriptions(Set<ProductDescription> productDescriptions) {
		this.descriptions = productDescriptions;
	}

	public Set<ProductRelationship> getRelationships() {
		return relationships;
	}

	public Product relationships(Set<ProductRelationship> productRelationships) {
		this.relationships = productRelationships;
		return this;
	}

	public Product addRelationships(ProductRelationship productRelationship) {
		this.relationships.add(productRelationship);
		productRelationship.setProduct(this);
		return this;
	}

	public Product removeRelationships(ProductRelationship productRelationship) {
		this.relationships.remove(productRelationship);
		productRelationship.setProduct(null);
		return this;
	}

	public void setRelationships(Set<ProductRelationship> productRelationships) {
		this.relationships = productRelationships;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public Product manufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
		return this;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public ProductType getType() {
		return type;
	}

	public Product type(ProductType productType) {
		this.type = productType;
		return this;
	}

	public void setType(ProductType productType) {
		this.type = productType;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public Product merchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
		return this;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public TaxClass getTaxClass() {
		return taxClass;
	}

	public Product taxClass(TaxClass taxClass) {
		this.taxClass = taxClass;
		return this;
	}

	public void setTaxClass(TaxClass taxClass) {
		this.taxClass = taxClass;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public String getMetatagDescription() {
		return metatagDescription;
	}

	public void setMetatagDescription(String metatagDescription) {
		this.metatagDescription = metatagDescription;
	}

	public String getSearchUrl() {
		return searchUrl;
	}

	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}

	public String getMetatagKeywords() {
		return metatagKeywords;
	}

	public void setMetatagKeywords(String metatagKeywords) {
		this.metatagKeywords = metatagKeywords;
	}

	public String getProductHighlight() {
		return productHighlight;
	}

	public void setProductHighlight(String productHighlight) {
		this.productHighlight = productHighlight;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMetatagTitle() {
		return metatagTitle;
	}

	public void setMetatagTitle(String metatagTitle) {
		this.metatagTitle = metatagTitle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductExternalDl() {
		return productExternalDl;
	}

	public void setProductExternalDl(String productExternalDl) {
		this.productExternalDl = productExternalDl;
	}

	public Boolean getProductShipeable() {
		return productShipeable;
	}

	public Boolean getProductIsFree() {
		return productIsFree;
	}

	public Boolean getProductVirtual() {
		return productVirtual;
	}
	

}
