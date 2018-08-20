package com.itlgl.demo.wechat.module.main.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.itlgl.demo.wechat.R;
import com.itlgl.demo.wechat.module.main.view.ItemRecentlyChatView;

import java.util.List;

public class RecentlyChatAdapter extends RecyclerView.Adapter<ItemRecentlyChatHolder> {
    private Context context;
    private List<RecentlyChatBean> chatDatas;

    public RecentlyChatAdapter(Context context, List<RecentlyChatBean> chatDatas) {
        this.context = context;
        this.chatDatas = chatDatas;
    }

    @Override
    public ItemRecentlyChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRecentlyChatView view = new ItemRecentlyChatView(context);
        return new ItemRecentlyChatHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemRecentlyChatHolder holder, int position) {
        holder.itemRecentlyChatView.setAvatar(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_default_avatar));
        holder.itemRecentlyChatView.setTitle("冠良");
        holder.itemRecentlyChatView.setMessage("你好你好你好你好你好你好你好你好你好你好");
        holder.itemRecentlyChatView.setDatetime("16:00");
        holder.itemRecentlyChatView.setBadgeString(position + "");
        holder.itemRecentlyChatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
