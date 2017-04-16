package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartshop.eshop.common.SchemaConstant;

/**
 * A ProductAvailability.
 */
@Entity
@Table(name = "product_availability")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productavailability")
public class ProductAvailability extends BusinessDomain<Long, ProductAvailability>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_quantity_order_max")
    private Integer productQuantityOrderMax = 0;

    @Column(name = "product_is_always_free_shipping")
    private Boolean productIsAlwaysFreeShipping;

    @Column(name = "region")
    private String region=SchemaConstant.ALL_REGIONS;;

    @NotNull
    @Column(name = "product_quantity")
    private Integer productQuantity=0;

    @Column(name = "product_quantity_order_min")
    private Integer productQuantityOrderMin=0;

    @Column(name = "product_date_available")
    private LocalDate productDateAvailable;

    @Column(name = "product_status")
    private Boolean productStatus=true;

    @Column(name = "region_variant")
    private String regionVariant;

    @JsonIgnore
    @OneToMany(mappedBy = "productAvailability")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductPrice> prices = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;
    
    @Transient
	public ProductPrice defaultPrice() {
		
		for(ProductPrice price : prices) {
			if(price.isDefaultPrice()) {
				return price;
			}
		}
		return new ProductPrice();
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProductQuantityOrderMax() {
        return productQuantityOrderMax;
    }

    public ProductAvailability productQuantityOrderMax(Integer productQuantityOrderMax) {
        this.productQuantityOrderMax = productQuantityOrderMax;
        return this;
    }

    public void setProductQuantityOrderMax(Integer productQuantityOrderMax) {
        this.productQuantityOrderMax = productQuantityOrderMax;
    }

    public Boolean isProductIsAlwaysFreeShipping() {
        return productIsAlwaysFreeShipping;
    }

    public ProductAvailability productIsAlwaysFreeShipping(Boolean productIsAlwaysFreeShipping) {
        this.productIsAlwaysFreeShipping = productIsAlwaysFreeShipping;
        return this;
    }

    public void setProductIsAlwaysFreeShipping(Boolean productIsAlwaysFreeShipping) {
        this.productIsAlwaysFreeShipping = productIsAlwaysFreeShipping;
    }

    public String getRegion() {
        return region;
    }

    public ProductAvailability region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public ProductAvailability productQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
        return this;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Integer getProductQuantityOrderMin() {
        return productQuantityOrderMin;
    }

    public ProductAvailability productQuantityOrderMin(Integer productQuantityOrderMin) {
        this.productQuantityOrderMin = productQuantityOrderMin;
        return this;
    }

    public void setProductQuantityOrderMin(Integer productQuantityOrderMin) {
        this.productQuantityOrderMin = productQuantityOrderMin;
    }

    public LocalDate getProductDateAvailable() {
        return productDateAvailable;
    }

    public ProductAvailability productDateAvailable(LocalDate productDateAvailable) {
        this.productDateAvailable = productDateAvailable;
        return this;
    }

    public void setProductDateAvailable(LocalDate productDateAvailable) {
        this.productDateAvailable = productDateAvailable;
    }

    public Boolean isProductStatus() {
        return productStatus;
    }

    public ProductAvailability productStatus(Boolean productStatus) {
        this.productStatus = productStatus;
        return this;
    }

    public void setProductStatus(Boolean productStatus) {
        this.productStatus = productStatus;
    }

    public String getRegionVariant() {
        return regionVariant;
    }

    public ProductAvailability regionVariant(String regionVariant) {
        this.regionVariant = regionVariant;
        return this;
    }

    public void setRegionVariant(String regionVariant) {
        this.regionVariant = regionVariant;
    }

    public Set<ProductPrice> getPrices() {
        return prices;
    }

    public ProductAvailability prices(Set<ProductPrice> productPrices) {
        this.prices = productPrices;
        return this;
    }

    public ProductAvailability addPrices(ProductPrice productPrice) {
        this.prices.add(productPrice);
        productPrice.setProductAvailability(this);
        return this;
    }

    public ProductAvailability removePrices(ProductPrice productPrice) {
        this.prices.remove(productPrice);
        productPrice.setProductAvailability(null);
        return this;
    }

    public void setPrices(Set<ProductPrice> productPrices) {
        this.prices = productPrices;
    }

    public Product getProduct() {
        return product;
    }

    public ProductAvailability product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductAvailability productAvailability = (ProductAvailability) o;
        if (productAvailability.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, productAvailability.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductAvailability{" +
            "id=" + id +
            ", productQuantityOrderMax='" + productQuantityOrderMax + "'" +
            ", productIsAlwaysFreeShipping='" + productIsAlwaysFreeShipping + "'" +
            ", region='" + region + "'" +
            ", productQuantity='" + productQuantity + "'" +
            ", productQuantityOrderMin='" + productQuantityOrderMin + "'" +
            ", productDateAvailable='" + productDateAvailable + "'" +
            ", productStatus='" + productStatus + "'" +
            ", regionVariant='" + regionVariant + "'" +
            '}';
    }
}
