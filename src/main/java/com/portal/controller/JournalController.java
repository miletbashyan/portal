package com.portal.controller;

import com.portal.model.Journal;
import com.portal.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/journals")
public class JournalController {
    private static Logger LOG = Logger.getLogger(JournalController.class.getName());

    @Autowired
    private JournalService journalService;

    /**
     * Retrieves the list of existing journals. Supports pagination
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Journal>> list(Pageable pageable) {
        LOG.log(Level.INFO, "Fetching the list");
        Page<Journal> journals = journalService.findAll(pageable);

        if (!journals.hasContent()) {
            LOG.log(Level.INFO, "Empty list");
            return new ResponseEntity<Page<Journal>>(HttpStatus.NO_CONTENT);
        }
        LOG.log(Level.INFO, "Returning a list of {0} elements", journals.getSize());
        return new ResponseEntity<Page<Journal>>(journals, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Journal journal) {

        LOG.info("Creating Journal " + journal.getName());

        if (journalService.isJournalExist(journal)) {
            LOG.log(Level.INFO, "A Journal with name {0} already exists", journal.getName());
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        journalService.save(journal);
        LOG.log(Level.INFO, "Saved object {0} elements", journal);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * Retrieves Journal specified by Id.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Journal> get(@PathVariable("id") long id) {
        LOG.log(Level.INFO, "Fetching Journal with id {0}", id);
        Journal journal = journalService.findById(id);
        if (journal == null) {
            LOG.log(Level.INFO, "Journal with id {0} not found", id);
            return new ResponseEntity<Journal>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Journal>(journal, HttpStatus.OK);
    }

    /**
     * Updates Journal specified by Id.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Journal> update(@PathVariable("id") long id, @RequestBody Journal journal) {
        LOG.log(Level.FINE, "Updating Journal with id {0}", id);
        Journal currentJournal = journalService.findById(id);

        if (currentJournal == null) {
            LOG.log(Level.FINE, "Journal with id {0} not found", id);
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
        LOG.log(Level.FINE, "Fetching & Deleting Journal with id {0}", id);

        Journal journal = journalService.findById(id);
        if (journal == null) {
            LOG.log(Level.FINE, "Unable to delete. Journal with id {0} not found", id);
            return new ResponseEntity<Journal>(HttpStatus.NOT_FOUND);
        }

        journalService.delete(id);
        return new ResponseEntity<Journal>(HttpStatus.OK);
    }

}
