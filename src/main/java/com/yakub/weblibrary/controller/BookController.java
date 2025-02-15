package com.yakub.weblibrary.controller;

import com.yakub.weblibrary.model.Book;
import com.yakub.weblibrary.service.BookService;
import com.yakub.weblibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService BookService;
    private final UserRepository UserRepository;

    @Autowired
    public BookController(BookService BookService,
                          UserRepository UserRepository) {
        this.BookService = BookService;
        this.UserRepository = UserRepository;
    }

    @GetMapping("/")
    public List<Book> getAllBooks() {
        return BookService.getAllBooks();
    }

    // Get books by title
    @GetMapping("/title/{title}")
    public List<Book> getBooksByTitle(@PathVariable String title) {
        return BookService.findByTitle(title);
    }

    // Get books by author
    @GetMapping("/author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        return BookService.findByAuthor(author);
    }

    // Get books by genre
    @GetMapping("/genre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        return BookService.findByGenre(genre);
    }

    // Get books by ISBN
    @GetMapping("/isbn/{isbn}")
    public List<Book> getBooksByIsbn(@PathVariable String isbn) {
        return BookService.findByIsbn(isbn);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        Optional<Book> book = BookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/cleanup")
    public ResponseEntity<?> cleanupTestUsers() {
        UserRepository.deleteByUsername("testUser");
        UserRepository.deleteByUsername("admin");
        return ResponseEntity.ok("Test users deleted");
    }


    @PostMapping("/")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book savedBook = BookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }
    //todo: add search functionality for isbn (maybe author, title and genre too) & make sure isbn is valid in post
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable String id, @Valid @RequestBody Book book) {
        Book updatedBook = BookService.updateBook(id, book);
        return updatedBook != null ? ResponseEntity.ok(updatedBook) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        return BookService.deleteBook(id) ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
