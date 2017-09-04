package com.hicetech.demodouban.home;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hicetech.demodouban.R;
import com.hicetech.demodouban.commonality.utils.SystemStatusManager;
import com.hicetech.demodouban.commonality.view.HomeTabView;
import com.hicetech.demodouban.commonality.view.NoScrollViewPager;
import com.hicetech.demodouban.forum.ForumFragment;
import com.hicetech.demodouban.mine.MineFragment;
import com.hicetech.demodouban.movie.MovieFragment;
import com.hicetech.demodouban.weather.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/23.
 */

public class HomeActivity extends FragmentActivity implements HomeTabView.HomeClick {


    @BindView(R.id.vp_home)
    NoScrollViewPager vpHome;
    @BindView(R.id.home_tab)
    HomeTabView mHomeTabView;
    private MyAdapter mMyAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//标题栏就没有了。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemStatusManager tintManager = new SystemStatusManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.movie_bg_color);//通知栏所需颜色
        }
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        //初始化 Fragment
        vpHome.setIsCanScroll(false);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MovieFragment());
        fragments.add(new WeatherFragment());
        fragments.add(new ForumFragment());
        fragments.add(new MineFragment());
        mMyAdapter = new MyAdapter(getSupportFragmentManager(),fragments);
        vpHome.setAdapter(mMyAdapter);
        vpHome.setOffscreenPageLimit(4);//默认加载4个页面
        mHomeTabView.setOnHomeClick(this,0);




    }
    @Override
    public void onHomeClick(View view) {
        switch (view.getId()){
            case R.id.rl_movie:
                mHomeTabView.setSelectTab(0);
                vpHome.setCurrentItem(0,false);
                break;
            case R.id.rl_weather:
                mHomeTabView.setSelectTab(1);
                vpHome.setCurrentItem(1,false);
                break;
            case R.id.rl_x:
                mHomeTabView.setSelectTab(2);
                vpHome.setCurrentItem(2,false);
                break;
            case R.id.rl_mine:
                mHomeTabView.setSelectTab(3);
                vpHome.setCurrentItem(3,false);
                break;
        }


    }

    //禁用返回键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    class MyAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;
        public MyAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list=list;
        }

        @Override
        public Fragment getItem(int position) {

            return list.get(position);
        }

        @Override
        public int getCount() {

            return list.size();
        }
    }

}
