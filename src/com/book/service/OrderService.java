package com.book.service;

import com.book.pojo.Cart;
import com.book.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * @author LVFASEN
 * @create 2021-08-25 16:40
 */
public interface OrderService {
    public String createOrder(Cart cart,Integer userId);


    List<Order> showAllOrders();
}
