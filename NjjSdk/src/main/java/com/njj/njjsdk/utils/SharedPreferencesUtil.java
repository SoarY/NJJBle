package com.njj.njjsdk.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * 本地存储数据类
 *
 * @ClassName SharedPreferencesUtil
 * @Description TODO
 * @Author Darcy
 * @Date 2021/4/16 14:25
 * @Version 1.0
 */
public abstract class SharedPreferencesUtil {

    private SharedPreferences mSharedPreferences;

    /**
     * 初始化
     *
     * @param context 上下文对象
     */
    public void init(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(getName(), Context.MODE_PRIVATE);
    }

    /**
     * APP存储名称
     *
     * @return
     */
    protected abstract String getName();

    /**
     * 移除某个键
     *
     * @param key 键
     * @return
     */
    public boolean remove(String key) {
        return mSharedPreferences.edit().remove(key).commit();
    }

    /**
     * 清除所有的键
     *
     * @return
     */
    public boolean clear() {
        return mSharedPreferences.edit().clear().commit();
    }

    /**
     * 获取值
     *
     * @param key      键
     * @param defValue 默认值
     * @return
     */
    protected int getValue(String key, int defValue) {
        return this.mSharedPreferences.getInt(key, defValue);
    }

    protected float getValue(String key, float defValue) {
        return this.mSharedPreferences.getFloat(key, defValue);
    }

    protected long getValue(String key, long defValue) {
        return this.mSharedPreferences.getLong(key, defValue);
    }

    protected String getValue(String key, String defValue) {
        if (this.mSharedPreferences==null)
            return "";
        return this.mSharedPreferences.getString(key, defValue);
    }

    protected boolean getValue(String key, boolean defValue) {
        return this.mSharedPreferences.getBoolean(key, defValue);
    }

    protected Set<String> getValue(String key, Set<String> defValue) {
        return this.mSharedPreferences.getStringSet(key, defValue);
    }

    /**
     * 设置值
     *
     * @param key   键
     * @param value 要设置的值
     */
    protected void putValue(String key, Object value) {
        if (value instanceof Integer) {
            this.mSharedPreferences.edit().putInt(key, (Integer) value).commit();
        } else if (value instanceof Float) {
            this.mSharedPreferences.edit().putFloat(key, (Float) value).commit();
        } else if (value instanceof Long) {
            this.mSharedPreferences.edit().putLong(key, (Long) value).commit();
        } else if (value instanceof String) {
            this.mSharedPreferences.edit().putString(key, (String) value).commit();
        } else if (value instanceof Boolean) {
            this.mSharedPreferences.edit().putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Set) {
            this.mSharedPreferences.edit().putStringSet(key, (Set<String>) value).commit();
        }
    }

    /**
     * 是否包含某个键
     *
     * @param key 键
     * @return
     */
    public boolean contains(String key) {
        return mSharedPreferences.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public Map<String, ?> getAll() {
        return mSharedPreferences.getAll();
    }


}
