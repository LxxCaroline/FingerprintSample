package com.example.fingerprintsample;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class LocalSharedPreference {

    final String dataKeyName = "data";
    final String IVKeyName = "IV";
    private SharedPreferences preferences;

    LocalSharedPreference(Context context) {
        preferences = context.getSharedPreferences("sample", Activity.MODE_PRIVATE);
    }

    String getData(String keyName) {
        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        return preferences.getString(keyName, "");
    }

    boolean storeData(String key, String data) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, data);
        return editor.commit();
    }

    boolean containsKey(String key) {
        return !TextUtils.isEmpty(getData(key));
    }
}
