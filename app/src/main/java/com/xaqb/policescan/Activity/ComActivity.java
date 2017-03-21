package com.xaqb.policescan.Activity;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.xaqb.policescan.R;
import com.xaqb.policescan.Utils.HttpUrlUtils;
import com.xaqb.policescan.Utils.LogUtils;
import com.xaqb.policescan.adapter.ComAdapter;
import com.xaqb.policescan.entity.Company;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComActivity extends BaseActivity implements OnDataFinishedLinstern{
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
        Intent intent = instance.getIntent();
        String select = intent.getStringExtra("select");
        LogUtils.i("-----"+select);
    }

    /**
     * 增加事件处理
     */
    @Override
    public void addListener() {

        getOkConnection(HttpUrlUtils.getHttpUrl().quer_yCode()+ "&code=123456");//进行网络连接
        this.setOnDataFinishedLinstern(instance);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(instance,ComDetailActivity.class);
                intent.putExtra("coms",coms);
                startActivity(intent);
            }
        });

    }

    /**
     * 处理回调回来的数据
     * @param data
     */
    String coms;
    @Override
    public void dataFinishedLinstern(Map<String, Object> data) {

        mCompanys = new ArrayList<>();
        for (int i =0;i<data.size()-1;i++){

            Company company = new Company();
            company.setCom(data.get("mancertcode").toString());
            coms = data.get("mancertcode").toString();
            mCompanys.add(company);
        }
        comAdapter = new ComAdapter(instance,mCompanys);
        mList.setAdapter(comAdapter);//设置适配器
        mTvCount.setText(""+mCompanys.size());
    }
}
