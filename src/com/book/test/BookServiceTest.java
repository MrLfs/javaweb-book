package com.book.test;

import com.book.pojo.Book;
import com.book.pojo.Page;
import com.book.service.BookService;
import com.book.service.imple.BookServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author LVFASEN
 * @create 2021-08-22 15:54
 */
public class BookServiceTest {

    private BookService bookService = new BookServiceImpl();

    @Test
    public void addBook() {
        bookService.addBook(new Book(null,"泰戈尔诗集","泰戈尔",new BigDecimal(25),2100000,1111500,null));
    }

    @Test
    public void deleteBookById() {
        bookService.deleteBookById(43);
    }

    @Test
    public void updateBook() {
        bookService.updateBook(new Book(25,"舌尖上美味","余华",new BigDecimal(28),2100,100,null));
    }

    @Test
    public void queryBookById() {
        Book book = bookService.queryBookById(43);
        System.out.println(book);
    }

    @Test
    public void queryBooks() {
        List<Book> books = bookService.queryBooks();
        books.forEach(System.out::println);
    }

    @Test
    public void page(){
        Page<Book> page = bookService.page(1, Page.PAGE_SIZE);
        System.out.println(page);
    }

    @Test
    public void pageByPrice(){
        Page<Book> page = bookService.pageByPrice(2, Page.PAGE_SIZE,0,Integer.MAX_VALUE);
        List<Book> items = page.getItems();
        items.forEach(System.out::println);
    }
}