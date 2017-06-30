package com.hicetech.demodouban.movie.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hicetech.demodouban.R;
import com.hicetech.demodouban.commonality.utils.ImageUtils;
import com.hicetech.demodouban.movie.module.Celebrity;

import static com.hicetech.demodouban.R.id.sdv_alt;

/**
 * Created by Administrator on 2017/6/30.
 */

public class CelebrityAdapter extends BaseQuickAdapter<Celebrity.WorksBean,BaseViewHolder> {
    Context mContext;

    public CelebrityAdapter(Context context) {
        super(R.layout.item_celebrity);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Celebrity.WorksBean work) {
        viewHolder.setText(R.id.tv_title,work.getSubject().getTitle()+"("+work.getSubject().getOriginal_title()+")")
                .setText(R.id.tv_genres,getGenres(work))
                .setText(R.id.tv_role,getRoles(work))
                .setText(R.id.tv_rating,work.getSubject().getRating().getAverage()+"")
                .setText(R.id.tv_collection_count,work.getSubject().getCollect_count()+"");
        String url = work.getSubject().getImages().getSmall();
        if (!TextUtils.isEmpty(url)){
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) viewHolder.getConvertView().findViewById(sdv_alt);
            ImageUtils.setImgShowEP(mContext,url,null,simpleDraweeView);
        }

        LinearLayout dot = (LinearLayout) viewHolder.getConvertView().findViewById(R.id.ll_dot);
        float rank = (float) work.getSubject().getRating().getAverage();
        getRank(dot,rank);

    }
    //获得类型
    public String getGenres(Celebrity.WorksBean worksBean){
        String genres = "";
        for (int i = 0; i <worksBean.getSubject().getGenres().size(); i++) {
            if (i!=(worksBean.getSubject().getGenres().size()-1)){
                genres+=worksBean.getSubject().getGenres().get(i)+"/";
            }else {
                genres+=worksBean.getSubject().getGenres().get(i);
            }
        }
        return genres;
    }
    //获得职责
    public String getRoles(Celebrity.WorksBean worksBean){
        String roles = "";
        int size = worksBean.getRoles().size();

            for (int i = 0; i <size; i++) {
                if (i!=(size-1)){
                    roles+=worksBean.getRoles().get(i)+"/";
                }else {
                    roles+=worksBean.getRoles().get(i);
                }
        }

        return roles;
    }
    //获得评分
    public void getRank(LinearLayout dot,float rank){
        if (dot.getChildCount()!=0){
            Log.d(TAG,dot.getChildCount()+"");
            dot.removeAllViews();
        }
        for (int i = 0; i <5 ; i++) {
            //设置评分间距
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) dot.getLayoutParams();
            lp.setMargins(5,4,0,0);
            //根据评分设置星
            if (rank>=9.5){
                ImageView star = new ImageView(mContext);
                star.setImageResource(R.drawable.ic_star);

                dot.addView(star,lp);
            }else if(rank<9.5&&rank>=9.0){
                if (i==4){
                    ImageView half = new ImageView(mContext);

                    half.setImageResource(R.drawable.ic_star_half);
                    dot.addView(half,lp);
                }else {
                    ImageView star = new ImageView(mContext);
                    star.setImageResource(R.drawable.ic_star);
                    dot.addView(star,lp);
                }
            }else if (rank<9.0&&rank>=8.5){
                if (i==4){
                    ImageView none = new ImageView(mContext);
                    none.setImageResource(R.drawable.ic_star_none);
                    dot.addView(none,lp);
                }else {
                    ImageView star = new ImageView(mContext);
                    star.setImageResource(R.drawable.ic_star);
                    dot.addView(star,lp);
                }
            }else {
                if (i==4){
                    ImageView none = new ImageView(mContext);
                    none.setImageResource(R.drawable.ic_star_none);
                    dot.addView(none,lp);
                }else if (i==3){
                    ImageView half = new ImageView(mContext);
                    half.setImageResource(R.drawable.ic_star_half);
                    dot.addView(half,lp);
                }else {
                    ImageView star = new ImageView(mContext);
                    star.setImageResource(R.drawable.ic_star);
                    dot.addView(star,lp);
                }
            }
        }
    }

}
