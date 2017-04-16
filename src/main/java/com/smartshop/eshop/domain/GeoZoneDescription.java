package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A GeoZoneDescription.
 */
@Entity
@Table(name = "geo_zone_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "geozonedescription")
public class GeoZoneDescription extends BusinessDomain<Long, GeoZoneDescription> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private GeoZone geoZone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public GeoZoneDescription title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public GeoZoneDescription name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public GeoZoneDescription description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GeoZone getGeoZone() {
        return geoZone;
    }

    public GeoZoneDescription geoZone(GeoZone geoZone) {
        this.geoZone = geoZone;
        return this;
    }

    public void setGeoZone(GeoZone geoZone) {
        this.geoZone = geoZone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeoZoneDescription geoZoneDescription = (GeoZoneDescription) o;
        if (geoZoneDescription.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, geoZoneDescription.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GeoZoneDescription{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}