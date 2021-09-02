package com.book.service.imple;

import com.book.dao.BookDao;
import com.book.dao.impl.BookDaoImpl;
import com.book.pojo.Book;
import com.book.pojo.Page;
import com.book.service.BookService;

import java.util.List;

/**
 * @author LVFASEN
 * @create 2021-08-22 15:51
 */
public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();
    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public void deleteBookById(Integer id) {
        bookDao.deleteBookById(id);

    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public Book queryBookById(Integer id) {
        return bookDao.queryBookById(id);
    }

    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize) {

        Page<Book> page = new Page<>();



        page.setPageSize(pageSize);

        Integer pageTotalCount = bookDao.queryForPageTotalCount();
        page.setPageTotalCount(pageTotalCount);

        Integer pageTotal = pageTotalCount/pageSize;
        if(pageTotalCount%pageSize>0){
            pageTotal += 1;
        }
        page.setPageTotal(pageTotal);

        //数据边界检查
        page.setPageNo(pageNo);

        int begin = (page.getPageNo()-1)*page.getPageSize();
        List<Book> items = bookDao.queryForPageItems(begin,pageSize);
        page.setItems(items);
        return page;
    }

    @Override
    public Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max) {

        Page<Book> page = new Page<>();

        //显示个数
        page.setPageSize(pageSize);

        //总记录数
        Integer pageTotalCount = bookDao.queryForPageTotalCountByPrice(min, max);
        page.setPageTotalCount(pageTotalCount);

        //页数
        Integer pageTotal = pageTotalCount/pageSize;
        if(pageTotalCount%pageSize > 0){
            pageTotal += 1;
        }
        page.setPageTotal(pageTotal);


        //数据边界检查
        page.setPageNo(pageNo);

        int begin = (page.getPageNo()-1)*page.getPageSize();
        List<Book> items = bookDao.queryForPageItemsByPrice(begin,pageSize,min,max);
        page.setItems(items);

        return page;
    }
}
