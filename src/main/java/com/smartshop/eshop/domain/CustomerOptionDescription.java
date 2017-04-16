package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A CustomerOptionDescription.
 */
@Entity
@Table(name = "customer_option_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customeroptiondescription")
public class CustomerOptionDescription extends BusinessDomain<Long, CustomerOptionDescription> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "customer_option_comment")
	private String customerOptionComment;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@ManyToOne
	private Language language;

	@ManyToOne
	private CustomerOption customerOption;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public CustomerOptionDescription title(String title) {
		this.title = title;
		return this;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCustomerOptionComment() {
		return customerOptionComment;
	}

	public CustomerOptionDescription customerOptionComment(String customerOptionComment) {
		this.customerOptionComment = customerOptionComment;
		return this;
	}

	public void setCustomerOptionComment(String customerOptionComment) {
		this.customerOptionComment = customerOptionComment;
	}

	public String getName() {
		return name;
	}

	public CustomerOptionDescription name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public CustomerOptionDescription description(String description) {
		this.description = description;
		return this;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Language getLanguage() {
		return language;
	}

	public CustomerOptionDescription language(Language language) {
		this.language = language;
		return this;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public CustomerOption getCustomerOption() {
		return customerOption;
	}

	public CustomerOptionDescription customerOption(CustomerOption customerOption) {
		this.customerOption = customerOption;
		return this;
	}

	public void setCustomerOption(CustomerOption customerOption) {
		this.customerOption = customerOption;
	}

}
