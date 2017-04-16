package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CustomerOptionSet.
 */
@Entity
@Table(name = "customer_option_set")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customeroptionset")
public class CustomerOptionSet extends BusinessDomain<Long,CustomerOptionSet>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @ManyToOne
    private CustomerOption customerOption;

    @ManyToOne
    private CustomerOptionValue customerOptionValue;

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

    public CustomerOption getCustomerOption() {
        return customerOption;
    }

    public CustomerOptionSet customerOption(CustomerOption customerOption) {
        this.customerOption = customerOption;
        return this;
    }

    public void setCustomerOption(CustomerOption customerOption) {
        this.customerOption = customerOption;
    }

    public CustomerOptionValue getCustomerOptionValue() {
        return customerOptionValue;
    }

    public CustomerOptionSet customerOptionValue(CustomerOptionValue customerOptionValue) {
        this.customerOptionValue = customerOptionValue;
        return this;
    }

    public void setCustomerOptionValue(CustomerOptionValue customerOptionValue) {
        this.customerOptionValue = customerOptionValue;
    }

    

    
}
