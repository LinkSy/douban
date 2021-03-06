package com.hicetech.demodouban.movie.adapter;

import android.content.Context;
import android.text.TextUtils;
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
        String url ;
        if (castsBean.getAvatars()==null){
                return;
        }else {
            url = castsBean.getAvatars().getSmall();
        }
        if (TextUtils.isEmpty(url)){
            return;
        }

        String url_null = "http://img1.juimg.com/141021/330794-14102112262527.jpg";
        switch (viewHolder.getItemViewType()){
            case Moviedetail.CastsBean.NULL:
                viewHolder.setText(R.id.tv_name,castsBean.getName()!=null?castsBean.getName():"");
                ImageView imageView = (ImageView) viewHolder.getConvertView().findViewById(R.id.iv_cast);
                ImageUtils.setImgShowEP(mContext,url!=null?url:url_null,imageView);
                break;
            case Moviedetail.CastsBean.DOUBLE:
                viewHolder.setText(R.id.tv_name,castsBean.getName()!=null?castsBean.getName():"");
                ImageView imageView2 = (ImageView) viewHolder.getConvertView().findViewById(R.id.iv_cast);
                ImageUtils.setImgShowEP(mContext,url!=null?url:url_null,imageView2);
                break;
            case Moviedetail.CastsBean.LEFT:
                viewHolder.setText(R.id.tv_name,castsBean.getName()!=null?castsBean.getName():"");
                ImageView imageView3 = (ImageView) viewHolder.getConvertView().findViewById(R.id.iv_cast);
                ImageUtils.setImgShowEP(mContext,url!=null?url:url_null,imageView3);
                break;
            case Moviedetail.CastsBean.RIGHT:
                viewHolder.setText(R.id.tv_name,castsBean.getName()!=null?castsBean.getName():"");
                ImageView imageView4 = (ImageView) viewHolder.getConvertView().findViewById(R.id.iv_cast);
                ImageUtils.setImgShowEP(mContext,url!=null?url:url_null,imageView4);
                break;
        }


    }


}
