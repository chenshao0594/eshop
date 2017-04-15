package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.SalesOrderService;
import com.smartshop.eshop.domain.SalesOrder;
import com.smartshop.eshop.repository.SalesOrderRepository;
import com.smartshop.eshop.repository.search.SalesOrderSearchRepository;
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
 * Service Implementation for managing SalesOrder.
 */
@Service
@Transactional
public class SalesOrderServiceImpl extends AbstractDomainServiceImpl< SalesOrder, Long> implements SalesOrderService{

    private final Logger LOGGER = LoggerFactory.getLogger(SalesOrderServiceImpl.class);
    private final SalesOrderRepository salesOrderRepository;
    private final SalesOrderSearchRepository salesOrderSearchRepository;
    
    public SalesOrderServiceImpl(SalesOrderRepository salesOrderRepository, SalesOrderSearchRepository salesOrderSearchRepository) {
        super(salesOrderRepository,salesOrderSearchRepository);
        this.salesOrderRepository = salesOrderRepository;
        this.salesOrderSearchRepository = salesOrderSearchRepository;
    }
    
}
