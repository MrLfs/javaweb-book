package com.book.filter;

import com.book.utils.JDBCUtil;

import javax.servlet.*;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 * @author LVFASEN
 * @create 2021-08-26 10:36
 */
public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try {
            filterChain.doFilter(servletRequest, servletResponse);
            JDBCUtil.commitAndClose();
        }catch (Exception e){
            JDBCUtil.rollbackAndClose();
            e.printStackTrace();
            throw new RuntimeException(e);//把异常报给服务器
        }
    }

    @Override
    public void destroy() {

    }
}
