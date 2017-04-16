package com.smartshop.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Manufacturer.
 */
@Entity
@Table(name = "manufacturer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "manufacturer")
public class Manufacturer extends BusinessDomain<Long,Manufacturer>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_order")
    private Integer order;

    @Column(name = "image")
    private String image;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @OneToMany(mappedBy = "manufacturer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ManufacturerDescription> descriptions = new HashSet<>();

    @ManyToOne
    private MerchantStore merchantStore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public Manufacturer order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getImage() {
        return image;
    }

    public Manufacturer image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public Manufacturer code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<ManufacturerDescription> getDescriptions() {
        return descriptions;
    }

    public Manufacturer descriptions(Set<ManufacturerDescription> manufacturerDescriptions) {
        this.descriptions = manufacturerDescriptions;
        return this;
    }

    public Manufacturer addDescriptions(ManufacturerDescription manufacturerDescription) {
        this.descriptions.add(manufacturerDescription);
        manufacturerDescription.setManufacturer(this);
        return this;
    }

    public Manufacturer removeDescriptions(ManufacturerDescription manufacturerDescription) {
        this.descriptions.remove(manufacturerDescription);
        manufacturerDescription.setManufacturer(null);
        return this;
    }

    public void setDescriptions(Set<ManufacturerDescription> manufacturerDescriptions) {
        this.descriptions = manufacturerDescriptions;
    }

    public MerchantStore getMerchantStore() {
        return merchantStore;
    }

    public Manufacturer merchantStore(MerchantStore merchantStore) {
        this.merchantStore = merchantStore;
        return this;
    }

    public void setMerchantStore(MerchantStore merchantStore) {
        this.merchantStore = merchantStore;
    }

    

    
}
