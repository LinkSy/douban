package com.hicetech.demodouban.commonality.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hicetech.demodouban.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2017/6/26.
 */

public class ImageUtils {
    //设置普通图片
    public static void setImgShow(Context context, String url, ImageView imageView, SimpleDraweeView simpleDraweeView){
        if(TextUtils.isEmpty(url)){
            return;
        }if(imageView!=null){
            Picasso.with(context).load(url).into(imageView);
        } else {
            simpleDraweeView.setImageURI(url);
        }
    }
    //设置展位图和加载失败的图
    public static void setImgShowEP(Context context, String url, ImageView imageView, SimpleDraweeView simpleDraweeView){
        if(TextUtils.isEmpty(url)){
            return;
        }if(imageView!=null){
            Picasso.with(context).load(url).
                    placeholder(R.drawable.ic_movie_default).
                    error(R.drawable.ic_movie_default).
                    into(imageView);
        } else {
            simpleDraweeView.setImageURI(url);
            GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
            hierarchy.setPlaceholderImage(R.drawable.ic_movie_default);
            hierarchy.setFailureImage(R.drawable.ic_movie_default);
            hierarchy.setProgressBarImage(new ProgressBarDrawable());

        }
    }

}
