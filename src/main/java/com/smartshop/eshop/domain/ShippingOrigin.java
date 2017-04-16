package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A ShippingOrigin.
 */
@Entity
@Table(name = "shipping_origin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "shippingorigin")
public class ShippingOrigin extends BusinessDomain<Long, ShippingOrigin> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "city", nullable = false)
	private String city;

	@NotNull
	@Column(name = "postal_code", nullable = false)
	private String postalCode;

	@NotNull
	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "active")
	private Boolean active;

	@Column(name = "state")
	private String state;

	@ManyToOne
	private Zone zone;

	@ManyToOne
	private Country country;

	@ManyToOne
	private MerchantStore merchantStore;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public ShippingOrigin city(String city) {
		this.city = city;
		return this;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public ShippingOrigin postalCode(String postalCode) {
		this.postalCode = postalCode;
		return this;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public ShippingOrigin address(String address) {
		this.address = address;
		return this;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean isActive() {
		return active;
	}

	public ShippingOrigin active(Boolean active) {
		this.active = active;
		return this;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getState() {
		return state;
	}

	public ShippingOrigin state(String state) {
		this.state = state;
		return this;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Zone getZone() {
		return zone;
	}

	public ShippingOrigin zone(Zone zone) {
		this.zone = zone;
		return this;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public Country getCountry() {
		return country;
	}

	public ShippingOrigin country(Country country) {
		this.country = country;
		return this;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public ShippingOrigin merchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
		return this;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

}
