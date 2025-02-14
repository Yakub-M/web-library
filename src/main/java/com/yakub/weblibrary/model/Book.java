package com.yakub.weblibrary.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Document(collection = "books")
public class Book {

    public interface UpdateValidation {} //ID is created by MongoDB, so this is only needed when updating a book

    @Id
    @NotNull(groups = UpdateValidation.class, message = "ID must not be null when updating")
    private String id;

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    @Pattern(regexp = "^[A-Za-z -]+$", message = "Title can only contain Latin letters, spaces, and dashes")
    private String title;

    @NotBlank(message = "Author name is required")
    @Size(min = 2, max = 50, message = "Author name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z -]+$", message = "Author name can only contain Latin letters, spaces, and dashes")
    private String author;

    @NotNull(message = "Publication date is required")
    private LocalDate publicationDate;

    @NotBlank(message = "Genre is required")
    @Size(min = 3, max = 30, message = "Genre must be between 3 and 30 characters")
    @Pattern(regexp = "^[A-Za-z -]+$", message = "Genre can only contain Latin letters, spaces, and dashes")
    private String genre;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^[0-9]{1,10}$", message = "ISBN must be a number with at most 10 digits")
    private String isbn;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
