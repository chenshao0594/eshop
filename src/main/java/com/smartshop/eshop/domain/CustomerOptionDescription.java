package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A CustomerOptionDescription.
 */
@Entity
@Table(name = "customer_option_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customeroptiondescription")
public class CustomerOptionDescription extends BusinessDomain<Long, CustomerOptionDescription> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "customer_option_comment")
    private String customerOptionComment;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private CustomerOption customerOption;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public CustomerOptionDescription title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustomerOptionComment() {
        return customerOptionComment;
    }

    public CustomerOptionDescription customerOptionComment(String customerOptionComment) {
        this.customerOptionComment = customerOptionComment;
        return this;
    }

    public void setCustomerOptionComment(String customerOptionComment) {
        this.customerOptionComment = customerOptionComment;
    }

    public String getName() {
        return name;
    }

    public CustomerOptionDescription name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CustomerOptionDescription description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CustomerOption getCustomerOption() {
        return customerOption;
    }

    public CustomerOptionDescription customerOption(CustomerOption customerOption) {
        this.customerOption = customerOption;
        return this;
    }

    public void setCustomerOption(CustomerOption customerOption) {
        this.customerOption = customerOption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerOptionDescription customerOptionDescription = (CustomerOptionDescription) o;
        if (customerOptionDescription.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customerOptionDescription.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomerOptionDescription{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", customerOptionComment='" + customerOptionComment + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
