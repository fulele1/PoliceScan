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
import com.xaqb.policescan.adapter.ComAdapter;
import com.xaqb.policescan.entity.Company;
import com.xaqb.policescan.entity.Person;

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
                intent.putExtra("coms",mCompanys.get(i).getCom());
                startActivity(intent);
            }
        });

    }

    @Override
    public void dataFinishedLinstern(String s) {
        mCompanys = new ArrayList<>();
        if (s.startsWith("0")){
            //响应成功
            loadingDialog.dismiss();
            String str = ChangeUtil.procRet(s);
            str = str.substring(1,str.length());
            Map<String ,Object> data = GsonUtil.JsonToMap(str);
            Company company = new Company();
            company.setCom(data.get("mancertcode").toString());
            mCompanys.add(company);
        }else{
            //响应失败
        }
        comAdapter = new ComAdapter(instance,mCompanys);
        mList.setAdapter(comAdapter);//设置适配器
        mTvCount.setText(""+mCompanys.size());

    }

}
