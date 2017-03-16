package com.xaqb.policescan.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.xaqb.policescan.R;

public class QueryPerActivity extends BaseActivity {

    private QueryPerActivity instance;
    private Button mBtnQuery;//查询
    private EditText mEtCom;//企业
    private EditText mEtPer;//姓名
    private EditText mEtPhone;//电话号码
    private EditText mEtIde;//证件号码

    @Override
    public void initTitleBar() {
        setTitle("快递员查询");
        showBackwardView(true);

    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_query_per);
        instance = this;
        asSignViews();

    }

    private void asSignViews() {
        mBtnQuery = (Button) findViewById(R.id.bt_query_per);
        mEtCom = (EditText) findViewById(R.id.et_com_per);
        mEtPer = (EditText) findViewById(R.id.et_per_per);
        mEtPhone = (EditText) findViewById(R.id.et_phone_per);
        mEtIde = (EditText) findViewById(R.id.et_ide_per);
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        mBtnQuery.setOnClickListener(instance);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_query_per://查询按钮
                startActivity(new Intent(instance,PerActivity.class));
                break;
        }
    }
}
