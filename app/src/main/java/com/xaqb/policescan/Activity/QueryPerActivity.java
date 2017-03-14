package com.xaqb.policescan.Activity;

import android.widget.EditText;
import android.widget.ImageView;

import com.xaqb.policescan.R;

public class QueryPerActivity extends BaseActivity {

    private QueryPerActivity instance;
    private EditText mEtPer;
    private ImageView ivPhoto;

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
        mEtPer = (EditText) findViewById(R.id.et_order_per);
        ivPhoto = (ImageView) findViewById(R.id.iv_photo_per);
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {

    }
}
