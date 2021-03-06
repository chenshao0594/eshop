package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A ProductImageDescription.
 */
@Entity
@Table(name = "product_image_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productimagedescription")
public class ProductImageDescription extends BusinessDomain<Long, ProductImageDescription> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "alt_tag")
	private String altTag;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;
	@ManyToOne
	private Language language;

	@ManyToOne
	private ProductImage productImage;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public ProductImageDescription title(String title) {
		this.title = title;
		return this;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAltTag() {
		return altTag;
	}

	public ProductImageDescription altTag(String altTag) {
		this.altTag = altTag;
		return this;
	}

	public void setAltTag(String altTag) {
		this.altTag = altTag;
	}

	public String getName() {
		return name;
	}

	public ProductImageDescription name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public ProductImageDescription description(String description) {
		this.description = description;
		return this;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Language getLanguage() {
		return language;
	}

	public ProductImageDescription language(Language language) {
		this.language = language;
		return this;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public ProductImage getProductImage() {
		return productImage;
	}

	public ProductImageDescription productImage(ProductImage productImage) {
		this.productImage = productImage;
		return this;
	}

	public void setProductImage(ProductImage productImage) {
		this.productImage = productImage;
	}

}
