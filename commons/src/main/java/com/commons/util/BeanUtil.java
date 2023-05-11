package com.commons.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yyh
 * @date 2023/5/11 17:03
 * @description
 */
public class BeanUtil {

    public static <T, R> List<R> convert(List<T> list, Class<R> clazz) throws IllegalAccessException, InstantiationException {
        List<R> result = new ArrayList<>();
        for (T item : list) {
            R obj = clazz.newInstance();
            BeanUtils.copyProperties(item, obj);
            result.add(obj);
        }
        return result;
    }

    public static <T, R> T convert(Class<T> target, R resource) throws IllegalAccessException, InstantiationException {
        T instance = target.newInstance();
        BeanUtils.copyProperties(resource, instance);
        return instance;
    }
}
