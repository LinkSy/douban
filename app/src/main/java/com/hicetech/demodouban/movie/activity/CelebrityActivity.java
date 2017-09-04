package com.hicetech.demodouban.movie.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hicetech.demodouban.R;
import com.hicetech.demodouban.commonality.base.BaseActivityTitle;
import com.hicetech.demodouban.commonality.utils.ImageUtils;
import com.hicetech.demodouban.commonality.utils.PopupWindowUtility;
import com.hicetech.demodouban.movie.adapter.CelebrityAdapter;
import com.hicetech.demodouban.movie.module.Celebrity;
import com.hicetech.demodouban.network.NetWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Link on 2017/6/30.
 * 演员,导演详情
 */

public class CelebrityActivity extends BaseActivityTitle {
    private static final String TAG = CelebrityActivity.class.getSimpleName();
    private static String celebrity_id = ""; //名人id

    ImageView ivCelebrity;

    TextView tvCelebrityNameEn;

    TextView tvCelebrityNameAka;

    TextView tvGender;

    TextView tvCelebrityBornPlace;

    @BindView(R.id.rv_celebrity)
    RecyclerView rvCelebrity;

    @BindView(R.id.ll_movie_detail)
    LinearLayout llMovieDetail;

    CelebrityAdapter mAdapter;

    private Dialog mDialog;

    private int[] random_color;
    List<Integer> list ;

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mDialog = PopupWindowUtility.loading(this);
        celebrity_id = getIntent().getStringExtra("celebrity_id");
        mAdapter = new CelebrityAdapter();
        mAdapter.addHeaderView(HeadView());
        rvCelebrity.setLayoutManager(new LinearLayoutManager(this));
        random_color = getResources().getIntArray(R.array.random_bg_color);
        setLeftIMG(R.drawable.ic_celebrity);
        getBGColor();

        getHttpData();
    }

    @Override
    public int setView() {
        return R.layout.activity_celebrity;
    }

    public View HeadView(){
        View view = View.inflate(this,R.layout.actor_director_details,null);
        tvCelebrityNameEn = (TextView) view.findViewById(R.id.tv_celebrity_name_en);
        tvCelebrityNameAka = (TextView) view.findViewById(R.id.tv_celebrity_name_aka);
        ivCelebrity = (ImageView) view.findViewById(R.id.iv_celebrity);
        tvGender = (TextView) view.findViewById(R.id.tv_gender);
        tvCelebrityBornPlace = (TextView) view.findViewById(R.id.tv_celebrity_born_place);
        return view;
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
                        setLeftText(celebrity.getName());
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


                        mAdapter.setNewData(celebrity.getWorks());
                        rvCelebrity.setAdapter(mAdapter);
                        mDialog.dismiss();

                        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
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
    public void getBGColor() {
        Random random = new Random();
        int color = random.nextInt(5);
        list = new ArrayList<>();
        llMovieDetail.setBackgroundColor(random_color[color]);

        list.add(R.color.random_bg_color);
        list.add(R.color.random_bg_color2);
        list.add(R.color.random_bg_color3);
        list.add(R.color.random_bg_color4);
        list.add(R.color.random_bg_color5);
        upThemeStyle(list.get(color));


    }
}
