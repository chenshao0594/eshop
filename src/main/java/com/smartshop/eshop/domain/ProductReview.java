package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    public Long getId() {
        return id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductReview productReview = (ProductReview) o;
        if (productReview.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, productReview.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductReview{" +
            "id=" + id +
            ", reviewRead='" + reviewRead + "'" +
            ", reviewDate='" + reviewDate + "'" +
            ", status='" + status + "'" +
            ", reviewRating='" + reviewRating + "'" +
            '}';
    }
}
