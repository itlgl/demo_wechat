package com.itlgl.demo.wechat.module.main.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.itlgl.demo.wechat.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_moments)
    void btnMomentsClick(View v) {
        Toast.makeText(getContext(), R.string.moments, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_scan)
    void btnScanClick(View v) {
        Toast.makeText(getContext(), R.string.scan_qr_code, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_shake)
    void btnShakeClick(View v) {
        Toast.makeText(getContext(), R.string.shake, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_people_nearby)
    void btnPeopleNearbyClick(View v) {
        Toast.makeText(getContext(), R.string.people_nearby, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_games)
    void btnGamesClick(View v) {
        Toast.makeText(getContext(), R.string.games, Toast.LENGTH_SHORT).show();
    }
}
