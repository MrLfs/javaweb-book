package com.book.web;

import com.book.pojo.Book;
import com.book.pojo.Cart;
import com.book.pojo.CartItem;
import com.book.service.BookService;
import com.book.service.imple.BookServiceImpl;
import com.book.utils.WebUtil;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LVFASEN
 * @create 2021-08-25 12:04
 */
public class CartServlet extends BaseServlet{

    private BookService bookService = new BookServiceImpl();
    /**
     * 加入购物车
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("加入购物车");
        //获取请求参数，商品编号
        int id = WebUtil.parsseInt(req.getParameter("id"), 0);
        //调用bookServlet.queryBookById():得到图书信息
        Book book = bookService.queryBookById(id);
        //把图书信息，转换成CartItem商品项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());

        //调用Cart.addItem()方法：添加商品项
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if(cart == null){
           cart = new Cart();
           req.getSession().setAttribute("cart",cart);
        }

        cart.addItem(cartItem);
        req.getSession().setAttribute("lastName",cartItem.getName());
//        System.out.println(cart);
        //重定向回原来商品所在页面：使用请求头中的referer属性
        resp.sendRedirect(req.getHeader("referer"));
    }

    /**
     * 删除商品项
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = WebUtil.parsseInt(req.getParameter("id"), 0);
        //获取商品项
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if(cart != null){
            cart.deleteItem(id);
            //重定向到原页面
            resp.sendRedirect(req.getHeader("referer"));
        }
    }

    /**
     * 清空购物车
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if(cart != null){
            cart.clear();
            resp.sendRedirect(req.getHeader("referer"));
        }
    }

    /**
     * 修改商品数量
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = WebUtil.parsseInt(req.getParameter("id"), 0);
        int count = WebUtil.parsseInt(req.getParameter("count"), 0);

        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if(cart != null){
            cart.updateCount(id,count);
            resp.sendRedirect(req.getHeader("referer"));
        }
    }


    protected void ajaxAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数，商品编号
        int id = WebUtil.parsseInt(req.getParameter("id"), 0);
        //调用bookServlet.queryBookById():得到图书信息
        Book book = bookService.queryBookById(id);
        //把图书信息，转换成CartItem商品项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());

        //调用Cart.addItem()方法：添加商品项
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if(cart == null){
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }

        cart.addItem(cartItem);
        req.getSession().setAttribute("lastName",cartItem.getName());

        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("totalCount",cart.getTotalCount());
        resultMap.put("lastName",cartItem.getName());

        Gson gson = new Gson();
        String toJson = gson.toJson(resultMap);

        resp.getWriter().write(toJson);
    }



}
