package cn.com.brother.studio.activity;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageButton;

import butterknife.BindView;
import cn.com.brother.studio.R;

/**
 * Description:仪表盘页面
 * author: LiangHD
 * Date: 2018/7/20
 */
public class DashboardActivity extends BaseActivity implements View.OnClickListener {
    // 返回按钮
    @BindView(R.id.back)
    ImageButton mBack;
    // 标题
    @BindView(R.id.title)
    AppCompatTextView mTitle;

    @Override
    protected int getContentView() {
        return R.layout.activity_dashboard;
    }

    @Override
    protected void init() {
        mTitle.setText(getString(R.string.dashboard));
    }

    @Override
    protected void setListener() {
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
