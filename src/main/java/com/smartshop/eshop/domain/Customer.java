package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartshop.eshop.domain.common.Billing;
import com.smartshop.eshop.domain.common.Delivery;
import com.smartshop.eshop.domain.enumeration.CustomerGender;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customer")
public class Customer extends BusinessDomain<Long, Customer> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@NotEmpty
	@Column (name ="LAST_NAME", length=64, nullable=false)
	private String lastName;

	@NotEmpty
	@Column (name ="FIRST_NAME", length=64, nullable=false)
	private String firstName;
	

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private CustomerGender gender;

	@Column(name = "anonymous")
	private Boolean anonymous;

	@Column(name = "company")
	private String company;

	@Column(name = "nick")
	private String nick;

	@NotNull
	@Column(name = "email_address", nullable = false)
	private String emailAddress;

	@Column(name = "CUSTOMER_PASSWORD")
	private String password;

	@OneToMany(mappedBy = "customer")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<CustomerAttribute> attributes = new HashSet<>();

	@OneToMany(mappedBy = "customer")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ProductReview> reviews = new HashSet<>();

	@ManyToOne
	private MerchantStore merchantStore;

	@ManyToOne
	private Language defaultLanguage;
	
	@Valid
	@Embedded
	private Billing billing = null;
	
	@Embedded
	private Delivery delivery = null;
	
	@Transient
	private String showCustomerStateList;
	
	@Transient
	private String showBillingStateList;
	
	@Transient
	private String showDeliveryStateList;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public Customer dateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public CustomerGender getGender() {
		return gender;
	}

	public Customer gender(CustomerGender gender) {
		this.gender = gender;
		return this;
	}

	public void setGender(CustomerGender gender) {
		this.gender = gender;
	}

	public Boolean isAnonymous() {
		return anonymous;
	}

	public Customer anonymous(Boolean anonymous) {
		this.anonymous = anonymous;
		return this;
	}

	public void setAnonymous(Boolean anonymous) {
		this.anonymous = anonymous;
	}

	public String getCompany() {
		return company;
	}

	public Customer company(String company) {
		this.company = company;
		return this;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getNick() {
		return nick;
	}

	public Customer nick(String nick) {
		this.nick = nick;
		return this;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public Customer emailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
		return this;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public Customer password(String password) {
		this.password = password;
		return this;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<CustomerAttribute> getAttributes() {
		return attributes;
	}

	public Customer attributes(Set<CustomerAttribute> customerAttributes) {
		this.attributes = customerAttributes;
		return this;
	}

	public Customer addAttributes(CustomerAttribute customerAttribute) {
		this.attributes.add(customerAttribute);
		customerAttribute.setCustomer(this);
		return this;
	}

	public Customer removeAttributes(CustomerAttribute customerAttribute) {
		this.attributes.remove(customerAttribute);
		customerAttribute.setCustomer(null);
		return this;
	}

	public void setAttributes(Set<CustomerAttribute> customerAttributes) {
		this.attributes = customerAttributes;
	}

	public Set<ProductReview> getReviews() {
		return reviews;
	}

	public Customer reviews(Set<ProductReview> productReviews) {
		this.reviews = productReviews;
		return this;
	}

	public Customer addReviews(ProductReview productReview) {
		this.reviews.add(productReview);
		productReview.setCustomer(this);
		return this;
	}

	public Customer removeReviews(ProductReview productReview) {
		this.reviews.remove(productReview);
		productReview.setCustomer(null);
		return this;
	}

	public void setReviews(Set<ProductReview> productReviews) {
		this.reviews = productReviews;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public Customer merchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
		return this;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public Language getDefaultLanguage() {
		return defaultLanguage;
	}

	public Customer defaultLanguage(Language language) {
		this.defaultLanguage = language;
		return this;
	}

	public void setDefaultLanguage(Language language) {
		this.defaultLanguage = language;
	}

	public Billing getBilling() {
		return billing;
	}

	public void setBilling(Billing billing) {
		this.billing = billing;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public String getShowCustomerStateList() {
		return showCustomerStateList;
	}

	public void setShowCustomerStateList(String showCustomerStateList) {
		this.showCustomerStateList = showCustomerStateList;
	}

	public String getShowBillingStateList() {
		return showBillingStateList;
	}

	public void setShowBillingStateList(String showBillingStateList) {
		this.showBillingStateList = showBillingStateList;
	}

	public String getShowDeliveryStateList() {
		return showDeliveryStateList;
	}

	public void setShowDeliveryStateList(String showDeliveryStateList) {
		this.showDeliveryStateList = showDeliveryStateList;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	

}
