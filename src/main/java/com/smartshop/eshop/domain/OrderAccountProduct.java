package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A OrderAccountProduct.
 */
@Entity
@Table(name = "order_account_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orderaccountproduct")
public class OrderAccountProduct extends BusinessDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_account_product_id")
    private Long orderAccountProductId;

    @Column(name = "order_account_product_last_transaction_status")
    private Integer orderAccountProductLastTransactionStatus;

    @Column(name = "order_account_product_end_date")
    private LocalDate orderAccountProductEndDate;

    @Column(name = "order_account_product_start_date")
    private LocalDate orderAccountProductStartDate;

    @Column(name = "order_account_product_last_status_date")
    private LocalDate orderAccountProductLastStatusDate;

    @Column(name = "order_account_product_status")
    private Integer orderAccountProductStatus;

    @Column(name = "order_account_product_accounted_date")
    private LocalDate orderAccountProductAccountedDate;

    @Column(name = "order_account_product_payment_frequency_type")
    private Integer orderAccountProductPaymentFrequencyType;

    @Column(name = "order_account_product_eot")
    private LocalDate orderAccountProductEot;

    @ManyToOne
    private OrderAccount orderAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderAccountProductId() {
        return orderAccountProductId;
    }

    public OrderAccountProduct orderAccountProductId(Long orderAccountProductId) {
        this.orderAccountProductId = orderAccountProductId;
        return this;
    }

    public void setOrderAccountProductId(Long orderAccountProductId) {
        this.orderAccountProductId = orderAccountProductId;
    }

    public Integer getOrderAccountProductLastTransactionStatus() {
        return orderAccountProductLastTransactionStatus;
    }

    public OrderAccountProduct orderAccountProductLastTransactionStatus(Integer orderAccountProductLastTransactionStatus) {
        this.orderAccountProductLastTransactionStatus = orderAccountProductLastTransactionStatus;
        return this;
    }

    public void setOrderAccountProductLastTransactionStatus(Integer orderAccountProductLastTransactionStatus) {
        this.orderAccountProductLastTransactionStatus = orderAccountProductLastTransactionStatus;
    }

    public LocalDate getOrderAccountProductEndDate() {
        return orderAccountProductEndDate;
    }

    public OrderAccountProduct orderAccountProductEndDate(LocalDate orderAccountProductEndDate) {
        this.orderAccountProductEndDate = orderAccountProductEndDate;
        return this;
    }

    public void setOrderAccountProductEndDate(LocalDate orderAccountProductEndDate) {
        this.orderAccountProductEndDate = orderAccountProductEndDate;
    }

    public LocalDate getOrderAccountProductStartDate() {
        return orderAccountProductStartDate;
    }

    public OrderAccountProduct orderAccountProductStartDate(LocalDate orderAccountProductStartDate) {
        this.orderAccountProductStartDate = orderAccountProductStartDate;
        return this;
    }

    public void setOrderAccountProductStartDate(LocalDate orderAccountProductStartDate) {
        this.orderAccountProductStartDate = orderAccountProductStartDate;
    }

    public LocalDate getOrderAccountProductLastStatusDate() {
        return orderAccountProductLastStatusDate;
    }

    public OrderAccountProduct orderAccountProductLastStatusDate(LocalDate orderAccountProductLastStatusDate) {
        this.orderAccountProductLastStatusDate = orderAccountProductLastStatusDate;
        return this;
    }

    public void setOrderAccountProductLastStatusDate(LocalDate orderAccountProductLastStatusDate) {
        this.orderAccountProductLastStatusDate = orderAccountProductLastStatusDate;
    }

    public Integer getOrderAccountProductStatus() {
        return orderAccountProductStatus;
    }

    public OrderAccountProduct orderAccountProductStatus(Integer orderAccountProductStatus) {
        this.orderAccountProductStatus = orderAccountProductStatus;
        return this;
    }

    public void setOrderAccountProductStatus(Integer orderAccountProductStatus) {
        this.orderAccountProductStatus = orderAccountProductStatus;
    }

    public LocalDate getOrderAccountProductAccountedDate() {
        return orderAccountProductAccountedDate;
    }

    public OrderAccountProduct orderAccountProductAccountedDate(LocalDate orderAccountProductAccountedDate) {
        this.orderAccountProductAccountedDate = orderAccountProductAccountedDate;
        return this;
    }

    public void setOrderAccountProductAccountedDate(LocalDate orderAccountProductAccountedDate) {
        this.orderAccountProductAccountedDate = orderAccountProductAccountedDate;
    }

    public Integer getOrderAccountProductPaymentFrequencyType() {
        return orderAccountProductPaymentFrequencyType;
    }

    public OrderAccountProduct orderAccountProductPaymentFrequencyType(Integer orderAccountProductPaymentFrequencyType) {
        this.orderAccountProductPaymentFrequencyType = orderAccountProductPaymentFrequencyType;
        return this;
    }

    public void setOrderAccountProductPaymentFrequencyType(Integer orderAccountProductPaymentFrequencyType) {
        this.orderAccountProductPaymentFrequencyType = orderAccountProductPaymentFrequencyType;
    }

    public LocalDate getOrderAccountProductEot() {
        return orderAccountProductEot;
    }

    public OrderAccountProduct orderAccountProductEot(LocalDate orderAccountProductEot) {
        this.orderAccountProductEot = orderAccountProductEot;
        return this;
    }

    public void setOrderAccountProductEot(LocalDate orderAccountProductEot) {
        this.orderAccountProductEot = orderAccountProductEot;
    }

    public OrderAccount getOrderAccount() {
        return orderAccount;
    }

    public OrderAccountProduct orderAccount(OrderAccount orderAccount) {
        this.orderAccount = orderAccount;
        return this;
    }

    public void setOrderAccount(OrderAccount orderAccount) {
        this.orderAccount = orderAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderAccountProduct orderAccountProduct = (OrderAccountProduct) o;
        if (orderAccountProduct.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, orderAccountProduct.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderAccountProduct{" +
            "id=" + id +
            ", orderAccountProductId='" + orderAccountProductId + "'" +
            ", orderAccountProductLastTransactionStatus='" + orderAccountProductLastTransactionStatus + "'" +
            ", orderAccountProductEndDate='" + orderAccountProductEndDate + "'" +
            ", orderAccountProductStartDate='" + orderAccountProductStartDate + "'" +
            ", orderAccountProductLastStatusDate='" + orderAccountProductLastStatusDate + "'" +
            ", orderAccountProductStatus='" + orderAccountProductStatus + "'" +
            ", orderAccountProductAccountedDate='" + orderAccountProductAccountedDate + "'" +
            ", orderAccountProductPaymentFrequencyType='" + orderAccountProductPaymentFrequencyType + "'" +
            ", orderAccountProductEot='" + orderAccountProductEot + "'" +
            '}';
    }
}
