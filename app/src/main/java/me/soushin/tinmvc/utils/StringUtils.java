package me.soushin.tinmvc.utils;

import java.util.List;

/**
 * author : guof
 * Description :
 * date : 2018/10/12
 */
public class StringUtils {

    /**
     * 字符串判空
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        return str == null || str.trim().length()==0 || "null".equals(str);
    }

    /**
     * 对象判空
     * @param ob
     * @return
     */
    public static boolean isNull(Object ob) {
        return ob == null || ob.toString().length()==0;
    }

    /**
     * 数组判空
     * @param list
     * @return
     */
    public static boolean isNull(List list){
        return list==null||list.size()==0;
    }

}
