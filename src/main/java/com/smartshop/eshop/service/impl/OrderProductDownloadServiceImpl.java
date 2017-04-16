package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.OrderProductDownload;
import com.smartshop.eshop.repository.OrderProductDownloadRepository;
import com.smartshop.eshop.repository.search.OrderProductDownloadSearchRepository;
import com.smartshop.eshop.service.OrderProductDownloadService;

/**
 * Service Implementation for managing OrderProductDownload.
 */
@Service
@Transactional
public class OrderProductDownloadServiceImpl extends AbstractDomainServiceImpl<OrderProductDownload, Long>
		implements OrderProductDownloadService {

	private final Logger LOGGER = LoggerFactory.getLogger(OrderProductDownloadServiceImpl.class);
	private final OrderProductDownloadRepository orderProductDownloadRepository;
	private final OrderProductDownloadSearchRepository orderProductDownloadSearchRepository;

	public OrderProductDownloadServiceImpl(OrderProductDownloadRepository orderProductDownloadRepository,
			OrderProductDownloadSearchRepository orderProductDownloadSearchRepository) {
		super(orderProductDownloadRepository, orderProductDownloadSearchRepository);
		this.orderProductDownloadRepository = orderProductDownloadRepository;
		this.orderProductDownloadSearchRepository = orderProductDownloadSearchRepository;
	}

}
