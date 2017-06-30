package com.hicetech.demodouban.movie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.ybq.android.spinkit.SpinKitView;
import com.hicetech.demodouban.R;
import com.hicetech.demodouban.commonality.utils.ImageUtils;
import com.hicetech.demodouban.movie.adapter.CastAdapter;
import com.hicetech.demodouban.movie.module.Moviedetail;
import com.hicetech.demodouban.network.NetWork;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;
import static com.hicetech.demodouban.R.id.tv_top;

/**
 * Created by Administrator on 2017/6/28.
 */

public class MoiveDetailActivity extends Activity {
    private static String movie_id;//电影ID
    private static String movie_top;//电影排名
    @BindView(tv_top)
    TextView tvTop;//排名
    @BindView(R.id.tv_movie_title)
    TextView tvMovieTitle;//电影名
    @BindView(R.id.iv_movie)
    ImageView ivMovie;//电影海报
    @BindView(R.id.tv_director)
    TextView tvDirector;//导演
    @BindView(R.id.tv_actor)
    TextView tvActor;//演员
    @BindView(R.id.tv_type)
    TextView tvType;//类型
    @BindView(R.id.tv_country)
    TextView tvCountry;//国家
    @BindView(R.id.tv_year)
    TextView tvYear;//年代
    @BindView(R.id.tv_aka)
    TextView tvAka;//又名
    @BindView(R.id.ll_dot)
    LinearLayout llDot;//星星
    @BindView(R.id.tv_ranking)
    TextView tvRanking;//评分
    @BindView(R.id.tv_summary)
    TextView tvSummary;//简介
    @BindView(R.id.rv_movie_man)
    RecyclerView rvMovieMan;//导演和演员
    private static ArrayList<Moviedetail.CastsBean> datas;
    private static CastAdapter mAdapter;
    @BindView(R.id.pb_movie)
    SpinKitView pbMovie;
    @BindView(R.id.ll_bg)
    LinearLayout llBg;
    @BindView(R.id.sv_movie_detail)
    ScrollView svMovieDetail;
    @BindView(R.id.ll_item)
    LinearLayout llItem;
    @BindView(R.id.ll_movie_detail)
    LinearLayout llMovieDetail;
    @BindView(R.id.ll_movie_title)
    LinearLayout llMovieTitle;
    @BindView(R.id.iv_movie_icon)
    ImageView ivMovieIcon;
    private int[] random_color;
    //不同类型
    private static final int NULL = 0;
    private static final int DOUBLE = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        //设置ImageView的动画
        AnimatedVectorDrawable mAnimatedVectorDrawable =  (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.douban_animator);
        ivMovieIcon.setImageDrawable(mAnimatedVectorDrawable);
        if(mAnimatedVectorDrawable!=null){
            mAnimatedVectorDrawable.start();
        }

        random_color = getResources().getIntArray(R.array.random_bg_color);
        getBGcolor();
        datas = new ArrayList<>();
        mAdapter = new CastAdapter(datas, getApplicationContext());
        rvMovieMan.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvMovieMan.setAdapter(mAdapter);
        movie_id = getIntent().getStringExtra("Movie_id");
        movie_top = getIntent().getStringExtra("Movie_top");
        if (movie_top==null){
            tvTop.setText("  ");
        }else {
            tvTop.setText("NO." + movie_top);
        }

        getHttpData();

    }


    //网络请求
    private void getHttpData() {
        Subscription subscription = NetWork.getDoubanApi()
                .getMovieDetail(movie_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Moviedetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toasty.error(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Moviedetail moviedetail) {
                        tvMovieTitle.setText(moviedetail.getTitle());
                        tvDirector.setText(moviedetail.getDirectors().get(0).getName());
                        tvActor.setText(getActor(moviedetail));
                        tvType.setText(getType(moviedetail));
                        tvCountry.setText(moviedetail.getCountries().get(0));
                        tvYear.setText(moviedetail.getYear());
                        tvAka.setText(getAka(moviedetail));
                        tvRanking.setText("  " + moviedetail.getRating().getAverage() + "");
                        String summary = moviedetail.getSummary();
                        tvSummary.setText("简介:" + "\n\u3000\u3000" + summary.replace("\n", "\n\u3000\u3000"));
                        //星星
                        getRank(llDot, (float) moviedetail.getRating().getAverage());
                        String url = moviedetail.getImages().getMedium();
                        ImageUtils.setImgShowEP(getApplicationContext(), url, ivMovie, null);
                        //设置适配器
                        mAdapter.setNewData(getCasts(moviedetail));
                        llBg.setVisibility(View.GONE);
                        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent = new Intent(MoiveDetailActivity.this,CelebrityActivity.class);
                                intent.putExtra("celebrity_id",datas.get(position).getId());
                                startActivity(intent);
                            }
                        });

                    }
                });
    }

    //将导演转换成演员
    public ArrayList getCasts(Moviedetail detail) {
        int size = detail.getDirectors().size();
        if (size == 1) {
            Moviedetail.CastsBean cast = new Moviedetail.CastsBean(DOUBLE);
            cast.setAlt(detail.getDirectors().get(0).getAlt());
            //设置图片
            Moviedetail.CastsBean.AvatarsBean avatar = new Moviedetail.CastsBean.AvatarsBean();
            //拿到图片
            avatar.setSmall(detail.getDirectors().get(0).getAvatars().getSmall());
            avatar.setMedium(detail.getDirectors().get(0).getAvatars().getMedium());
            avatar.setLarge(detail.getDirectors().get(0).getAvatars().getLarge());

            cast.setAvatars(avatar);
            cast.setId(detail.getDirectors().get(0).getId());
            cast.setName(detail.getDirectors().get(0).getName());
            datas.add(cast);
        } else if (size == 2) {
            Moviedetail.CastsBean cast = new Moviedetail.CastsBean(LEFT);
            cast.setAlt(detail.getDirectors().get(0).getAlt());
            //设置图片
            Moviedetail.CastsBean.AvatarsBean avatar = new Moviedetail.CastsBean.AvatarsBean();
            //拿到图片
            avatar.setSmall(detail.getDirectors().get(0).getAvatars().getSmall());
            avatar.setMedium(detail.getDirectors().get(0).getAvatars().getMedium());
            avatar.setLarge(detail.getDirectors().get(0).getAvatars().getLarge());

            cast.setAvatars(avatar);
            cast.setId(detail.getDirectors().get(0).getId());
            cast.setName(detail.getDirectors().get(0).getName());
            datas.add(cast);
            //----------------------------------------------------------
            Moviedetail.CastsBean cast2 = new Moviedetail.CastsBean(RIGHT);
            cast2.setAlt(detail.getDirectors().get(1).getAlt());
            //设置图片
            Moviedetail.CastsBean.AvatarsBean avatar2 = new Moviedetail.CastsBean.AvatarsBean();
            //拿到图片
            avatar2.setSmall(detail.getDirectors().get(1).getAvatars().getSmall());
            avatar2.setMedium(detail.getDirectors().get(1).getAvatars().getMedium());
            avatar2.setLarge(detail.getDirectors().get(1).getAvatars().getLarge());

            cast2.setAvatars(avatar2);
            cast2.setId(detail.getDirectors().get(1).getId());
            cast2.setName(detail.getDirectors().get(1).getName());
            datas.add(cast2);


        } else {
            for (int i = 0; i < size; i++) {
                Moviedetail.CastsBean cast;
                if (i == 0) {
                    cast = new Moviedetail.CastsBean(LEFT);
                } else if (i == (size - 1)) {
                    cast = new Moviedetail.CastsBean(RIGHT);
                } else {
                    cast = new Moviedetail.CastsBean(NULL);
                }
                cast.setAlt(detail.getDirectors().get(i).getAlt());
                //设置图片
                Moviedetail.CastsBean.AvatarsBean avatar = new Moviedetail.CastsBean.AvatarsBean();
                //拿到图片
                avatar.setSmall(detail.getDirectors().get(i).getAvatars().getSmall());
                avatar.setMedium(detail.getDirectors().get(i).getAvatars().getMedium());
                avatar.setLarge(detail.getDirectors().get(i).getAvatars().getLarge());

                cast.setAvatars(avatar);
                cast.setId(detail.getDirectors().get(i).getId());
                cast.setName(detail.getDirectors().get(i).getName());
                datas.add(cast);


            }

        }

        for (int i = 0; i < detail.getCasts().size(); i++) {
            Moviedetail.CastsBean castsBean = detail.getCasts().get(i);
            castsBean.setTypeItem(NULL);
            datas.add(castsBean);
        }

        return datas;
    }

    //获得演员名字
    public String getActor(Moviedetail subjectsBean) {
        String actor = "";
        for (int i = 0; i < subjectsBean.getCasts().size(); i++) {
            if (i != (subjectsBean.getCasts().size() - 1)) {
                actor += subjectsBean.getCasts().get(i).getName() + "/";
            } else {
                actor += subjectsBean.getCasts().get(i).getName();
            }

        }
        return actor;
    }

    //获得电影类型
    public String getType(Moviedetail detail) {
        String type = "";
        for (int i = 0; i < detail.getGenres().size(); i++) {
            type += detail.getGenres().get(i) + " ";
        }
        return type;
    }

    //获得又名
    public String getAka(Moviedetail detail) {
        String aka = "";
        for (int i = 0; i < detail.getAka().size(); i++) {
            if (i != (detail.getAka().size() - 1)) {
                aka += detail.getAka().get(i) + "/";
            } else {
                aka += detail.getAka().get(i);
            }
        }
        return aka;
    }

    //获得评分
    public void getRank(LinearLayout dot, float rank) {
        if (dot.getChildCount() != 0) {
            Log.d(TAG, dot.getChildCount() + "");
            dot.removeAllViews();
        }
        for (int i = 0; i < 5; i++) {
            //设置评分间距
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) dot.getLayoutParams();
            lp.setMargins(5, 4, 0, 0);
            //根据评分设置星
            if (rank >= 9.5) {
                ImageView star = new ImageView(getApplicationContext());
                star.setImageResource(R.drawable.ic_star);

                dot.addView(star, lp);
            } else if (rank < 9.5 && rank >= 9.0) {
                if (i == 4) {
                    ImageView half = new ImageView(getApplicationContext());

                    half.setImageResource(R.drawable.ic_star_half);
                    dot.addView(half, lp);
                } else {
                    ImageView star = new ImageView(getApplicationContext());
                    star.setImageResource(R.drawable.ic_star);
                    dot.addView(star, lp);
                }
            } else if (rank < 9.0 && rank >= 8.5) {
                if (i == 4) {
                    ImageView none = new ImageView(getApplicationContext());
                    none.setImageResource(R.drawable.ic_star_none);
                    dot.addView(none, lp);
                } else {
                    ImageView star = new ImageView(getApplicationContext());
                    star.setImageResource(R.drawable.ic_star);
                    dot.addView(star, lp);
                }
            } else {
                if (i == 4) {
                    ImageView none = new ImageView(getApplicationContext());
                    none.setImageResource(R.drawable.ic_star_none);
                    dot.addView(none, lp);
                } else if (i == 3) {
                    ImageView half = new ImageView(getApplicationContext());
                    half.setImageResource(R.drawable.ic_star_half);
                    dot.addView(half, lp);
                } else {
                    ImageView star = new ImageView(getApplicationContext());
                    star.setImageResource(R.drawable.ic_star);
                    dot.addView(star, lp);
                }
            }
        }
    }

    //随机背景颜色
    public void getBGcolor() {
        Random random = new Random();
        int color = random.nextInt(5);
        svMovieDetail.setBackgroundColor(random_color[color]);
        llMovieTitle.setBackgroundColor(random_color[color]);
    }

}
