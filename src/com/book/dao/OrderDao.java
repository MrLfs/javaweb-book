package com.book.dao;

import com.book.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * @author LVFASEN
 * @create 2021-08-25 16:24
 */
public interface OrderDao {

    public int saveOrder(Order order);

    List<Order> queryOrders();
}
