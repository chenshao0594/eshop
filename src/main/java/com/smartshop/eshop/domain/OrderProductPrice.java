package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A OrderProductPrice.
 */
@Entity
@Table(name = "order_product_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orderproductprice")
public class OrderProductPrice extends BusinessDomain<Long,OrderProductPrice>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_price", precision=10, scale=2)
    private BigDecimal productPrice;

    @Column(name = "product_price_code")
    private String productPriceCode;

    @Column(name = "product_price_special_start_date")
    private LocalDate productPriceSpecialStartDate;

    @Column(name = "product_price_special", precision=10, scale=2)
    private BigDecimal productPriceSpecial;

    @Column(name = "product_price_special_end_date")
    private LocalDate productPriceSpecialEndDate;

    @Column(name = "product_price_name")
    private String productPriceName;

    @Column(name = "default_price")
    private Boolean defaultPrice;

    @ManyToOne
    private OrderProduct orderProduct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public OrderProductPrice productPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
        return this;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductPriceCode() {
        return productPriceCode;
    }

    public OrderProductPrice productPriceCode(String productPriceCode) {
        this.productPriceCode = productPriceCode;
        return this;
    }

    public void setProductPriceCode(String productPriceCode) {
        this.productPriceCode = productPriceCode;
    }

    public LocalDate getProductPriceSpecialStartDate() {
        return productPriceSpecialStartDate;
    }

    public OrderProductPrice productPriceSpecialStartDate(LocalDate productPriceSpecialStartDate) {
        this.productPriceSpecialStartDate = productPriceSpecialStartDate;
        return this;
    }

    public void setProductPriceSpecialStartDate(LocalDate productPriceSpecialStartDate) {
        this.productPriceSpecialStartDate = productPriceSpecialStartDate;
    }

    public BigDecimal getProductPriceSpecial() {
        return productPriceSpecial;
    }

    public OrderProductPrice productPriceSpecial(BigDecimal productPriceSpecial) {
        this.productPriceSpecial = productPriceSpecial;
        return this;
    }

    public void setProductPriceSpecial(BigDecimal productPriceSpecial) {
        this.productPriceSpecial = productPriceSpecial;
    }

    public LocalDate getProductPriceSpecialEndDate() {
        return productPriceSpecialEndDate;
    }

    public OrderProductPrice productPriceSpecialEndDate(LocalDate productPriceSpecialEndDate) {
        this.productPriceSpecialEndDate = productPriceSpecialEndDate;
        return this;
    }

    public void setProductPriceSpecialEndDate(LocalDate productPriceSpecialEndDate) {
        this.productPriceSpecialEndDate = productPriceSpecialEndDate;
    }

    public String getProductPriceName() {
        return productPriceName;
    }

    public OrderProductPrice productPriceName(String productPriceName) {
        this.productPriceName = productPriceName;
        return this;
    }

    public void setProductPriceName(String productPriceName) {
        this.productPriceName = productPriceName;
    }

    public Boolean isDefaultPrice() {
        return defaultPrice;
    }

    public OrderProductPrice defaultPrice(Boolean defaultPrice) {
        this.defaultPrice = defaultPrice;
        return this;
    }

    public void setDefaultPrice(Boolean defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public OrderProductPrice orderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
        return this;
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    

    
}
