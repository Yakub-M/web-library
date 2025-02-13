package com.yakub.weblibrary.service;

import com.yakub.weblibrary.model.Book;
import com.yakub.weblibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository BookRepository;

    @Autowired
    public BookService(BookRepository BookRepository) {
        this.BookRepository = BookRepository;
    }

    public List<Book> getAllBooks() {
        return BookRepository.findAll();
    }
    
    public List<Book> findByTitle(String title) {
        return BookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> findByAuthor(String author) {
        return BookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> findByGenre(String genre) {
        return BookRepository.findByGenreContainingIgnoreCase(genre);
    }

    public List<Book> findByIsbn(String isbn) {
        return BookRepository.findByIsbn(isbn);
    }

    public Optional<Book> getBookById(String id) {
        return BookRepository.findById(id);
    }

    public Book createBook(Book book) {
        return BookRepository.save(book);
    }

    public Book updateBook(String id, Book book) {
        if (BookRepository.existsById(id)) {
            book.setId(id);
            return BookRepository.save(book);
        }
        return null;
    }

    public boolean deleteBook(String id) {
        if (BookRepository.existsById(id)) {
            BookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
