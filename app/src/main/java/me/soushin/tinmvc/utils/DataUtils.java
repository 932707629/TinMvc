package me.soushin.tinmvc.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据获取工具类
 * @auther SouShin
 * @time 2019/1/19 18:16
 */
public class DataUtils {

    public static List<String> getActiveList(){
        List<String> activeList=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            activeList.add("动态数组");
        }
        return activeList;
    }




}
