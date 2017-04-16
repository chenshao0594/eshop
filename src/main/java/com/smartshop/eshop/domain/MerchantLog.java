package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MerchantLog.
 */
@Entity
@Table(name = "merchant_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "merchantlog")
public class MerchantLog extends BusinessDomain<Long,MerchantLog>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_log")
    private String log;

    @Column(name = "module")
    private String module;

    @ManyToOne
    private MerchantStore store;

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

    public MerchantStore getStore() {
        return store;
    }

    public MerchantLog store(MerchantStore merchantStore) {
        this.store = merchantStore;
        return this;
    }

    public void setStore(MerchantStore merchantStore) {
        this.store = merchantStore;
    }

    

    
}
