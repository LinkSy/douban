package com.hicetech.demodouban.home;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.hicetech.demodouban.R;
import com.hicetech.demodouban.commonality.utils.SystemStatusManager;
import com.hicetech.demodouban.commonality.view.NoScrollViewPager;
import com.hicetech.demodouban.forum.ForumFragment;
import com.hicetech.demodouban.mine.MineFragment;
import com.hicetech.demodouban.movie.MovieFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.long1.spacetablayout.SpaceTabLayout;

/**
 * Created by Administrator on 2017/6/23.
 */

public class HomeActivity extends FragmentActivity {


    @BindView(R.id.vp_home)
    NoScrollViewPager vpHome;
    @BindView(R.id.spaceTabLayout)
    SpaceTabLayout spaceTabLayout;

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
        fragments.add(new ForumFragment());
        fragments.add(new MineFragment());
        vpHome.setOffscreenPageLimit(3);//默认加载3个页面
        spaceTabLayout.initialize(vpHome,getSupportFragmentManager()
                ,fragments,savedInstanceState);



    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        spaceTabLayout.saveState(outState);
        super.onSaveInstanceState(outState);
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
}
