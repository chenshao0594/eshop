package com.smartshop.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A GeoZone.
 */
@Entity
@Table(name = "geo_zone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "geozone")
public class GeoZone extends BusinessDomain<Long,GeoZone>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "geoZone")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Country> countries = new HashSet<>();

    @OneToMany(mappedBy = "geoZone")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GeoZoneDescription> descriptions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public GeoZone name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public GeoZone code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public GeoZone countries(Set<Country> countries) {
        this.countries = countries;
        return this;
    }

    public GeoZone addCountries(Country country) {
        this.countries.add(country);
        country.setGeoZone(this);
        return this;
    }

    public GeoZone removeCountries(Country country) {
        this.countries.remove(country);
        country.setGeoZone(null);
        return this;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public Set<GeoZoneDescription> getDescriptions() {
        return descriptions;
    }

    public GeoZone descriptions(Set<GeoZoneDescription> geoZoneDescriptions) {
        this.descriptions = geoZoneDescriptions;
        return this;
    }

    public GeoZone addDescriptions(GeoZoneDescription geoZoneDescription) {
        this.descriptions.add(geoZoneDescription);
        geoZoneDescription.setGeoZone(this);
        return this;
    }

    public GeoZone removeDescriptions(GeoZoneDescription geoZoneDescription) {
        this.descriptions.remove(geoZoneDescription);
        geoZoneDescription.setGeoZone(null);
        return this;
    }

    public void setDescriptions(Set<GeoZoneDescription> geoZoneDescriptions) {
        this.descriptions = geoZoneDescriptions;
    }

    

    
}
