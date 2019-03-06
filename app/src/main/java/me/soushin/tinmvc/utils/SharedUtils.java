package me.soushin.tinmvc.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by SouShin on 2018/8/270916.
 * 可以存取Boolean/Long/Integer/Float/Object/List/HashMap
 */
public class SharedUtils {
    private static String filename = "cookie";
    private static SharedPreferences.Editor editor;
    private static SharedPreferences preferences;

    /**
     * 需要初始化
     * @param context
     */
    public static void initShared(Context context){
        preferences = context.getSharedPreferences(filename, context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 保存数据到SharedPreferences
     *
     * @param key   键
     * @param value 需要保存的数据
     * @return 保存结果
     */
    public static boolean putData(String key, Object value) {
        String type = value.getClass().getSimpleName();
        try {
            switch (type) {
                case "Boolean":
                    editor.putBoolean(key, (Boolean) value);
                    break;
                case "Long":
                    editor.putLong(key, (Long) value);
                    break;
                case "Float":
                    editor.putFloat(key, (Float) value);
                    break;
                case "String":
                    editor.putString(key, (String) value);
                    break;
                case "Integer":
                    editor.putInt(key, (Integer) value);
                    break;
                default:
                    String json = GsonUtils.toString(value);
                    editor.putString(key, json);
                    break;
            }
        } catch (Exception e) {
           return false;
        }
        return editor.commit();
    }

    /**
     * 获取SharedPreferences中保存的数据
     *
     * @param key          键
     * @param defaultValue 获取失败默认值
     * @return 从SharedPreferences读取的数据
     */
    public static Object getData(String key,@NonNull Object defaultValue) {
        Object result;
        String type = defaultValue.getClass().getSimpleName();
        try {
            switch (type) {
                case "Boolean":
                    result = preferences.getBoolean(key, (Boolean) defaultValue);
                    break;
                case "Long":
                    result = preferences.getLong(key, (Long) defaultValue);
                    break;
                case "Float":
                    result = preferences.getFloat(key, (Float) defaultValue);
                    break;
                case "String":
                    result = preferences.getString(key, (String) defaultValue);
                    break;
                case "Integer":
                    result = preferences.getInt(key, (Integer) defaultValue);
                    break;
                default:
                    String json = preferences.getString(key, "");
                    if (!json.equals("") && json.length() > 0) {
                        result = GsonUtils.getGson().fromJson(json, defaultValue.getClass());
                    } else {
                        result = defaultValue;
                    }
                    break;
            }
        } catch (Exception e) {
            result = defaultValue;
        }
        return result;
    }

    /**
     * 获取字符串数据
     * @param key
     * @return
     */
    public static String getStringData(String key){
        return (String) getData(key,"");
    }

    /**
     * 获取int型数据
     * @param key
     * @return
     */
    public static int getIntData(String key){
        return (int) getData(key,0);
    }

    /**
     * 获取布尔型数据
     * @param key
     * @return
     */
    public static boolean getBooleanData(String key){
        return (boolean) getData(key,false);
    }

    /**
     * 用于保存集合
     *
     * @param key  key
     * @param list 集合数据
     * @return 保存结果
     */
    public static <T> boolean putListData(String key, List<T> list) {
        String type = list.get(0).getClass().getSimpleName();
        JsonArray array = new JsonArray();
        try {
            switch (type) {
                case "Boolean":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Boolean) list.get(i));
                    }
                    break;
                case "Long":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Long) list.get(i));
                    }
                    break;
                case "Float":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Float) list.get(i));
                    }
                    break;
                case "String":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((String) list.get(i));
                    }
                    break;
                case "Integer":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Integer) list.get(i));
                    }
                    break;
                default:
                    for (int i = 0; i < list.size(); i++) {
                        JsonElement obj = GsonUtils.getGson().toJsonTree(list.get(i));
                        array.add(obj);
                    }
                    break;
            }
            editor.putString(key, array.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return  editor.commit();
    }

    /**
     * 获取保存的List
     *
     * @param key key
     * @return 对应的Lis集合
     */
    public static <T> List<T> getListData(String key, Class<T> cls) {
        List<T> list = new ArrayList<>();
        String json = preferences.getString(key, "");
        if (!json.equals("") && json.length() > 0) {
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement elem : array) {
                list.add(GsonUtils.getGson().fromJson(elem, cls));
            }
        }
        return list;
    }

    /**
     * 用于保存集合
     *
     * @param key key
     * @param map map数据
     * @return 保存结果
     */
    public static <K, V> boolean putHashMapData(String key, Map<K, V> map) {
        boolean result;
        try {
            String json = GsonUtils.toString(map);
            editor.putString(key, json);
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    /**
     * 用于保存集合
     *
     * @param key key
     * @return HashMap
     */
    public static <V> HashMap<String, V> getHashMapData(String key, Class<V> clsV) {
        String json = preferences.getString(key, "");
        HashMap<String, V> map = new HashMap<>();

        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String entryKey = entry.getKey();
            JsonObject value = (JsonObject) entry.getValue();
            map.put(entryKey, GsonUtils.getGson().fromJson(value, clsV));
        }
        return map;
    }

    /**
     * 清除缓存的数据
     * @return
     */
    public static Boolean clearCache() {
        try {
            editor.clear();
            return editor.commit();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
