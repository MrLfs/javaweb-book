package com.book.test;

import com.book.dao.BookDao;
import com.book.dao.impl.BookDaoImpl;
import com.book.pojo.Book;
import com.book.pojo.Page;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author LVFASEN
 * @create 2021-08-22 15:36
 */
public class BookDaoTest {

    private BookDao bookDao = new BookDaoImpl();
    @Test
    public void addBook() {
        bookDao.addBook(new Book(null,"活着","余华",new BigDecimal(28),2100000,1111500,null));
    }

    @Test
    public void deleteBookById() {

    }

    @Test
    public void updateBook() {
        bookDao.updateBook(new Book(42,"活着","余华",new BigDecimal(28),100000,1111500,null));
    }

    @Test
    public void queryBookById() {
        Book book = bookDao.queryBookById(42);
        System.out.println(book);
    }

    @Test
    public void queryBooks() {
        List<Book> books = bookDao.queryBooks();
        books.forEach(System.out::println);


    }

    @Test
    public void queryForPageTotalCount(){
        System.out.println(bookDao.queryForPageTotalCount());
    }

    @Test
    public void queryForPageItems(){
        for (Book book:bookDao.queryForPageItems(8, Page.PAGE_SIZE)){
            System.out.println(book);
        }
    }
    @Test
    public void queryForPageTotalCountByPrice(){
        Integer integer = bookDao.queryForPageTotalCountByPrice(10, 50);
        System.out.println(integer);
    }

    @Test
    public void queryForPageByPrice(){
        List<Book> books = bookDao.queryForPageItemsByPrice(0, 4, 10, 50);
        books.forEach(System.out::println);
    }

}