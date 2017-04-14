package com.smartshop.eshop.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smartshop.eshop.domain.BusinessDomain;

public interface AbstractDomainService <E extends BusinessDomain, K extends Serializable & Comparable<K>>{

	E save(E entity) throws ServiceException;
	void update(E entity) throws ServiceException;
	void delete(K id) throws ServiceException;
	List<E> list();
	Page<E> findAll(Pageable pageable);
	Long count();
	void flush();
    Page<E> search(String query, Pageable pageable);
	E getById(K id);
	E findOne(K id);
}
