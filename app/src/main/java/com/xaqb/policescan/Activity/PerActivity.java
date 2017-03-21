package com.xaqb.policescan.Activity;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.xaqb.policescan.R;
import com.xaqb.policescan.Utils.ChangeUtil;
import com.xaqb.policescan.Utils.GsonUtil;
import com.xaqb.policescan.Utils.HttpUrlUtils;
import com.xaqb.policescan.Utils.LogUtils;
import com.xaqb.policescan.adapter.MyAdapter;
import com.xaqb.policescan.entity.Person;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class PerActivity extends BaseActivity implements OnDataFinishedLinstern{
    private List<Person> mPeople;
    private PerActivity instance;
    private ListView mList;
    private TextView mCount;
    private MyAdapter mAdapter;
    private String ide;


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
        Intent intent = instance.getIntent();
        String select = intent.getStringExtra("select");
    }

    @Override
    public void addListener() {

        setOnDataFinishedLinstern(instance);
        getOkConnection(HttpUrlUtils.getHttpUrl().quer_yCode()+ "&code=123456");

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(instance,PerDetailActivity.class);
                intent.putExtra("ide",ide);
                startActivity(intent);
            }
        });
    }


    @Override
    public void dataFinishedLinstern(Map<String, Object> data) {
        mPeople = new ArrayList<>();
        for (int i =0;i<data.size()-1;i++){
            ide = data.get("mancertcode").toString();
            Person person = new Person();
            person.setName(data.get("expresstype").toString());
            person.setSix(data.get("expresstype").toString());
            person.setNum(data.get("manphone").toString());
            person.setIde(data.get("mancertcode").toString());
            mPeople.add(person);
        }
        mCount.setText(mPeople.size()+"");
        mAdapter = new MyAdapter(instance,mPeople);
        mList.setAdapter(mAdapter);
    }


}
