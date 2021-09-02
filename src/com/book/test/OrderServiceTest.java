package com.book.test;

import com.book.pojo.Cart;
import com.book.pojo.CartItem;
import com.book.service.OrderService;
import com.book.service.imple.OrderServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author LVFASEN
 * @create 2021-08-25 16:52
 */
public class OrderServiceTest {

    @Test
    public void createOrder() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1,"java教程",1,new BigDecimal(1000),new BigDecimal(1000)));
        cart.addItem(new CartItem(1,"java教程",1,new BigDecimal(1000),new BigDecimal(1000)));
        cart.addItem(new CartItem(2,"数据结构",1,new BigDecimal(100),new BigDecimal(100)));

        OrderService orderService = new OrderServiceImpl();
        System.out.println("订单号:" + orderService.createOrder(cart,1));
    }
}