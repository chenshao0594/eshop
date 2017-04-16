package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A ShippingOrigin.
 */
@Entity
@Table(name = "shipping_origin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "shippingorigin")
public class ShippingOrigin extends BusinessDomain<Long, ShippingOrigin> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "state")
    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public ShippingOrigin city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public ShippingOrigin postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public ShippingOrigin address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean isActive() {
        return active;
    }

    public ShippingOrigin active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getState() {
        return state;
    }

    public ShippingOrigin state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShippingOrigin shippingOrigin = (ShippingOrigin) o;
        if (shippingOrigin.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, shippingOrigin.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ShippingOrigin{" +
            "id=" + id +
            ", city='" + city + "'" +
            ", postalCode='" + postalCode + "'" +
            ", address='" + address + "'" +
            ", active='" + active + "'" +
            ", state='" + state + "'" +
            '}';
    }
}
