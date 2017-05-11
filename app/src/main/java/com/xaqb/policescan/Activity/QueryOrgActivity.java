package com.xaqb.policescan.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.xaqb.policescan.R;

public class QueryOrgActivity extends BaseActivity {

    private QueryOrgActivity instance;
    private EditText mEtName;
    private EditText mEtStart;
    private EditText mEtEnd;
    private ImageView mIvClear;
    private ImageView mIvOrg;
    private Button mBtFinished;
    private String mName;
    private String mStart;
    private String mEnd;

    @Override
    public void initTitleBar() {
        setTitle("辖区统计查询");
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_query_org);
        instance = this;
        mEtName = (EditText) findViewById(R.id.et_name_query_org);
        mEtStart = (EditText) findViewById(R.id.et_start_org);
        mEtEnd = (EditText) findViewById(R.id.et_end_org);
        mBtFinished = (Button) findViewById(R.id.bt_finished_query_org);
        mIvClear = (ImageView) findViewById(R.id.iv_clean_org);
        mIvOrg = (ImageView) findViewById(R.id.iv_org_org);
    }

    @Override
    public void initData() {
    }

    @Override
    public void addListener() {
        mBtFinished.setOnClickListener(instance);
        mIvClear.setOnClickListener(instance);
        mIvOrg.setOnClickListener(instance);
        mEtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.equals("")){
                    mIvClear.setVisibility(View.GONE);
                }else if (!charSequence.equals("")){
                    mIvClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_clean_org://清空
                mEtName.setText("");
                mIvClear.setVisibility(View.GONE);
                break;
            case R.id.iv_org_org://选择管辖机构
                Intent intent1 = new Intent(instance,SearchOrgActivity.class);
                startActivityForResult(intent1,0);
                break;
            case R.id.bt_finished_query_org://完成
                String mNameOld = mEtName.getText().toString().trim();
                if (mNameOld.contains("-")){
                    mName = mNameOld.substring(0,mNameOld.indexOf("-"));
                }else{
                    mName = mNameOld;
                }
                mStart = mEtStart.getText().toString().trim();
                mEnd = mEtEnd.getText().toString().trim();

                if (mName.equals("")&&mStart.equals("")&&mEnd.equals("")){
                    showToast("请输入查询条件");
                    return;
                }
                Intent intent = new Intent(instance,OrgDetailActivity.class);
                intent.putExtra("mName",mName);
                intent.putExtra("mStart",mStart);
                intent.putExtra("mEnd",mEnd);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode ==RESULT_OK){
            Bundle bundle = data.getExtras();
            if (bundle != null){
                String result = bundle.getString("coms");
                String code = bundle.getString("code");
                mEtName.setText(code+"-"+result);
            }
        }
    }
}
