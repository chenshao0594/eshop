package com.smartshop.eshop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.smartshop.eshop.domain.Attachment;
import com.smartshop.eshop.service.AttachmentService;
import com.smartshop.eshop.web.rest.util.HeaderUtil;
import com.smartshop.eshop.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Attachment.
 */
@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

	private final Logger log = LoggerFactory.getLogger(AttachmentController.class);

	private static final String ENTITY_NAME = "attachment";

	private final AttachmentService attachmentService;

	public AttachmentController(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	@PostMapping()
	@Timed
	public ResponseEntity<Attachment> createAttachment(@Valid @RequestBody Attachment attachment)
			throws URISyntaxException {
		log.debug("REST request to save Attachment : {}", attachment);
		if (attachment.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
					"A new attachment cannot already have an ID")).body(null);
		}
		Attachment result = attachmentService.save(attachment);
		return ResponseEntity.created(new URI("/api/attachments/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	@PutMapping()
	@Timed
	public ResponseEntity<Attachment> updateAttachment(@Valid @RequestBody Attachment attachment)
			throws URISyntaxException {
		log.debug("REST request to update Attachment : {}", attachment);
		if (attachment.getId() == null) {
			return createAttachment(attachment);
		}
		Attachment result = attachmentService.save(attachment);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attachment.getId().toString())).body(result);
	}

	@GetMapping()
	@Timed
	public ResponseEntity<List<Attachment>> getAllAttachments(@ApiParam Pageable pageable) {
		log.debug("REST request to get a page of Attachments");
		Page<Attachment> page = attachmentService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/attachments");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@Timed
	public ResponseEntity<Attachment> getAttachment(@PathVariable Long id) {
		log.debug("REST request to get Attachment : {}", id);
		Attachment attachment = attachmentService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(attachment));
	}

	@DeleteMapping("/{id}")
	@Timed
	public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
		log.debug("REST request to delete Attachment : {}", id);
		attachmentService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

}
