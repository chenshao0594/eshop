package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CategoryDescription.
 */
@Entity
@Table(name = "category_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "categorydescription")
public class CategoryDescription extends BusinessDomain<Long,CategoryDescription>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_highlight")
    private String categoryHighlight;

    @Column(name = "title")
    private String title;

    @Column(name = "metatag_description")
    private String metatagDescription;

    @Column(name = "description")
    private String description;

    @Column(name = "se_url")
    private String seUrl;

    @Column(name = "metatag_keywords")
    private String metatagKeywords;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "metatag_title")
    private String metatagTitle;

    @ManyToOne
    private Language language;

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

    public String getTitle() {
        return title;
    }

    public CategoryDescription title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDescription() {
        return description;
    }

    public CategoryDescription description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getName() {
        return name;
    }

    public CategoryDescription name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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


    public Language getLanguage() {
        return language;
    }

    public CategoryDescription language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
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
