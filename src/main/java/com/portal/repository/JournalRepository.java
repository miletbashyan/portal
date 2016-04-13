package com.portal.repository;

import com.portal.model.Journal;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JournalRepository extends PagingAndSortingRepository<Journal, Long>{
    Journal findByName(String name);
}
