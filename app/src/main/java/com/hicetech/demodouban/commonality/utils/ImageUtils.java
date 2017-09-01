package com.hicetech.demodouban.commonality.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hicetech.demodouban.R;


/**
 * Created by Administrator on 2017/6/26.
 */

public class ImageUtils {
    //设置普通图片

    //设置展位图和加载失败的图
    public static void setImgShowEP(Context context, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (imageView != null) {
            Glide.with(context).load(url)
                    .apply(new RequestOptions().error(R.drawable.ic_movie_default).placeholder(R.drawable.ic_movie_default))
                    .into(imageView);

        }
    }
}
