package com.sosalert.repository;

import com.sosalert.model.UserContact;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserContactRepository extends MongoRepository<UserContact, String> {
    Optional<UserContact> findByContactId(String contactId);
    Optional<UserContact> findByUserId(String userId);
}

