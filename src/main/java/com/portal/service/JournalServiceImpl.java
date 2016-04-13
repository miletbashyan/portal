package com.portal.service;

import com.portal.model.Journal;
import com.portal.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class JournalServiceImpl implements JournalService {
    @Autowired
    private JournalRepository journalRepository;

    public Page<Journal> findAll(Pageable pageable) {
        return journalRepository.findAll(pageable);
    }

    public Journal findById(long id) {
        return journalRepository.findOne(id);
    }

    public Journal findByName(String name) {
        return journalRepository.findByName(name);
    }

    public void save(Journal journal) {
        journalRepository.save(journal);
    }

    public void update(Journal journal) {
        journalRepository.save(journal);
    }

    public void delete(long id) {
        journalRepository.delete(id);
    }

    public boolean isJournalExist(Journal journal) {
        return findByName(journal.getName()) != null;
    }
}