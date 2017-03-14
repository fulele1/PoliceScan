package com.xaqb.policescan.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xaqb.policescan.R;


public class QueryComActivity extends BaseActivity {
    private QueryComActivity instance;
    private Button mBtQuery;


    @Override
    public void initTitleBar() {
        setTitle("企业查询");
        showBackwardView(true);
    }


    @Override
    public void initViews() {
        setContentView(R.layout.activity_query_com);
        instance = this;
        asSignView();
    }

    private void asSignView() {
        mBtQuery = (Button) findViewById(R.id.bt_query_com);

    }


    @Override
    public void initData() {
    }


    @Override
    public void addListener() {
        mBtQuery.setOnClickListener(instance);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_query_com:
                startActivity( new Intent(instance,ComActivity.class));
                break;
        }
    }
}
