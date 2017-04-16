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
 * A CustomerOption.
 */
@Entity
@Table(name = "customer_option")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customeroption")
public class CustomerOption extends BusinessDomain<Long,CustomerOption>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "customer_option_type")
    private String customerOptionType;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "public_option")
    private Boolean publicOption;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @OneToMany(mappedBy = "customerOption")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CustomerOptionDescription> descriptions = new HashSet<>();

    @ManyToOne
    private MerchantStore merchantStore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public CustomerOption active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCustomerOptionType() {
        return customerOptionType;
    }

    public CustomerOption customerOptionType(String customerOptionType) {
        this.customerOptionType = customerOptionType;
        return this;
    }

    public void setCustomerOptionType(String customerOptionType) {
        this.customerOptionType = customerOptionType;
    }

    public String getCode() {
        return code;
    }

    public CustomerOption code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isPublicOption() {
        return publicOption;
    }

    public CustomerOption publicOption(Boolean publicOption) {
        this.publicOption = publicOption;
        return this;
    }

    public void setPublicOption(Boolean publicOption) {
        this.publicOption = publicOption;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public CustomerOption sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Set<CustomerOptionDescription> getDescriptions() {
        return descriptions;
    }

    public CustomerOption descriptions(Set<CustomerOptionDescription> customerOptionDescriptions) {
        this.descriptions = customerOptionDescriptions;
        return this;
    }

    public CustomerOption addDescriptions(CustomerOptionDescription customerOptionDescription) {
        this.descriptions.add(customerOptionDescription);
        customerOptionDescription.setCustomerOption(this);
        return this;
    }

    public CustomerOption removeDescriptions(CustomerOptionDescription customerOptionDescription) {
        this.descriptions.remove(customerOptionDescription);
        customerOptionDescription.setCustomerOption(null);
        return this;
    }

    public void setDescriptions(Set<CustomerOptionDescription> customerOptionDescriptions) {
        this.descriptions = customerOptionDescriptions;
    }

    public MerchantStore getMerchantStore() {
        return merchantStore;
    }

    public CustomerOption merchantStore(MerchantStore merchantStore) {
        this.merchantStore = merchantStore;
        return this;
    }

    public void setMerchantStore(MerchantStore merchantStore) {
        this.merchantStore = merchantStore;
    }

    

    
}
