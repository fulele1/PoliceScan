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
    private Button mBtnQuery;

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
