package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A ProductPriceDescription.
 */
@Entity
@Table(name = "product_price_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productpricedescription")
public class ProductPriceDescription extends BusinessDomain<Long, ProductPriceDescription> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "d_efaultpricedescription")
    private String dEFAULTPRICEDESCRIPTION;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private ProductPrice productPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public ProductPriceDescription title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getdEFAULTPRICEDESCRIPTION() {
        return dEFAULTPRICEDESCRIPTION;
    }

    public ProductPriceDescription dEFAULTPRICEDESCRIPTION(String dEFAULTPRICEDESCRIPTION) {
        this.dEFAULTPRICEDESCRIPTION = dEFAULTPRICEDESCRIPTION;
        return this;
    }

    public void setdEFAULTPRICEDESCRIPTION(String dEFAULTPRICEDESCRIPTION) {
        this.dEFAULTPRICEDESCRIPTION = dEFAULTPRICEDESCRIPTION;
    }

    public String getName() {
        return name;
    }

    public ProductPriceDescription name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ProductPriceDescription description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public ProductPriceDescription productPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
        return this;
    }

    public void setProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductPriceDescription productPriceDescription = (ProductPriceDescription) o;
        if (productPriceDescription.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, productPriceDescription.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductPriceDescription{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", dEFAULTPRICEDESCRIPTION='" + dEFAULTPRICEDESCRIPTION + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}