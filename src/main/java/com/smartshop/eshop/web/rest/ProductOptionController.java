package com.smartshop.eshop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.smartshop.eshop.domain.ProductOption;
import com.smartshop.eshop.domain.ProductOptionValue;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.service.ProductOptionService;
import com.smartshop.eshop.service.ProductOptionValueService;
import com.smartshop.eshop.web.rest.util.HeaderUtil;
import com.smartshop.eshop.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing ProductOption.
 */
@RestController
@RequestMapping("/api/" + ProductOptionController.SECTION_KEY)
public class ProductOptionController extends AbstractDomainController<ProductOption, Long> {

	private final Logger log = LoggerFactory.getLogger(ProductOptionController.class);
	public static final String SECTION_KEY = "product-options";
	private static final String ENTITY_NAME = "productOption";

	private final ProductOptionService productOptionService;

	@Autowired
	private ProductOptionValueService productOptionValueService;

	public ProductOptionController(ProductOptionService productOptionService) {
		super(productOptionService);
		this.productOptionService = productOptionService;
	}

	@Override
	protected void preCreate(ProductOption option) throws BusinessException {
		ProductOption productOption = this.productOptionService.getByCode(option.getMerchantStore(), option.getCode());
		if (productOption != null) {
			throw new BusinessException("code has been existed !!!");
		}
	};

	@Timed
	@PostMapping("/{id}/product-option-values")
	public ResponseEntity<ProductOptionValue> createProductOptionValue(@PathVariable Long id,
			@Valid @RequestBody ProductOptionValue entity) throws URISyntaxException {
		log.debug("REST request to save entity : {}", entity);
		if (entity.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(getEntityName(), "idexists",
					"A new entity cannot already have an ID")).body(null);
		}
		ProductOption productOption = this.productOptionService.findOne(id);
		entity.setProductOption(productOption);
		productOption.getProductOptionValues().add(entity);
		ProductOption result = this.productOptionService.save(productOption);
		return ResponseEntity.created(new URI("/api/product-options/" + id + "/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(getEntityName(), result.getId().toString())).body(entity);

	}

	@Timed
	@GetMapping("/{id}/product-option-values")
	public ResponseEntity<List<ProductOptionValue>> getAllProductOptionValues(@PathVariable Long id,
			@ApiParam Pageable pageable) {
		ProductOption option = this.productOptionService.findOne(id);
		if (option == null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(getEntityName(), "null ", "null"))
					.body(null);
		}
		Page<ProductOptionValue> page = productOptionValueService.queryOptionValuesByOption(option, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/" + getSectionKey());
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