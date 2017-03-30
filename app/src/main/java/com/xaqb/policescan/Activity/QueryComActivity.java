package com.xaqb.policescan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.xaqb.policescan.R;


/**
 * 企业查询界面
 */
public class QueryComActivity extends BaseActivity {
    private QueryComActivity instance;
    private Button mBtQuery;
    private ImageView mIvComs;
    private EditText mEdComs;
    private EditText mEdCom;

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
        mIvComs = (ImageView) findViewById(R.id.iv_coms_com);
        mEdComs = (EditText) findViewById(R.id.et_coms_com);
        mEdCom = (EditText) findViewById(R.id.et_com_com);
    }

    @Override
    public void initData() {
    }


    @Override
    public void addListener() {
        mBtQuery.setOnClickListener(instance);
        mIvComs.setOnClickListener(instance);
    }

    @Override
    public void onClick(View v) {

        String comscode = mEdComs.getText().toString();
        String com = mEdCom.getText().toString();
        switch (v.getId()) {
            case R.id.bt_query_com://查询按钮
                if (comscode.equals("")&&com.equals("")){
                    showToast("请输入查询条件");
                }else if (comscode.equals("")&&!com.equals("")){
                    toIntent(comscode,com);
                }
                else{
                    toIntent(comscode.substring(0,comscode.indexOf("-")),com);
                }
                break;
            case R.id.iv_coms_com://选择品牌
                Intent intent = new Intent(instance,SearchComsActivity.class);
                startActivityForResult(intent,2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==2 && resultCode==RESULT_OK){

            Bundle bundle = data.getExtras();
            if (bundle != null) {
                String result = bundle.getString("coms");
                String code = bundle.getString("code");
                mEdComs.setText(code+"-"+result);
            }
        }
    }

    public void toIntent(String comscode,String com){
        Intent intent = new Intent(instance,ComActivity.class);
        intent.putExtra("comscode",comscode);
        intent.putExtra("com",com);
        startActivity(intent);
    }
}
