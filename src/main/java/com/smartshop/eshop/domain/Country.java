package com.smartshop.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "country")
public class Country extends BusinessDomain<Long, Country> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "iso_code")
	private String isoCode;
	
	@Column(name = "country_name")
	private String countryName;

	@Column(name = "supported")
	private Boolean supported;

	@OneToMany(mappedBy = "country")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<CountryDescription> descriptions = new HashSet<>();

	@OneToMany(mappedBy = "country")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Zone> zones = new HashSet<>();

	@ManyToOne
	private GeoZone geoZone;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public Country isoCode(String isoCode) {
		this.isoCode = isoCode;
		return this;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public Boolean isSupported() {
		return supported;
	}

	public Country supported(Boolean supported) {
		this.supported = supported;
		return this;
	}

	public void setSupported(Boolean supported) {
		this.supported = supported;
	}

	public Set<CountryDescription> getDescriptions() {
		return descriptions;
	}

	public Country descriptions(Set<CountryDescription> countryDescriptions) {
		this.descriptions = countryDescriptions;
		return this;
	}

	public Country addDescriptions(CountryDescription countryDescription) {
		this.descriptions.add(countryDescription);
		countryDescription.setCountry(this);
		return this;
	}

	public Country removeDescriptions(CountryDescription countryDescription) {
		this.descriptions.remove(countryDescription);
		countryDescription.setCountry(null);
		return this;
	}

	public void setDescriptions(Set<CountryDescription> countryDescriptions) {
		this.descriptions = countryDescriptions;
	}

	public Set<Zone> getZones() {
		return zones;
	}

	public Country zones(Set<Zone> zones) {
		this.zones = zones;
		return this;
	}

	public Country addZones(Zone zone) {
		this.zones.add(zone);
		zone.setCountry(this);
		return this;
	}

	public Country removeZones(Zone zone) {
		this.zones.remove(zone);
		zone.setCountry(null);
		return this;
	}

	public void setZones(Set<Zone> zones) {
		this.zones = zones;
	}

	public GeoZone getGeoZone() {
		return geoZone;
	}

	public Country geoZone(GeoZone geoZone) {
		this.geoZone = geoZone;
		return this;
	}

	public void setGeoZone(GeoZone geoZone) {
		this.geoZone = geoZone;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	

}
