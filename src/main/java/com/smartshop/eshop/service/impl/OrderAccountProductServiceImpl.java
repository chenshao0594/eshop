package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.OrderAccountProductService;
import com.smartshop.eshop.domain.OrderAccountProduct;
import com.smartshop.eshop.repository.OrderAccountProductRepository;
import com.smartshop.eshop.repository.search.OrderAccountProductSearchRepository;
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
 * Service Implementation for managing OrderAccountProduct.
 */
@Service
@Transactional
public class OrderAccountProductServiceImpl extends AbstractDomainServiceImpl< OrderAccountProduct, Long> implements OrderAccountProductService{

    private final Logger LOGGER = LoggerFactory.getLogger(OrderAccountProductServiceImpl.class);
    private final OrderAccountProductRepository orderAccountProductRepository;
    private final OrderAccountProductSearchRepository orderAccountProductSearchRepository;
    
    public OrderAccountProductServiceImpl(OrderAccountProductRepository orderAccountProductRepository, OrderAccountProductSearchRepository orderAccountProductSearchRepository) {
        super(orderAccountProductRepository,orderAccountProductSearchRepository);
        this.orderAccountProductRepository = orderAccountProductRepository;
        this.orderAccountProductSearchRepository = orderAccountProductSearchRepository;
    }
    
}
