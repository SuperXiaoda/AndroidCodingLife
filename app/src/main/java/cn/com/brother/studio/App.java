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

}
