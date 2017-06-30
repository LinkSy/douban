package com.hicetech.demodouban.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.ybq.android.spinkit.SpinKitView;
import com.hicetech.demodouban.R;
import com.hicetech.demodouban.commonality.base.BaseFragment;
import com.hicetech.demodouban.movie.activity.MoiveDetailActivity;
import com.hicetech.demodouban.movie.adapter.MovieAdapter;
import com.hicetech.demodouban.movie.module.Movie;
import com.hicetech.demodouban.network.NetWork;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/24.
 */

public class MovieFragment extends BaseFragment {
    private static final String TAG = MovieFragment.class.getSimpleName();
    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    Unbinder unbinder;
    MovieAdapter mAdapter;
    private static int start = 0;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    @BindView(R.id.pb_movie)
    SpinKitView pbMovie;
    List<Movie.SubjectsBean> datas ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        datas = new ArrayList<>();
        mAdapter = new MovieAdapter(getContext());
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getHttpData(false);
        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                start++;
                getHttpData(false);
                easylayout.loadMoreComplete();
            }

            @Override
            public void onRefreshing() {
                start = 0;
                getHttpData(true);
            }
        });

    }


    //获得数据
    private void getHttpData(final boolean refresh) {
        Subscription subscription = NetWork.getDoubanApi()
                .getMovie(10 * start, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        toast("请求失败", "error");
                    }

                    @Override
                    public void onNext(final Movie movie) {
                        if ((start == 0) && (refresh == false)) {
                            toast("请求成功", "success");
                        }
                        if (refresh) {
                            datas.clear();
                            datas.addAll(movie.getSubjects());
                            //Log.d(TAG,"datas.size()"+datas.size()+" refresh");
                            mAdapter.setNewData(datas);
                            easylayout.refreshComplete();

                        } else {
                            for (int i = 0; i <movie.getSubjects().size() ; i++) {
                                datas.add(movie.getSubjects().get(i));
                                //Log.d(TAG,"电影名: "+movie.getSubjects().get(i).getTitle()+" loadmore "+start);
                            }//第一次进入时
                            if (start==0){
                                mAdapter.setNewData(datas);
                            }//下拉加载时
                            else {
                                mAdapter.notifyDataSetChanged();
                            }
                            pbMovie.setVisibility(View.GONE);
                            //Log.d(TAG,"datas.size()"+datas.size()+" loadmore");


                        }
                        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                //EventBus.getDefault().post(new MoiveDetailActivity());
                                int index = position%10 ;
                                Log.d(TAG,index+"");
                                Bundle bundle = new Bundle();
                                bundle.putString("Movie_id",datas.get(position).getId());
                                bundle.putString("Movie_top",position+"");
                                openActivity(MoiveDetailActivity.class,bundle);

                            }
                        });
                    }



                });

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

}
