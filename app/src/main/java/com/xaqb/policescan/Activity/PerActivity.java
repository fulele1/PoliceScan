package com.xaqb.policescan.Activity;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.xaqb.policescan.R;
import com.xaqb.policescan.Utils.ChangeUtil;
import com.xaqb.policescan.Utils.GsonUtil;
import com.xaqb.policescan.Utils.HttpUrlUtils;
import com.xaqb.policescan.adapter.PerAdapter;
import com.xaqb.policescan.entity.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PerActivity extends BaseActivity implements OnDataFinishedLinstern{
    private List<Person> mPeople;
    private PerActivity instance;
    private ListView mList;
    private TextView mCount;
    private PerAdapter mAdapter;

    public PerActivity() {
    }


    @Override
    public void initTitleBar() {
        setTitle("快递员");
        showBackwardView(true);
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_per);
        instance = this;
        mList = (ListView) findViewById(R.id.list_per);
        mCount = (TextView) findViewById(R.id.tv_count);
    }

    @Override
    public void initData() {

    }

    public String getData(){
        Intent intent = instance.getIntent();
        String com = intent.getStringExtra("com");
        String per = intent.getStringExtra("per");
        String phone = intent.getStringExtra("phone");
        String ide = intent.getStringExtra("ide");
        return "&comname="+com+"&name="+per+"&mp="+phone+"&cert="+ide;
    }


    @Override
    public void addListener() {

        setOnDataFinishedLinstern(instance);
        getOkConnection(HttpUrlUtils.getHttpUrl().get_query_per()+getData());
    }

    @Override
    public void dataFinishedLinstern(String s) {
        mPeople = new ArrayList<>();
        if (s.startsWith("0")){
            //响应成功
            loadingDialog.dismiss();
            String str = ChangeUtil.procRet(s);
            str = str.substring(1,str.length());
            List<Map<String ,Object>> data = GsonUtil.GsonToListMaps(str);
            for (int i = 0;i < data.size();i++){
            Person person = new Person();
            person.setName(data.get(i).get("empname").toString());
            person.setSix(data.get(i).get("sexname").toString());
            person.setNum(data.get(i).get("empphone").toString());
            person.setIde(data.get(i).get("empcertcode").toString());
            person.setComeCode(data.get(i).get("empcode").toString());
            person.setCom(data.get(i).get("comname").toString());
            mPeople.add(person);
            }
        }else{
            //响应失败
        }
        mCount.setText(mPeople.size()+"");
        mAdapter = new PerAdapter(instance,mPeople);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(instance,PerDetailActivity.class);
                intent.putExtra("empcode",mPeople.get(i).getComeCode());
                startActivity(intent);
            }
        });
    }
}
