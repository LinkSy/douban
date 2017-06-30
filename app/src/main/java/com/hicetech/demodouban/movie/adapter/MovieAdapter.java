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
import com.hicetech.demodouban.movie.module.Movie;

import static com.hicetech.demodouban.R.id.sdv_alt;


/**
 * Created by Administrator on 2017/6/26.
 */

public class MovieAdapter extends BaseQuickAdapter<Movie.SubjectsBean,BaseViewHolder> {
    private static final String TAG = MovieAdapter.class.getSimpleName();
    Context mContext;




    public MovieAdapter(Context context) {
        super(R.layout.item_movie);
        this.mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Movie.SubjectsBean subjectsBean) {



        viewHolder.setText(R.id.tv_title,subjectsBean.getTitle())
                .setText(R.id.tv_director,getDirector(subjectsBean))
                .setText(R.id.tv_cast,getActor(subjectsBean))
                .setText(R.id.tv_rating,subjectsBean.getRating().getAverage()+"")
                .setText(R.id.tv_ranking,viewHolder.getAdapterPosition()+1+"");

        String url = subjectsBean.getImages().getSmall();
        if (!TextUtils.isEmpty(url)){
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) viewHolder.getConvertView().findViewById(sdv_alt);
            ImageUtils.setImgShowEP(mContext,url,null,simpleDraweeView);
        }

        LinearLayout dot = (LinearLayout) viewHolder.getConvertView().findViewById(R.id.ll_dot);
        float rank = (float) subjectsBean.getRating().getAverage();
        getRank(dot,rank);



    }
    //获得导演名字
    public String getDirector(Movie.SubjectsBean subjectsBean){
        String director = "";
        for (int i = 0; i <subjectsBean.getDirectors().size(); i++) {
            director+=subjectsBean.getDirectors().get(i).getName()+" ";
        }
        return director;
    }
    //获得演员名字
    public String getActor(Movie.SubjectsBean subjectsBean){
        String actor = "";
        for (int i = 0; i <subjectsBean.getCasts().size() ; i++) {
            if (i!=(subjectsBean.getCasts().size()-1)){
                actor+=subjectsBean.getCasts().get(i).getName()+"/";
            }else {
                actor+=subjectsBean.getCasts().get(i).getName();
            }

        }
        return actor;
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
