package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Currency.
 */
@Entity
@Table(name = "currency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "currency")
public class Currency extends BusinessDomain<Long, Currency> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CURRENCY_CODE")
	private String code;

	@Column(name = "CURRENCY_SUPPORTED")
	private Boolean supported =true;

	@Column(name = "CURRENCY_NAME")
	private String name;

	@Column(name = "CURRENCY_CURRENCY_CODE", nullable = false, unique = true)
	private java.util.Currency currency;

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

	public Currency code(String code) {
		this.code = code;
		return this;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean isSupported() {
		return supported;
	}

	public Currency supported(Boolean supported) {
		this.supported = supported;
		return this;
	}

	public void setSupported(Boolean supported) {
		this.supported = supported;
	}

	public String getName() {
		return name;
	}

	public Currency name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public java.util.Currency getCurrency() {
		return currency;
	}

	public Currency currency(java.util.Currency currency) {
		this.currency = currency;
		return this;
	}

	public void setCurrency(java.util.Currency currency) {
		this.currency = currency;
	}

}
