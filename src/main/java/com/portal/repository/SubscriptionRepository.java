package com.portal.repository;

import org.springframework.data.repository.CrudRepository;
import com.portal.model.Subscription;
import com.portal.model.User;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
	Subscription findByUser(User user);
}
