package com.smartshop.eshop.domain;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A ProductImage.
 */
@Entity
@Table(name = "product_image")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productimage")
public class ProductImage extends BusinessDomain<Long, ProductImage> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;
    
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

    @NotNull
    @Lob
    @Column(name = "image_content", nullable = false)
    private byte[] imageContent;
    @Transient
	private InputStream image = null;

    @Column(name = "image_content_content_type", nullable = false)
    private String imageContentContentType;
    
    @JsonIgnore
    @OneToMany(mappedBy = "productImage",cascade = CascadeType.ALL)
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

    public byte[] getImageContent() {
        return imageContent;
    }

    public ProductImage imageContent(byte[] imageContent) {
        this.imageContent = imageContent;
        return this;
    }

    public void setImageContent(byte[] imageContent) {
        this.imageContent = imageContent;
    }

    public String getImageContentContentType() {
        return imageContentContentType;
    }

    public ProductImage imageContentContentType(String imageContentContentType) {
        this.imageContentContentType = imageContentContentType;
        return this;
    }

    public void setImageContentContentType(String imageContentContentType) {
        this.imageContentContentType = imageContentContentType;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getDefaultImage() {
		return defaultImage;
	}

	public Boolean getImageCrop() {
		return imageCrop;
	}

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}

   
}
