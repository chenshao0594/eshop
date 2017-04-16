package com.smartshop.eshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A FileHistory.
 */
@Entity
@Table(name = "file_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "filehistory")
public class FileHistory extends BusinessDomain<Long, FileHistory> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "date_added")
	private LocalDate dateAdded;

	@Column(name = "date_deleted")
	private LocalDate dateDeleted;

	@Column(name = "download_count")
	private Integer downloadCount;

	@Column(name = "file_id")
	private Long fileId;

	@Column(name = "accounted_date")
	private LocalDate accountedDate;

	@Column(name = "filesize")
	private Integer filesize;

	@ManyToOne
	private MerchantStore store;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateAdded() {
		return dateAdded;
	}

	public FileHistory dateAdded(LocalDate dateAdded) {
		this.dateAdded = dateAdded;
		return this;
	}

	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded = dateAdded;
	}

	public LocalDate getDateDeleted() {
		return dateDeleted;
	}

	public FileHistory dateDeleted(LocalDate dateDeleted) {
		this.dateDeleted = dateDeleted;
		return this;
	}

	public void setDateDeleted(LocalDate dateDeleted) {
		this.dateDeleted = dateDeleted;
	}

	public Integer getDownloadCount() {
		return downloadCount;
	}

	public FileHistory downloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
		return this;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	public Long getFileId() {
		return fileId;
	}

	public FileHistory fileId(Long fileId) {
		this.fileId = fileId;
		return this;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public LocalDate getAccountedDate() {
		return accountedDate;
	}

	public FileHistory accountedDate(LocalDate accountedDate) {
		this.accountedDate = accountedDate;
		return this;
	}

	public void setAccountedDate(LocalDate accountedDate) {
		this.accountedDate = accountedDate;
	}

	public Integer getFilesize() {
		return filesize;
	}

	public FileHistory filesize(Integer filesize) {
		this.filesize = filesize;
		return this;
	}

	public void setFilesize(Integer filesize) {
		this.filesize = filesize;
	}

	public MerchantStore getStore() {
		return store;
	}

	public FileHistory store(MerchantStore merchantStore) {
		this.store = merchantStore;
		return this;
	}

	public void setStore(MerchantStore merchantStore) {
		this.store = merchantStore;
	}

}
