package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.smartshop.eshop.domain.enumeration.TransactionEnum;

import com.smartshop.eshop.domain.enumeration.PaymentEnum;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "transaction")
public class Transaction extends BusinessDomain<Long, Transaction> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "details")
	private String details;

	@Enumerated(EnumType.STRING)
	@Column(name = "transaction_type")
	private TransactionEnum transactionType;

	@Column(name = "transaction_date")
	private LocalDate transactionDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_type")
	private PaymentEnum paymentType;

	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;

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

	public String getDetails() {
		return details;
	}

	public Transaction details(String details) {
		this.details = details;
		return this;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public TransactionEnum getTransactionType() {
		return transactionType;
	}

	public Transaction transactionType(TransactionEnum transactionType) {
		this.transactionType = transactionType;
		return this;
	}

	public void setTransactionType(TransactionEnum transactionType) {
		this.transactionType = transactionType;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public Transaction transactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
		return this;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public PaymentEnum getPaymentType() {
		return paymentType;
	}

	public Transaction paymentType(PaymentEnum paymentType) {
		this.paymentType = paymentType;
		return this;
	}

	public void setPaymentType(PaymentEnum paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Transaction amount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public SalesOrder getOrder() {
		return order;
	}

	public Transaction order(SalesOrder salesOrder) {
		this.order = salesOrder;
		return this;
	}

	public void setOrder(SalesOrder salesOrder) {
		this.order = salesOrder;
	}

}
