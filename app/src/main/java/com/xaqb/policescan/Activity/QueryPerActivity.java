package com.xaqb.policescan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private EditText mEtOrg;//管辖机构
    private ImageView mIvComs;//选择按钮
    private ImageView mIvClean;//全部删除

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
        mEtOrg = (EditText) findViewById(R.id.et_org_per);
        mIvComs = (ImageView) findViewById(R.id.iv_coms_per);
        mIvClean = (ImageView) findViewById(R.id.iv_clean_per);
        mEtOrg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.equals("")){
                    mIvClean.setVisibility(View.GONE);
                }else if (!charSequence.equals("")){
                    mIvClean.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        mBtnQuery.setOnClickListener(instance);
        mIvComs.setOnClickListener(instance);
        mIvClean.setOnClickListener(instance);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_coms_per://选择管辖机构
                Intent intent = new Intent(instance,SearchOrgActivity.class);
                startActivityForResult(intent,0);
                break;
            case R.id.iv_clean_per://清空
                mEtOrg.setText("");
                mIvClean.setVisibility(View.GONE);
                break;
            case R.id.bt_query_per://查询按钮
                String com = mEtCom.getText().toString();
                String per = mEtPer.getText().toString();
                String phone = mEtPhone.getText().toString();
                String ide = mEtIde.getText().toString();
                String orgOld = mEtOrg.getText().toString();
                String org = orgOld.substring(0,orgOld.indexOf("-"));
                if(com.equals("")&&per.equals("")&&phone.equals("")&&ide.equals("")&&org.equals("")){
                    showToast("请输入查询条件");
                }else{
                    toIntent(com,per, phone, ide,org,PerActivity.class);
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
    public void toIntent(String com,String per,String phone,String ide,String org,Class activity ){
        Intent intent = new Intent(instance,activity);
        intent.putExtra("com",com);
        intent.putExtra("per",per);
        intent.putExtra("phone",phone);
        intent.putExtra("ide",ide);
        intent.putExtra("org",org);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode ==RESULT_OK){
            Bundle bundle = data.getExtras();
            if (bundle != null){
                String result = bundle.getString("coms");
                String code = bundle.getString("code");
                mEtOrg.setText(code+"-"+result);
            }
        }
    }
}
