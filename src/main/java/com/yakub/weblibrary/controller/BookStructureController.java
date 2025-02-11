package com.yakub.weblibrary.controller;

import com.yakub.weblibrary.model.BookStructure;
import com.yakub.weblibrary.service.BookStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookStructureController {

    private final BookStructureService bookStructureService;

    @Autowired
    public BookStructureController(BookStructureService bookStructureService) {
        this.bookStructureService = bookStructureService;
    }

    @GetMapping
    public List<BookStructure> getAllBooks() {
        return bookStructureService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookStructure> getBookById(@PathVariable String id) {
        Optional<BookStructure> bookStructure = bookStructureService.getBookById(id);
        return bookStructure.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<BookStructure> createBook(@RequestBody BookStructure bookStructure) {
        BookStructure savedBookStructure = bookStructureService.createBook(bookStructure);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBookStructure);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookStructure> updateBook(@PathVariable String id, @RequestBody BookStructure bookStructure) {
        BookStructure updatedBookStructure = bookStructureService.updateBook(id, bookStructure);
        return updatedBookStructure != null ? ResponseEntity.ok(updatedBookStructure) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        return bookStructureService.deleteBook(id) ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
