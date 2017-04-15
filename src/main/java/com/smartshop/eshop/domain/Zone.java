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
 * A Zone.
 */
@Entity
@Table(name = "zone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "zone")
public class Zone extends BusinessDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "zone")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ZoneDescription> descriptions = new HashSet<>();

    @ManyToOne
    private Country country;

    public Long getId() {
        return id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Zone zone = (Zone) o;
        if (zone.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, zone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Zone{" +
            "id=" + id +
            ", code='" + code + "'" +
            '}';
    }
}
