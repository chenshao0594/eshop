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
 * A TaxClass.
 */
@Entity
@Table(name = "tax_class")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "taxclass")
public class TaxClass extends BusinessDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @OneToMany(mappedBy = "taxClass")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "taxClass")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TaxRate> taxRates = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public TaxClass title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public TaxClass code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public TaxClass products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public TaxClass addProducts(Product product) {
        this.products.add(product);
        product.setTaxClass(this);
        return this;
    }

    public TaxClass removeProducts(Product product) {
        this.products.remove(product);
        product.setTaxClass(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<TaxRate> getTaxRates() {
        return taxRates;
    }

    public TaxClass taxRates(Set<TaxRate> taxRates) {
        this.taxRates = taxRates;
        return this;
    }

    public TaxClass addTaxRates(TaxRate taxRate) {
        this.taxRates.add(taxRate);
        taxRate.setTaxClass(this);
        return this;
    }

    public TaxClass removeTaxRates(TaxRate taxRate) {
        this.taxRates.remove(taxRate);
        taxRate.setTaxClass(null);
        return this;
    }

    public void setTaxRates(Set<TaxRate> taxRates) {
        this.taxRates = taxRates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaxClass taxClass = (TaxClass) o;
        if (taxClass.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, taxClass.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TaxClass{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", code='" + code + "'" +
            '}';
    }
}
