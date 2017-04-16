package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A OrderProductAttribute.
 */
@Entity
@Table(name = "order_product_attribute")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orderproductattribute")
public class OrderProductAttribute extends BusinessDomain<Long, OrderProductAttribute> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_attribute_name")
    private String productAttributeName;

    @Column(name = "product_attribute_weight", precision=10, scale=2)
    private BigDecimal productAttributeWeight;

    @Column(name = "product_option_value_id")
    private Long productOptionValueId;

    @Column(name = "product_attribute_price", precision=10, scale=2)
    private BigDecimal productAttributePrice;

    @Column(name = "product_attribute_is_free")
    private Boolean productAttributeIsFree;

    @Column(name = "product_option_id")
    private Long productOptionId;

    @Column(name = "product_attribute_value_name")
    private String productAttributeValueName;

    @ManyToOne
    private OrderProduct orderProduct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductAttributeName() {
        return productAttributeName;
    }

    public OrderProductAttribute productAttributeName(String productAttributeName) {
        this.productAttributeName = productAttributeName;
        return this;
    }

    public void setProductAttributeName(String productAttributeName) {
        this.productAttributeName = productAttributeName;
    }

    public BigDecimal getProductAttributeWeight() {
        return productAttributeWeight;
    }

    public OrderProductAttribute productAttributeWeight(BigDecimal productAttributeWeight) {
        this.productAttributeWeight = productAttributeWeight;
        return this;
    }

    public void setProductAttributeWeight(BigDecimal productAttributeWeight) {
        this.productAttributeWeight = productAttributeWeight;
    }

    public Long getProductOptionValueId() {
        return productOptionValueId;
    }

    public OrderProductAttribute productOptionValueId(Long productOptionValueId) {
        this.productOptionValueId = productOptionValueId;
        return this;
    }

    public void setProductOptionValueId(Long productOptionValueId) {
        this.productOptionValueId = productOptionValueId;
    }

    public BigDecimal getProductAttributePrice() {
        return productAttributePrice;
    }

    public OrderProductAttribute productAttributePrice(BigDecimal productAttributePrice) {
        this.productAttributePrice = productAttributePrice;
        return this;
    }

    public void setProductAttributePrice(BigDecimal productAttributePrice) {
        this.productAttributePrice = productAttributePrice;
    }

    public Boolean isProductAttributeIsFree() {
        return productAttributeIsFree;
    }

    public OrderProductAttribute productAttributeIsFree(Boolean productAttributeIsFree) {
        this.productAttributeIsFree = productAttributeIsFree;
        return this;
    }

    public void setProductAttributeIsFree(Boolean productAttributeIsFree) {
        this.productAttributeIsFree = productAttributeIsFree;
    }

    public Long getProductOptionId() {
        return productOptionId;
    }

    public OrderProductAttribute productOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
        return this;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public String getProductAttributeValueName() {
        return productAttributeValueName;
    }

    public OrderProductAttribute productAttributeValueName(String productAttributeValueName) {
        this.productAttributeValueName = productAttributeValueName;
        return this;
    }

    public void setProductAttributeValueName(String productAttributeValueName) {
        this.productAttributeValueName = productAttributeValueName;
    }

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public OrderProductAttribute orderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
        return this;
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderProductAttribute orderProductAttribute = (OrderProductAttribute) o;
        if (orderProductAttribute.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, orderProductAttribute.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderProductAttribute{" +
            "id=" + id +
            ", productAttributeName='" + productAttributeName + "'" +
            ", productAttributeWeight='" + productAttributeWeight + "'" +
            ", productOptionValueId='" + productOptionValueId + "'" +
            ", productAttributePrice='" + productAttributePrice + "'" +
            ", productAttributeIsFree='" + productAttributeIsFree + "'" +
            ", productOptionId='" + productOptionId + "'" +
            ", productAttributeValueName='" + productAttributeValueName + "'" +
            '}';
    }
}
