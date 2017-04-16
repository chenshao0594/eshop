package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Optin.
 */
@Entity
@Table(name = "optin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "optin")
public class Optin extends BusinessDomain<Long, Optin> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "description")
	private String description;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "code")
	private String code;

	@Column(name = "end_date")
	private LocalDate endDate;

	@ManyToOne
	private MerchantStore merchant;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public Optin description(String description) {
		this.description = description;
		return this;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public Optin startDate(LocalDate startDate) {
		this.startDate = startDate;
		return this;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public String getCode() {
		return code;
	}

	public Optin code(String code) {
		this.code = code;
		return this;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public Optin endDate(LocalDate endDate) {
		this.endDate = endDate;
		return this;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public MerchantStore getMerchant() {
		return merchant;
	}

	public Optin merchant(MerchantStore merchantStore) {
		this.merchant = merchantStore;
		return this;
	}

	public void setMerchant(MerchantStore merchantStore) {
		this.merchant = merchantStore;
	}

}
