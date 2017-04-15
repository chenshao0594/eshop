package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProductType.
 */
@Entity
@Table(name = "product_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "producttype")
public class ProductType extends BusinessDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "g_eneraltype")
    private String gENERALTYPE;

    @Column(name = "allow_add_to_cart")
    private Boolean allowAddToCart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public ProductType code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getgENERALTYPE() {
        return gENERALTYPE;
    }

    public ProductType gENERALTYPE(String gENERALTYPE) {
        this.gENERALTYPE = gENERALTYPE;
        return this;
    }

    public void setgENERALTYPE(String gENERALTYPE) {
        this.gENERALTYPE = gENERALTYPE;
    }

    public Boolean isAllowAddToCart() {
        return allowAddToCart;
    }

    public ProductType allowAddToCart(Boolean allowAddToCart) {
        this.allowAddToCart = allowAddToCart;
        return this;
    }

    public void setAllowAddToCart(Boolean allowAddToCart) {
        this.allowAddToCart = allowAddToCart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductType productType = (ProductType) o;
        if (productType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, productType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductType{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", gENERALTYPE='" + gENERALTYPE + "'" +
            ", allowAddToCart='" + allowAddToCart + "'" +
            '}';
    }
}
