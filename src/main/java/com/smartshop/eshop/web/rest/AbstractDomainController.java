package com.smartshop.eshop.web.rest;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.smartshop.eshop.domain.BusinessDomain;
import com.smartshop.eshop.service.AbstractDomainService;
import com.smartshop.eshop.web.rest.util.HeaderUtil;
import com.smartshop.eshop.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

@RestController("BasicEntityController")
@RequestMapping("/{sectionKey:.+}")
public abstract class AbstractDomainController<E extends BusinessDomain, K extends Serializable & Comparable<K>> {

	private final Logger log = LoggerFactory.getLogger(AbstractDomainController.class);

	private final AbstractDomainService<E, K> service;

	protected abstract String getSectionKey();

	protected abstract String getEntityName();

	public AbstractDomainController(AbstractDomainService<E, K> service) {
		this.service = service;
	}

	@Timed
	@PostMapping()
	public ResponseEntity<E> create(@Valid @RequestBody E entity) throws URISyntaxException {
		log.debug("REST request to save entity : {}", entity);
		if (entity.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(getEntityName(), "idexists",
					"A new entity cannot already have an ID")).body(null);
		}
		E result = service.save(entity);
		return ResponseEntity.created(new URI("/api/" + getSectionKey() + "/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(getEntityName(), result.getId().toString())).body(result);
	}

	@Timed
	@PutMapping()
	public ResponseEntity<E> updateEntity(@Valid @RequestBody E entity) throws URISyntaxException {
		log.debug("REST request to update entity : {}", entity);
		if (entity.getId() == null) {
			return create(entity);
		}
		E result = service.save(entity);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(getEntityName(), entity.getId().toString())).body(result);
	}

	@Timed
	@GetMapping()
	public ResponseEntity<List<E>> getAllentitys(@ApiParam Pageable pageable) {
		Page<E> page = service.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/" + getSectionKey());
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	@Timed
	@GetMapping("/{id}")
	public ResponseEntity<E> getEntity(@PathVariable K id) {
		E entity = service.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entity));
	}

	@Timed
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEntity(@PathVariable K id) {
		service.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(getEntityName(), id.toString()))
				.build();
	}

	@Timed
	@GetMapping("/_search")
	public ResponseEntity<List<E>> searchEntities(@RequestParam String query, @ApiParam Pageable pageable) {
		log.debug("REST request to search for a page of entitys for query {}", query);
		Page<E> page = service.search(query, pageable);
		HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page,
				"/api/" + getSectionKey() + "/_search");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

}
