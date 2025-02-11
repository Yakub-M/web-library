package com.yakub.weblibrary.service;

import com.yakub.weblibrary.model.BookStructure;
import com.yakub.weblibrary.repository.BookStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookStructureService {

    private final BookStructureRepository bookStructureRepository;

    @Autowired
    public BookStructureService(BookStructureRepository bookStructureRepository) {
        this.bookStructureRepository = bookStructureRepository;
    }

    public List<BookStructure> getAllBooks() {
        return bookStructureRepository.findAll();
    }

    public Optional<BookStructure> getBookById(String id) {
        return bookStructureRepository.findById(id);
    }

    public BookStructure createBook(BookStructure bookStructure) {
        return bookStructureRepository.save(bookStructure);
    }

    public BookStructure updateBook(String id, BookStructure bookStructure) {
        if (bookStructureRepository.existsById(id)) {
            bookStructure.setId(id);
            return bookStructureRepository.save(bookStructure);
        }
        return null;
    }

    public boolean deleteBook(String id) {
        if (bookStructureRepository.existsById(id)) {
            bookStructureRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
