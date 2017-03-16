package com.xaqb.policescan.Activity;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.xaqb.policescan.R;
import com.xaqb.policescan.adapter.ComAdapter;
import com.xaqb.policescan.entity.Company;

import java.util.ArrayList;
import java.util.List;

public class ComActivity extends BaseActivity {
    private ListView mList;
    private TextView mTvCount;
    private ComActivity instance;
    private ComAdapter comAdapter;
    private List<Company> mCompanys;


    @Override
    public void initTitleBar() {
        setTitle("企业");
        showBackwardView(true);
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_com);
        instance = this;
        mList = (ListView) findViewById(R.id.list_com);
        mTvCount = (TextView) findViewById(R.id.tv_count_com_list);
    }

    @Override
    public void initData() {
        mCompanys = new ArrayList<>();
        Company company = new Company();
        company.setCom("天天快递");
        mCompanys.add(company);

    }


    @Override
    public void addListener() {
        comAdapter = new ComAdapter(instance,mCompanys);
        mList.setAdapter(comAdapter);
        mTvCount.setText(""+mCompanys.size());
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(instance,ComDetailActivity.class));
            }
        });

    }
}
