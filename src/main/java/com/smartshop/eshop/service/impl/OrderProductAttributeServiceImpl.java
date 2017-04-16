package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.OrderProductAttribute;
import com.smartshop.eshop.repository.OrderProductAttributeRepository;
import com.smartshop.eshop.repository.search.OrderProductAttributeSearchRepository;
import com.smartshop.eshop.service.OrderProductAttributeService;

/**
 * Service Implementation for managing OrderProductAttribute.
 */
@Service
@Transactional
public class OrderProductAttributeServiceImpl extends AbstractDomainServiceImpl<OrderProductAttribute, Long>
		implements OrderProductAttributeService {

	private final Logger LOGGER = LoggerFactory.getLogger(OrderProductAttributeServiceImpl.class);
	private final OrderProductAttributeRepository orderProductAttributeRepository;
	private final OrderProductAttributeSearchRepository orderProductAttributeSearchRepository;

	public OrderProductAttributeServiceImpl(OrderProductAttributeRepository orderProductAttributeRepository,
			OrderProductAttributeSearchRepository orderProductAttributeSearchRepository) {
		super(orderProductAttributeRepository, orderProductAttributeSearchRepository);
		this.orderProductAttributeRepository = orderProductAttributeRepository;
		this.orderProductAttributeSearchRepository = orderProductAttributeSearchRepository;
	}

}
