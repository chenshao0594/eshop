package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A CustomerOptin.
 */
@Entity
@Table(name = "customer_optin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customeroptin")
public class CustomerOptin extends BusinessDomain<Long, CustomerOptin> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "optin_date")
	private LocalDate optinDate;

	@Column(name = "email")
	private String email;

	@Column(name = "jhi_value")
	private String value;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@ManyToOne
	private Optin optin;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getOptinDate() {
		return optinDate;
	}

	public CustomerOptin optinDate(LocalDate optinDate) {
		this.optinDate = optinDate;
		return this;
	}

	public void setOptinDate(LocalDate optinDate) {
		this.optinDate = optinDate;
	}

	public String getEmail() {
		return email;
	}

	public CustomerOptin email(String email) {
		this.email = email;
		return this;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getValue() {
		return value;
	}

	public CustomerOptin value(String value) {
		this.value = value;
		return this;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFirstName() {
		return firstName;
	}

	public CustomerOptin firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public CustomerOptin lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Optin getOptin() {
		return optin;
	}

	public CustomerOptin optin(Optin optin) {
		this.optin = optin;
		return this;
	}

	public void setOptin(Optin optin) {
		this.optin = optin;
	}

}
