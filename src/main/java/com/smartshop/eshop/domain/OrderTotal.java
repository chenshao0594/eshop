package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.smartshop.eshop.domain.enumeration.OrderValueType;

import com.smartshop.eshop.domain.enumeration.OrderTotalType;

/**
 * A OrderTotal.
 */
@Entity
@Table(name = "order_total")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ordertotal")
public class OrderTotal extends BusinessDomain<Long,OrderTotal>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_value", precision=10, scale=2)
    private BigDecimal value;

    @Column(name = "order_total_code")
    private String orderTotalCode;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_value_type")
    private OrderValueType orderValueType;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_total_type")
    private OrderTotalType orderTotalType;

    @Column(name = "title")
    private String title;

    @Column(name = "module")
    private String module;

    @ManyToOne
    private SalesOrder order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public OrderTotal value(BigDecimal value) {
        this.value = value;
        return this;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getOrderTotalCode() {
        return orderTotalCode;
    }

    public OrderTotal orderTotalCode(String orderTotalCode) {
        this.orderTotalCode = orderTotalCode;
        return this;
    }

    public void setOrderTotalCode(String orderTotalCode) {
        this.orderTotalCode = orderTotalCode;
    }

    public String getText() {
        return text;
    }

    public OrderTotal text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public OrderValueType getOrderValueType() {
        return orderValueType;
    }

    public OrderTotal orderValueType(OrderValueType orderValueType) {
        this.orderValueType = orderValueType;
        return this;
    }

    public void setOrderValueType(OrderValueType orderValueType) {
        this.orderValueType = orderValueType;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public OrderTotal sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public OrderTotalType getOrderTotalType() {
        return orderTotalType;
    }

    public OrderTotal orderTotalType(OrderTotalType orderTotalType) {
        this.orderTotalType = orderTotalType;
        return this;
    }

    public void setOrderTotalType(OrderTotalType orderTotalType) {
        this.orderTotalType = orderTotalType;
    }

    public String getTitle() {
        return title;
    }

    public OrderTotal title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModule() {
        return module;
    }

    public OrderTotal module(String module) {
        this.module = module;
        return this;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public SalesOrder getOrder() {
        return order;
    }

    public OrderTotal order(SalesOrder salesOrder) {
        this.order = salesOrder;
        return this;
    }

    public void setOrder(SalesOrder salesOrder) {
        this.order = salesOrder;
    }

    

    
}
