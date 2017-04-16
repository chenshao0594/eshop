package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.smartshop.eshop.domain.enumeration.MerchantConfigurationType;

/**
 * A MerchantConfiguration.
 */
@Entity
@Table(name = "merchant_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "merchantconfiguration")
public class MerchantConfiguration extends BusinessDomain<Long,MerchantConfiguration>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "merchant_configuration_type")
    private MerchantConfigurationType merchantConfigurationType;

    @Column(name = "jhi_key")
    private String key;

    @Column(name = "jhi_value")
    private String value;

    @ManyToOne
    private MerchantStore merchantStore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MerchantConfigurationType getMerchantConfigurationType() {
        return merchantConfigurationType;
    }

    public MerchantConfiguration merchantConfigurationType(MerchantConfigurationType merchantConfigurationType) {
        this.merchantConfigurationType = merchantConfigurationType;
        return this;
    }

    public void setMerchantConfigurationType(MerchantConfigurationType merchantConfigurationType) {
        this.merchantConfigurationType = merchantConfigurationType;
    }

    public String getKey() {
        return key;
    }

    public MerchantConfiguration key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public MerchantConfiguration value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MerchantStore getMerchantStore() {
        return merchantStore;
    }

    public MerchantConfiguration merchantStore(MerchantStore merchantStore) {
        this.merchantStore = merchantStore;
        return this;
    }

    public void setMerchantStore(MerchantStore merchantStore) {
        this.merchantStore = merchantStore;
    }

    

    
}
