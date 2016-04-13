package com.portal.repository;

import org.springframework.data.repository.CrudRepository;
import com.portal.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
