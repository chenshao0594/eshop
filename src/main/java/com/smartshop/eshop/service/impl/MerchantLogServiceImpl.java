package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.MerchantLogService;
import com.smartshop.eshop.domain.MerchantLog;
import com.smartshop.eshop.repository.MerchantLogRepository;
import com.smartshop.eshop.repository.search.MerchantLogSearchRepository;
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
 * Service Implementation for managing MerchantLog.
 */
@Service
@Transactional
public class MerchantLogServiceImpl extends AbstractDomainServiceImpl< MerchantLog, Long> implements MerchantLogService{

    private final Logger LOGGER = LoggerFactory.getLogger(MerchantLogServiceImpl.class);
    private final MerchantLogRepository merchantLogRepository;
    private final MerchantLogSearchRepository merchantLogSearchRepository;
    
    public MerchantLogServiceImpl(MerchantLogRepository merchantLogRepository, MerchantLogSearchRepository merchantLogSearchRepository) {
        super(merchantLogRepository,merchantLogSearchRepository);
        this.merchantLogRepository = merchantLogRepository;
        this.merchantLogSearchRepository = merchantLogSearchRepository;
    }
    
}
