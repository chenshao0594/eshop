package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A DigitalProduct.
 */
@Entity
@Table(name = "digital_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "digitalproduct")
public class DigitalProduct extends BusinessDomain<Long, DigitalProduct> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "product_file_name")
	private String productFileName;

	@ManyToOne
	private Product product;

	@Override
	public Long getId() {
		return id;
	}

	@Override
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

	public Product getProduct() {
		return product;
	}

	public DigitalProduct product(Product product) {
		this.product = product;
		return this;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
