package com.hicetech.demodouban.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hicetech.demodouban.R;
import com.hicetech.demodouban.commonality.base.BaseFragment;
import com.hicetech.demodouban.movie.fragment.CategoryFragment;

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
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.vp_movie)
    ViewPager vpMovie;
    List<String> mTitleDataList;
    String [] subject ;
    String [] title ;
    CommonViewPagerAdapter mAdapter;

    CategoryFragment InTheatersFragment;
    CategoryFragment ComingSoonFragment;
    CategoryFragment Top250Fragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subject = getResources().getStringArray(R.array.movie_subject);
        title =  getResources().getStringArray(R.array.movie_title);

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
        for (int i = 0; i < title.length ; i++) {
            mTitleDataList.add(title[i]);
        }



        InTheatersFragment = new CategoryFragment().newInstance(subject[0]);
        ComingSoonFragment = new CategoryFragment().newInstance(subject[1]);
        Top250Fragment = new CategoryFragment().newInstance(subject[2]);


        mAdapter.addFragment(InTheatersFragment,title[0]);
        mAdapter.addFragment(ComingSoonFragment,title[1]);
        mAdapter.addFragment(Top250Fragment,title[2]);


        vpMovie.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(vpMovie);




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

    public class CommonViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments = new ArrayList<>();
        private List <String> titles = new ArrayList<>();
        public CommonViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        public void addFragment(Fragment fragment,String title) {
            mFragments.add(fragment);
            titles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

    }


}
