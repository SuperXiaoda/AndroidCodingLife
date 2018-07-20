package cn.com.brother.studio.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * Description: 基础FragmentActivity
 * author: Lianghd
 * Date: 2018-7-20
 */
public abstract class BaseMainActivity extends AppCompatActivity {

    // 页面TAG
    final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        // 设置透明状态栏
        StatusBarUtil.setTranslucentForImageView(this, 0, null);

        ButterKnife.bind(this);

        init();

        setListener();
    }

    @Override
    protected void onResume() {
        // 检测程序是否回到前段
        super.onResume();
        // 缺少权限时, 进入权限配置页面
       /* if (PermissionsChecker.lacksPermissions(this, Constants.PERMISSIONS)) {
            Intent intent = new Intent(this, PermissionsActivity.class);
            startActivity(intent);
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            release();
        }
    }

    /**
     * Description: 获取主布局Layout Id
     * author: Lianghd
     * Date: 2018-7-20
     */
    protected abstract int getContentView();

    /**
     * Description: 初始化
     * author: Lianghd
     * Date: 2018-7-20
     */
    protected abstract void init();

    /**
     * Description: 添加事件监听器
     * author: Lianghd
     * Date: 2018-7-20
     */
    protected abstract void setListener();

    /**
     * Description:
     * author: Lianghd
     * Date: 2018-7-20
     */
    protected void release() {
    }

}

