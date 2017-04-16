package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A TaxClass.
 */
@Entity
@Table(name = "tax_class",uniqueConstraints=
			@UniqueConstraint(columnNames = {"MERCHANT_ID", "TAX_CLASS_CODE"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "taxclass")
public class TaxClass extends BusinessDomain<Long, TaxClass> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "TAX_CLASS_TITLE" , nullable=false , length=32 )
    private String title;

    @NotEmpty
    @Column(name="TAX_CLASS_CODE", nullable=false, length=10)
    private String code;

    @OneToMany(mappedBy = "taxClass")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> products = new HashSet<Product>();

    @OneToMany(mappedBy = "taxClass")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TaxRate> taxRates = new HashSet<TaxRate>();
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MERCHANT_ID", nullable=true)
	private MerchantStore merchantStore;
    
    public TaxClass() {
	}
    
    public TaxClass(String code) {
		this.code = code;
		this.title = code;
	}

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

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

}