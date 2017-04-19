package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.smartshop.eshop.core.config.ModuleConfig;

/**
 * A IntegrationModule.
 */
@Entity
@Table(name = "integration_module")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "integrationmodule")
public class IntegrationModule extends BusinessDomain<Long, IntegrationModule> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "details")
	private String configDetails;

	@Column(name = "custom_module")
	private Boolean customModule=true;

	@Column(name = "config_type")
	private String type;

	@Column(name = "code")
	private String code;

	@Column(name = "regions")
	private String regions;

	@Column(name = "image")
	private String image;

	@Column(name = "module")
	private String module;

	@Column(name = "configuration")
	private String configuration;
	
	@Transient
	private Set<String> regionsSet = new HashSet<String>();
	
	/**
	 * Contains a map of module config by environment (DEV,PROD)
	 */
	@Transient
	private Map<String,ModuleConfig> moduleConfigs = new HashMap<String,ModuleConfig>();
	
	
	@Transient
	private Map<String,String> details = new HashMap<String,String>();


	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getConfigDetails() {
		return configDetails;
	}

	public IntegrationModule configDetails(String configDetails) {
		this.configDetails = configDetails;
		return this;
	}

	public void setConfigDetails(String configDetails) {
		this.configDetails = configDetails;
	}

	public Boolean isCustomModule() {
		return customModule;
	}

	public IntegrationModule customModule(Boolean customModule) {
		this.customModule = customModule;
		return this;
	}

	public void setCustomModule(Boolean customModule) {
		this.customModule = customModule;
	}

	public String getType() {
		return type;
	}

	public IntegrationModule type(String type) {
		this.type = type;
		return this;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public IntegrationModule code(String code) {
		this.code = code;
		return this;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRegions() {
		return regions;
	}

	public IntegrationModule regions(String regions) {
		this.regions = regions;
		return this;
	}

	public void setRegions(String regions) {
		this.regions = regions;
	}

	public String getImage() {
		return image;
	}

	public IntegrationModule image(String image) {
		this.image = image;
		return this;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getModule() {
		return module;
	}

	public IntegrationModule module(String module) {
		this.module = module;
		return this;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getConfiguration() {
		return configuration;
	}

	public IntegrationModule configuration(String configuration) {
		this.configuration = configuration;
		return this;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public Set<String> getRegionsSet() {
		return regionsSet;
	}

	public void setRegionsSet(Set<String> regionsSet) {
		this.regionsSet = regionsSet;
	}

	public Map<String, ModuleConfig> getModuleConfigs() {
		return moduleConfigs;
	}

	public void setModuleConfigs(Map<String, ModuleConfig> moduleConfigs) {
		this.moduleConfigs = moduleConfigs;
	}

	public Map<String, String> getDetails() {
		return details;
	}

	public void setDetails(Map<String, String> details) {
		this.details = details;
	}

	public Boolean getCustomModule() {
		return customModule;
	}
	

}
