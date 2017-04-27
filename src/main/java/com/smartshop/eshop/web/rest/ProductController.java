package com.smartshop.eshop.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.smartshop.eshop.domain.Attachment;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.service.AttachmentService;
import com.smartshop.eshop.service.ProductService;
import com.smartshop.eshop.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Product.
 */
@Transactional
@RestController
@RequestMapping("/api/" + ProductController.SECTION_KEY)
public class ProductController extends AbstractDomainController<Product, Long> {

	private final Logger log = LoggerFactory.getLogger(ProductController.class);
	public static final String SECTION_KEY = "products";
	private static final String ENTITY_NAME = "product";

	private final ProductService productService;

	@Autowired
	private AttachmentService attachmentService;

	public ProductController(ProductService productService) {
		super(productService);
		this.productService = productService;
	}

	@Timed
	@GetMapping("/{id}/attachments")
	public ResponseEntity<List<Attachment>> getAllAttachments(@PathVariable("id") Long boid,
			@ApiParam Pageable pageable) {
		log.debug("REST request to get a page of Attachments");
		Page<Attachment> page = attachmentService.findAllByBOInfo("product", boid, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,
				"/api/products/" + boid + "/attachments");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	@Override
	protected String getSectionKey() {
		return SECTION_KEY;
	}

	@Override
	protected String getEntityName() {
		return ENTITY_NAME;
	}

}