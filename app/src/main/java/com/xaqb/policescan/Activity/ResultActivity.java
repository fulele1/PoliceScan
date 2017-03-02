package com.xaqb.policescan.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.xaqb.policescan.R;

public class ResultActivity extends BaseActivity {
    private ResultActivity instance;
    private String mCode;

    @Override
    public void initTitleBar() {
        setTitle(R.string.query_recode);
        showBackwardView(true);
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_result);
        instance = this;
        mCode= instance.getIntent().getStringExtra("code");
        assignViews();
    }

    Button query;
    private void assignViews(){
        query = (Button) instance.findViewById(R.id.bt_query_result);
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        query.setOnClickListener(instance);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
       switch(v.getId()){
           case R.id.bt_query_result:
               intent = new Intent(instance, QueryWebviewActivity.class);
               intent.putExtra("url", "https://m.kuaidi100.com/index_all.html?postid=" + mCode);
               startActivity(intent);
            break;
       }
    }
}
