package cn.com.brother.studio.activity;

import android.content.Intent;
import android.os.Build;
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
                if (isChecked) {
                    if (Settings.canDrawOverlays(DistinguishActivity.this)) {
                        Intent intent = new Intent(DistinguishActivity.this, DistinguishService.class);
                        startService(intent);
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
}
