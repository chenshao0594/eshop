package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A MerchantLog.
 */
@Entity
@Table(name = "merchant_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "merchantlog")
public class MerchantLog extends BusinessDomain<Long, MerchantLog> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_log")
    private String log;

    @Column(name = "module")
    private String module;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public MerchantLog log(String log) {
        this.log = log;
        return this;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getModule() {
        return module;
    }

    public MerchantLog module(String module) {
        this.module = module;
        return this;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MerchantLog merchantLog = (MerchantLog) o;
        if (merchantLog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, merchantLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MerchantLog{" +
            "id=" + id +
            ", log='" + log + "'" +
            ", module='" + module + "'" +
            '}';
    }
}
