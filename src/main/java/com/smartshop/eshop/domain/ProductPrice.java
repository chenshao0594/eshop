package com.smartshop.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.smartshop.eshop.domain.enumeration.ProductPriceType;

/**
 * A ProductPrice.
 */
@Entity
@Table(name = "product_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productprice")
public class ProductPrice extends BusinessDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "default_price")
    private Boolean defaultPrice;

    @Column(name = "d_efaultpricecode")
    private String dEFAULTPRICECODE;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "product_price_special_end_date")
    private LocalDate productPriceSpecialEndDate;

    @Column(name = "product_price_amount", precision=10, scale=2)
    private BigDecimal productPriceAmount;

    @Column(name = "product_price_special_amount", precision=10, scale=2)
    private BigDecimal productPriceSpecialAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_price_type")
    private ProductPriceType productPriceType;

    @Column(name = "product_price_special_start_date")
    private LocalDate productPriceSpecialStartDate;

    @OneToMany(mappedBy = "productPrice")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductPriceDescription> descriptions = new HashSet<>();

    @ManyToOne
    private ProductAvailability productAvailability;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDefaultPrice() {
        return defaultPrice;
    }

    public ProductPrice defaultPrice(Boolean defaultPrice) {
        this.defaultPrice = defaultPrice;
        return this;
    }

    public void setDefaultPrice(Boolean defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public String getdEFAULTPRICECODE() {
        return dEFAULTPRICECODE;
    }

    public ProductPrice dEFAULTPRICECODE(String dEFAULTPRICECODE) {
        this.dEFAULTPRICECODE = dEFAULTPRICECODE;
        return this;
    }

    public void setdEFAULTPRICECODE(String dEFAULTPRICECODE) {
        this.dEFAULTPRICECODE = dEFAULTPRICECODE;
    }

    public String getCode() {
        return code;
    }

    public ProductPrice code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getProductPriceSpecialEndDate() {
        return productPriceSpecialEndDate;
    }

    public ProductPrice productPriceSpecialEndDate(LocalDate productPriceSpecialEndDate) {
        this.productPriceSpecialEndDate = productPriceSpecialEndDate;
        return this;
    }

    public void setProductPriceSpecialEndDate(LocalDate productPriceSpecialEndDate) {
        this.productPriceSpecialEndDate = productPriceSpecialEndDate;
    }

    public BigDecimal getProductPriceAmount() {
        return productPriceAmount;
    }

    public ProductPrice productPriceAmount(BigDecimal productPriceAmount) {
        this.productPriceAmount = productPriceAmount;
        return this;
    }

    public void setProductPriceAmount(BigDecimal productPriceAmount) {
        this.productPriceAmount = productPriceAmount;
    }

    public BigDecimal getProductPriceSpecialAmount() {
        return productPriceSpecialAmount;
    }

    public ProductPrice productPriceSpecialAmount(BigDecimal productPriceSpecialAmount) {
        this.productPriceSpecialAmount = productPriceSpecialAmount;
        return this;
    }

    public void setProductPriceSpecialAmount(BigDecimal productPriceSpecialAmount) {
        this.productPriceSpecialAmount = productPriceSpecialAmount;
    }

    public ProductPriceType getProductPriceType() {
        return productPriceType;
    }

    public ProductPrice productPriceType(ProductPriceType productPriceType) {
        this.productPriceType = productPriceType;
        return this;
    }

    public void setProductPriceType(ProductPriceType productPriceType) {
        this.productPriceType = productPriceType;
    }

    public LocalDate getProductPriceSpecialStartDate() {
        return productPriceSpecialStartDate;
    }

    public ProductPrice productPriceSpecialStartDate(LocalDate productPriceSpecialStartDate) {
        this.productPriceSpecialStartDate = productPriceSpecialStartDate;
        return this;
    }

    public void setProductPriceSpecialStartDate(LocalDate productPriceSpecialStartDate) {
        this.productPriceSpecialStartDate = productPriceSpecialStartDate;
    }

    public Set<ProductPriceDescription> getDescriptions() {
        return descriptions;
    }

    public ProductPrice descriptions(Set<ProductPriceDescription> productPriceDescriptions) {
        this.descriptions = productPriceDescriptions;
        return this;
    }

    public ProductPrice addDescriptions(ProductPriceDescription productPriceDescription) {
        this.descriptions.add(productPriceDescription);
        productPriceDescription.setProductPrice(this);
        return this;
    }

    public ProductPrice removeDescriptions(ProductPriceDescription productPriceDescription) {
        this.descriptions.remove(productPriceDescription);
        productPriceDescription.setProductPrice(null);
        return this;
    }

    public void setDescriptions(Set<ProductPriceDescription> productPriceDescriptions) {
        this.descriptions = productPriceDescriptions;
    }

    public ProductAvailability getProductAvailability() {
        return productAvailability;
    }

    public ProductPrice productAvailability(ProductAvailability productAvailability) {
        this.productAvailability = productAvailability;
        return this;
    }

    public void setProductAvailability(ProductAvailability productAvailability) {
        this.productAvailability = productAvailability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductPrice productPrice = (ProductPrice) o;
        if (productPrice.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, productPrice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
            "id=" + id +
            ", defaultPrice='" + defaultPrice + "'" +
            ", dEFAULTPRICECODE='" + dEFAULTPRICECODE + "'" +
            ", code='" + code + "'" +
            ", productPriceSpecialEndDate='" + productPriceSpecialEndDate + "'" +
            ", productPriceAmount='" + productPriceAmount + "'" +
            ", productPriceSpecialAmount='" + productPriceSpecialAmount + "'" +
            ", productPriceType='" + productPriceType + "'" +
            ", productPriceSpecialStartDate='" + productPriceSpecialStartDate + "'" +
            '}';
    }
}
