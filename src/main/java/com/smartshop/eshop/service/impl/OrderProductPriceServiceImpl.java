package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.OrderProductPriceService;
import com.smartshop.eshop.domain.OrderProductPrice;
import com.smartshop.eshop.repository.OrderProductPriceRepository;
import com.smartshop.eshop.repository.search.OrderProductPriceSearchRepository;
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
 * Service Implementation for managing OrderProductPrice.
 */
@Service
@Transactional
public class OrderProductPriceServiceImpl extends AbstractDomainServiceImpl< OrderProductPrice, Long> implements OrderProductPriceService{

    private final Logger LOGGER = LoggerFactory.getLogger(OrderProductPriceServiceImpl.class);
    private final OrderProductPriceRepository orderProductPriceRepository;
    private final OrderProductPriceSearchRepository orderProductPriceSearchRepository;
    
    public OrderProductPriceServiceImpl(OrderProductPriceRepository orderProductPriceRepository, OrderProductPriceSearchRepository orderProductPriceSearchRepository) {
        super(orderProductPriceRepository,orderProductPriceSearchRepository);
        this.orderProductPriceRepository = orderProductPriceRepository;
        this.orderProductPriceSearchRepository = orderProductPriceSearchRepository;
    }
    
}
