package cn.dingrui.italker;


import com.igexin.sdk.PushManager;

import cn.dingrui.common.common.app.BaseApplication;
import cn.dingrui.factory.Factory;

/**
 * Created by dingrui
 */

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //调用Factory进行初始化
        Factory.setup();
        //推送初始化
        PushManager.getInstance().initialize(this);
    }
}
