package com.hicetech.demodouban.movie;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hicetech.demodouban.R;
import com.hicetech.demodouban.commonality.base.BaseFragment;
import com.hicetech.demodouban.movie.fragment.CategoryFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/6/24.
 */

public class MovieFragment extends BaseFragment {
    private static final String TAG = MovieFragment.class.getSimpleName();
    Unbinder unbinder;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.vp_movie)
    ViewPager vpMovie;
    List<String> mTitleDataList;
    String [] subject = null;
    String [] titile = null;
    CommonViewPagerAdapter mAdapter;

    CategoryFragment InTheatersFragment;
    CategoryFragment ComingSoonFragment;
    CategoryFragment Top250Fragment;
    CategoryFragment WeeklyFragment;
    CategoryFragment UsBoxFragment;
    CategoryFragment NewMoviesFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subject = getResources().getStringArray(R.array.movie_subject);
        titile =  getResources().getStringArray(R.array.movie_title);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        mAdapter = new CommonViewPagerAdapter(getFragmentManager());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        mTitleDataList = new ArrayList<>();
        for (int i = 0; i <titile.length ; i++) {
            mTitleDataList.add(titile[i]);
        }
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setText(mTitleDataList.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vpMovie.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);

        InTheatersFragment = new CategoryFragment().newInstance(subject[0]);
        ComingSoonFragment = new CategoryFragment().newInstance(subject[1]);
        Top250Fragment = new CategoryFragment().newInstance(subject[2]);
        WeeklyFragment = new CategoryFragment().newInstance(subject[3]);
        UsBoxFragment = new CategoryFragment().newInstance(subject[4]);
        NewMoviesFragment = new CategoryFragment().newInstance(subject[5]);

        mAdapter.addFragment(InTheatersFragment);
        mAdapter.addFragment(ComingSoonFragment);
        mAdapter.addFragment(Top250Fragment);
        mAdapter.addFragment(WeeklyFragment);
        mAdapter.addFragment(UsBoxFragment);
        mAdapter.addFragment(NewMoviesFragment);

        vpMovie.setAdapter(mAdapter);


        ViewPagerHelper.bind(magicIndicator, vpMovie);


    }


    public void initEvent() {

    }


    @Override
    public int getLayout() {
        return R.layout.fragment_movie;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class CommonViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments = new ArrayList<>();

        public CommonViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }



    }


}
