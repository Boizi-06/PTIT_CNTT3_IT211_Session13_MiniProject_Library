package com.example.miniproject.service;

import com.example.miniproject.entity.Book;

import java.util.List;
import java.util.Map;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book createBook(Book book);

    Book updateBook(Long id, Book book);

    Book patchBook(Long id, Map<String, Object> updates);

    void deleteBook(Long id);
}