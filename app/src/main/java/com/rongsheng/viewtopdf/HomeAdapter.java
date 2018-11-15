package com.rongsheng.viewtopdf;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author : lei
 * @desc :
 * @date : 2018/11/14 0014  上午 10:59.
 * 个人博客站: http://www.bestlei.top
 */

public class HomeAdapter extends BaseQuickAdapter<Person,BaseViewHolder> {

    public HomeAdapter(@LayoutRes int layoutResId, @Nullable List<Person> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Person item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_id, item.getId());
    }
}
