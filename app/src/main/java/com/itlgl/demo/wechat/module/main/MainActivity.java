package com.itlgl.demo.wechat.module.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.itlgl.demo.wechat.R;
import com.itlgl.demo.wechat.module.main.fragment.ContactsFragment;
import com.itlgl.demo.wechat.module.main.fragment.DiscoverFragment;
import com.itlgl.demo.wechat.module.main.fragment.MeFragment;
import com.itlgl.demo.wechat.module.main.fragment.WechatFragment;
import com.itlgl.demo.wechat.module.main.view.WeChatRadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.radiogroup)
    WeChatRadioGroup wechatRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // test
        List<Fragment> list = new ArrayList<>(4);
        list.add(new WechatFragment());
        list.add(new ContactsFragment());
        list.add(new DiscoverFragment());
        list.add(new MeFragment());

        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), list));
        wechatRadioGroup.setViewPager(viewPager);
    }

    class MainPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> mData;

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public MainPagerAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            mData = data;
        }

        @Override
        public Fragment getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getCount() {
            return mData.size();
        }
    }
}
