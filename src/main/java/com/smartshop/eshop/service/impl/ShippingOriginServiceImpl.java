package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ShippingOrigin;
import com.smartshop.eshop.repository.ShippingOriginRepository;
import com.smartshop.eshop.repository.search.ShippingOriginSearchRepository;
import com.smartshop.eshop.service.ShippingOriginService;

/**
 * Service Implementation for managing ShippingOrigin.
 */
@Service
@Transactional
public class ShippingOriginServiceImpl extends AbstractDomainServiceImpl< ShippingOrigin, Long> implements ShippingOriginService{

    private final Logger LOGGER = LoggerFactory.getLogger(ShippingOriginServiceImpl.class);
    private final ShippingOriginRepository shippingOriginRepository;
    private final ShippingOriginSearchRepository shippingOriginSearchRepository;
    
    public ShippingOriginServiceImpl(ShippingOriginRepository shippingOriginRepository, ShippingOriginSearchRepository shippingOriginSearchRepository) {
        super(shippingOriginRepository,shippingOriginSearchRepository);
        this.shippingOriginRepository = shippingOriginRepository;
        this.shippingOriginSearchRepository = shippingOriginSearchRepository;
    }
    
}
