package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.OrderProductService;
import com.smartshop.eshop.domain.OrderProduct;
import com.smartshop.eshop.repository.OrderProductRepository;
import com.smartshop.eshop.repository.search.OrderProductSearchRepository;
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
 * Service Implementation for managing OrderProduct.
 */
@Service
@Transactional
public class OrderProductServiceImpl extends AbstractDomainServiceImpl< OrderProduct, Long> implements OrderProductService{

    private final Logger LOGGER = LoggerFactory.getLogger(OrderProductServiceImpl.class);
    private final OrderProductRepository orderProductRepository;
    private final OrderProductSearchRepository orderProductSearchRepository;
    
    public OrderProductServiceImpl(OrderProductRepository orderProductRepository, OrderProductSearchRepository orderProductSearchRepository) {
        super(orderProductRepository,orderProductSearchRepository);
        this.orderProductRepository = orderProductRepository;
        this.orderProductSearchRepository = orderProductSearchRepository;
    }
    
}
