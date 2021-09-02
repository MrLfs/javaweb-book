package com.book.service.imple;

import com.book.dao.BookDao;
import com.book.dao.OrderDao;
import com.book.dao.OrderItemDao;
import com.book.dao.impl.BookDaoImpl;
import com.book.dao.impl.OrderDaoImpl;
import com.book.dao.impl.OrderItemDaoImpl;
import com.book.pojo.*;
import com.book.service.OrderService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LVFASEN
 * @create 2021-08-25 16:42
 */
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public String createOrder(Cart cart, Integer userId) {
        //订单号要唯一性
        String orderId = System.currentTimeMillis() + "" + userId;
        Order order = new Order(orderId, new Date(), cart.getTotalPrice(), 0, userId);

        orderDao.saveOrder(order);

        //遍历购物车中每一个商品项转变成订单项保存到数据库
        for (Map.Entry<Integer, CartItem> entry: cart.getItems().entrySet()){
            //获取购物车中的每一个商品
            CartItem cartItem = entry.getValue();
            //生成订单项
            OrderItem orderItem = new OrderItem(null,cartItem.getName(),cartItem.getCount(),cartItem.getPrice(),cartItem.getTotalPrice(),orderId);
            //保存
            orderItemDao.saveOrderItem(orderItem);

            //更新库存和销量
            Book book = bookDao.queryBookById(cartItem.getId());
            book.setSales(book.getSales() + cartItem.getCount());
            book.setStock(book.getStock() - cartItem.getCount());
            bookDao.updateBook(book);
        }
        cart.clear();
        return  orderId;
    }

    @Override
    public List<Order> showAllOrders() {
        return orderDao.queryOrders();
    }
}
