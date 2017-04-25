package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Attachment.
 */
@Entity
@Table(name = "attachment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "attachment")
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    private byte[] content;

    @Column(name = "content_content_type", nullable = false)
    private String contentContentType;

    @Column(name = "jhi_size")
    private Long size;

    @NotNull
    @Column(name = "bo_name", nullable = false)
    private String boName;

    @NotNull
    @Column(name = "bo_id", nullable = false)
    private Long boId;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private String created_by;

    @Column(name = "created_date")
    private LocalDate created_date;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String last_modified_by;

    @Column(name = "last_modified_date")
    private LocalDate last_modified_date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Attachment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public Attachment content(byte[] content) {
        this.content = content;
        return this;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public Attachment contentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
        return this;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    public Long getSize() {
        return size;
    }

    public Attachment size(Long size) {
        this.size = size;
        return this;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getBoName() {
        return boName;
    }

    public Attachment boName(String boName) {
        this.boName = boName;
        return this;
    }

    public void setBoName(String boName) {
        this.boName = boName;
    }

    public Long getBoId() {
        return boId;
    }

    public Attachment boId(Long boId) {
        this.boId = boId;
        return this;
    }

    public void setBoId(Long boId) {
        this.boId = boId;
    }

    public String getCreated_by() {
        return created_by;
    }

    public Attachment created_by(String created_by) {
        this.created_by = created_by;
        return this;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public LocalDate getCreated_date() {
        return created_date;
    }

    public Attachment created_date(LocalDate created_date) {
        this.created_date = created_date;
        return this;
    }

    public void setCreated_date(LocalDate created_date) {
        this.created_date = created_date;
    }

    public String getLast_modified_by() {
        return last_modified_by;
    }

    public Attachment last_modified_by(String last_modified_by) {
        this.last_modified_by = last_modified_by;
        return this;
    }

    public void setLast_modified_by(String last_modified_by) {
        this.last_modified_by = last_modified_by;
    }

    public LocalDate getLast_modified_date() {
        return last_modified_date;
    }

    public Attachment last_modified_date(LocalDate last_modified_date) {
        this.last_modified_date = last_modified_date;
        return this;
    }

    public void setLast_modified_date(LocalDate last_modified_date) {
        this.last_modified_date = last_modified_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attachment attachment = (Attachment) o;
        if (attachment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, attachment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Attachment{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", content='" + content + "'" +
            ", contentContentType='" + contentContentType + "'" +
            ", size='" + size + "'" +
            ", boName='" + boName + "'" +
            ", boId='" + boId + "'" +
            ", created_by='" + created_by + "'" +
            ", created_date='" + created_date + "'" +
            ", last_modified_by='" + last_modified_by + "'" +
            ", last_modified_date='" + last_modified_date + "'" +
            '}';
    }
}
