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
 * A ProductOption.
 */
@Entity
@Table(name = "product_option")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productoption")
public class ProductOption extends BusinessDomain<Long,ProductOption>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "read_only")
    private Boolean readOnly;

    @Column(name = "product_option_type")
    private String productOptionType;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "product_option_sort_order")
    private Integer productOptionSortOrder;

    @OneToMany(mappedBy = "productOption")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductOptionDescription> descriptions = new HashSet<>();

    @ManyToOne
    private MerchantStore merchantStore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isReadOnly() {
        return readOnly;
    }

    public ProductOption readOnly(Boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getProductOptionType() {
        return productOptionType;
    }

    public ProductOption productOptionType(String productOptionType) {
        this.productOptionType = productOptionType;
        return this;
    }

    public void setProductOptionType(String productOptionType) {
        this.productOptionType = productOptionType;
    }

    public String getCode() {
        return code;
    }

    public ProductOption code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getProductOptionSortOrder() {
        return productOptionSortOrder;
    }

    public ProductOption productOptionSortOrder(Integer productOptionSortOrder) {
        this.productOptionSortOrder = productOptionSortOrder;
        return this;
    }

    public void setProductOptionSortOrder(Integer productOptionSortOrder) {
        this.productOptionSortOrder = productOptionSortOrder;
    }

    public Set<ProductOptionDescription> getDescriptions() {
        return descriptions;
    }

    public ProductOption descriptions(Set<ProductOptionDescription> productOptionDescriptions) {
        this.descriptions = productOptionDescriptions;
        return this;
    }

    public ProductOption addDescriptions(ProductOptionDescription productOptionDescription) {
        this.descriptions.add(productOptionDescription);
        productOptionDescription.setProductOption(this);
        return this;
    }

    public ProductOption removeDescriptions(ProductOptionDescription productOptionDescription) {
        this.descriptions.remove(productOptionDescription);
        productOptionDescription.setProductOption(null);
        return this;
    }

    public void setDescriptions(Set<ProductOptionDescription> productOptionDescriptions) {
        this.descriptions = productOptionDescriptions;
    }

    public MerchantStore getMerchantStore() {
        return merchantStore;
    }

    public ProductOption merchantStore(MerchantStore merchantStore) {
        this.merchantStore = merchantStore;
        return this;
    }

    public void setMerchantStore(MerchantStore merchantStore) {
        this.merchantStore = merchantStore;
    }

    

    
}
