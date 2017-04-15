package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CustomerOptin.
 */
@Entity
@Table(name = "customer_optin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customeroptin")
public class CustomerOptin extends BusinessDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "optin_date")
    private LocalDate optinDate;

    @Column(name = "email")
    private String email;

    @Column(name = "jhi_value")
    private String value;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOptinDate() {
        return optinDate;
    }

    public CustomerOptin optinDate(LocalDate optinDate) {
        this.optinDate = optinDate;
        return this;
    }

    public void setOptinDate(LocalDate optinDate) {
        this.optinDate = optinDate;
    }

    public String getEmail() {
        return email;
    }

    public CustomerOptin email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getValue() {
        return value;
    }

    public CustomerOptin value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFirstName() {
        return firstName;
    }

    public CustomerOptin firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public CustomerOptin lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerOptin customerOptin = (CustomerOptin) o;
        if (customerOptin.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customerOptin.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomerOptin{" +
            "id=" + id +
            ", optinDate='" + optinDate + "'" +
            ", email='" + email + "'" +
            ", value='" + value + "'" +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            '}';
    }
}
