package com.smartshop.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A OrderAccount.
 */
@Entity
@Table(name = "order_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orderaccount")
public class OrderAccount extends BusinessDomain<Long, OrderAccount> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "order_account_start_date")
	private LocalDate orderAccountStartDate;

	@Column(name = "order_account_end_date")
	private LocalDate orderAccountEndDate;

	@Column(name = "order_account_bill_day")
	private Integer orderAccountBillDay;

	@OneToMany(mappedBy = "orderAccount")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<OrderAccountProduct> orderAccountProducts = new HashSet<>();

	@ManyToOne
	private SalesOrder order;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getOrderAccountStartDate() {
		return orderAccountStartDate;
	}

	public OrderAccount orderAccountStartDate(LocalDate orderAccountStartDate) {
		this.orderAccountStartDate = orderAccountStartDate;
		return this;
	}

	public void setOrderAccountStartDate(LocalDate orderAccountStartDate) {
		this.orderAccountStartDate = orderAccountStartDate;
	}

	public LocalDate getOrderAccountEndDate() {
		return orderAccountEndDate;
	}

	public OrderAccount orderAccountEndDate(LocalDate orderAccountEndDate) {
		this.orderAccountEndDate = orderAccountEndDate;
		return this;
	}

	public void setOrderAccountEndDate(LocalDate orderAccountEndDate) {
		this.orderAccountEndDate = orderAccountEndDate;
	}

	public Integer getOrderAccountBillDay() {
		return orderAccountBillDay;
	}

	public OrderAccount orderAccountBillDay(Integer orderAccountBillDay) {
		this.orderAccountBillDay = orderAccountBillDay;
		return this;
	}

	public void setOrderAccountBillDay(Integer orderAccountBillDay) {
		this.orderAccountBillDay = orderAccountBillDay;
	}

	public Set<OrderAccountProduct> getOrderAccountProducts() {
		return orderAccountProducts;
	}

	public OrderAccount orderAccountProducts(Set<OrderAccountProduct> orderAccountProducts) {
		this.orderAccountProducts = orderAccountProducts;
		return this;
	}

	public OrderAccount addOrderAccountProducts(OrderAccountProduct orderAccountProduct) {
		this.orderAccountProducts.add(orderAccountProduct);
		orderAccountProduct.setOrderAccount(this);
		return this;
	}

	public OrderAccount removeOrderAccountProducts(OrderAccountProduct orderAccountProduct) {
		this.orderAccountProducts.remove(orderAccountProduct);
		orderAccountProduct.setOrderAccount(null);
		return this;
	}

	public void setOrderAccountProducts(Set<OrderAccountProduct> orderAccountProducts) {
		this.orderAccountProducts = orderAccountProducts;
	}

	public SalesOrder getOrder() {
		return order;
	}

	public OrderAccount order(SalesOrder salesOrder) {
		this.order = salesOrder;
		return this;
	}

	public void setOrder(SalesOrder salesOrder) {
		this.order = salesOrder;
	}

}
