package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A ShoppingCart.
 */
@Entity
@Table(name = "shopping_cart")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "shoppingcart")
public class ShoppingCart extends BusinessDomain<Long, ShoppingCart> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "shopping_cart_code")
    private String shoppingCartCode;

    @OneToMany(mappedBy = "shoppingCart")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ShoppingCartItem> lineItems = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public ShoppingCart customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getShoppingCartCode() {
        return shoppingCartCode;
    }

    public ShoppingCart shoppingCartCode(String shoppingCartCode) {
        this.shoppingCartCode = shoppingCartCode;
        return this;
    }

    public void setShoppingCartCode(String shoppingCartCode) {
        this.shoppingCartCode = shoppingCartCode;
    }

    public Set<ShoppingCartItem> getLineItems() {
        return lineItems;
    }

    public ShoppingCart lineItems(Set<ShoppingCartItem> shoppingCartItems) {
        this.lineItems = shoppingCartItems;
        return this;
    }

    public ShoppingCart addLineItems(ShoppingCartItem shoppingCartItem) {
        this.lineItems.add(shoppingCartItem);
        shoppingCartItem.setShoppingCart(this);
        return this;
    }

    public ShoppingCart removeLineItems(ShoppingCartItem shoppingCartItem) {
        this.lineItems.remove(shoppingCartItem);
        shoppingCartItem.setShoppingCart(null);
        return this;
    }

    public void setLineItems(Set<ShoppingCartItem> shoppingCartItems) {
        this.lineItems = shoppingCartItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShoppingCart shoppingCart = (ShoppingCart) o;
        if (shoppingCart.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, shoppingCart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
            "id=" + id +
            ", customerId='" + customerId + "'" +
            ", shoppingCartCode='" + shoppingCartCode + "'" +
            '}';
    }
}
