package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A IntegrationModule.
 */
@Entity
@Table(name = "integration_module")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "integrationmodule")
public class IntegrationModule extends BusinessDomain<Long,IntegrationModule>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "config_details")
    private String configDetails;

    @Column(name = "custom_module")
    private Boolean customModule;

    @Column(name = "jhi_type")
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

    public Long getId() {
        return id;
    }

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

    

    
}
