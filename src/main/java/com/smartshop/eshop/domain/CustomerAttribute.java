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
 * A CustomerAttribute.
 */
@Entity
@Table(name = "customer_attribute")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customerattribute")
public class CustomerAttribute extends BusinessDomain<Long,CustomerAttribute> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text_value")
    private String textValue;

    @ManyToOne
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextValue() {
        return textValue;
    }

    public CustomerAttribute textValue(String textValue) {
        this.textValue = textValue;
        return this;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomerAttribute customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerAttribute customerAttribute = (CustomerAttribute) o;
        if (customerAttribute.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customerAttribute.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomerAttribute{" +
            "id=" + id +
            ", textValue='" + textValue + "'" +
            '}';
    }
}
