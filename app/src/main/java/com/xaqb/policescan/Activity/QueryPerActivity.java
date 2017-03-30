package com.xaqb.policescan.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xaqb.policescan.R;
import com.xaqb.policescan.Utils.LogUtils;

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
                String com = mEtCom.getText().toString();
                String per = mEtPer.getText().toString();
                String phone = mEtPhone.getText().toString();
                String ide = mEtIde.getText().toString();
                if(com.equals("")&&per.equals("")&&phone.equals("")&&ide.equals("")){
                    showToast("请输入查询条件");
                }else{
                    toIntent(com,per, phone, ide, PerActivity.class);
                }
                break;
        }
    }


    /**
     * 跳转界面
     * @param com
     * @param per
     * @param phone
     * @param ide
     * @param activity
     */
    public void toIntent(String com,String per,String phone,String ide,Class activity ){
        Intent intent = new Intent(instance,activity);
        intent.putExtra("com",com);
        intent.putExtra("per",per);
        intent.putExtra("phone",phone);
        intent.putExtra("ide",ide);
        startActivity(intent);
    }
}
