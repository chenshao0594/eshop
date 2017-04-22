package com.smartshop.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ProductOptionValue.
 */
@Entity
@Table(name = "product_option_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productoptionvalue")
public class ProductOptionValue extends BusinessDomain<Long, ProductOption> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_option_value_image")
    private String productOptionValueImage;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "product_option_value_sort_order")
    private Integer productOptionValueSortOrder;

    @Column(name = "product_option_display_only")
    private Boolean productOptionDisplayOnly;

    @OneToMany(mappedBy = "productOptionValue")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductOptionValueDescription> descriptions = new HashSet<>();

    @ManyToOne
    private MerchantStore merchantStore;

    @ManyToOne
    private ProductOption productOption;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductOptionValueImage() {
        return productOptionValueImage;
    }

    public ProductOptionValue productOptionValueImage(String productOptionValueImage) {
        this.productOptionValueImage = productOptionValueImage;
        return this;
    }

    public void setProductOptionValueImage(String productOptionValueImage) {
        this.productOptionValueImage = productOptionValueImage;
    }

    public String getCode() {
        return code;
    }

    public ProductOptionValue code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getProductOptionValueSortOrder() {
        return productOptionValueSortOrder;
    }

    public ProductOptionValue productOptionValueSortOrder(Integer productOptionValueSortOrder) {
        this.productOptionValueSortOrder = productOptionValueSortOrder;
        return this;
    }

    public void setProductOptionValueSortOrder(Integer productOptionValueSortOrder) {
        this.productOptionValueSortOrder = productOptionValueSortOrder;
    }

    public Boolean isProductOptionDisplayOnly() {
        return productOptionDisplayOnly;
    }

    public ProductOptionValue productOptionDisplayOnly(Boolean productOptionDisplayOnly) {
        this.productOptionDisplayOnly = productOptionDisplayOnly;
        return this;
    }

    public void setProductOptionDisplayOnly(Boolean productOptionDisplayOnly) {
        this.productOptionDisplayOnly = productOptionDisplayOnly;
    }

    public Set<ProductOptionValueDescription> getDescriptions() {
        return descriptions;
    }

    public ProductOptionValue descriptions(Set<ProductOptionValueDescription> productOptionValueDescriptions) {
        this.descriptions = productOptionValueDescriptions;
        return this;
    }

    public ProductOptionValue addDescriptions(ProductOptionValueDescription productOptionValueDescription) {
        this.descriptions.add(productOptionValueDescription);
        productOptionValueDescription.setProductOptionValue(this);
        return this;
    }

    public ProductOptionValue removeDescriptions(ProductOptionValueDescription productOptionValueDescription) {
        this.descriptions.remove(productOptionValueDescription);
        productOptionValueDescription.setProductOptionValue(null);
        return this;
    }

    public void setDescriptions(Set<ProductOptionValueDescription> productOptionValueDescriptions) {
        this.descriptions = productOptionValueDescriptions;
    }

    public MerchantStore getMerchantStore() {
        return merchantStore;
    }

    public ProductOptionValue merchantStore(MerchantStore merchantStore) {
        this.merchantStore = merchantStore;
        return this;
    }

    public void setMerchantStore(MerchantStore merchantStore) {
        this.merchantStore = merchantStore;
    }

    public ProductOption getProductOption() {
        return productOption;
    }

    public ProductOptionValue productOption(ProductOption productOption) {
        this.productOption = productOption;
        return this;
    }

    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductOptionValue productOptionValue = (ProductOptionValue) o;
        if (productOptionValue.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, productOptionValue.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductOptionValue{" +
            "id=" + id +
            ", productOptionValueImage='" + productOptionValueImage + "'" +
            ", code='" + code + "'" +
            ", productOptionValueSortOrder='" + productOptionValueSortOrder + "'" +
            ", productOptionDisplayOnly='" + productOptionDisplayOnly + "'" +
            '}';
    }
}
