package com.xaqb.policescan.Activity;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.xaqb.policescan.R;
import com.xaqb.policescan.adapter.MyAdapter;
import com.xaqb.policescan.entity.Person;

import java.util.ArrayList;
import java.util.List;

public class PerActivity extends BaseActivity {
    private List<Person> mPeople;
    private PerActivity instance;
    private ListView mList;
    private TextView mCount;
    private MyAdapter mAdapter;


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
        mPeople = new ArrayList<>();
        Person person = new Person();
        person.setName("张三");
        person.setSix("男");
        person.setNum("15245652889");
        person.setIde("61042219956879");
        mPeople.add(person);
        mCount.setText(mPeople.size()+"");
    }

    @Override
    public void addListener() {

        mAdapter = new MyAdapter(instance,mPeople);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(instance,PerDetailActivity.class));
            }
        });
    }
}
