package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ManufacturerDescription.
 */
@Entity
@Table(name = "manufacturer_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "manufacturerdescription")
public class ManufacturerDescription extends BusinessDomain<Long,ManufacturerDescription>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url_clicked")
    private Integer urlClicked;

    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private String url;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date_last_click")
    private LocalDate dateLastClick;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Language language;

    @ManyToOne
    private Manufacturer manufacturer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUrlClicked() {
        return urlClicked;
    }

    public ManufacturerDescription urlClicked(Integer urlClicked) {
        this.urlClicked = urlClicked;
        return this;
    }

    public void setUrlClicked(Integer urlClicked) {
        this.urlClicked = urlClicked;
    }

    public String getTitle() {
        return title;
    }

    public ManufacturerDescription title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public ManufacturerDescription url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public ManufacturerDescription name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateLastClick() {
        return dateLastClick;
    }

    public ManufacturerDescription dateLastClick(LocalDate dateLastClick) {
        this.dateLastClick = dateLastClick;
        return this;
    }

    public void setDateLastClick(LocalDate dateLastClick) {
        this.dateLastClick = dateLastClick;
    }

    public String getDescription() {
        return description;
    }

    public ManufacturerDescription description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Language getLanguage() {
        return language;
    }

    public ManufacturerDescription language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public ManufacturerDescription manufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    

    
}
