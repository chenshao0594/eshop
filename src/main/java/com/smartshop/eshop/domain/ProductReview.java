package com.smartshop.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProductReview.
 */
@Entity
@Table(name = "product_review")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productreview")
public class ProductReview extends BusinessDomain<Long, ProductReview> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "review_read")
	private Long reviewRead;

	@Column(name = "review_date")
	private LocalDate reviewDate;

	@Column(name = "status")
	private Integer status;

	@Column(name = "review_rating")
	private Double reviewRating;

	@OneToMany(mappedBy = "productReview")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ProductReviewDescription> descriptions = new HashSet<>();

	@ManyToOne
	private Customer customer;
	@OneToOne
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getReviewRead() {
		return reviewRead;
	}

	public ProductReview reviewRead(Long reviewRead) {
		this.reviewRead = reviewRead;
		return this;
	}

	public void setReviewRead(Long reviewRead) {
		this.reviewRead = reviewRead;
	}

	public LocalDate getReviewDate() {
		return reviewDate;
	}

	public ProductReview reviewDate(LocalDate reviewDate) {
		this.reviewDate = reviewDate;
		return this;
	}

	public void setReviewDate(LocalDate reviewDate) {
		this.reviewDate = reviewDate;
	}

	public Integer getStatus() {
		return status;
	}

	public ProductReview status(Integer status) {
		this.status = status;
		return this;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getReviewRating() {
		return reviewRating;
	}

	public ProductReview reviewRating(Double reviewRating) {
		this.reviewRating = reviewRating;
		return this;
	}

	public void setReviewRating(Double reviewRating) {
		this.reviewRating = reviewRating;
	}

	public Set<ProductReviewDescription> getDescriptions() {
		return descriptions;
	}

	public ProductReview descriptions(Set<ProductReviewDescription> productReviewDescriptions) {
		this.descriptions = productReviewDescriptions;
		return this;
	}

	public ProductReview addDescriptions(ProductReviewDescription productReviewDescription) {
		this.descriptions.add(productReviewDescription);
		productReviewDescription.setProductReview(this);
		return this;
	}

	public ProductReview removeDescriptions(ProductReviewDescription productReviewDescription) {
		this.descriptions.remove(productReviewDescription);
		productReviewDescription.setProductReview(null);
		return this;
	}

	public void setDescriptions(Set<ProductReviewDescription> productReviewDescriptions) {
		this.descriptions = productReviewDescriptions;
	}

	public Customer getCustomer() {
		return customer;
	}

	public ProductReview customer(Customer customer) {
		this.customer = customer;
		return this;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
