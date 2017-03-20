package com.xaqb.policescan.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xaqb.policescan.R;


public class QueryComActivity extends BaseActivity {
    private QueryComActivity instance;
    private Button mBtQuery;
    private EditText mEdComs;
    private EditText mEdCom;
    private EditText mEdLocation;


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
        mEdComs = (EditText) findViewById(R.id.et_coms_com);
        mEdCom = (EditText) findViewById(R.id.et_com_com);
        mEdLocation = (EditText) findViewById(R.id.et_location_com);

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

        String coms = mEdComs.getText().toString();
        String com = mEdCom.getText().toString();
        String location = mEdLocation.getText().toString();
        switch (v.getId()) {
            case R.id.bt_query_com:
               if (!coms.equals("")){
                   toIntent(coms);
               }if (!com.equals("")){
                   toIntent(com);
               }if (!location.equals("")){
                   toIntent(location);
               }
                break;
        }

    }

    public void toIntent(String string){
        Intent intent = new Intent(instance,ComActivity.class);
        intent.putExtra("select",string);
        startActivity(intent);
    }

}
