package com.smartshop.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A OrderProduct.
 */
@Entity
@Table(name = "order_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orderproduct")
public class OrderProduct extends BusinessDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_quantity")
    private Integer productQuantity;

    @Column(name = "sku")
    private String sku;

    @Column(name = "one_time_charge", precision=10, scale=2)
    private BigDecimal oneTimeCharge;

    @Column(name = "product_name")
    private String productName;

    @OneToMany(mappedBy = "orderProduct")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderProductDownload> downloads = new HashSet<>();

    @OneToMany(mappedBy = "orderProduct")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderProductAttribute> orderAttributes = new HashSet<>();

    @OneToMany(mappedBy = "orderProduct")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderProductPrice> prices = new HashSet<>();

    @ManyToOne
    private SalesOrder order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public OrderProduct productQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
        return this;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getSku() {
        return sku;
    }

    public OrderProduct sku(String sku) {
        this.sku = sku;
        return this;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getOneTimeCharge() {
        return oneTimeCharge;
    }

    public OrderProduct oneTimeCharge(BigDecimal oneTimeCharge) {
        this.oneTimeCharge = oneTimeCharge;
        return this;
    }

    public void setOneTimeCharge(BigDecimal oneTimeCharge) {
        this.oneTimeCharge = oneTimeCharge;
    }

    public String getProductName() {
        return productName;
    }

    public OrderProduct productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Set<OrderProductDownload> getDownloads() {
        return downloads;
    }

    public OrderProduct downloads(Set<OrderProductDownload> orderProductDownloads) {
        this.downloads = orderProductDownloads;
        return this;
    }

    public OrderProduct addDownloads(OrderProductDownload orderProductDownload) {
        this.downloads.add(orderProductDownload);
        orderProductDownload.setOrderProduct(this);
        return this;
    }

    public OrderProduct removeDownloads(OrderProductDownload orderProductDownload) {
        this.downloads.remove(orderProductDownload);
        orderProductDownload.setOrderProduct(null);
        return this;
    }

    public void setDownloads(Set<OrderProductDownload> orderProductDownloads) {
        this.downloads = orderProductDownloads;
    }

    public Set<OrderProductAttribute> getOrderAttributes() {
        return orderAttributes;
    }

    public OrderProduct orderAttributes(Set<OrderProductAttribute> orderProductAttributes) {
        this.orderAttributes = orderProductAttributes;
        return this;
    }

    public OrderProduct addOrderAttributes(OrderProductAttribute orderProductAttribute) {
        this.orderAttributes.add(orderProductAttribute);
        orderProductAttribute.setOrderProduct(this);
        return this;
    }

    public OrderProduct removeOrderAttributes(OrderProductAttribute orderProductAttribute) {
        this.orderAttributes.remove(orderProductAttribute);
        orderProductAttribute.setOrderProduct(null);
        return this;
    }

    public void setOrderAttributes(Set<OrderProductAttribute> orderProductAttributes) {
        this.orderAttributes = orderProductAttributes;
    }

    public Set<OrderProductPrice> getPrices() {
        return prices;
    }

    public OrderProduct prices(Set<OrderProductPrice> orderProductPrices) {
        this.prices = orderProductPrices;
        return this;
    }

    public OrderProduct addPrices(OrderProductPrice orderProductPrice) {
        this.prices.add(orderProductPrice);
        orderProductPrice.setOrderProduct(this);
        return this;
    }

    public OrderProduct removePrices(OrderProductPrice orderProductPrice) {
        this.prices.remove(orderProductPrice);
        orderProductPrice.setOrderProduct(null);
        return this;
    }

    public void setPrices(Set<OrderProductPrice> orderProductPrices) {
        this.prices = orderProductPrices;
    }

    public SalesOrder getOrder() {
        return order;
    }

    public OrderProduct order(SalesOrder salesOrder) {
        this.order = salesOrder;
        return this;
    }

    public void setOrder(SalesOrder salesOrder) {
        this.order = salesOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderProduct orderProduct = (OrderProduct) o;
        if (orderProduct.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, orderProduct.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
            "id=" + id +
            ", productQuantity='" + productQuantity + "'" +
            ", sku='" + sku + "'" +
            ", oneTimeCharge='" + oneTimeCharge + "'" +
            ", productName='" + productName + "'" +
            '}';
    }
}
