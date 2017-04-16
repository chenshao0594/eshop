package com.smartshop.eshop.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A CategoryDescription.
 */
@Entity
@Table(name = "category_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "categorydescription")
public class CategoryDescription extends Description implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_highlight")
    private String categoryHighlight;


    @Column(name = "metatag_description")
    private String metatagDescription;

    @Column(name = "se_url")
    private String seUrl;

    @Column(name = "metatag_keywords")
    private String metatagKeywords;

    @Column(name = "metatag_title")
    private String metatagTitle;

    @ManyToOne
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryHighlight() {
        return categoryHighlight;
    }

    public CategoryDescription categoryHighlight(String categoryHighlight) {
        this.categoryHighlight = categoryHighlight;
        return this;
    }

    public void setCategoryHighlight(String categoryHighlight) {
        this.categoryHighlight = categoryHighlight;
    }


    public String getMetatagDescription() {
        return metatagDescription;
    }

    public CategoryDescription metatagDescription(String metatagDescription) {
        this.metatagDescription = metatagDescription;
        return this;
    }

    public void setMetatagDescription(String metatagDescription) {
        this.metatagDescription = metatagDescription;
    }


    public String getSeUrl() {
        return seUrl;
    }

    public CategoryDescription seUrl(String seUrl) {
        this.seUrl = seUrl;
        return this;
    }

    public void setSeUrl(String seUrl) {
        this.seUrl = seUrl;
    }

    public String getMetatagKeywords() {
        return metatagKeywords;
    }

    public CategoryDescription metatagKeywords(String metatagKeywords) {
        this.metatagKeywords = metatagKeywords;
        return this;
    }

    public void setMetatagKeywords(String metatagKeywords) {
        this.metatagKeywords = metatagKeywords;
    }

    public String getMetatagTitle() {
        return metatagTitle;
    }

    public CategoryDescription metatagTitle(String metatagTitle) {
        this.metatagTitle = metatagTitle;
        return this;
    }

    public void setMetatagTitle(String metatagTitle) {
        this.metatagTitle = metatagTitle;
    }

    public Category getCategory() {
        return category;
    }

    public CategoryDescription category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
