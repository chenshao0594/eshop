package com.smartshop.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A TaxRate.
 */
@Entity
@Table(name = "tax_rate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "taxrate")
public class TaxRate extends BusinessDomain<Long, TaxRate> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "piggyback")
	private Boolean piggyback;

	@Column(name = "state_province")
	private String stateProvince;

	@Column(name = "tax_priority")
	private Integer taxPriority;

	@NotNull
	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "tax_rate", precision = 10, scale = 2)
	private BigDecimal taxRate;

	@OneToMany(mappedBy = "taxRate")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<TaxRateDescription> descriptions = new HashSet<>();

	@OneToMany(mappedBy = "parent")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<TaxRate> taxRates = new HashSet<>();

	@ManyToOne
	private Country country;

	@ManyToOne
	private MerchantStore merchantStore;

	@ManyToOne
	@JoinColumn(name = "TAX_CLASS_ID", nullable = false)
	private TaxClass taxClass;

	@ManyToOne
	private TaxRate parent;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ZONE_ID", nullable = true, updatable = true)
	private Zone zone;

	@Transient
	private String rateText;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Boolean isPiggyback() {
		return piggyback;
	}

	public TaxRate piggyback(Boolean piggyback) {
		this.piggyback = piggyback;
		return this;
	}

	public void setPiggyback(Boolean piggyback) {
		this.piggyback = piggyback;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public TaxRate stateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
		return this;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public Integer getTaxPriority() {
		return taxPriority;
	}

	public TaxRate taxPriority(Integer taxPriority) {
		this.taxPriority = taxPriority;
		return this;
	}

	public void setTaxPriority(Integer taxPriority) {
		this.taxPriority = taxPriority;
	}

	public String getCode() {
		return code;
	}

	public TaxRate code(String code) {
		this.code = code;
		return this;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public TaxRate taxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
		return this;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public Set<TaxRateDescription> getDescriptions() {
		return descriptions;
	}

	public TaxRate descriptions(Set<TaxRateDescription> taxRateDescriptions) {
		this.descriptions = taxRateDescriptions;
		return this;
	}

	public TaxRate addDescriptions(TaxRateDescription taxRateDescription) {
		this.descriptions.add(taxRateDescription);
		taxRateDescription.setTaxRate(this);
		return this;
	}

	public TaxRate removeDescriptions(TaxRateDescription taxRateDescription) {
		this.descriptions.remove(taxRateDescription);
		taxRateDescription.setTaxRate(null);
		return this;
	}

	public void setDescriptions(Set<TaxRateDescription> taxRateDescriptions) {
		this.descriptions = taxRateDescriptions;
	}

	public Set<TaxRate> getTaxRates() {
		return taxRates;
	}

	public TaxRate taxRates(Set<TaxRate> taxRates) {
		this.taxRates = taxRates;
		return this;
	}

	public TaxRate addTaxRates(TaxRate taxRate) {
		this.taxRates.add(taxRate);
		taxRate.setParent(this);
		return this;
	}

	public TaxRate removeTaxRates(TaxRate taxRate) {
		this.taxRates.remove(taxRate);
		taxRate.setParent(null);
		return this;
	}

	public void setTaxRates(Set<TaxRate> taxRates) {
		this.taxRates = taxRates;
	}

	public Country getCountry() {
		return country;
	}

	public TaxRate country(Country country) {
		this.country = country;
		return this;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public TaxRate merchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
		return this;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public TaxClass getTaxClass() {
		return taxClass;
	}

	public TaxRate taxClass(TaxClass taxClass) {
		this.taxClass = taxClass;
		return this;
	}

	public void setTaxClass(TaxClass taxClass) {
		this.taxClass = taxClass;
	}

	public TaxRate getParent() {
		return parent;
	}

	public TaxRate parent(TaxRate taxRate) {
		this.parent = taxRate;
		return this;
	}

	public void setParent(TaxRate taxRate) {
		this.parent = taxRate;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public String getRateText() {
		return rateText;
	}

	public void setRateText(String rateText) {
		this.rateText = rateText;
	}

	public Boolean getPiggyback() {
		return piggyback;
	}

}
