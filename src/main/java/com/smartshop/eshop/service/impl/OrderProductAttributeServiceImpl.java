package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.OrderProductAttributeService;
import com.smartshop.eshop.domain.OrderProductAttribute;
import com.smartshop.eshop.repository.OrderProductAttributeRepository;
import com.smartshop.eshop.repository.search.OrderProductAttributeSearchRepository;
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
 * Service Implementation for managing OrderProductAttribute.
 */
@Service
@Transactional
public class OrderProductAttributeServiceImpl extends AbstractDomainServiceImpl< OrderProductAttribute, Long> implements OrderProductAttributeService{

    private final Logger LOGGER = LoggerFactory.getLogger(OrderProductAttributeServiceImpl.class);
    private final OrderProductAttributeRepository orderProductAttributeRepository;
    private final OrderProductAttributeSearchRepository orderProductAttributeSearchRepository;
    
    public OrderProductAttributeServiceImpl(OrderProductAttributeRepository orderProductAttributeRepository, OrderProductAttributeSearchRepository orderProductAttributeSearchRepository) {
        super(orderProductAttributeRepository,orderProductAttributeSearchRepository);
        this.orderProductAttributeRepository = orderProductAttributeRepository;
        this.orderProductAttributeSearchRepository = orderProductAttributeSearchRepository;
    }
    
}
