package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Language.
 */
@Entity
@Table(name = "language")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "language")
public class Language extends BusinessDomain<Long, Language> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "sort_order")
	private Integer sortOrder;

	@OneToMany(mappedBy = "defaultLanguage")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<MerchantStore> storesDefaultLanguages = new HashSet<>();

	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@ManyToMany(mappedBy = "languages", targetEntity = MerchantStore.class)
	private Set<MerchantStore> stores = new HashSet<>();

	public Language() {
	}

	public Language(String code) {
		this.setCode(code);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public Language code(String code) {
		this.code = code;
		return this;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public Language sortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
		return this;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Set<MerchantStore> getStoresDefaultLanguages() {
		return storesDefaultLanguages;
	}

	public Language storesDefaultLanguages(Set<MerchantStore> merchantStores) {
		this.storesDefaultLanguages = merchantStores;
		return this;
	}

	public Language addStoresDefaultLanguage(MerchantStore merchantStore) {
		this.storesDefaultLanguages.add(merchantStore);
		merchantStore.setDefaultLanguage(this);
		return this;
	}

	public Language removeStoresDefaultLanguage(MerchantStore merchantStore) {
		this.storesDefaultLanguages.remove(merchantStore);
		merchantStore.setDefaultLanguage(null);
		return this;
	}

	public void setStoresDefaultLanguages(Set<MerchantStore> merchantStores) {
		this.storesDefaultLanguages = merchantStores;
	}

	public Set<MerchantStore> getStores() {
		return stores;
	}

	public Language stores(Set<MerchantStore> merchantStores) {
		this.stores = merchantStores;
		return this;
	}

	public Language addStores(MerchantStore merchantStore) {
		this.stores.add(merchantStore);
		merchantStore.getLanguages().add(this);
		return this;
	}

	public Language removeStores(MerchantStore merchantStore) {
		this.stores.remove(merchantStore);
		merchantStore.getLanguages().remove(this);
		return this;
	}

	public void setStores(Set<MerchantStore> merchantStores) {
		this.stores = merchantStores;
	}

}
