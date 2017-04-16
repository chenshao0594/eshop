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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A CustomerOptionValueDescription.
 */
@Entity
@Table(name = "customer_option_value_desc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customeroptionvaluedescription")
public class CustomerOptionValueDescription extends BusinessDomain<Long,CustomerOptionValueDescription> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private CustomerOptionValue customerOptionValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public CustomerOptionValueDescription title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public CustomerOptionValueDescription name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CustomerOptionValueDescription description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CustomerOptionValue getCustomerOptionValue() {
        return customerOptionValue;
    }

    public CustomerOptionValueDescription customerOptionValue(CustomerOptionValue customerOptionValue) {
        this.customerOptionValue = customerOptionValue;
        return this;
    }

    public void setCustomerOptionValue(CustomerOptionValue customerOptionValue) {
        this.customerOptionValue = customerOptionValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerOptionValueDescription customerOptionValueDescription = (CustomerOptionValueDescription) o;
        if (customerOptionValueDescription.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customerOptionValueDescription.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomerOptionValueDescription{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
