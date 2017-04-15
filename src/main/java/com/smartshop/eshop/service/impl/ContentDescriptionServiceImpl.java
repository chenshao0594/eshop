package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ContentDescriptionService;
import com.smartshop.eshop.domain.ContentDescription;
import com.smartshop.eshop.repository.ContentDescriptionRepository;
import com.smartshop.eshop.repository.search.ContentDescriptionSearchRepository;
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
