package com.book.web;

import com.book.pojo.Book;
import com.book.pojo.Page;
import com.book.service.BookService;
import com.book.service.imple.BookServiceImpl;
import com.book.utils.WebUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LVFASEN
 * @create 2021-08-24 7:15
 */
public class ClientBookServlet extends BaseServlet{
    BookService bookService = new BookServiceImpl();
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
        page.setUrl("client/bookServlet?action=page");
        req.setAttribute("page",page);

        //请求转发到"/pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void pageByPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数
        int pageNo = WebUtil.parsseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtil.parsseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        int min = WebUtil.parsseInt(req.getParameter("min"), 0);
        int max = WebUtil.parsseInt(req.getParameter("max"), Integer.MAX_VALUE);

        System.out.println("pageNo:"+req.getParameter("pageNo"));
        System.out.println("pageSize"+req.getParameter("PageSize"));
        System.out.println("min:"+req.getParameter("min"));
        System.out.println("max"+req.getParameter("max"));

        Page<Book> bookPage = bookService.pageByPrice(pageNo, pageSize, min, max);

        StringBuilder sb = new StringBuilder("client/bookServlet?action=pageByPrice");

        if(req.getParameter("min") != null){
            sb.append("&min=").append(req.getParameter("min"));
        }



        if(req.getParameter("max") != null){
            sb.append("&max=").append(req.getParameter("max"));
        }

        bookPage.setUrl(sb.toString());

        req.setAttribute("page",bookPage);
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);

    }
}
