package com.example.miniproject.service;

import com.example.miniproject.entity.Book;
import com.example.miniproject.exception.BookNotFoundException;
import com.example.miniproject.repository.BookRepository;
import com.example.miniproject.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        book = new Book(
                1L,
                "Java",
                "Author",
                "IT",
                10
        );
    }

    @Test
    void getAllBooks_returnList() {

        when(bookRepository.findAll())
                .thenReturn(List.of(book, book));

        List<Book> books = bookService.getAllBooks();

        assertEquals(2, books.size());
    }

    @Test
    void getBookById_found() {

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        assertEquals(book, result);
    }

    @Test
    void getBookById_notFound() {

        when(bookRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                BookNotFoundException.class,
                () -> bookService.getBookById(1L)
        );
    }

    @Test
    void createBook_success() {

        when(bookRepository.save(book))
                .thenReturn(book);

        Book result = bookService.createBook(book);

        assertEquals(book, result);

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void deleteBook_notFound() {

        when(bookRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                BookNotFoundException.class,
                () -> bookService.deleteBook(1L)
        );

        verify(bookRepository, never()).delete(any());
    }
}