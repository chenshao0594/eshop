package com.smartshop.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.smartshop.eshop.domain.enumeration.PaymentType;

import com.smartshop.eshop.domain.enumeration.OrderChannel;

import com.smartshop.eshop.domain.enumeration.OrderType;

import com.smartshop.eshop.domain.enumeration.OrderStatus;

/**
 * A SalesOrder.
 */
@Entity
@Table(name = "sales_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "salesorder")
public class SalesOrder extends BusinessDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "confirmed_address")
    private Boolean confirmedAddress;

    @Column(name = "order_date_finished")
    private LocalDate orderDateFinished;

    @Column(name = "total", precision=10, scale=2)
    private BigDecimal total;

    @Column(name = "payment_module_code")
    private String paymentModuleCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "locale")
    private String locale;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel")
    private OrderChannel channel;

    @Column(name = "customer_email_address")
    private String customerEmailAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "last_modified")
    private LocalDate lastModified;

    @Column(name = "currency_value", precision=10, scale=2)
    private BigDecimal currencyValue;

    @Column(name = "date_purchased")
    private LocalDate datePurchased;

    @Column(name = "shipping_module_code")
    private String shippingModuleCode;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "customer_agreement")
    private Boolean customerAgreement;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderTotal> orderTotals = new HashSet<>();

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderStatusHistory> orderHistories = new HashSet<>();

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderProduct> orderProducts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public SalesOrder customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Boolean isConfirmedAddress() {
        return confirmedAddress;
    }

    public SalesOrder confirmedAddress(Boolean confirmedAddress) {
        this.confirmedAddress = confirmedAddress;
        return this;
    }

    public void setConfirmedAddress(Boolean confirmedAddress) {
        this.confirmedAddress = confirmedAddress;
    }

    public LocalDate getOrderDateFinished() {
        return orderDateFinished;
    }

    public SalesOrder orderDateFinished(LocalDate orderDateFinished) {
        this.orderDateFinished = orderDateFinished;
        return this;
    }

    public void setOrderDateFinished(LocalDate orderDateFinished) {
        this.orderDateFinished = orderDateFinished;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public SalesOrder total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getPaymentModuleCode() {
        return paymentModuleCode;
    }

    public SalesOrder paymentModuleCode(String paymentModuleCode) {
        this.paymentModuleCode = paymentModuleCode;
        return this;
    }

    public void setPaymentModuleCode(String paymentModuleCode) {
        this.paymentModuleCode = paymentModuleCode;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public SalesOrder paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getLocale() {
        return locale;
    }

    public SalesOrder locale(String locale) {
        this.locale = locale;
        return this;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public OrderChannel getChannel() {
        return channel;
    }

    public SalesOrder channel(OrderChannel channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(OrderChannel channel) {
        this.channel = channel;
    }

    public String getCustomerEmailAddress() {
        return customerEmailAddress;
    }

    public SalesOrder customerEmailAddress(String customerEmailAddress) {
        this.customerEmailAddress = customerEmailAddress;
        return this;
    }

    public void setCustomerEmailAddress(String customerEmailAddress) {
        this.customerEmailAddress = customerEmailAddress;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public SalesOrder orderType(OrderType orderType) {
        this.orderType = orderType;
        return this;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public SalesOrder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public SalesOrder lastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }

    public BigDecimal getCurrencyValue() {
        return currencyValue;
    }

    public SalesOrder currencyValue(BigDecimal currencyValue) {
        this.currencyValue = currencyValue;
        return this;
    }

    public void setCurrencyValue(BigDecimal currencyValue) {
        this.currencyValue = currencyValue;
    }

    public LocalDate getDatePurchased() {
        return datePurchased;
    }

    public SalesOrder datePurchased(LocalDate datePurchased) {
        this.datePurchased = datePurchased;
        return this;
    }

    public void setDatePurchased(LocalDate datePurchased) {
        this.datePurchased = datePurchased;
    }

    public String getShippingModuleCode() {
        return shippingModuleCode;
    }

    public SalesOrder shippingModuleCode(String shippingModuleCode) {
        this.shippingModuleCode = shippingModuleCode;
        return this;
    }

    public void setShippingModuleCode(String shippingModuleCode) {
        this.shippingModuleCode = shippingModuleCode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public SalesOrder ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Boolean isCustomerAgreement() {
        return customerAgreement;
    }

    public SalesOrder customerAgreement(Boolean customerAgreement) {
        this.customerAgreement = customerAgreement;
        return this;
    }

    public void setCustomerAgreement(Boolean customerAgreement) {
        this.customerAgreement = customerAgreement;
    }

    public Set<OrderTotal> getOrderTotals() {
        return orderTotals;
    }

    public SalesOrder orderTotals(Set<OrderTotal> orderTotals) {
        this.orderTotals = orderTotals;
        return this;
    }

    public SalesOrder addOrderTotal(OrderTotal orderTotal) {
        this.orderTotals.add(orderTotal);
        orderTotal.setOrder(this);
        return this;
    }

    public SalesOrder removeOrderTotal(OrderTotal orderTotal) {
        this.orderTotals.remove(orderTotal);
        orderTotal.setOrder(null);
        return this;
    }

    public void setOrderTotals(Set<OrderTotal> orderTotals) {
        this.orderTotals = orderTotals;
    }

    public Set<OrderStatusHistory> getOrderHistories() {
        return orderHistories;
    }

    public SalesOrder orderHistories(Set<OrderStatusHistory> orderStatusHistories) {
        this.orderHistories = orderStatusHistories;
        return this;
    }

    public SalesOrder addOrderHistory(OrderStatusHistory orderStatusHistory) {
        this.orderHistories.add(orderStatusHistory);
        orderStatusHistory.setOrder(this);
        return this;
    }

    public SalesOrder removeOrderHistory(OrderStatusHistory orderStatusHistory) {
        this.orderHistories.remove(orderStatusHistory);
        orderStatusHistory.setOrder(null);
        return this;
    }

    public void setOrderHistories(Set<OrderStatusHistory> orderStatusHistories) {
        this.orderHistories = orderStatusHistories;
    }

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public SalesOrder orderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
        return this;
    }

    public SalesOrder addOrderProducts(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
        return this;
    }

    public SalesOrder removeOrderProducts(OrderProduct orderProduct) {
        this.orderProducts.remove(orderProduct);
        orderProduct.setOrder(null);
        return this;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SalesOrder salesOrder = (SalesOrder) o;
        if (salesOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, salesOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SalesOrder{" +
            "id=" + id +
            ", customerId='" + customerId + "'" +
            ", confirmedAddress='" + confirmedAddress + "'" +
            ", orderDateFinished='" + orderDateFinished + "'" +
            ", total='" + total + "'" +
            ", paymentModuleCode='" + paymentModuleCode + "'" +
            ", paymentType='" + paymentType + "'" +
            ", locale='" + locale + "'" +
            ", channel='" + channel + "'" +
            ", customerEmailAddress='" + customerEmailAddress + "'" +
            ", orderType='" + orderType + "'" +
            ", status='" + status + "'" +
            ", lastModified='" + lastModified + "'" +
            ", currencyValue='" + currencyValue + "'" +
            ", datePurchased='" + datePurchased + "'" +
            ", shippingModuleCode='" + shippingModuleCode + "'" +
            ", ipAddress='" + ipAddress + "'" +
            ", customerAgreement='" + customerAgreement + "'" +
            '}';
    }
}
