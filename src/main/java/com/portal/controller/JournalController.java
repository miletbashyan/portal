package com.portal.controller;

import com.portal.model.Journal;
import com.portal.service.JournalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/journals")
public class JournalController {
    private static Logger LOG = LoggerFactory.getLogger(JournalController.class);

    @Autowired
    private JournalService journalService;

    /**
     * Retrieves the list of existing journals. Supports pagination
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Journal>> list(Pageable pageable) {
        Page<Journal> journals = journalService.findAll(pageable);
        LOG.debug("Fetching the list");
        if (!journals.hasContent()) {
            LOG.debug("Empty list");
            return new ResponseEntity<Page<Journal>>(HttpStatus.NO_CONTENT);
        }
        LOG.debug("Returning a list of {} elements", journals.getSize());
        return new ResponseEntity<Page<Journal>>(journals, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Journal journal) {

        LOG.debug("Creating Journal " + journal.getName());

        if (journalService.isJournalExist(journal)) {
            LOG.debug("A Journal with name {} already exists", journal.getName());
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        journalService.save(journal);
        LOG.info("Saved object {} elements", journal);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * Retrieves Journal specified by Id.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Journal> get(@PathVariable("id") long id) {
        LOG.debug("Fetching Journal with id {}", id);
        Journal journal = journalService.findById(id);
        if (journal == null) {
            LOG.debug("Journal with id {} not found", id);
            return new ResponseEntity<Journal>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Journal>(journal, HttpStatus.OK);
    }

    /**
     * Updates Journal specified by Id.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Journal> update(@PathVariable("id") long id, @RequestBody Journal journal) {
        LOG.debug("Updating Journal with id {}", id);
        Journal currentJournal = journalService.findById(id);

        if (currentJournal == null) {
            LOG.debug("Journal with id {} not found", id);
            return new ResponseEntity<Journal>(HttpStatus.NOT_FOUND);
        }

        currentJournal.setName(journal.getName());
        currentJournal.setDescription(journal.getDescription());
        journalService.update(currentJournal);
        return new ResponseEntity<Journal>(currentJournal, HttpStatus.OK);
    }

    /**
     * Deletes a Journal specified by Id.
     */
    @RequestMapping(value = "/journal/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Journal> delete(@PathVariable("id") long id) {
        LOG.debug("Fetching & Deleting Journal with id {}", id);

        Journal journal = journalService.findById(id);
        if (journal == null) {
            LOG.debug("Unable to delete. Journal with id {} not found", id);
            return new ResponseEntity<Journal>(HttpStatus.NOT_FOUND);
        }

        journalService.delete(id);
        return new ResponseEntity<Journal>(HttpStatus.OK);
    }

}
