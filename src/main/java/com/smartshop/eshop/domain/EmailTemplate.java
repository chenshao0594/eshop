package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A EmailTemplate.
 */
@Entity
@Table(name = "email_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "emailtemplate")
public class EmailTemplate extends BusinessDomain<Long, EmailTemplate> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	@NotNull
	@Column(name = "subject", nullable = false)
	private String subject;

	@Lob
	@Column(name = "content")
	private String content;

	@Column(name = "action_key")
	private String actionKey;

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

	public EmailTemplate name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public EmailTemplate subject(String subject) {
		this.subject = subject;
		return this;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public EmailTemplate content(String content) {
		this.content = content;
		return this;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getActionKey() {
		return actionKey;
	}

	public EmailTemplate actionKey(String actionKey) {
		this.actionKey = actionKey;
		return this;
	}

	public void setActionKey(String actionKey) {
		this.actionKey = actionKey;
	}

}
