package com.smartshop.eshop.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.io.Serializable;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.BusinessDomain;
import com.smartshop.eshop.service.AbstractDomainService;

public abstract class AbstractDomainServiceImpl<E extends BusinessDomain, K extends Serializable & Comparable<K>>
		implements AbstractDomainService<E, K> {

	private JpaRepository<E, K> repository;

	private ElasticsearchRepository<E, K> searchRepository;

	public AbstractDomainServiceImpl(JpaRepository<E, K> repository, ElasticsearchRepository<E, K> searchRepository) {
		this.repository = repository;
		this.searchRepository = searchRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public E getById(K id) {
		return repository.findOne(id);
	}

	@Override
	public E save(E entity) throws ServiceException {
		repository.saveAndFlush(entity);
		searchRepository.save(entity);
		return entity;
	}

	@Override
	public void update(E entity) throws ServiceException {
		save(entity);
	}

	@Override
	public void delete(E entity) throws ServiceException {
		repository.delete(entity);
		searchRepository.delete(entity);
	}

	@Override
	public void delete(K id) throws ServiceException {
		repository.delete(id);
		searchRepository.delete(id);
	}

	@Override
	public void flush() {
		repository.flush();
	}

	@Override
	public List<E> list() {
		return repository.findAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

	@Override
	public E findOne(K id) {
		return repository.findOne(id);
	}

	@Override
	public Page<E> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<E> search(String query, Pageable pageable) {
		Page<E> result = searchRepository.search(queryStringQuery(query), pageable);
		return result;
	}
}
