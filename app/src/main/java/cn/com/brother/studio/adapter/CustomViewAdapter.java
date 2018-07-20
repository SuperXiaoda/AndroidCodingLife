package cn.com.brother.studio.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.brother.studio.R;
import cn.com.brother.studio.model.Function;

/**
 * Description:自定义视图列表数据适配器
 * author: LiangHD
 * Date: 2018/7/20
 */
public class CustomViewAdapter extends BaseQuickAdapter<Function, BaseViewHolder> {

    public CustomViewAdapter() {
        super(R.layout.item_custom_ui, null);
    }

    @Override
    protected void convert(BaseViewHolder holder, Function item) {
        holder.setText(R.id.name, item.getName());

    }
}
