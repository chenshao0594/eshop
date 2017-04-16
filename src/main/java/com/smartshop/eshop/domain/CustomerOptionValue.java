package com.smartshop.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CustomerOptionValue.
 */
@Entity
@Table(name = "customer_option_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customeroptionvalue")
public class CustomerOptionValue extends BusinessDomain<Long,CustomerOptionValue>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "customer_option_value_image")
    private String customerOptionValueImage;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @OneToMany(mappedBy = "customerOptionValue")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CustomerOptionValueDescription> descriptions = new HashSet<>();

    @ManyToOne
    private MerchantStore merchantStore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public CustomerOptionValue code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCustomerOptionValueImage() {
        return customerOptionValueImage;
    }

    public CustomerOptionValue customerOptionValueImage(String customerOptionValueImage) {
        this.customerOptionValueImage = customerOptionValueImage;
        return this;
    }

    public void setCustomerOptionValueImage(String customerOptionValueImage) {
        this.customerOptionValueImage = customerOptionValueImage;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public CustomerOptionValue sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Set<CustomerOptionValueDescription> getDescriptions() {
        return descriptions;
    }

    public CustomerOptionValue descriptions(Set<CustomerOptionValueDescription> customerOptionValueDescriptions) {
        this.descriptions = customerOptionValueDescriptions;
        return this;
    }

    public CustomerOptionValue addDescriptions(CustomerOptionValueDescription customerOptionValueDescription) {
        this.descriptions.add(customerOptionValueDescription);
        customerOptionValueDescription.setCustomerOptionValue(this);
        return this;
    }

    public CustomerOptionValue removeDescriptions(CustomerOptionValueDescription customerOptionValueDescription) {
        this.descriptions.remove(customerOptionValueDescription);
        customerOptionValueDescription.setCustomerOptionValue(null);
        return this;
    }

    public void setDescriptions(Set<CustomerOptionValueDescription> customerOptionValueDescriptions) {
        this.descriptions = customerOptionValueDescriptions;
    }

    public MerchantStore getMerchantStore() {
        return merchantStore;
    }

    public CustomerOptionValue merchantStore(MerchantStore merchantStore) {
        this.merchantStore = merchantStore;
        return this;
    }

    public void setMerchantStore(MerchantStore merchantStore) {
        this.merchantStore = merchantStore;
    }

    

    
}
