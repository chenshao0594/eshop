package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

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

	@Override
	public Long getId() {
		return id;
	}

	@Override
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

}
