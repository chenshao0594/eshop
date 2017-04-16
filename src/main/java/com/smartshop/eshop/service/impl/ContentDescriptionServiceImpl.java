package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ContentDescription;
import com.smartshop.eshop.repository.ContentDescriptionRepository;
import com.smartshop.eshop.repository.search.ContentDescriptionSearchRepository;
import com.smartshop.eshop.service.ContentDescriptionService;

/**
 * Service Implementation for managing ContentDescription.
 */
@Service
@Transactional
public class ContentDescriptionServiceImpl extends AbstractDomainServiceImpl< ContentDescription, Long> implements ContentDescriptionService{

    private final Logger LOGGER = LoggerFactory.getLogger(ContentDescriptionServiceImpl.class);
    private final ContentDescriptionRepository contentDescriptionRepository;
    private final ContentDescriptionSearchRepository contentDescriptionSearchRepository;
    
    public ContentDescriptionServiceImpl(ContentDescriptionRepository contentDescriptionRepository, ContentDescriptionSearchRepository contentDescriptionSearchRepository) {
        super(contentDescriptionRepository,contentDescriptionSearchRepository);
        this.contentDescriptionRepository = contentDescriptionRepository;
        this.contentDescriptionSearchRepository = contentDescriptionSearchRepository;
    }
    
}
