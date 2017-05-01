package com.smartshop.eshop.web.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.smartshop.core.catelog.dto.ProductOptionDTO;
import com.smartshop.eshop.domain.Attachment;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.domain.ProductOption;
import com.smartshop.eshop.service.AttachmentService;
import com.smartshop.eshop.service.ProductOptionService;
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

	@Autowired
	private ProductOptionService productOptionService;

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

	@Timed
	@PostMapping("{id}/productOptions")
	public ResponseEntity<Set<ProductOption>> createProductOptions(@PathVariable Long id,
			@Valid @RequestBody ProductOptionDTO productOption) throws URISyntaxException {
		Set<Long> optionIds = productOption.getOptionIds();
		if (CollectionUtils.isEmpty(optionIds)) {
			return ResponseEntity.ok().body(null);
		}
		Product p = this.productService.findOne(id);
		for (Long optionId : optionIds) {
			ProductOption po = this.productOptionService.findOne(optionId);
			p.addProductOption(po);
		}
		this.productService.save(p);
		return ResponseEntity.ok().body(p.getProductOptions());
	}

	@Timed
	@GetMapping("{id}/productOptions")
	public ResponseEntity<List<ProductOption>> listProductOptions(@PathVariable Long id) throws URISyntaxException {
		Product p = this.productService.findOne(id);
		return ResponseEntity.ok().body(new ArrayList(p.getProductOptions()));
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