package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A ProductOptionDescription.
 */
@Entity
@Table(name = "product_option_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productoptiondescription")
public class ProductOptionDescription extends BusinessDomain<Long, ProductOptionDescription> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "product_option_comment")
	private String productOptionComment;

	@Column(name = "title")
	private String title;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@ManyToOne
	private Language language;

	@ManyToOne
	private ProductOption productOption;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getProductOptionComment() {
		return productOptionComment;
	}

	public ProductOptionDescription productOptionComment(String productOptionComment) {
		this.productOptionComment = productOptionComment;
		return this;
	}

	public void setProductOptionComment(String productOptionComment) {
		this.productOptionComment = productOptionComment;
	}

	public String getTitle() {
		return title;
	}

	public ProductOptionDescription title(String title) {
		this.title = title;
		return this;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public ProductOptionDescription name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public ProductOptionDescription description(String description) {
		this.description = description;
		return this;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Language getLanguage() {
		return language;
	}

	public ProductOptionDescription language(Language language) {
		this.language = language;
		return this;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public ProductOption getProductOption() {
		return productOption;
	}

	public ProductOptionDescription productOption(ProductOption productOption) {
		this.productOption = productOption;
		return this;
	}

	public void setProductOption(ProductOption productOption) {
		this.productOption = productOption;
	}

}
