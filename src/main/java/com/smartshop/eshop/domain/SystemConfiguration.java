package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A SystemConfiguration.
 */
@Entity
@Table(name = "system_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "systemconfiguration")
public class SystemConfiguration extends BusinessDomain<Long, SystemConfiguration> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CONFIG_KEY")
	private String key;

	@Column(name = "value")
	private String value;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public SystemConfiguration key(String key) {
		this.key = key;
		return this;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public SystemConfiguration value(String value) {
		this.value = value;
		return this;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
