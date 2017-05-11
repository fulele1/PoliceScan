package com.xaqb.policescan.Activity;


import android.content.Intent;
import android.widget.TextView;

import com.xaqb.policescan.R;
import com.xaqb.policescan.Utils.LogUtils;

public class OrgDetailActivity extends BaseActivity {

    private OrgDetailActivity instance;
    private String mName;
    private String mStart;
    private String mEnd;
    private TextView mTvName;
    private TextView mTvBrand;
    private TextView mTvCom;
    private TextView mTvPer;
    private TextView mTvPostCount;
    private TextView mTvReCount;

    @Override
    public void initTitleBar() {
        setTitle("辖区详情");
    }


    @Override
    public void initViews() {
        setContentView(R.layout.activity_org_detail);
        instance = this;
        Intent intent = getIntent();
        mName  = intent.getStringExtra("mName");
        mStart = intent.getStringExtra("mStart");
        mEnd = intent.getStringExtra("mEnd");
        LogUtils.i(mName+mStart+mEnd);

        mTvName = (TextView) findViewById(R.id.tv_name_org);
        mTvBrand = (TextView) findViewById(R.id.tv_brand_org);
        mTvCom = (TextView) findViewById(R.id.tv_com_org);
        mTvPer = (TextView) findViewById(R.id.tv_per_org);
        mTvPostCount = (TextView) findViewById(R.id.tv_post_count_org);
        mTvReCount = (TextView) findViewById(R.id.tv_receive_count_org);
    }

    @Override
    public void initData() {
    }

    @Override
    public void addListener() {
    }
}
