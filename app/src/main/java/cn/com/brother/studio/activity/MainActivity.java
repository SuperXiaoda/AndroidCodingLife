package cn.com.brother.studio.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.brother.studio.R;
import cn.com.brother.studio.fragment.CustomUiFragment;
import cn.com.brother.studio.view.FixViewPager;

/**
 * Description: 主页
 * author: Lianghd
 * Date: 2018/7/20
 */
public class MainActivity extends BaseActivity {

    // viewPage
    @BindView(R.id.view_pager)
    FixViewPager mMainViewPager;
    // 导航栏
    @BindView(R.id.navigation)
    AHBottomNavigation mBottomNavigation;
    // 存储各个界面
    private List<Fragment> mTabs = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        // 设置底部导航布局
        mBottomNavigation.setForceTitlesDisplay(true);
        mBottomNavigation.setAccentColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        mBottomNavigation.setInactiveColor(ContextCompat.getColor(getApplicationContext(), R.color.colorInactive));

        mBottomNavigation.addItem(new AHBottomNavigationItem(R.string.ui, R.drawable.ic_custom_view));

        // 列车
        CustomUiFragment customUiFragment = new CustomUiFragment();
        mTabs.add(customUiFragment);
        // 界面适配器
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mTabs.get(arg0);
            }
        };

        mMainViewPager.setOffscreenPageLimit(mTabs.size());
        mMainViewPager.setAdapter(mAdapter);

        mMainViewPager.setCurrentItem(0);
        mBottomNavigation.setCurrentItem(0);
    }

    @Override
    protected void setListener() {

    }
}
