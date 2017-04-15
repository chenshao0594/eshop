package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProductDescription.
 */
@Entity
@Table(name = "product_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productdescription")
public class ProductDescription extends BusinessDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "metatag_description")
    private String metatagDescription;

    @Column(name = "se_url")
    private String seUrl;

    @Column(name = "metatag_keywords")
    private String metatagKeywords;

    @Column(name = "product_highlight")
    private String productHighlight;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "metatag_title")
    private String metatagTitle;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "product_external_dl")
    private String productExternalDl;

    @ManyToOne
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetatagDescription() {
        return metatagDescription;
    }

    public ProductDescription metatagDescription(String metatagDescription) {
        this.metatagDescription = metatagDescription;
        return this;
    }

    public void setMetatagDescription(String metatagDescription) {
        this.metatagDescription = metatagDescription;
    }

    public String getSeUrl() {
        return seUrl;
    }

    public ProductDescription seUrl(String seUrl) {
        this.seUrl = seUrl;
        return this;
    }

    public void setSeUrl(String seUrl) {
        this.seUrl = seUrl;
    }

    public String getMetatagKeywords() {
        return metatagKeywords;
    }

    public ProductDescription metatagKeywords(String metatagKeywords) {
        this.metatagKeywords = metatagKeywords;
        return this;
    }

    public void setMetatagKeywords(String metatagKeywords) {
        this.metatagKeywords = metatagKeywords;
    }

    public String getProductHighlight() {
        return productHighlight;
    }

    public ProductDescription productHighlight(String productHighlight) {
        this.productHighlight = productHighlight;
        return this;
    }

    public void setProductHighlight(String productHighlight) {
        this.productHighlight = productHighlight;
    }

    public String getTitle() {
        return title;
    }

    public ProductDescription title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public ProductDescription description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetatagTitle() {
        return metatagTitle;
    }

    public ProductDescription metatagTitle(String metatagTitle) {
        this.metatagTitle = metatagTitle;
        return this;
    }

    public void setMetatagTitle(String metatagTitle) {
        this.metatagTitle = metatagTitle;
    }

    public String getName() {
        return name;
    }

    public ProductDescription name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductExternalDl() {
        return productExternalDl;
    }

    public ProductDescription productExternalDl(String productExternalDl) {
        this.productExternalDl = productExternalDl;
        return this;
    }

    public void setProductExternalDl(String productExternalDl) {
        this.productExternalDl = productExternalDl;
    }

    public Product getProduct() {
        return product;
    }

    public ProductDescription product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductDescription productDescription = (ProductDescription) o;
        if (productDescription.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, productDescription.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductDescription{" +
            "id=" + id +
            ", metatagDescription='" + metatagDescription + "'" +
            ", seUrl='" + seUrl + "'" +
            ", metatagKeywords='" + metatagKeywords + "'" +
            ", productHighlight='" + productHighlight + "'" +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", metatagTitle='" + metatagTitle + "'" +
            ", name='" + name + "'" +
            ", productExternalDl='" + productExternalDl + "'" +
            '}';
    }
}
