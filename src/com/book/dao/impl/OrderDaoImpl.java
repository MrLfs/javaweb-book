package com.book.dao.impl;

import com.book.dao.OrderDao;
import com.book.pojo.Order;

import java.util.List;

/**
 * @author LVFASEN
 * @create 2021-08-25 16:26
 */
public class OrderDaoImpl extends BaseDao<Order> implements OrderDao {
    @Override
    public int saveOrder(Order order) {
        String sql = "insert into t_order (order_id,create_time,price,status,user_id) values(?,?,?,?,?)";
        return update(sql,order.getOrderId(),order.getCreateTime(),order.getPrice(),order.getStatus(),order.getUserId());
    }

    @Override
    public List<Order> queryOrders() {
        String sql = "select order_id orderId,create_time createTime,price price,status status,user_id userId from t_order";
        return queryForList(sql);
    }
}
