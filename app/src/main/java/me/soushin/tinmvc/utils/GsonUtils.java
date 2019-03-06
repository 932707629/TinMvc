package me.soushin.tinmvc.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author : guof
 * Description :
 * date : 2018/8/6
 * Copyright (c) 2014-2017 银湾技术二部
 */

public class GsonUtils {
    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    /**
     * 将object对象转成json字符串
     *
     * @param object
     * @return
     */
    public static String toString(Object object) {
        return getGson().toJson(object);
    }

    /**
     * 将gsonString转成泛型bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T toBean(String gsonString, Class<T> cls) {
        return getGson().fromJson(gsonString, cls);
    }

    /**
     * 转成list
     * 此方法不可用
     * 泛型在编译期类型被擦除导致报错
     * @param gsonString
     * @param cls
     * @return
    public static <T> List<T> toList(String gsonString, Class<T> cls) {
    return getGson().fromJson(gsonString, new TypeToken<List<T>>() {}.getType());
    }*/

    /**
     * 转成list
     * 解决泛型问题
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(String json, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(getGson().fromJson(elem, cls));
        }
        return list;
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> toListMaps(String gsonString) {
        return getGson().fromJson(gsonString, new TypeToken<List<Map<String, T>>>(){}.getType());
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> toMaps(String gsonString) {
        return getGson().fromJson(gsonString, new TypeToken<Map<String, T>>(){}.getType());
    }
}
