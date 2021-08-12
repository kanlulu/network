package com.iarc.network.utils;

import com.iarc.base.utils.LogUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * author:kanlulu
 * data  :6/5/21 10:39 AM
 **/
public class ReflectUtils {

    /**
     *
     * @param data 支持的数据结构  data层里集合名称是list
     *             data: {
     *                  title:"",//其他数据
     *                  list:[]//目标数据
     *             }
     * @param <T>
     * @return
     */
    public static <T> int pageData(T data) {
        int dataSize = -1;
        Class dataClass = data.getClass();
        try {
            Field listFiled = dataClass.getDeclaredField("list");
            //打开私有访问
            listFiled.setAccessible(true);
            //获取属性值
            Object listObj = listFiled.get(data);
            if (listObj instanceof List){
                dataSize = ((List) listObj).size();
            }
        } catch (Exception e) {
            LogUtils.INSTANCE.logeInfo("=test=", "ReflectUtils -- " + e.getMessage());
        }

        return dataSize;

    }
}
