package com.manong.domain.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtil {

    private BeanCopyUtil() {

    }

    /**
     * 复制单个对象
     * @param object
     * @param clazz
     * @param <V>
     * @return
     */
    public static <V> V copyBean(Object object,Class<V> clazz){

        //创建目标对象
        V v = null;
        try {
            v = clazz.newInstance();
            //复制
            BeanUtils.copyProperties(object,v);
        } catch (Exception e) {
        }
        return v;
    }

    public static <O,V> List<V> copyBeanList(List<O> o,Class<V> clazz){
       List<V> vList = o.stream().map((item) ->{
           V v = copyBean(item, clazz);
           return v;
       }).collect(Collectors.toList());

       return vList;
    }

//    public static <O,V> List<V> copyBeanList(List<O> list,Class<V> clazz){
//        return list.stream()
//                .map(o -> copyBean(o, clazz))
//                .collect(Collectors.toList());
//    }

}
