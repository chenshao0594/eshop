package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.FileHistory;
import com.smartshop.eshop.service.FileHistoryService;

/**
 * REST controller for managing FileHistory.
 */
@RestController
@RequestMapping("/api/" + FileHistoryController.SECTION_KEY)
public class FileHistoryController extends AbstractDomainController<FileHistory, Long> {

	private final Logger log = LoggerFactory.getLogger(FileHistoryController.class);
	public static final String SECTION_KEY = "file-histories";
	private static final String ENTITY_NAME = "fileHistory";

	private final FileHistoryService fileHistoryService;

	public FileHistoryController(FileHistoryService fileHistoryService) {
		super(fileHistoryService);
		this.fileHistoryService = fileHistoryService;
	}

	@Override
	protected String getSectionKey() {
		return SECTION_KEY;
	}

	@Override
	protected String getEntityName() {
		return ENTITY_NAME;
	}

}