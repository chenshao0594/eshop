package com.smartshop.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ProductAvailability.
 */
@Entity
@Table(name = "product_availability")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productavailability")
public class ProductAvailability extends BusinessDomain<Long,ProductAvailability>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_quantity_order_max")
    private Integer productQuantityOrderMax;

    @Column(name = "product_is_always_free_shipping")
    private Boolean productIsAlwaysFreeShipping;

    @Column(name = "region")
    private String region;

    @Column(name = "product_quantity")
    private Integer productQuantity;

    @Column(name = "product_quantity_order_min")
    private Integer productQuantityOrderMin;

    @Column(name = "product_date_available")
    private LocalDate productDateAvailable;

    @Column(name = "product_status")
    private Boolean productStatus;

    @Column(name = "region_variant")
    private String regionVariant;

    @OneToMany(mappedBy = "productAvailability")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductPrice> prices = new HashSet<>();

    @ManyToOne
    private Product product;

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

    

    
}
