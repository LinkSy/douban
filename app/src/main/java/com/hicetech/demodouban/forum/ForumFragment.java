package com.hicetech.demodouban.forum;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hicetech.demodouban.R;
import com.hicetech.demodouban.commonality.base.BaseFragment;
import com.hicetech.demodouban.commonality.view.CustomView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/6/24.
 */

public class ForumFragment extends BaseFragment {


    @BindView(R.id.custom)
    CustomView custom;
    Unbinder unbinder;

    @Override
    public int getLayout() {
        return R.layout.fragment_forum;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<PieData> pieDatas = new ArrayList<>();
        for (int i = 0; i <6 ; i++) {
            PieData pie = new PieData(i+"",i+1);
            pieDatas.add(pie);
        }
        custom.setData(pieDatas);
        custom.setStartAngle(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
