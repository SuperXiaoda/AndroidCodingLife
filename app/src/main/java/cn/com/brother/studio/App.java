package cn.com.brother.studio;

import android.app.Application;

/**
 * Description: Application
 * author: LiangHD
 * Date: 2018/7/20
 */
public class App extends Application {

    // App单例
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    // 获取实例
    public static App getInstance() {
        return mInstance;
    }
}
