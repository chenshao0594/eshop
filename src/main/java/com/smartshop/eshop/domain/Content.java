package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartshop.eshop.domain.enumeration.ContentPositionEnum;
import com.smartshop.eshop.domain.enumeration.ContentTypeEnum;

/**
 * A Content.
 */
@Entity
@Table(name = "content")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "content")
public class Content extends BusinessDomain<Long, Content> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sort_order")
	private Integer sortOrder;

	@Column(name = "product_group")
	private String productGroup;

	@Enumerated(EnumType.STRING)
	@Column(name = "content_type")
	private ContentTypeEnum contentType;

	@NotNull
	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "visible")
	private Boolean visible;

	@Enumerated(EnumType.STRING)
	@Column(name = "content_position")
	private ContentPositionEnum contentPosition;

	@OneToMany(mappedBy = "content")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ContentDescription> descriptions = new HashSet<>();

	@ManyToOne
	private MerchantStore merchantStore;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public Content sortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
		return this;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public Content productGroup(String productGroup) {
		this.productGroup = productGroup;
		return this;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public ContentTypeEnum getContentType() {
		return contentType;
	}

	public Content contentType(ContentTypeEnum contentType) {
		this.contentType = contentType;
		return this;
	}

	public void setContentType(ContentTypeEnum contentType) {
		this.contentType = contentType;
	}

	public String getCode() {
		return code;
	}

	public Content code(String code) {
		this.code = code;
		return this;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean isVisible() {
		return visible;
	}

	public Content visible(Boolean visible) {
		this.visible = visible;
		return this;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public ContentPositionEnum getContentPosition() {
		return contentPosition;
	}

	public Content contentPosition(ContentPositionEnum contentPosition) {
		this.contentPosition = contentPosition;
		return this;
	}

	public void setContentPosition(ContentPositionEnum contentPosition) {
		this.contentPosition = contentPosition;
	}

	public Set<ContentDescription> getDescriptions() {
		return descriptions;
	}

	public ContentDescription getDescription() {
		if (this.getDescriptions() != null && this.getDescriptions().size() > 0) {
			List<ContentDescription> list2 = new ArrayList<ContentDescription>();
			list2.addAll(this.getDescriptions());
			return list2.get(0);
		}
		return null;

	}

	public Content descriptions(Set<ContentDescription> contentDescriptions) {
		this.descriptions = contentDescriptions;
		return this;
	}

	public Content addDescriptions(ContentDescription contentDescription) {
		this.descriptions.add(contentDescription);
		contentDescription.setContent(this);
		return this;
	}

	public Content removeDescriptions(ContentDescription contentDescription) {
		this.descriptions.remove(contentDescription);
		contentDescription.setContent(null);
		return this;
	}

	public void setDescriptions(Set<ContentDescription> contentDescriptions) {
		this.descriptions = contentDescriptions;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public Content merchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
		return this;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

}
