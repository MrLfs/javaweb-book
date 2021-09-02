package com.book.utils;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author LVFASEN
 * @create 2021-08-22 14:29
 */
public class WebUtil {
    /**
     * 把Map中的值注入对应的Javabean属性中
     * @param value
     * @param bean
     */
    public  static <T>T copyParamToBean(Map value, T bean){
        try{
            BeanUtils.populate(bean,value);
        }catch( Exception e){
            e.printStackTrace();
        }
        return bean;
    }

    public static int parsseInt(String str,int defaultNum){
           try {
               if(str != ""){

                   return Integer.parseInt(str);
               }
           } catch (Exception e) {
//               e.printStackTrace();
           }
           return defaultNum;

    }
}
