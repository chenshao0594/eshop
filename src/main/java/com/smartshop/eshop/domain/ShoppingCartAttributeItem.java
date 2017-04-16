package com.smartshop.eshop.domain;

import java.io.Serializable;
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
 * A ShoppingCartAttributeItem.
 */
@Entity
@Table(name = "shopping_cart_attribute_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "shoppingcartattributeitem")
public class ShoppingCartAttributeItem extends BusinessDomain<Long, ShoppingCartAttributeItem> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_attribute_id")
    private Long productAttributeId;

    @ManyToOne
    private ShoppingCartItem shoppingCartItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductAttributeId() {
        return productAttributeId;
    }

    public ShoppingCartAttributeItem productAttributeId(Long productAttributeId) {
        this.productAttributeId = productAttributeId;
        return this;
    }

    public void setProductAttributeId(Long productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public ShoppingCartItem getShoppingCartItem() {
        return shoppingCartItem;
    }

    public ShoppingCartAttributeItem shoppingCartItem(ShoppingCartItem shoppingCartItem) {
        this.shoppingCartItem = shoppingCartItem;
        return this;
    }

    public void setShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        this.shoppingCartItem = shoppingCartItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShoppingCartAttributeItem shoppingCartAttributeItem = (ShoppingCartAttributeItem) o;
        if (shoppingCartAttributeItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, shoppingCartAttributeItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ShoppingCartAttributeItem{" +
            "id=" + id +
            ", productAttributeId='" + productAttributeId + "'" +
            '}';
    }
}
