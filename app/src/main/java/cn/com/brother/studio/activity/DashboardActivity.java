package cn.com.brother.studio.activity;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;

import butterknife.BindView;
import cn.com.brother.studio.R;
import cn.com.brother.studio.util.StringUtil;
import cn.com.brother.studio.util.ToastUtil;

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
    // 最小值输入框
    @BindView(R.id.min_value)
    AppCompatEditText mMinValue;
    // 最大值输入框
    @BindView(R.id.max_value)
    AppCompatEditText mMaxValue;
    // 切分份数输入框
    @BindView(R.id.number_of_copies)
    AppCompatEditText mNumberOfCopies;
    // 当前值输入框
    @BindView(R.id.real_time_value)
    AppCompatEditText mRealTimeValue;
    // 确定按钮
    @BindView(R.id.sure)
    AppCompatButton mSure;

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
        mSure.setOnClickListener(this);
        mMaxValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int minValue = Integer.valueOf(StringUtil.isNotEmpty(mMinValue.getText().toString().trim()) ? mMinValue.getText().toString().trim() : "0");
                int maxValue = Integer.valueOf(StringUtil.isNotEmpty(mMaxValue.toString()) ? mMaxValue.toString().trim() : "100");
                if (minValue >= maxValue) {
                    ToastUtil.showShort(R.string.min_max_prompt);
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
            case R.id.sure:
                if (checkData()) {

                }
                break;
        }
    }

    // 检查数据正确性
    private boolean checkData() {

        String numberOfCopies = mNumberOfCopies.getText().toString().trim();
        String realTimeValue = mRealTimeValue.getText().toString().trim();
    /*    if (StringUtil.isNotEmpty(String.valueOf(minValue)) && StringUtil.isNotEmpty(String.valueOf(maxValue))) {
           if (minValue >= maxValue) {
                ToastUtil.showShort(R.string.);
                return false;
            }
        }*/
        return true;
    }
}
