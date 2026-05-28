package com.example.miniproject.controller;

import com.example.miniproject.entity.Book;
import com.example.miniproject.exception.BookNotFoundException;
import com.example.miniproject.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBooks() throws Exception {

        Book book = new Book(
                1L,
                "Java",
                "Author",
                "IT",
                10
        );

        when(bookService.getAllBooks())
                .thenReturn(List.of(book));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Java"));
    }

    @Test
    void getBookById_found() throws Exception {

        Book book = new Book(
                1L,
                "Java",
                "Author",
                "IT",
                10
        );

        when(bookService.getBookById(1L))
                .thenReturn(book);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java"));
    }

    @Test
    void getBookById_notFound() throws Exception {

        when(bookService.getBookById(1L))
                .thenThrow(new BookNotFoundException("Not found"));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createBook() throws Exception {

        Book book = new Book(
                1L,
                "Java",
                "Author",
                "IT",
                10
        );

        when(bookService.createBook(book))
                .thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Java"));
    }
}