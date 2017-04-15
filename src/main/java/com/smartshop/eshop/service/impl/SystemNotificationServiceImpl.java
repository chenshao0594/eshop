package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.SystemNotificationService;
import com.smartshop.eshop.domain.SystemNotification;
import com.smartshop.eshop.repository.SystemNotificationRepository;
import com.smartshop.eshop.repository.search.SystemNotificationSearchRepository;
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
 * Service Implementation for managing SystemNotification.
 */
@Service
@Transactional
public class SystemNotificationServiceImpl extends AbstractDomainServiceImpl< SystemNotification, Long> implements SystemNotificationService{

    private final Logger LOGGER = LoggerFactory.getLogger(SystemNotificationServiceImpl.class);
    private final SystemNotificationRepository systemNotificationRepository;
    private final SystemNotificationSearchRepository systemNotificationSearchRepository;
    
    public SystemNotificationServiceImpl(SystemNotificationRepository systemNotificationRepository, SystemNotificationSearchRepository systemNotificationSearchRepository) {
        super(systemNotificationRepository,systemNotificationSearchRepository);
        this.systemNotificationRepository = systemNotificationRepository;
        this.systemNotificationSearchRepository = systemNotificationSearchRepository;
    }
    
}
