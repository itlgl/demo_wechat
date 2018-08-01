package com.itlgl.demo.wechat.module.main;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.itlgl.demo.wechat.R;
import com.itlgl.demo.wechat.module.main.fragment.ContactsFragment;
import com.itlgl.demo.wechat.module.main.fragment.DiscoverFragment;
import com.itlgl.demo.wechat.module.main.fragment.MeFragment;
import com.itlgl.demo.wechat.module.main.fragment.WechatFragment;
import com.itlgl.demo.wechat.module.main.view.WeChatRadioGroup;
import com.itlgl.demo.wechat.utils.DipUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.radiogroup)
    WeChatRadioGroup wechatRadioGroup;
    @BindView(R.id.menu_more)
    View menu_more;
    @BindView(R.id.title)
    View title;

    private MenuListener menuListener = new MenuListener();
    private PopupWindow menuPopupWindow;
//    private Animation menuShowAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        List<Fragment> list = new ArrayList<>(4);
        list.add(new WechatFragment());
        list.add(new ContactsFragment());
        list.add(new DiscoverFragment());
        list.add(new MeFragment());

        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), list));
        viewPager.setOffscreenPageLimit(3);
        wechatRadioGroup.setViewPager(viewPager);

//        menuShowAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_menu);
//        menuShowAnimation.setFillAfter(false);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.menu_more)
    void menuMoreClick(View v) {
        showMenu();
    }

    private void showMenu() {
        if(menuPopupWindow == null) {
            View menuView = LayoutInflater.from(this).inflate(R.layout.view_menu, null);
            ButterKnife.bind(menuListener, menuView);
            menuPopupWindow = new PopupWindow(this);
            menuPopupWindow.setContentView(menuView);
            menuPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            menuPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            menuPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            menuPopupWindow.setFocusable(true);
            menuPopupWindow.setOutsideTouchable(true);
        }
        // 背景图.9.png，外面有阴影部分，会导致menu和按钮之间有空隙，阴影部分高为36px
        PopupWindowCompat.showAsDropDown(menuPopupWindow, menu_more, 0, -36, Gravity.NO_GRAVITY);
        // menuPopupWindow.showAsDropDown(menu_more, 0, -36);
    }

    @SuppressWarnings("unused")
    @OnLongClick(R.id.menu_search)
    boolean menuSearchLongClick(View v) {
        Toast toast = Toast.makeText(this, R.string.menu_search_tip, Toast.LENGTH_SHORT);
        int x = (int) (menu_more.getX() - title.getWidth() / 2);
        int y = menu_more.getHeight();
        toast.setGravity(Gravity.TOP, x, y);
        toast.show();
        return true;
    }

    class MenuListener {
        @SuppressWarnings("unused")
        @OnClick(R.id.menu_create_group_chat)
        void menuCreateGroupChatClick(View v) {
            menuPopupWindow.dismiss();
            Toast.makeText(MainActivity.this, "发起群聊", Toast.LENGTH_SHORT).show();
        }

        @SuppressWarnings("unused")
        @OnClick(R.id.menu_add_friends)
        void menuAddFriendsClick(View v) {
            menuPopupWindow.dismiss();
            Toast.makeText(MainActivity.this, "添加朋友", Toast.LENGTH_SHORT).show();
        }

        @SuppressWarnings("unused")
        @OnClick(R.id.menu_scan_qr_code)
        void menuScanQrCodeClick(View v) {
            menuPopupWindow.dismiss();
            Toast.makeText(MainActivity.this, "扫一扫", Toast.LENGTH_SHORT).show();
        }

        @SuppressWarnings("unused")
        @OnClick(R.id.menu_feedback)
        void menuFeedbackClick(View v) {
            menuPopupWindow.dismiss();
            Toast.makeText(MainActivity.this, "帮助与反馈", Toast.LENGTH_SHORT).show();
        }
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
