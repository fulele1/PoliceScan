package com.xaqb.policescan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xaqb.policescan.R;
import com.xaqb.policescan.Utils.ChangeUtil;
import com.xaqb.policescan.Utils.GsonUtil;
import com.xaqb.policescan.Utils.HttpUrlUtils;
import com.xaqb.policescan.adapter.ComsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchComsActivity extends BaseActivity implements OnDataFinishedLinstern{
    private ListView mList;
    private List<String> coms;
    private SearchComsActivity instance;
    @Override
    public void initTitleBar() {
        setTitle("企业列表");
        showBackwardView(false);

    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_search_coms);
        instance = this;
        mList = (ListView) findViewById(R.id.list_search);
    }

    @Override
    public void initData() {
        getOkConnection(HttpUrlUtils.getHttpUrl().quer_yCode()+ "&code=123456");

    }

    @Override
    public void addListener() {
        setOnDataFinishedLinstern(this);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("coms",coms.get(i));
                intent.putExtras(bundle);
                instance.setResult(RESULT_OK,intent);
                SearchComsActivity.this.finish();
            }
        });

    }

    @Override
    public void dataFinishedLinstern(String s) {
        coms = new ArrayList<>();
        if (s.startsWith("0")){
            //响应成功
            loadingDialog.dismiss();
            String str = ChangeUtil.procRet(s);
            str = str.substring(1,str.length());
            Map<String ,Object> data = GsonUtil.JsonToMap(str);
            coms.add(data.get("expresstype").toString());
            mList.setAdapter(new ComsAdapter(this,coms));
        }else{
            //响应失败
        }
    }
}
