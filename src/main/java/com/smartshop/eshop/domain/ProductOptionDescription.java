package com.smartshop.eshop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A ProductOptionDescription.
 */
@Entity
@Table(name = "product_option_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productoptiondescription")
public class ProductOptionDescription extends   Description {

    private static final long serialVersionUID = 1L;


    @ManyToOne(targetEntity = ProductOption.class)
	@JoinColumn(name = "PRODUCT_OPTION_ID", nullable = false)
	private ProductOption productOption;
	
	@Column(name="PRODUCT_OPTION_COMMENT")
	@Type(type = "org.hibernate.type.MaterializedClobType")
	private String productOptionComment;

	public ProductOption getProductOption() {
		return productOption;
	}

	public void setProductOption(ProductOption productOption) {
		this.productOption = productOption;
	}

	public String getProductOptionComment() {
		return productOptionComment;
	}

	public void setProductOptionComment(String productOptionComment) {
		this.productOptionComment = productOptionComment;
	}
	

}
