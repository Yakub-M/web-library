package com.yakub.weblibrary.repository;

import com.yakub.weblibrary.model.BookStructure;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStructureRepository extends MongoRepository<BookStructure, String> {
    // You can define custom queries here if needed
}