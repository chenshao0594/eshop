package com.smartshop.eshop.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A ProductDescription.
 */
@Entity
@Table(name = "product_description",uniqueConstraints={
		@UniqueConstraint(columnNames={
				"PRODUCT_ID",
				"LANGUAGE_ID"
			})
		})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productdescription")
public class ProductDescription extends Description implements Serializable {

    private static final long serialVersionUID = 1L;

   
    @ManyToOne(targetEntity = Product.class)
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	private Product product;
	
	@Column(name = "PRODUCT_HIGHLIGHT")
	private String productHighlight;

	@Column(name = "DOWNLOAD_LNK")
	private String productExternalDl;

	@Column(name = "SEF_URL")
	private String seUrl;

	@Column(name = "META_TITLE")
	private String metatagTitle;

	@Column(name = "META_KEYWORDS")
	private String metatagKeywords;

	@Column(name = "META_DESCRIPTION")
	private String metatagDescription;

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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getProductExternalDl() {
		return productExternalDl;
	}

	public void setProductExternalDl(String productExternalDl) {
		this.productExternalDl = productExternalDl;
	}

	public String getMetatagTitle() {
		return metatagTitle;
	}

	public void setMetatagTitle(String metatagTitle) {
		this.metatagTitle = metatagTitle;
	}

}
