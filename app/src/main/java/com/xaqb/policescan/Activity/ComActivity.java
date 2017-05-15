package com.xaqb.policescan.Activity;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xaqb.policescan.R;
import com.xaqb.policescan.Utils.ChangeUtil;
import com.xaqb.policescan.Utils.GsonUtil;
import com.xaqb.policescan.Utils.HttpUrlUtils;
import com.xaqb.policescan.Utils.LogUtils;
import com.xaqb.policescan.adapter.ComAdapter;
import com.xaqb.policescan.entity.Company;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 *查询出的企业列表界面
 */
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
        mList.setDivider(new ColorDrawable(Color.GRAY));
        mList.setDividerHeight(1);
        mTvCount = (TextView) findViewById(R.id.tv_count_com_list);
    }

    @Override
    public void initData() {
    }


    /**
     *
     * @return 返回详情接口的参数
     */
    public String getData(){
        Intent intent = instance.getIntent();
        String comscode = intent.getStringExtra("comscode");
        String com = intent.getStringExtra("com");
        String org = intent.getStringExtra("org");
        return "&combrand="+comscode+"&comname="+com+"&comorg="+org;
    }


    /**
     * 增加事件处理
     */
    @Override
    public void addListener() {
        this.setOnDataFinishedLinstern(instance);
        getOkConnection(HttpUrlUtils.getHttpUrl().get_query_com()+getData());//进行网络连接
    }


    /**
     * 回调接口返回的网络请求后的数据
     * @param s
     */
    @Override
    public void dataFinishedLinstern(String s) {
        LogUtils.i(s);
        mCompanys = new ArrayList<>();
        if (s.startsWith("0")){
            //响应成功
            String str = ChangeUtil.procRet(s);
            str = str.substring(1,str.length());
            List<Map<String ,Object>> data = GsonUtil.GsonToListMaps(str);
            LogUtils.i(data.toString());
            for (int i = 0;i<data.size();i++){
            Company company = new Company();
            company.setCom(data.get(i).get("comname").toString());
            company.setComCode(data.get(i).get("comcode").toString());
            company.setComs(data.get(i).get("bcname").toString());
            company.setPer(data.get(i).get("comman").toString());
            company.setNum(data.get(i).get("commanphone").toString());
            company.setAddress(data.get(i).get("comaddress").toString());
            mCompanys.add(company);
            }
        }else{
            //响应失败
            Toast.makeText(instance, "未查询到有效数据", Toast.LENGTH_SHORT).show();
        }
        loadingDialog.dismiss();
        addEvent();
    }


    /**
     * 事件的处理
     */
    private void addEvent() {
        comAdapter = new ComAdapter(instance,mCompanys);
        mList.setAdapter(comAdapter);//设置适配器
        mTvCount.setText(""+mCompanys.size());
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(instance,ComDetailActivity.class);
                intent.putExtra("comcode",mCompanys.get(i).getComCode());
                startActivity(intent);
            }
        });
    }


}
