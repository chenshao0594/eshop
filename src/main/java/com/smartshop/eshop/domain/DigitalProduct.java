package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DigitalProduct.
 */
@Entity
@Table(name = "digital_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "digitalproduct")
public class DigitalProduct extends BusinessDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_file_name")
    private String productFileName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductFileName() {
        return productFileName;
    }

    public DigitalProduct productFileName(String productFileName) {
        this.productFileName = productFileName;
        return this;
    }

    public void setProductFileName(String productFileName) {
        this.productFileName = productFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DigitalProduct digitalProduct = (DigitalProduct) o;
        if (digitalProduct.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, digitalProduct.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DigitalProduct{" +
            "id=" + id +
            ", productFileName='" + productFileName + "'" +
            '}';
    }
}
