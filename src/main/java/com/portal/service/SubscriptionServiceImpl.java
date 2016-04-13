package com.portal.service;

import com.portal.model.Subscription;
import com.portal.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public void saveSubscription(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }
}
