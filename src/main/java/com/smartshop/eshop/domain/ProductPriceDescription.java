package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProductPriceDescription.
 */
@Entity
@Table(name = "product_price_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productpricedescription")
public class ProductPriceDescription extends BusinessDomain<Long,ProductPriceDescription>  implements Serializable {

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
    private Language language;

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

    public Language getLanguage() {
        return language;
    }

    public ProductPriceDescription language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
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

    

    
}
