package cn.com.brother.studio.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import cn.com.brother.studio.R;
import cn.com.brother.studio.activity.DashboardActivity;
import cn.com.brother.studio.activity.DistinguishActivity;
import cn.com.brother.studio.adapter.FunctionAdapter;
import cn.com.brother.studio.model.Function;

/**
 * Description:功能模块
 * author: Lianghd
 * Date:2018/8/29
 */
public class FunctionFragment extends BaseFragment {

    // recyclerView
    @BindView(R.id.view_recycler)
    RecyclerView mViewRecycler;
    // 数据适配器
    private FunctionAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_function;
    }

    @Override
    protected void init(View rootView) {
        mViewRecycler.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mViewRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mActivity)
                .colorResId(R.color.colorDivider)
                .sizeResId(R.dimen.DIP_0_5)
                .showLastDivider()
                .build());
        mAdapter = new FunctionAdapter();
        mViewRecycler.setAdapter(mAdapter);
        initData();
    }

    @Override
    protected void setListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Function function = (Function) adapter.getData().get(position);
                Intent intent = new Intent();
                switch  (function.getId()) {
                    case 1:
                        intent.setClass(mActivity, DistinguishActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }

    // 初始化数据
    private void initData() {
        ArrayList<Function> data = new ArrayList<>();
        data.add(new Function(1, getString(R.string.distinguish), getString(R.string.distinguish_desc)));
        mAdapter.setNewData(data);
    }
}
