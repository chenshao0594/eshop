package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ContentService;
import com.smartshop.eshop.domain.Content;
import com.smartshop.eshop.repository.ContentRepository;
import com.smartshop.eshop.repository.search.ContentSearchRepository;
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
 * Service Implementation for managing Content.
 */
@Service
@Transactional
public class ContentServiceImpl extends AbstractDomainServiceImpl< Content, Long> implements ContentService{

    private final Logger LOGGER = LoggerFactory.getLogger(ContentServiceImpl.class);
    private final ContentRepository contentRepository;
    private final ContentSearchRepository contentSearchRepository;
    
    public ContentServiceImpl(ContentRepository contentRepository, ContentSearchRepository contentSearchRepository) {
        super(contentRepository,contentSearchRepository);
        this.contentRepository = contentRepository;
        this.contentSearchRepository = contentSearchRepository;
    }
    
}
