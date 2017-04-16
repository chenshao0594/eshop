package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A Template.
 */
@Entity
@Table(name = "template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "template")
public class Template extends BusinessDomain<Long, Template> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "super_id")
    private Long superId;

    @NotNull
    @Column(name = "template_key", nullable = false)
    private String templateKey;

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Template name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSuperId() {
        return superId;
    }

    public Template superId(Long superId) {
        this.superId = superId;
        return this;
    }

    public void setSuperId(Long superId) {
        this.superId = superId;
    }

    public String getTemplateKey() {
        return templateKey;
    }

    public Template templateKey(String templateKey) {
        this.templateKey = templateKey;
        return this;
    }

    public void setTemplateKey(String templateKey) {
        this.templateKey = templateKey;
    }

    public String getContent() {
        return content;
    }

    public Template content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Template template = (Template) o;
        if (template.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, template.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Template{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", superId='" + superId + "'" +
            ", templateKey='" + templateKey + "'" +
            ", content='" + content + "'" +
            '}';
    }
}
