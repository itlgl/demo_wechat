package com.itlgl.demo.wechat.module.main.adapter;

import android.support.v7.widget.RecyclerView;

import com.itlgl.demo.wechat.module.main.view.ItemRecentlyChatView;

public class ItemRecentlyChatHolder extends RecyclerView.ViewHolder {
    public ItemRecentlyChatView itemRecentlyChatView;

    public ItemRecentlyChatHolder(ItemRecentlyChatView itemView) {
        super(itemView);
        itemRecentlyChatView = itemView;
    }
}
