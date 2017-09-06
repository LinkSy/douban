package com.hicetech.demodouban.unknown;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hicetech.demodouban.R;
import com.hicetech.demodouban.commonality.base.BaseFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/6/24.
 */

public class UnknownFragment extends BaseFragment {


    @BindView(R.id.rv_unknown)
    RecyclerView rvUnknown;
    Unbinder unbinder;
    private VoiceAdapter mVoiceAdapter;
    private List<Bean> data;
    private MediaPlayer mMediaPlayer ;
    private static String voiceUrl = "123";
    private String [] voiceUrls = {"http://o8r47jnnr.bkt.clouddn.com/o_1atqrbfmhdkv63o31hhia996s.mp3",
            "http://o8r47jnnr.bkt.clouddn.com/o_1b39ngt5984j55ficth1qqj11d.mp3"};
    @Override
    public int getLayout() {
        return R.layout.fragment_unknown;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        data = new ArrayList<>();

        mVoiceAdapter = new VoiceAdapter();
        rvUnknown.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUnknown.setAdapter(mVoiceAdapter);
        for (int i = 0; i <voiceUrls.length ; i++) {
            Bean bean = new Bean();
            bean.setTime("0s");
            bean.setVoiceAudio(voiceUrls[i]);
            data.add(bean);
        }
        mVoiceAdapter.setNewData(data);

        mVoiceAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.iv:

                        setMediaPlayer(data.get(position).getVoiceAudio());

                        break;

                }

            }
        });

    }

    private void setMediaPlayer(String path){
        //第一次进入时
        if (mMediaPlayer == null){
            mMediaPlayer = new MediaPlayer();
        }

        if (!voiceUrl.equals(path)){     //两次音频连接不同时
            voiceUrl = path;
            if (mMediaPlayer.isPlaying()){    //如果没暂停的话
                mMediaPlayer.pause();
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
                mMediaPlayer = new MediaPlayer();

            }else {//已经暂停的话
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
                mMediaPlayer = new MediaPlayer();
            }
            try {
                mMediaPlayer.setDataSource(voiceUrl);
                mMediaPlayer.prepareAsync();
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {

                        mMediaPlayer.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            if (mMediaPlayer.isPlaying()){
                mMediaPlayer.pause();
            }else {
                mMediaPlayer.start();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private class VoiceAdapter extends BaseQuickAdapter<Bean,BaseViewHolder>{

        public VoiceAdapter() {
            super(R.layout.item_unknown);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Bean bean) {
            viewHolder.addOnClickListener(R.id.iv);
        }
    }


    class Bean{
        private String voiceAudio;
        private String time;

        public String getVoiceAudio() {
            return voiceAudio;
        }

        public void setVoiceAudio(String voiceAudio) {
            this.voiceAudio = voiceAudio;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }


}
