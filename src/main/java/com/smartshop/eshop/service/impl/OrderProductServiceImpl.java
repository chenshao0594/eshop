package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.OrderProduct;
import com.smartshop.eshop.repository.OrderProductRepository;
import com.smartshop.eshop.repository.search.OrderProductSearchRepository;
import com.smartshop.eshop.service.OrderProductService;

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
