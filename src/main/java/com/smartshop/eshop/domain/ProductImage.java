package com.smartshop.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.InputStream;
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
public class ProductImage extends BusinessDomain<Long,ProductImage>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "product_image_url")
    private String productImageUrl;

    @Column(name = "default_image")
    private Boolean defaultImage = true;

    @Column(name = "image_type")
    private Integer imageType;

    @Column(name = "image_crop")
    private Boolean imageCrop;

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    private byte[] content;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @OneToMany(mappedBy = "productImage")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductImageDescription> descriptions = new HashSet<>();

    @ManyToOne
    private Product product;
    
    @Transient
	private InputStream image = null;
	

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

    public byte[] getContent() {
        return content;
    }

    public ProductImage content(byte[] content) {
        this.content = content;
        return this;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public ProductImage contentType(String contentContentType) {
        this.contentType = contentContentType;
        return this;
    }

    public void setContentType(String contentContentType) {
        this.contentType = contentContentType;
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

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}
}
