package com.iris.foundation.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by wangbin on 17/2/7.
 */
public class SpUtils {

    private final static String CADILLAC_SP = "aido_sp";
    private static final String COOKIE = "cookie";
    private final static String SCREEN_WITH = "screen_with";


    public static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(CADILLAC_SP, Context.MODE_PRIVATE);
    }

    /**
     * 保存cookie
     *
     * @param context
     * @param cookie
     */
    public static void setCooike(Context context, String cookie) {
        getSp(context).edit().putString(COOKIE, cookie).commit();
    }

    /**
     * 获取cookie
     *
     * @param context
     * @return
     */
    public static String getCookie(Context context) {
        return getSp(context).getString(COOKIE, "");
    }

    /**
     * 清除sp
     *
     * @param context
     */
    public static void clearSp(Context context) {
        getSp(context).edit().clear().commit();
    }

    public static void setScreenWith(Context context, int screenWith) {
        getSp(context).edit().putInt(SCREEN_WITH, screenWith).commit();
    }

    public static int getScreenWith(Context context) {
        return getSp(context).getInt(SCREEN_WITH, 0);
    }



}
