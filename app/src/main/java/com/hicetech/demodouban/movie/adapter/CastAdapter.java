package com.hicetech.demodouban.movie.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hicetech.demodouban.R;
import com.hicetech.demodouban.commonality.utils.ImageUtils;
import com.hicetech.demodouban.movie.module.Moviedetail;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class CastAdapter extends BaseMultiItemQuickAdapter<Moviedetail.CastsBean,BaseViewHolder> {
    Context mContext;

    public CastAdapter(List<Moviedetail.CastsBean> datas,Context context) {
        super(datas);
        this.mContext = context;
        addItemType(Moviedetail.CastsBean.NULL,R.layout.item_cast);
        addItemType(Moviedetail.CastsBean.DOUBLE,R.layout.item_cast_double);
        addItemType(Moviedetail.CastsBean.LEFT,R.layout.item_cast_left);
        addItemType(Moviedetail.CastsBean.RIGHT,R.layout.item_cast_right);



    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Moviedetail.CastsBean castsBean) {
        switch (viewHolder.getItemViewType()){
            case Moviedetail.CastsBean.NULL:
                viewHolder.setText(R.id.tv_name,castsBean.getName()!=null?castsBean.getName():"");
                ImageView imageView = (ImageView) viewHolder.getConvertView().findViewById(R.id.iv_cast);
                ImageUtils.setImgShowEP(mContext,castsBean.getAvatars().getSmall(),imageView,null);
                break;
            case Moviedetail.CastsBean.DOUBLE:
                viewHolder.setText(R.id.tv_name,castsBean.getName()!=null?castsBean.getName():"");
                ImageView imageView2 = (ImageView) viewHolder.getConvertView().findViewById(R.id.iv_cast);
                ImageUtils.setImgShowEP(mContext,castsBean.getAvatars().getSmall(),imageView2,null);
                break;
            case Moviedetail.CastsBean.LEFT:
                viewHolder.setText(R.id.tv_name,castsBean.getName()!=null?castsBean.getName():"");
                ImageView imageView3 = (ImageView) viewHolder.getConvertView().findViewById(R.id.iv_cast);
                ImageUtils.setImgShowEP(mContext,castsBean.getAvatars().getSmall(),imageView3,null);
                break;
            case Moviedetail.CastsBean.RIGHT:
                viewHolder.setText(R.id.tv_name,castsBean.getName()!=null?castsBean.getName():"");
                ImageView imageView4 = (ImageView) viewHolder.getConvertView().findViewById(R.id.iv_cast);
                ImageUtils.setImgShowEP(mContext,castsBean.getAvatars().getSmall(),imageView4,null);
                break;
        }


    }


}
