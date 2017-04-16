package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A SystemNotification.
 */
@Entity
@Table(name = "system_notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "systemnotification")
public class SystemNotification extends BusinessDomain<Long, SystemNotification> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "jhi_key")
	private String key;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "jhi_value")
	private String value;

	@ManyToOne
	private MerchantStore merchantStore;

	@ManyToOne
	private User user;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public SystemNotification endDate(LocalDate endDate) {
		this.endDate = endDate;
		return this;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getKey() {
		return key;
	}

	public SystemNotification key(String key) {
		this.key = key;
		return this;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public SystemNotification startDate(LocalDate startDate) {
		this.startDate = startDate;
		return this;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public String getValue() {
		return value;
	}

	public SystemNotification value(String value) {
		this.value = value;
		return this;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public SystemNotification merchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
		return this;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public User getUser() {
		return user;
	}

	public SystemNotification user(User user) {
		this.user = user;
		return this;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
