package com.portal.service;

import com.portal.model.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface JournalService {
    Journal findById(long id);

    Journal findByName(String name);

    void save(Journal journal);

    void update(Journal journal);

    void delete(long id);

    Page<Journal> findAll(Pageable pageable);

    boolean isJournalExist(Journal journal);

}