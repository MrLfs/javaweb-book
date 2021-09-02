package com.book.web;

import com.book.pojo.Cart;
import com.book.pojo.Order;
import com.book.pojo.User;
import com.book.service.OrderService;
import com.book.service.imple.OrderServiceImpl;
import com.book.utils.JDBCUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author LVFASEN
 * @create 2021-08-25 17:02
 */
public class OrderServlet extends BaseServlet{


    private OrderService orderService = new OrderServiceImpl();
    /**
     * 生成订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //调用orderService.createOrder(cart,userId)
        Cart cart = (Cart) req.getSession().getAttribute("cart");


        //获取userid
        User loginUser = (User) req.getSession().getAttribute("user");

        if(loginUser == null){
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
            return;
        }
        Integer userId = loginUser.getId();

        String orderId = orderService.createOrder(cart, userId);


        req.getSession().setAttribute("orderId",orderId);

//        req.getRequestDispatcher("/pages/cart/checkout.jsp").forward(req,resp);
        resp.sendRedirect(req.getContextPath() + "/pages/cart/checkout.jsp");
    }


    protected void showAllOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orderList = orderService.showAllOrders();

        req.getSession().setAttribute("orderList",orderList);

        req.getRequestDispatcher("/pages/order/order.jsp").forward(req,resp);
    }
}
