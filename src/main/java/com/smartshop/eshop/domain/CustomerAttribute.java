package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A CustomerAttribute.
 */
@Entity
@Table(name = "customer_attribute")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customerattribute")
public class CustomerAttribute extends BusinessDomain<Long, CustomerAttribute> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "text_value")
	private String textValue;

	@ManyToOne
	private CustomerOptionValue customerOptionValue;

	@ManyToOne
	private CustomerOption customerOption;

	@ManyToOne
	private Customer customer;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getTextValue() {
		return textValue;
	}

	public CustomerAttribute textValue(String textValue) {
		this.textValue = textValue;
		return this;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	public CustomerOptionValue getCustomerOptionValue() {
		return customerOptionValue;
	}

	public CustomerAttribute customerOptionValue(CustomerOptionValue customerOptionValue) {
		this.customerOptionValue = customerOptionValue;
		return this;
	}

	public void setCustomerOptionValue(CustomerOptionValue customerOptionValue) {
		this.customerOptionValue = customerOptionValue;
	}

	public CustomerOption getCustomerOption() {
		return customerOption;
	}

	public CustomerAttribute customerOption(CustomerOption customerOption) {
		this.customerOption = customerOption;
		return this;
	}

	public void setCustomerOption(CustomerOption customerOption) {
		this.customerOption = customerOption;
	}

	public Customer getCustomer() {
		return customer;
	}

	public CustomerAttribute customer(Customer customer) {
		this.customer = customer;
		return this;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
