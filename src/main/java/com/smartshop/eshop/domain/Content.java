package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartshop.eshop.domain.enumeration.ContentPosition;
import com.smartshop.eshop.domain.enumeration.ContentType;

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
	private ContentType contentType;

	@NotNull
	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "visible")
	private Boolean visible;

	@Enumerated(EnumType.STRING)
	@Column(name = "content_position")
	private ContentPosition contentPosition;

	@Valid
	@JsonIgnore
	@OneToMany(mappedBy = "content")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<ContentDescription> descriptions = new ArrayList<ContentDescription>();

	public Long getId() {
		return id;
	}

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

	public ContentType getContentType() {
		return contentType;
	}

	public Content contentType(ContentType contentType) {
		this.contentType = contentType;
		return this;
	}

	public void setContentType(ContentType contentType) {
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

	public ContentPosition getContentPosition() {
		return contentPosition;
	}

	public Content contentPosition(ContentPosition contentPosition) {
		this.contentPosition = contentPosition;
		return this;
	}

	public void setContentPosition(ContentPosition contentPosition) {
		this.contentPosition = contentPosition;
	}

	public List<ContentDescription> getDescriptions() {
		return descriptions;
	}

	public Content descriptions(List<ContentDescription> contentDescriptions) {
		this.descriptions = contentDescriptions;
		return this;
	}
	public ContentDescription getDescription() {
		if(this.getDescriptions()!=null && this.getDescriptions().size()>0) {
			return this.getDescriptions().get(0);
		}
		return null;
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

	public void setDescriptions(List<ContentDescription> contentDescriptions) {
		this.descriptions = contentDescriptions;
	}
}
