package com.hicetech.demodouban.commonality.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hicetech.demodouban.R;

/**
 * Created by Link on 2017/9/4.
 * 底部导航
 */

public class HomeTabView extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private RelativeLayout mRlMovie,mRlWeather,mRlX,mRlMine;
    private TextView mTvMovie,mTvWeather,mTvX,mTvMine;
    public HomeClick homeClick;
    public HomeTabView(Context context) {
        super(context);
        mContext = context;
        initView();
    }
    public HomeTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }
    public HomeTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    @Override
    public void onClick(View v) {
        homeClick.onHomeClick(v);
    }

    public interface HomeClick{
        void onHomeClick(View view);
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext.getApplicationContext());
        LinearLayout home = (LinearLayout) inflater.inflate(R.layout.home_tab_view,null);
        mRlMovie = (RelativeLayout) home.findViewById(R.id.rl_movie);
        mRlWeather = (RelativeLayout) home.findViewById(R.id.rl_weather);
        mRlX = (RelativeLayout) home.findViewById(R.id.rl_x);
        mRlMine = (RelativeLayout) home.findViewById(R.id.rl_mine);

        mTvMovie = (TextView) home.findViewById(R.id.tv_tab_movie);
        mTvWeather = (TextView) home.findViewById(R.id.tv_tab_weather);
        mTvX = (TextView) home.findViewById(R.id.tv_tab_x);
        mTvMine = (TextView) home.findViewById(R.id.tv_tab_mine);
        addView(home, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

    }
    public void setSelectTab(int i){
        setImageBackground();
        switch(i){
            case  0:
                mTvMovie.setSelected(true);
                mTvMovie.setTextColor(Color.parseColor("#FF82AC"));
                break;
            case 1:
                mTvWeather.setSelected(true);
                mTvWeather.setTextColor(Color.parseColor("#FF82AC"));
                break;
            case 2:
                mTvX.setSelected(true);
                mTvX.setTextColor(Color.parseColor("#FF82AC"));
                break;
            case 3:
                mTvMine.setSelected(true);
                mTvMine.setTextColor(Color.parseColor("#FF82AC"));
                break;
            default:
                break;
        }
    }

    public void setOnHomeClick(HomeClick click,int currentItem){

        this.homeClick= click;
        setSelectTab(currentItem);
        mRlMovie.setOnClickListener(this);
        mRlWeather.setOnClickListener(this);
        mRlX.setOnClickListener(this);
        mRlMine.setOnClickListener(this);


    }

    private void setImageBackground(){
        mTvMovie.setSelected(false);
        mTvWeather.setSelected(false);
        mTvX.setSelected(false);
        mTvMine.setSelected(false);

        mTvMovie.setTextColor(Color.parseColor("#A2A2A2"));
        mTvWeather.setTextColor(Color.parseColor("#A2A2A2"));
        mTvX.setTextColor(Color.parseColor("#A2A2A2"));
        mTvMine.setTextColor(Color.parseColor("#A2A2A2"));

    }
}
