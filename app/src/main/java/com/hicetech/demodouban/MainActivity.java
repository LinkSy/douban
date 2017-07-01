package com.hicetech.demodouban;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.cleveroad.androidmanimation.LoadingAnimationView;
import com.hicetech.demodouban.Home.HomeActivity;
import com.hicetech.demodouban.commonality.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.animation)
    LoadingAnimationView animation;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private static int time = 5;
    private LoadingAnimationView mAnimation;
    Animation mAnimationTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mHandler.sendEmptyMessageDelayed(0,1000);
        mAnimationTV = AnimationUtils.loadAnimation(this, R.anim.cut_down);
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time = -1;
                openActivity(HomeActivity.class);
            }
        });
    }



    private int getCount(){
        time--;
        if (time==0){
            openActivity(HomeActivity.class);
        }

        return time;
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                tvTime.setText(getCount()+"秒");
                mHandler.sendEmptyMessageDelayed(0, 1000);
                mAnimationTV.reset();
                tvTime.startAnimation(mAnimationTV);
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        animation.startAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        animation.pauseAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        animation.stopAnimation();
    }
}
