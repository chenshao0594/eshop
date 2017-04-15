package com.smartshop.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.smartshop.eshop.domain.enumeration.ContentType;

import com.smartshop.eshop.domain.enumeration.ContentPosition;

/**
 * A Content.
 */
@Entity
@Table(name = "content")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "content")
public class Content extends BusinessDomain implements Serializable {

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

    @OneToMany(mappedBy = "content")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContentDescription> descriptions = new HashSet<>();

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

    public Set<ContentDescription> getDescriptions() {
        return descriptions;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Content content = (Content) o;
        if (content.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, content.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Content{" +
            "id=" + id +
            ", sortOrder='" + sortOrder + "'" +
            ", productGroup='" + productGroup + "'" +
            ", contentType='" + contentType + "'" +
            ", code='" + code + "'" +
            ", visible='" + visible + "'" +
            ", contentPosition='" + contentPosition + "'" +
            '}';
    }
}
