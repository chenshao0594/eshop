package com.smartshop.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ProductImage.
 */
@Entity
@Table(name = "product_image")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productimage")
public class ProductImage extends BusinessDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "product_image_url")
    private String productImageUrl;

    @Column(name = "default_image")
    private Boolean defaultImage;

    @Column(name = "image_type")
    private Integer imageType;

    @Column(name = "image_crop")
    private Boolean imageCrop;

    @OneToMany(mappedBy = "productImage")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductImageDescription> descriptions = new HashSet<>();

    @ManyToOne
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductImage() {
        return productImage;
    }

    public ProductImage productImage(String productImage) {
        this.productImage = productImage;
        return this;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public ProductImage productImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
        return this;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public Boolean isDefaultImage() {
        return defaultImage;
    }

    public ProductImage defaultImage(Boolean defaultImage) {
        this.defaultImage = defaultImage;
        return this;
    }

    public void setDefaultImage(Boolean defaultImage) {
        this.defaultImage = defaultImage;
    }

    public Integer getImageType() {
        return imageType;
    }

    public ProductImage imageType(Integer imageType) {
        this.imageType = imageType;
        return this;
    }

    public void setImageType(Integer imageType) {
        this.imageType = imageType;
    }

    public Boolean isImageCrop() {
        return imageCrop;
    }

    public ProductImage imageCrop(Boolean imageCrop) {
        this.imageCrop = imageCrop;
        return this;
    }

    public void setImageCrop(Boolean imageCrop) {
        this.imageCrop = imageCrop;
    }

    public Set<ProductImageDescription> getDescriptions() {
        return descriptions;
    }

    public ProductImage descriptions(Set<ProductImageDescription> productImageDescriptions) {
        this.descriptions = productImageDescriptions;
        return this;
    }

    public ProductImage addDescriptions(ProductImageDescription productImageDescription) {
        this.descriptions.add(productImageDescription);
        productImageDescription.setProductImage(this);
        return this;
    }

    public ProductImage removeDescriptions(ProductImageDescription productImageDescription) {
        this.descriptions.remove(productImageDescription);
        productImageDescription.setProductImage(null);
        return this;
    }

    public void setDescriptions(Set<ProductImageDescription> productImageDescriptions) {
        this.descriptions = productImageDescriptions;
    }

    public Product getProduct() {
        return product;
    }

    public ProductImage product(Product product) {
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
        ProductImage productImage = (ProductImage) o;
        if (productImage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, productImage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductImage{" +
            "id=" + id +
            ", productImage='" + productImage + "'" +
            ", productImageUrl='" + productImageUrl + "'" +
            ", defaultImage='" + defaultImage + "'" +
            ", imageType='" + imageType + "'" +
            ", imageCrop='" + imageCrop + "'" +
            '}';
    }
}
