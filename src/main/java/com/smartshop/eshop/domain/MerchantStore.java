package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.elasticsearch.annotations.Document;

import com.smartshop.eshop.common.MeasureUnit;

/**
 * A MerchantStore.
 */
@Entity
@Table(name = "merchant_store")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "merchantstore")
public class MerchantStore extends BusinessDomain<Long, MerchantStore> implements Serializable {

	private static final long serialVersionUID = 1L;
	public final static String DEFAULT_STORE = "DEFAULT";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(name = "STORE_NAME", nullable=false, length=100)
	private String storename;
	
	@NotEmpty
	@Pattern(regexp="^[a-zA-Z0-9_]*$")
	@Column(name = "STORE_CODE", nullable=false, unique=true, length=100)
	private String code;
	
	@NotEmpty
	@Column(name = "STORE_PHONE", length=50)
	private String storephone;

	@Column(name = "STORE_ADDRESS")
	private String storeaddress;

	@NotEmpty
	@Column(name = "STORE_CITY", length=100)
	private String storecity;

	@NotEmpty
	@Column(name = "STORE_POSTAL_CODE", length=15)
	private String storepostalcode;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Country.class)
	@JoinColumn(name="COUNTRY_ID", nullable=false, updatable=true)
	private Country country;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Zone.class)
	@JoinColumn(name="ZONE_ID", nullable=true, updatable=true)
	private Zone zone;

	@Column(name = "STORE_STATE_PROVINCE", length=100)
	private String storestateprovince;
	
	@Column(name = "WEIGHTUNITCODE", length=5)
	private String weightunitcode = MeasureUnit.LB.name();

	@Column(name = "SEIZEUNITCODE", length=5)
	private String seizeunitcode = MeasureUnit.IN.name();

	@Temporal(TemporalType.DATE)
	@Column(name = "IN_BUSINESS_SINCE")
	private Date inBusinessSince = new Date();
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Language.class)
	@JoinColumn(name = "LANGUAGE_ID", nullable=false)
	private Language defaultLanguage;

	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "MERCHANT_LANGUAGE")
	private Set<Language> languages = new HashSet<Language>();
	
	@Column(name = "USE_CACHE")
	private boolean useCache = false;
	
	@Column(name="STORE_TEMPLATE", length=25)
	private String storeTemplate;
	
	@Column(name="INVOICE_TEMPLATE", length=25)
	private String invoiceTemplate;
	
	@Column(name="DOMAIN_NAME", length=80)
	private String domainName;
	
	@Column(name="CONTINUESHOPPINGURL", length=150)
	private String continueshoppingurl;
	
	@Email
	@NotEmpty
	@Column(name = "STORE_EMAIL", length=60, nullable=false)
	private String storeEmailAddress;
	
	@Column(name="STORE_LOGO", length=100)
	private String storeLogo;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Currency.class)
	@JoinColumn(name = "CURRENCY_ID", nullable=false)
	private Currency currency;
	
	@Column(name = "CURRENCY_FORMAT_NATIONAL")
	private boolean currencyFormatNational;
	@Transient
	private String dateBusinessSince;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getStoreaddress() {
		return storeaddress;
	}

	public MerchantStore storeaddress(String storeaddress) {
		this.storeaddress = storeaddress;
		return this;
	}

	public void setStoreaddress(String storeaddress) {
		this.storeaddress = storeaddress;
	}

	public String getCode() {
		return code;
	}

	public MerchantStore code(String code) {
		this.code = code;
		return this;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStorename() {
		return storename;
	}

	public MerchantStore storename(String storename) {
		this.storename = storename;
		return this;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public String getStoreEmailAddress() {
		return storeEmailAddress;
	}

	public MerchantStore storeEmailAddress(String storeEmailAddress) {
		this.storeEmailAddress = storeEmailAddress;
		return this;
	}

	public void setStoreEmailAddress(String storeEmailAddress) {
		this.storeEmailAddress = storeEmailAddress;
	}

	public String getStorephone() {
		return storephone;
	}

	public MerchantStore storephone(String storephone) {
		this.storephone = storephone;
		return this;
	}

	public void setStorephone(String storephone) {
		this.storephone = storephone;
	}

	public String getWeightunitcode() {
		return weightunitcode;
	}

	public MerchantStore weightunitcode(String weightunitcode) {
		this.weightunitcode = weightunitcode;
		return this;
	}

	public void setWeightunitcode(String weightunitcode) {
		this.weightunitcode = weightunitcode;
	}

	public Boolean isUseCache() {
		return useCache;
	}

	public MerchantStore useCache(Boolean useCache) {
		this.useCache = useCache;
		return this;
	}

	public void setUseCache(Boolean useCache) {
		this.useCache = useCache;
	}

	public String getStoreTemplate() {
		return storeTemplate;
	}

	public MerchantStore storeTemplate(String storeTemplate) {
		this.storeTemplate = storeTemplate;
		return this;
	}

	public void setStoreTemplate(String storeTemplate) {
		this.storeTemplate = storeTemplate;
	}

	public String getDomainName() {
		return domainName;
	}

	public MerchantStore domainName(String domainName) {
		this.domainName = domainName;
		return this;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getInvoiceTemplate() {
		return invoiceTemplate;
	}

	public MerchantStore invoiceTemplate(String invoiceTemplate) {
		this.invoiceTemplate = invoiceTemplate;
		return this;
	}

	public void setInvoiceTemplate(String invoiceTemplate) {
		this.invoiceTemplate = invoiceTemplate;
	}

	public String getStoreLogo() {
		return storeLogo;
	}

	public MerchantStore storeLogo(String storeLogo) {
		this.storeLogo = storeLogo;
		return this;
	}

	public void setStoreLogo(String storeLogo) {
		this.storeLogo = storeLogo;
	}

	public Boolean isCurrencyFormatNational() {
		return currencyFormatNational;
	}

	public MerchantStore currencyFormatNational(Boolean currencyFormatNational) {
		this.currencyFormatNational = currencyFormatNational;
		return this;
	}

	public void setCurrencyFormatNational(Boolean currencyFormatNational) {
		this.currencyFormatNational = currencyFormatNational;
	}

	public String getStorepostalcode() {
		return storepostalcode;
	}

	public MerchantStore storepostalcode(String storepostalcode) {
		this.storepostalcode = storepostalcode;
		return this;
	}

	public void setStorepostalcode(String storepostalcode) {
		this.storepostalcode = storepostalcode;
	}

	public String getSeizeunitcode() {
		return seizeunitcode;
	}

	public MerchantStore seizeunitcode(String seizeunitcode) {
		this.seizeunitcode = seizeunitcode;
		return this;
	}

	public void setSeizeunitcode(String seizeunitcode) {
		this.seizeunitcode = seizeunitcode;
	}

	public String getStorestateprovince() {
		return storestateprovince;
	}

	public MerchantStore storestateprovince(String storestateprovince) {
		this.storestateprovince = storestateprovince;
		return this;
	}

	public void setStorestateprovince(String storestateprovince) {
		this.storestateprovince = storestateprovince;
	}

	public String getContinueshoppingurl() {
		return continueshoppingurl;
	}

	public MerchantStore continueshoppingurl(String continueshoppingurl) {
		this.continueshoppingurl = continueshoppingurl;
		return this;
	}

	public void setContinueshoppingurl(String continueshoppingurl) {
		this.continueshoppingurl = continueshoppingurl;
	}

	public String getStorecity() {
		return storecity;
	}

	public MerchantStore storecity(String storecity) {
		this.storecity = storecity;
		return this;
	}

	public void setStorecity(String storecity) {
		this.storecity = storecity;
	}

	public Zone getZone() {
		return zone;
	}

	public MerchantStore zone(Zone zone) {
		this.zone = zone;
		return this;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public Currency getCurrency() {
		return currency;
	}

	public MerchantStore currency(Currency currency) {
		this.currency = currency;
		return this;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Country getCountry() {
		return country;
	}

	public MerchantStore country(Country country) {
		this.country = country;
		return this;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Language getDefaultLanguage() {
		return defaultLanguage;
	}

	public MerchantStore defaultLanguage(Language language) {
		this.defaultLanguage = language;
		return this;
	}

	public void setDefaultLanguage(Language language) {
		this.defaultLanguage = language;
	}

	public Set<Language> getLanguages() {
		return languages;
	}

	public MerchantStore languages(Set<Language> languages) {
		this.languages = languages;
		return this;
	}

	public MerchantStore addLanguages(Language language) {
		this.languages.add(language);
		language.getStores().add(this);
		return this;
	}

	public MerchantStore removeLanguages(Language language) {
		this.languages.remove(language);
		language.getStores().remove(this);
		return this;
	}

	public void setLanguages(Set<Language> languages) {
		this.languages = languages;
	}

	public MerchantStore dateBusinessSince(String dateBusinessSince) {
		this.dateBusinessSince = dateBusinessSince;
		return this;
	}

	public String getDateBusinessSince() {
		return dateBusinessSince;
	}

	public void setDateBusinessSince(String dateBusinessSince) {
		this.dateBusinessSince = dateBusinessSince;
	}

}
