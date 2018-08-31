package cn.com.brother.studio.service;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.media.projection.MediaProjectionManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileOutputStream;

import cn.com.brother.studio.R;
import cn.com.brother.studio.util.ToastUtil;
import cn.com.brother.studio.view.CustomButton;

/**
 * Description: 悬浮框service
 * author: LiangHD
 * Date: 2018/8/30
 */
public class DistinguishService extends Service {

    private DistinguishBinder distinguishBinder=new DistinguishBinder();

    // TAG
    private static final String TAG = "DistinguishService";
    // 悬浮窗体布局
    private LinearLayout mDistinguishLayout;
    private WindowManager.LayoutParams params;
    private WindowManager windowManager;
    private CustomButton mButton;
    //状态栏高度.
    int statusBarHeight = -1;

    // 页面
    private Activity mActivity;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return distinguishBinder;
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
        params.gravity = Gravity.END | Gravity.BOTTOM;
        params.x = 0;
        params.y = 0;

        //设置悬浮窗口长宽数据.
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT - 50;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局.
        mDistinguishLayout = (LinearLayout) inflater.inflate(R.layout.layout_distinguish, null);
        //添加悬浮窗体布局
        windowManager.addView(mDistinguishLayout, params);

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
            // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
              /*  if (!mButton.isDrag()) {
                    final MediaProjectionManager projectionManager = (MediaProjectionManager)
                            getSystemService(Context.MEDIA_PROJECTION_SERVICE);
                    Intent intent = projectionManager.createScreenCaptureIntent();
                    mActivity.startActivityForResult(intent, REQUEST_CODE);
                }*/
                if (mOnButtonClickLister != null) {
                    mOnButtonClickLister.onButtonClick();
                }

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

    public void printScreen(View view) {
        String imgPath = "/sdcard/test.png";
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        if (bitmap != null) {
            try {
                FileOutputStream out = new FileOutputStream(imgPath);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                        out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private OnButtonClickLister mOnButtonClickLister;

    public void setOnItemClickLister(OnButtonClickLister onButtonClickLister) {
        this.mOnButtonClickLister = onButtonClickLister;
    }

    /**
     * 按钮点击监听
     */
    public interface OnButtonClickLister {

        // 按钮点击监听
        void onButtonClick();
    }

    /**
     * 内部类继承Binder
     * @author lenovo
     *
     */
    public class DistinguishBinder extends Binder {
        /**
         * 声明方法返回值是MyService本身
         * @return
         */
        public DistinguishService getService() {
            return DistinguishService.this;
        }
    }
}
