package com.oil.vivek.oilmillcalc.utils;

/**
 * Created by Parsania Hardik on 19/01/2016.
 */
public class AppLog {

    public static final boolean isDebug = true;

    public static final void Log(String tag, String message) {
        if (isDebug) {
            android.util.Log.i(tag, message + "");
        }
    }
}
