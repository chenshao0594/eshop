package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.OrderAccount;
import com.smartshop.eshop.repository.OrderAccountRepository;
import com.smartshop.eshop.repository.search.OrderAccountSearchRepository;
import com.smartshop.eshop.service.OrderAccountService;

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
