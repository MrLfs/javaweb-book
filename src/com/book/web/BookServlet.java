package com.book.web;

import com.book.pojo.Book;
import com.book.pojo.Page;
import com.book.service.BookService;
import com.book.service.imple.BookServiceImpl;
import com.book.utils.WebUtil;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author LVFASEN
 * @create 2021-08-22 16:03
 */
public class BookServlet extends BaseServlet{

    private BookService bookService = new BookServiceImpl();

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        String name = req.getParameter("name");
//        name = new String(name.getBytes("iso-8859-1"), "UTF-8");
//        System.out.println(name);
        int pageNo =WebUtil.parsseInt(req.getParameter("pageNo"),0);
        pageNo+=1;

        Book book = WebUtil.copyParamToBean(req.getParameterMap(), new Book());

        bookService.addBook(book);


        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo="+pageNo);
    }

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        int idInt = WebUtil.parsseInt(req.getParameter("id"),0);
        int pageNo = WebUtil.parsseInt(req.getParameter("pageNo"),0);


        bookService.deleteBookById(idInt);

        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo="+ pageNo);
    }

    /**
     * 
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = WebUtil.parsseInt(req.getParameter("id"),0);

        Book book = bookService.queryBookById(id);
        req.setAttribute("book",book);

        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req,resp);
    }
    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Book book = WebUtil.copyParamToBean(req.getParameterMap(), new Book());

        bookService.updateBook(book);

        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo="+ req.getParameter("pageNo"));
    }


    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //通过bookService查询全部图书
        List<Book> books = bookService.queryBooks();
        //把全部图书保存到request域中
        req.setAttribute("books",books);
        //请求转发到/pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }

    /**
     *分页
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数pageNO和pageSize
        int pageNo = WebUtil.parsseInt(req.getParameter("pageNo"),1);
        int pageSize = WebUtil.parsseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //调用BookService.page(pageNo,pageSize):page对象
        Page<Book> page = bookService.page(pageNo, pageSize);
        //保存page对象到request域中
        page.setUrl("manager/bookServlet?action=page");
        req.setAttribute("page",page);
        //请求转发到"/pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }
}
