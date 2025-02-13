package com.yakub.weblibrary.repository;

import com.yakub.weblibrary.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    // Future custom queries here
}
