package cn.com.brother.studio.activity;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import butterknife.BindView;
import cn.com.brother.studio.R;
import cn.com.brother.studio.service.DistinguishService;
import cn.com.brother.studio.util.ToastUtil;

/**
 * Description: 识别文字页面
 * author: Lianghd
 * Date:2018/8/29
 */
public class DistinguishActivity extends BaseActivity implements View.OnClickListener {

    // 返回按钮
    @BindView(R.id.back)
    ImageButton mBack;
    // 标题
    @BindView(R.id.title)
    AppCompatTextView mTitle;
    // 显示悬浮框
    @BindView(R.id.suspension_switch)
    SwitchCompat mSuspensionSwitch;
    // 服务bind
    private DistinguishService.DistinguishBinder mDistinguishBinder;
    // 截屏请求码
    private static final int REQUEST_MEDIA_PROJECTION = 1001;

    @Override
    protected int getContentView() {
        return R.layout.activity_disting;
    }

    @Override
    protected void init() {
        mTitle.setText(getString(R.string.distinguish));
    }

    @Override
    protected void setListener() {
        mBack.setOnClickListener(this);

        mSuspensionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent(DistinguishActivity.this, DistinguishService.class);
                if (isChecked) {
                    if (Settings.canDrawOverlays(DistinguishActivity.this)) {
                        bindService(intent, connection, BIND_AUTO_CREATE);
                        // startService(intent);
                    } else {
                        new MaterialDialog.Builder(DistinguishActivity.this)
                                .title(R.string.prompt)
                                .content(R.string.suspension_prompt)
                                .positiveText(R.string.sure)
                                .negativeText(R.string.cancel)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@android.support.annotation.NonNull MaterialDialog dialog, @android.support.annotation.NonNull DialogAction which) {
                                        //若没有权限，提示获取.
                                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                                        startActivity(intent);
                                    }
                                })
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        finish();
                                    }
                                })
                                .show();

                    }
                } else {
                    unbindService(connection);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        mSuspensionSwitch.setChecked(Settings.canDrawOverlays(DistinguishActivity.this));
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_MEDIA_PROJECTION:

                break;
        }
    }



    // 判断是否5.0以上系统
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void requestCapturePermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //5.0 之后才允许使用屏幕截图
            ToastUtil.showShort("本系统版本不支持截屏功能");
            return;
        }

        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager)
                getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startActivityForResult(
                mediaProjectionManager.createScreenCaptureIntent(),
                REQUEST_MEDIA_PROJECTION);
    }


    private ServiceConnection connection = new ServiceConnection() {
        /**
         * 服务解除绑定时候调用
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub

        }

        /**
         * 绑定服务的时候调用
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            mDistinguishBinder = (DistinguishService.DistinguishBinder) service;
            DistinguishService distinguishService = mDistinguishBinder.getService();
            /**
             * 实现回调，得到实时刷新的数据
             */
            distinguishService.setOnItemClickLister(new DistinguishService.OnButtonClickLister() {
                @Override
                public void onButtonClick() {
                    ToastUtil.showShort("按钮被点击");
                    requestCapturePermission();
                }
            });
        }
    };


}
