package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ContentDescription.
 */
@Entity
@Table(name = "content_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "contentdescription")
public class ContentDescription extends BusinessDomain<Long,ContentDescription>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "metatag_title")
    private String metatagTitle;

    @Column(name = "metatag_description")
    private String metatagDescription;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "se_url")
    private String seUrl;

    @Column(name = "metatag_keywords")
    private String metatagKeywords;

    @ManyToOne
    private Language language;

    @ManyToOne
    private Content content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public ContentDescription title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public ContentDescription description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetatagTitle() {
        return metatagTitle;
    }

    public ContentDescription metatagTitle(String metatagTitle) {
        this.metatagTitle = metatagTitle;
        return this;
    }

    public void setMetatagTitle(String metatagTitle) {
        this.metatagTitle = metatagTitle;
    }

    public String getMetatagDescription() {
        return metatagDescription;
    }

    public ContentDescription metatagDescription(String metatagDescription) {
        this.metatagDescription = metatagDescription;
        return this;
    }

    public void setMetatagDescription(String metatagDescription) {
        this.metatagDescription = metatagDescription;
    }

    public String getName() {
        return name;
    }

    public ContentDescription name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeUrl() {
        return seUrl;
    }

    public ContentDescription seUrl(String seUrl) {
        this.seUrl = seUrl;
        return this;
    }

    public void setSeUrl(String seUrl) {
        this.seUrl = seUrl;
    }

    public String getMetatagKeywords() {
        return metatagKeywords;
    }

    public ContentDescription metatagKeywords(String metatagKeywords) {
        this.metatagKeywords = metatagKeywords;
        return this;
    }

    public void setMetatagKeywords(String metatagKeywords) {
        this.metatagKeywords = metatagKeywords;
    }

    public Language getLanguage() {
        return language;
    }

    public ContentDescription language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Content getContent() {
        return content;
    }

    public ContentDescription content(Content content) {
        this.content = content;
        return this;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    

    
}
