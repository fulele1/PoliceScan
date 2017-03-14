package com.xaqb.policescan.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xaqb.policescan.R;
import com.xaqb.policescan.zxing.activity.CaptureActivity;

public class TotalActivity extends BaseActivity {

    private  TotalActivity instance;
    private TextView mTxtPer;
    private TextView mTxtCome;
    private ImageView ivZxing;
    private EditText etOrderNum;
    @Override
    public void initTitleBar() {
        setTitle("警用快递查询");
        showBackwardView(false);

    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_total);
        instance = this;
        assignViews();
    }


    private void assignViews() {
        ivZxing = (ImageView) findViewById(R.id.iv_zxing);
        etOrderNum = (EditText) findViewById(R.id.et_order_total);
        mTxtPer = (TextView) findViewById(R.id.txt_per_total);
        mTxtCome = (TextView) findViewById(R.id.txt_com_total);

    }

    @Override
    public void initData() {
    }

    @Override
    public void addListener() {
        ivZxing.setOnClickListener(instance);
        mTxtPer.setOnClickListener(instance);
        mTxtCome.setOnClickListener(instance);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_zxing://条形码扫描
                String scanResult = etOrderNum.getText().toString();
                if (!scanResult.equals("")) {//跳转到查询结果界面
                    Intent intent = new Intent(instance,QueryActivity.class);
                    intent.putExtra("code",scanResult);
                    startActivity(intent);
                } else {//为空时
                    Intent intent2 = new Intent(instance, CaptureActivity.class);
                    startActivityForResult(intent2, 0);
                }
                break;
            case R.id.txt_per_total://快递员查询
                startActivity(new Intent(instance,QueryPerActivity.class));
                break;
            case R.id.txt_com_total://企业查询
                startActivity(new Intent(instance,QueryComActivity.class));
                break;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //扫描结果
        if (resultCode == RESULT_OK && requestCode == 0) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                String scanResult = bundle.getString("result");
                //跳转到查询结果结果界面
                Intent intent = new Intent(instance,QueryActivity.class);
                intent.putExtra("code",scanResult);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (etOrderNum.getText()!=null){
            etOrderNum.setText("");
        }
    }


}