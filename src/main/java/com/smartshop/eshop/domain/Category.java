package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Category.
 */
@Entity
@Table(name = "category", 
	   uniqueConstraints=
	   @UniqueConstraint(columnNames = {"MERCHANT_ID", "CODE"}) 
)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "category")
public class Category extends BusinessDomain<Long, Category> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "depth")
    private Integer depth;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Column(name = "category_status")
    private Boolean categoryStatus;

    @Column(name = "lineage")
    private String lineage;

    @Column(name = "visible")
    private Boolean visible=true;

    @NotEmpty
	@Column(name="CODE", length=100, nullable=false)
    private String code;

    @Column(name = "category_image")
    private String categoryImage;

    @JsonIgnore
    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CategoryDescription> descriptions = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MERCHANT_ID", nullable=false)
	private MerchantStore merchantStore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDepth() {
        return depth;
    }

    public Category depth(Integer depth) {
        this.depth = depth;
        return this;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public Category sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean isCategoryStatus() {
        return categoryStatus;
    }

    public Category categoryStatus(Boolean categoryStatus) {
        this.categoryStatus = categoryStatus;
        return this;
    }

    public void setCategoryStatus(Boolean categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public String getLineage() {
        return lineage;
    }

    public Category lineage(String lineage) {
        this.lineage = lineage;
        return this;
    }

    public void setLineage(String lineage) {
        this.lineage = lineage;
    }

    public Boolean isVisible() {
        return visible;
    }

    public Category visible(Boolean visible) {
        this.visible = visible;
        return this;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getCode() {
        return code;
    }

    public Category code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public Category categoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
        return this;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Category categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Category addCategories(Category category) {
        this.categories.add(category);
        category.setParent(this);
        return this;
    }

    public Category removeCategories(Category category) {
        this.categories.remove(category);
        category.setParent(null);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<CategoryDescription> getDescriptions() {
        return descriptions;
    }

    public Category descriptions(Set<CategoryDescription> categoryDescriptions) {
        this.descriptions = categoryDescriptions;
        return this;
    }

    public Category addDescriptions(CategoryDescription categoryDescription) {
        this.descriptions.add(categoryDescription);
        categoryDescription.setCategory(this);
        return this;
    }

    public Category removeDescriptions(CategoryDescription categoryDescription) {
        this.descriptions.remove(categoryDescription);
        categoryDescription.setCategory(null);
        return this;
    }

    public void setDescriptions(Set<CategoryDescription> categoryDescriptions) {
        this.descriptions = categoryDescriptions;
    }

    public Category getParent() {
        return parent;
    }

    public Category parent(Category category) {
        this.parent = category;
        return this;
    }

    public void setParent(Category category) {
        this.parent = category;
    }

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public Boolean getCategoryStatus() {
		return categoryStatus;
	}

	public Boolean getVisible() {
		return visible;
	}

}
