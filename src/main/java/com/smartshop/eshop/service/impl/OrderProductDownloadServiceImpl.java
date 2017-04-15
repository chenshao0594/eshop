package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.OrderProductDownloadService;
import com.smartshop.eshop.domain.OrderProductDownload;
import com.smartshop.eshop.repository.OrderProductDownloadRepository;
import com.smartshop.eshop.repository.search.OrderProductDownloadSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OrderProductDownload.
 */
@Service
@Transactional
public class OrderProductDownloadServiceImpl extends AbstractDomainServiceImpl< OrderProductDownload, Long> implements OrderProductDownloadService{

    private final Logger LOGGER = LoggerFactory.getLogger(OrderProductDownloadServiceImpl.class);
    private final OrderProductDownloadRepository orderProductDownloadRepository;
    private final OrderProductDownloadSearchRepository orderProductDownloadSearchRepository;
    
    public OrderProductDownloadServiceImpl(OrderProductDownloadRepository orderProductDownloadRepository, OrderProductDownloadSearchRepository orderProductDownloadSearchRepository) {
        super(orderProductDownloadRepository,orderProductDownloadSearchRepository);
        this.orderProductDownloadRepository = orderProductDownloadRepository;
        this.orderProductDownloadSearchRepository = orderProductDownloadSearchRepository;
    }
    
}
