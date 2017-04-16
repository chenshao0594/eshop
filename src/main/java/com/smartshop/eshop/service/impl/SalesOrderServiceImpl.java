package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.SalesOrder;
import com.smartshop.eshop.repository.SalesOrderRepository;
import com.smartshop.eshop.repository.search.SalesOrderSearchRepository;
import com.smartshop.eshop.service.SalesOrderService;

/**
 * Service Implementation for managing SalesOrder.
 */
@Service
@Transactional
public class SalesOrderServiceImpl extends AbstractDomainServiceImpl<SalesOrder, Long> implements SalesOrderService {

	private final Logger LOGGER = LoggerFactory.getLogger(SalesOrderServiceImpl.class);
	private final SalesOrderRepository salesOrderRepository;
	private final SalesOrderSearchRepository salesOrderSearchRepository;

	public SalesOrderServiceImpl(SalesOrderRepository salesOrderRepository,
			SalesOrderSearchRepository salesOrderSearchRepository) {
		super(salesOrderRepository, salesOrderSearchRepository);
		this.salesOrderRepository = salesOrderRepository;
		this.salesOrderSearchRepository = salesOrderSearchRepository;
	}

}
