package com.hicetech.demodouban.movie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hicetech.demodouban.R;
import com.hicetech.demodouban.commonality.utils.ImageUtils;
import com.hicetech.demodouban.movie.adapter.CelebrityAdapter;
import com.hicetech.demodouban.movie.module.Celebrity;
import com.hicetech.demodouban.network.NetWork;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/30.
 */

public class CelebrityActivity extends Activity {
    private static final String TAG = CelebrityActivity.class.getSimpleName();
    private static String celebrity_id = ""; //名人id
    @BindView(R.id.tv_celebrity_name)
    TextView tvCelebrityName;
    @BindView(R.id.iv_movie_icon)
    ImageView ivMovieIcon;
    @BindView(R.id.ll_movie_title)
    LinearLayout llMovieTitle;
    @BindView(R.id.iv_celebrity)
    ImageView ivCelebrity;
    @BindView(R.id.tv_celebrity_name_en)
    TextView tvCelebrityNameEn;
    @BindView(R.id.tv_celebrity_name_aka)
    TextView tvCelebrityNameAka;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_celebrity_born_place)
    TextView tvCelebrityBornPlace;
    @BindView(R.id.ll_item)
    LinearLayout llItem;
    @BindView(R.id.rv_celebrity)
    RecyclerView rvCelebrity;
    @BindView(R.id.ll_movie_detail)
    LinearLayout llMovieDetail;
    @BindView(R.id.cv_celebrity)
    CardView cvCelebrity;

    CelebrityAdapter mAapter;
    @BindView(R.id.sv_movie_celebrity)
    NestedScrollView svMovieCelebrity;
    @BindView(R.id.ll_bg)
    LinearLayout llBg;
    private int[] random_color;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_celebrity);
        ButterKnife.bind(this);
        celebrity_id = getIntent().getStringExtra("celebrity_id");
        mAapter = new CelebrityAdapter();
        rvCelebrity.setLayoutManager(new LinearLayoutManager(this));
        random_color = getResources().getIntArray(R.array.random_bg_color);
        getBGcolor();

        getHttpData();

    }

    //网络请求
    private void getHttpData() {
        Subscription subscription = NetWork.getDoubanApi()
                .getCelebrity(celebrity_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Celebrity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toasty.error(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(final Celebrity celebrity) {
                        Log.d(TAG, celebrity.getName());
                        tvCelebrityName.setText(celebrity.getName());
                        tvCelebrityNameEn.setText(celebrity.getName_en());
                        //获得又名
                        String aka_en = "";
                        for (int i = 0; i < celebrity.getAka_en().size(); i++) {
                            if (i != (celebrity.getAka_en().size() - 1)) {
                                aka_en += celebrity.getAka_en().get(i) + "/";
                            } else {
                                aka_en += celebrity.getAka_en().get(i);
                            }
                        }
                        tvCelebrityNameAka.setText(aka_en);

                        tvGender.setText(celebrity.getGender());
                        tvCelebrityBornPlace.setText(celebrity.getBorn_place());
                        ImageUtils.setImgShowEP(getApplicationContext(), celebrity.getAvatars().getMedium(), ivCelebrity);


                        mAapter.setNewData(celebrity.getWorks());
                        rvCelebrity.setAdapter(mAapter);
                        llBg.setVisibility(View.GONE);

                        mAapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent = new Intent(CelebrityActivity.this, MovieDetailActivity.class);
                                intent.putExtra("Movie_id", celebrity.getWorks().get(position).getSubject().getId());
                                startActivity(intent);
                            }
                        });
                    }
                });


    }

    //随机背景颜色
    public void getBGcolor() {
        Random random = new Random();
        int color = random.nextInt(5);
        svMovieCelebrity.setBackgroundColor(random_color[color]);
        llMovieTitle.setBackgroundColor(random_color[color]);
    }
}
