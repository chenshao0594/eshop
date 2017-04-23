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
 * A Attachment.
 */
@Entity
@Table(name = "attachment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "attachment")
public class Attachment extends BusinessDomain<Long, Attachment> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	@NotNull
	@Lob
	@Column(name = "content", nullable = false)
	private byte[] content;

	@Column(name = "content_content_type", nullable = false)
	private String contentContentType;

	@Column(name = "jhi_size")
	private Long size;

	@NotNull
	@Column(name = "bo_name", nullable = false)
	private String boName;

	@NotNull
	@Column(name = "bo_id", nullable = false)
	private Long boId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Attachment name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getContent() {
		return content;
	}

	public Attachment content(byte[] content) {
		this.content = content;
		return this;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getContentContentType() {
		return contentContentType;
	}

	public Attachment contentContentType(String contentContentType) {
		this.contentContentType = contentContentType;
		return this;
	}

	public void setContentContentType(String contentContentType) {
		this.contentContentType = contentContentType;
	}

	public Long getSize() {
		return size;
	}

	public Attachment size(Long size) {
		this.size = size;
		return this;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getBoName() {
		return boName;
	}

	public Attachment boName(String boName) {
		this.boName = boName;
		return this;
	}

	public void setBoName(String boName) {
		this.boName = boName;
	}

	public Long getBoId() {
		return boId;
	}

	public Attachment boId(Long boId) {
		this.boId = boId;
		return this;
	}

	public void setBoId(Long boId) {
		this.boId = boId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Attachment attachment = (Attachment) o;
		if (attachment.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, attachment.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Attachment{" + "id=" + id + ", name='" + name + "'" + ", content='" + content + "'"
				+ ", contentContentType='" + contentContentType + "'" + ", size='" + size + "'" + ", boName='" + boName
				+ "'" + ", boId='" + boId + "'" + '}';
	}
}
