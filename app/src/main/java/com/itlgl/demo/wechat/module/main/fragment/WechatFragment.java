package com.itlgl.demo.wechat.module.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itlgl.demo.wechat.R;
import com.itlgl.demo.wechat.module.main.adapter.RecentlyChatAdapter;
import com.itlgl.demo.wechat.module.main.adapter.RecentlyChatBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * WechatFragment
 */
public class WechatFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private RecentlyChatAdapter recentlyChatAdapter;
    private List<RecentlyChatBean> chatDatas = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recentlyChatAdapter = new RecentlyChatAdapter(getContext(), chatDatas);
        recyclerView.setAdapter(recentlyChatAdapter);
    }

}
