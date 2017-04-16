package com.smartshop.eshop.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A ProductOptionValueDescription.
 */
@Entity
@Table(name = "product_option_value_desc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productoptionvaluedescription")
public class ProductOptionValueDescription extends Description implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5378532723742640134L;
	@ManyToOne(targetEntity = ProductOptionValue.class)
	@JoinColumn(name = "PRODUCT_OPTION_VALUE_ID")
	private ProductOptionValue productOptionValue;
	public ProductOptionValue getProductOptionValue() {
		return productOptionValue;
	}
	public void setProductOptionValue(ProductOptionValue productOptionValue) {
		this.productOptionValue = productOptionValue;
	}
	

}
