package com.itlgl.demo.wechat.module.main.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.itlgl.demo.wechat.R;
import com.itlgl.demo.wechat.module.setting.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    @BindView(R.id.wallet)
    View walletView;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);

        Log.i("test", "walletView=" + walletView);
        walletView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("test", "钱包 click");
                Toast.makeText(getContext(), "钱包", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

//    @SuppressWarnings("unused")
//    @OnClick(R.id.wallet)
//    void walletClick(View v) {
//        Toast.makeText(getContext(), "钱包", Toast.LENGTH_SHORT).show();
//    }

    @SuppressWarnings("unused")
    @OnClick(R.id.favorites)
    void favoritesClick(View v) {
        Toast.makeText(getContext(), "收藏", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.my_posts)
    void myPostsClick(View v) {
        Toast.makeText(getContext(), "相册", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.sticker_gallery)
    void stickerGalleryClick(View v) {
        Toast.makeText(getContext(), "表情", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.setting)
    void settingClick(View v) {
        startActivity(new Intent(getContext(), SettingActivity.class));
    }
}
