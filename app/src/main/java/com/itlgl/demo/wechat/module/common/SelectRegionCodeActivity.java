package com.itlgl.demo.wechat.module.common;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itlgl.demo.wechat.R;
import com.itlgl.demo.wechat.bean.RegionCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectRegionCodeActivity extends AppCompatActivity {
    public static final String EXTRA_REGION_CODE = "extra_region_code";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    List<RegionCodeDisplay> regionCodeDisplayList;
    RegionAdapter regionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_region_code);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        // FIXME 现在只是完成测试的功能，以后这里要改成微信一样的风格
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        regionCodeDisplayList = loadRegionCodeData();

        regionAdapter = new RegionAdapter(this, regionCodeDisplayList);
        recyclerView.setAdapter(regionAdapter);
        regionAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(RegionViewHolder holder, RegionCodeDisplay item, int position) {
                Intent data = new Intent();
                data.putExtra(EXTRA_REGION_CODE, new RegionCode(item.getName(), item.getSortName(), item.getCode()));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    private List<RegionCodeDisplay> loadRegionCodeData() {
        List<RegionCodeDisplay> result = new ArrayList<>();
        List<RegionCode> regionCodeList = new ArrayList<>();
        try {
            String[] regionArray = getResources().getStringArray(R.array.region_array);
            for (String regionStr : regionArray) {
                String[] regionSingleArray = regionStr.split(":");
                if(regionSingleArray.length < 3) {
                    continue;
                }

                int code = Integer.parseInt(regionSingleArray[2].trim());
                String name = regionSingleArray[1].trim();
                String sortName = regionSingleArray[0].trim();

                regionCodeList.add(new RegionCode(name, sortName, code));
            }

            // 对数据进行排序
            Collections.sort(regionCodeList);

            char firstLetter = 0;
            for(RegionCode rc : regionCodeList) {
                char c = rc.getSortName().charAt(0);
                if(firstLetter != c) {
                    result.add(new RegionCodeDisplay(rc.getName(), rc.getSortName(), rc.getCode(), String.valueOf(c)));
                    firstLetter = c;
                } else {
                    result.add(new RegionCodeDisplay(rc.getName(), rc.getSortName(), rc.getCode()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @OnClick(R.id.back)
    void backClick(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    static class RegionCodeDisplay extends RegionCode {
        String letter;
        boolean isHead;

        public RegionCodeDisplay() {}

        public RegionCodeDisplay(String name, String sortName, int code) {
            super(name, sortName, code);
            this.isHead = false;
        }

        public RegionCodeDisplay(String name, String sortName, int code, String letter) {
            super(name, sortName, code);
            this.letter = letter;
            this.isHead = true;
        }

        public String getLetter() {
            return letter;
        }

        public boolean isHead() {
            return isHead;
        }
    }

    static class RegionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.region_name)
        TextView tvRegionName;
        @BindView(R.id.region_code)
        TextView tvRegionCode;
        @BindView(R.id.letter_layout)
        View vLetterLayout;
        @BindView(R.id.letter)
        TextView tvLetter;

        public RegionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static interface OnItemClickListener {
        void onItemClick(RegionViewHolder holder, RegionCodeDisplay item, int position);
    }

    static class RegionAdapter extends RecyclerView.Adapter<RegionViewHolder> {
        private List<RegionCodeDisplay> datas;
        private LayoutInflater inflater;
        private Context context;
        private OnItemClickListener onItemClickListener;

        RegionAdapter(Context context, List<RegionCodeDisplay> datas) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.datas = new ArrayList<>();
            if(datas != null) {
                this.datas.addAll(datas);
            }
        }

        @Override
        public RegionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RegionViewHolder holder =  new RegionViewHolder(inflater.inflate(R.layout.item_region_code, null));
            if(viewType == 0) {
                holder.vLetterLayout.setVisibility(View.GONE);
            } else {
                holder.vLetterLayout.setVisibility(View.VISIBLE);
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(final RegionViewHolder holder, final int position) {
            final RegionCodeDisplay rcd = datas.get(position);
            holder.tvRegionName.setText(rcd.getName());
            holder.tvRegionCode.setText(String.valueOf(rcd.getCode()));
            if(rcd.isHead) {
                holder.tvLetter.setText(rcd.getLetter());
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null) {
                        onItemClickListener.onItemClick(holder, rcd, position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas == null ? 0 : datas.size();
        }

        @Override
        public int getItemViewType(int position) {
            RegionCodeDisplay rcd = datas.get(position);
            return rcd.isHead() ? 1 : 0;
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.onItemClickListener = listener;
        }
    }

}
