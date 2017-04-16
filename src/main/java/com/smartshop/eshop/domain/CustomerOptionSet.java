package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A CustomerOptionSet.
 */
@Entity
@Table(name = "customer_option_set")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customeroptionset")
public class CustomerOptionSet extends BusinessDomain<Long, CustomerOptionSet> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sort_order")
    private Integer sortOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public CustomerOptionSet sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerOptionSet customerOptionSet = (CustomerOptionSet) o;
        if (customerOptionSet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customerOptionSet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomerOptionSet{" +
            "id=" + id +
            ", sortOrder='" + sortOrder + "'" +
            '}';
    }
}
