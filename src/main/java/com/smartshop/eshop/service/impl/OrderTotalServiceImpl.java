package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.OrderTotalService;
import com.smartshop.eshop.domain.OrderTotal;
import com.smartshop.eshop.repository.OrderTotalRepository;
import com.smartshop.eshop.repository.search.OrderTotalSearchRepository;
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
 * Service Implementation for managing OrderTotal.
 */
@Service
@Transactional
public class OrderTotalServiceImpl extends AbstractDomainServiceImpl< OrderTotal, Long> implements OrderTotalService{

    private final Logger LOGGER = LoggerFactory.getLogger(OrderTotalServiceImpl.class);
    private final OrderTotalRepository orderTotalRepository;
    private final OrderTotalSearchRepository orderTotalSearchRepository;
    
    public OrderTotalServiceImpl(OrderTotalRepository orderTotalRepository, OrderTotalSearchRepository orderTotalSearchRepository) {
        super(orderTotalRepository,orderTotalSearchRepository);
        this.orderTotalRepository = orderTotalRepository;
        this.orderTotalSearchRepository = orderTotalSearchRepository;
    }
    
}
