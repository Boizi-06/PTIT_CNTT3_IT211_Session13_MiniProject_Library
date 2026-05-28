package com.example.miniproject.service.impl;

import com.example.miniproject.entity.Book;
import com.example.miniproject.exception.BookNotFoundException;
import com.example.miniproject.repository.BookRepository;
import com.example.miniproject.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {

        log.debug("Getting all books");

        List<Book> books = bookRepository.findAll();

        log.info("Get all books success");

        return books;
    }

    @Override
    public Book getBookById(Long id) {

        log.debug("Get book by id: {}", id);

        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new BookNotFoundException("Book not found with id: " + id));
    }

    @Override
    public Book createBook(Book book) {

        log.debug("Create book: {}", book);

        Book savedBook = bookRepository.save(book);

        log.info("Create book success");

        return savedBook;
    }

    @Override
    public Book updateBook(Long id, Book book) {

        Book existingBook = getBookById(id);

        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setCategory(book.getCategory());
        existingBook.setQuantity(book.getQuantity());

        return bookRepository.save(existingBook);
    }

    @Override
    public Book patchBook(Long id, Map<String, Object> updates) {

        Book existingBook = getBookById(id);

        if (updates.containsKey("title")) {
            existingBook.setTitle((String) updates.get("title"));
        }

        if (updates.containsKey("author")) {
            existingBook.setAuthor((String) updates.get("author"));
        }

        if (updates.containsKey("category")) {
            existingBook.setCategory((String) updates.get("category"));
        }

        if (updates.containsKey("quantity")) {
            existingBook.setQuantity((Integer) updates.get("quantity"));
        }

        return bookRepository.save(existingBook);
    }

    @Override
    public void deleteBook(Long id) {

        Book existingBook = getBookById(id);

        bookRepository.delete(existingBook);

        log.info("Delete success");
    }
}