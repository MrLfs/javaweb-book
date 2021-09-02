package com.book.test;

import com.book.dao.OrderDao;
import com.book.dao.impl.OrderDaoImpl;
import com.book.pojo.Order;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author LVFASEN
 * @create 2021-08-25 16:33
 */
public class OrderDaoTest {

    OrderDao orderDao = new OrderDaoImpl();
    @Test
    public void saveOrder() {
        orderDao.saveOrder(new Order("12345678",new Date(),new BigDecimal(100),0,1));
    }
}