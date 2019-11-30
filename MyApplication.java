package com.waakka.myshowview;

import android.app.Application;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.helper.Logger;

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        Logger.d(TAG, "[ExampleApplication] onCreate");
        super.onCreate();

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush


        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
