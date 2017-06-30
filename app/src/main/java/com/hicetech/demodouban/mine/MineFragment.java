package com.hicetech.demodouban.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.hicetech.demodouban.R;
import com.hicetech.demodouban.commonality.utils.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/6/24.
 */

public class MineFragment extends Fragment {


    @BindView(R.id.sdv)
    SimpleDraweeView sdv;
    Unbinder unbinder;
    String url = "http://pic17.nipic.com/20111102/8732394_164338399113_2.jpg";
    @BindView(R.id.pb_movie)
    ProgressBar pbMovie;
    @BindView(R.id.spin_kit)
    SpinKitView spinKit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageUtils.setImgShowEP(getContext(), url, null, sdv);
        spinKit.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
