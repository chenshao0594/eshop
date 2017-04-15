package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.OrderAccountService;
import com.smartshop.eshop.domain.OrderAccount;
import com.smartshop.eshop.repository.OrderAccountRepository;
import com.smartshop.eshop.repository.search.OrderAccountSearchRepository;
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
 * Service Implementation for managing OrderAccount.
 */
@Service
@Transactional
public class OrderAccountServiceImpl extends AbstractDomainServiceImpl< OrderAccount, Long> implements OrderAccountService{

    private final Logger LOGGER = LoggerFactory.getLogger(OrderAccountServiceImpl.class);
    private final OrderAccountRepository orderAccountRepository;
    private final OrderAccountSearchRepository orderAccountSearchRepository;
    
    public OrderAccountServiceImpl(OrderAccountRepository orderAccountRepository, OrderAccountSearchRepository orderAccountSearchRepository) {
        super(orderAccountRepository,orderAccountSearchRepository);
        this.orderAccountRepository = orderAccountRepository;
        this.orderAccountSearchRepository = orderAccountSearchRepository;
    }
    
}
