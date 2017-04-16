package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.smartshop.eshop.domain.enumeration.MerchantConfigurationType;

/**
 * A MerchantConfiguration.
 */
@Entity
@Table(name = "merchant_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "merchantconfiguration")
public class MerchantConfiguration extends BusinessDomain<Long, MerchantConfiguration> implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MerchantConfiguration merchantConfiguration = (MerchantConfiguration) o;
        if (merchantConfiguration.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, merchantConfiguration.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MerchantConfiguration{" +
            "id=" + id +
            ", merchantConfigurationType='" + merchantConfigurationType + "'" +
            ", key='" + key + "'" +
            ", value='" + value + "'" +
            '}';
    }
}
