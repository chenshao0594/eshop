package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OrderProductDownload.
 */
@Entity
@Table(name = "order_product_download")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orderproductdownload")
public class OrderProductDownload extends BusinessDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "maxdays")
    private Integer maxdays;

    @Column(name = "download_count")
    private Integer downloadCount;

    @Column(name = "order_product_filename")
    private String orderProductFilename;

    @ManyToOne
    private OrderProduct orderProduct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaxdays() {
        return maxdays;
    }

    public OrderProductDownload maxdays(Integer maxdays) {
        this.maxdays = maxdays;
        return this;
    }

    public void setMaxdays(Integer maxdays) {
        this.maxdays = maxdays;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public OrderProductDownload downloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
        return this;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getOrderProductFilename() {
        return orderProductFilename;
    }

    public OrderProductDownload orderProductFilename(String orderProductFilename) {
        this.orderProductFilename = orderProductFilename;
        return this;
    }

    public void setOrderProductFilename(String orderProductFilename) {
        this.orderProductFilename = orderProductFilename;
    }

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public OrderProductDownload orderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
        return this;
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderProductDownload orderProductDownload = (OrderProductDownload) o;
        if (orderProductDownload.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, orderProductDownload.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderProductDownload{" +
            "id=" + id +
            ", maxdays='" + maxdays + "'" +
            ", downloadCount='" + downloadCount + "'" +
            ", orderProductFilename='" + orderProductFilename + "'" +
            '}';
    }
}
