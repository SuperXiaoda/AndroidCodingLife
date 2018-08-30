package cn.com.brother.studio.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import cn.com.brother.studio.R;
import cn.com.brother.studio.util.ToastUtil;

/**
 * Description: 悬浮框service
 * author: LiangHD
 * Date: 2018/8/30
 */
public class DistinguishService extends Service {

    // TAG
    private static final String TAG = "DistinguishService";
    // 悬浮窗体布局
    private LinearLayout mDistinguishLayout;
    private WindowManager.LayoutParams params;
    private WindowManager windowManager;
    private AppCompatButton mButton;
    //状态栏高度.
    int statusBarHeight = -1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createTouch();
    }

    // 创建悬浮窗体
    private void createTouch() {
        //赋值WindowManager&LayoutParam.
        params = new WindowManager.LayoutParams();
        windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        //设置type.系统提示型窗口，一般都在应用程序窗口之上.
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        //设置效果为背景透明.
        params.format = PixelFormat.RGBA_8888;
        //设置flags.不可聚焦及不可使用按钮对悬浮窗进行操控.
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        //设置窗口初始停靠位置.
        params.gravity = Gravity.START | Gravity.TOP;
        params.x = 0;
        params.y = 0;

        //设置悬浮窗口长宽数据.
        params.width = 300;
        params.height = 300;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局.
        mDistinguishLayout = (LinearLayout) inflater.inflate(R.layout.layout_distinguish, null);
        //添加悬浮窗体布局
        windowManager.addView(mDistinguishLayout, params);

        Log.i(TAG, "toucherlayout-->left:" + mDistinguishLayout.getLeft());
        Log.i(TAG, "toucherlayout-->right:" + mDistinguishLayout.getRight());
        Log.i(TAG, "toucherlayout-->top:" + mDistinguishLayout.getTop());
        Log.i(TAG, "toucherlayout-->bottom:" + mDistinguishLayout.getBottom());

        //主动计算出当前View的宽高信息.
        mDistinguishLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        //用于检测状态栏高度.
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);

        }
        Log.i(TAG, "状态栏高度为:" + statusBarHeight);

        //浮动窗口按钮.
        mButton = mDistinguishLayout.findViewById(R.id.distinguish_btn);

        mButton.setOnClickListener(new View.OnClickListener() {
            long[] hints = new long[2];

            @Override
            public void onClick(View v) {
                ToastUtil.showShort("被点击");

            }
        });


        mButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float stratX = 0;
                float startY = 0;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    stratX = event.getRawX();
                    startY = event.getRawY();
                    Log.d(TAG, "down  x----->" + stratX + "--------y----->" + startY);
                }
                // 判断点击时间类型
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.d(TAG, "move  x----->" + event.getRawX() + "--------y----->" + event.getRawY());
                    if (Math.abs(event.getRawX() - stratX) > 50 || Math.abs(event.getRawY() - startY) > 50) {

                        params.x = (int) event.getRawX();
                        params.y = (int) event.getRawY() - statusBarHeight;
                        windowManager.updateViewLayout(mDistinguishLayout, params);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {

                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        if (mButton != null) {
            windowManager.removeView(mDistinguishLayout);
        }
        super.onDestroy();
    }
}
