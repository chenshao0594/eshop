package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Zone.
 */
@Entity
@Table(name = "zone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "zone")
public class Zone extends BusinessDomain<Long, Zone> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ZONE_CODE")
	private String code;

	@JsonIgnore
	@OneToMany(mappedBy = "zone")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ZoneDescription> descriptions = new HashSet<>();

	@ManyToOne()
	private Country country;

	@Transient
	private String name;

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

	public Zone code(String code) {
		this.code = code;
		return this;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<ZoneDescription> getDescriptions() {
		return descriptions;
	}

	public Zone descriptions(Set<ZoneDescription> zoneDescriptions) {
		this.descriptions = zoneDescriptions;
		return this;
	}

	public Zone addDescriptions(ZoneDescription zoneDescription) {
		this.descriptions.add(zoneDescription);
		zoneDescription.setZone(this);
		return this;
	}

	public Zone removeDescriptions(ZoneDescription zoneDescription) {
		this.descriptions.remove(zoneDescription);
		zoneDescription.setZone(null);
		return this;
	}

	public void setDescriptions(Set<ZoneDescription> zoneDescriptions) {
		this.descriptions = zoneDescriptions;
	}

	public Country getCountry() {
		return country;
	}

	public Zone country(Country country) {
		this.country = country;
		return this;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
