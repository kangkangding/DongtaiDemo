package com.example.mama.dongtaidemo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mama on 2017/11/7.
 */

public class SharedPreferenceUtil {
    private static SharedPreferences sharedPreferences;
    String PREFS_NAME = "dingkangkang";//保存导本地文件的名称
    public SharedPreferenceUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);//MODE_PRIVATE 私有文件，只能本身访问
    }
    //保存数据 saveStr key ， saveData保存的数据
    public void saveSharePre(String saveStr,String saveData){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(saveStr, saveData);
        editor.commit();
    }
    //获取保存的数据 saveStr key ， defData默认值 数据获取失败的默认值
    public  String getSharePre(String getStr,String defData){

        String data = sharedPreferences.getString(getStr, defData);
        return data;
    }
    //清除指定数据
    public  void clearKeySharePre(String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }
    //清除所有数据
    public  void clearAllSharePre(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
