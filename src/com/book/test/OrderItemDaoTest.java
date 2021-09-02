package com.book.test;

import com.book.dao.OrderItemDao;
import com.book.dao.impl.OrderItemDaoImpl;
import com.book.pojo.OrderItem;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author LVFASEN
 * @create 2021-08-25 16:36
 */
public class OrderItemDaoTest {
    OrderItemDao orderItemDao = new OrderItemDaoImpl();

    @Test
    public void saveOrderItem() {
        orderItemDao.saveOrderItem(new OrderItem(null,"java教程",1,new BigDecimal(100),new BigDecimal(100),"12345678"));
    }
}