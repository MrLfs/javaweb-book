package com.book.service;

import com.book.pojo.Book;
import com.book.pojo.Page;

import java.util.List;

/**
 * @author LVFASEN
 * @create 2021-08-22 15:49
 */
public interface BookService {
    public void addBook(Book book);

    public void deleteBookById(Integer id);

    public void updateBook(Book book);

    public Book queryBookById(Integer id);

    public List<Book> queryBooks();

    public Page<Book> page(int pageNo, int pageSize);

    Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max);

}