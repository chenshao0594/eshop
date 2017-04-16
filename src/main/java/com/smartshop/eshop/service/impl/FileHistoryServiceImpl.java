package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.FileHistory;
import com.smartshop.eshop.repository.FileHistoryRepository;
import com.smartshop.eshop.repository.search.FileHistorySearchRepository;
import com.smartshop.eshop.service.FileHistoryService;

/**
 * Service Implementation for managing FileHistory.
 */
@Service
@Transactional
public class FileHistoryServiceImpl extends AbstractDomainServiceImpl< FileHistory, Long> implements FileHistoryService{

    private final Logger LOGGER = LoggerFactory.getLogger(FileHistoryServiceImpl.class);
    private final FileHistoryRepository fileHistoryRepository;
    private final FileHistorySearchRepository fileHistorySearchRepository;
    
    public FileHistoryServiceImpl(FileHistoryRepository fileHistoryRepository, FileHistorySearchRepository fileHistorySearchRepository) {
        super(fileHistoryRepository,fileHistorySearchRepository);
        this.fileHistoryRepository = fileHistoryRepository;
        this.fileHistorySearchRepository = fileHistorySearchRepository;
    }
    
}
