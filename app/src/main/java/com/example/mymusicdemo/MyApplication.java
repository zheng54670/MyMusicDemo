package com.example.mymusicdemo;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.example.mymusicdemo.helps.RealmHelp;

import io.realm.Realm;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // init it in the function of onCreate in ur Application
        Utils.init(this);
        Realm.init(this);

        RealmHelp.migration();
    }
}
