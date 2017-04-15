package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.smartshop.eshop.domain.enumeration.OrderStatus;

/**
 * A OrderStatusHistory.
 */
@Entity
@Table(name = "order_status_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orderstatushistory")
public class OrderStatusHistory extends BusinessDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comments")
    private String comments;

    @Column(name = "customer_notified")
    private Integer customerNotified;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @ManyToOne
    private SalesOrder order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public OrderStatusHistory comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getCustomerNotified() {
        return customerNotified;
    }

    public OrderStatusHistory customerNotified(Integer customerNotified) {
        this.customerNotified = customerNotified;
        return this;
    }

    public void setCustomerNotified(Integer customerNotified) {
        this.customerNotified = customerNotified;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public OrderStatusHistory dateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderStatusHistory status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public SalesOrder getOrder() {
        return order;
    }

    public OrderStatusHistory order(SalesOrder salesOrder) {
        this.order = salesOrder;
        return this;
    }

    public void setOrder(SalesOrder salesOrder) {
        this.order = salesOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderStatusHistory orderStatusHistory = (OrderStatusHistory) o;
        if (orderStatusHistory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, orderStatusHistory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderStatusHistory{" +
            "id=" + id +
            ", comments='" + comments + "'" +
            ", customerNotified='" + customerNotified + "'" +
            ", dateAdded='" + dateAdded + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
