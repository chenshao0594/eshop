package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ShippingOriginService;
import com.smartshop.eshop.domain.ShippingOrigin;
import com.smartshop.eshop.repository.ShippingOriginRepository;
import com.smartshop.eshop.repository.search.ShippingOriginSearchRepository;
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
