package com.xaqb.policescan.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.xaqb.policescan.R;
import com.xaqb.policescan.Utils.ChartUtil;
import com.xaqb.policescan.Utils.GsonUtil;
import com.xaqb.policescan.Utils.HttpUrlUtils;
import com.xaqb.policescan.Utils.LogUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class PerDetailActivity extends BaseActivity implements OnDataFinishedLinstern{

    PerDetailActivity instance;
    private TextView mTvCom;
    private TextView mTvName;
    private TextView mTvNum;
    private TextView mTvSix;
    private TextView mTvNational;
    private TextView mTvIde;
    private TextView mTvGet;
    private TextView mTvPost;
    private List<Double> at;
    private List<Double> dv;
    private List<String> keys;
    @Override
    public void initTitleBar() {
        setTitle("人员详情");
        showBackwardView(true);

    }
    LineChart chart;
    @Override
    public void initViews() {
        setContentView(R.layout.activity_per_detile);
        instance = this;
        chart = (LineChart) findViewById(R.id.chart_per_de);
        mTvCom = (TextView) findViewById(R.id.txt_com_per_dt);
        mTvName = (TextView) findViewById(R.id.txt_name_per_dt);
        mTvNum = (TextView) findViewById(R.id.txt_num_per_dt);
        mTvSix = (TextView) findViewById(R.id.txt_six_per_dt);
        mTvNational = (TextView) findViewById(R.id.txt_national_per_dt);
        mTvIde = (TextView) findViewById(R.id.txt_ide_per_dt);
        mTvGet = (TextView) findViewById(R.id.txt_get_per_dt);
        mTvPost = (TextView) findViewById(R.id.txt_post_per_dt);


    }
    @Override
    public void initData() {
    }

    @Override
    public void addListener() {
        setOnDataFinishedLinstern(instance);
        getOkConnection(HttpUrlUtils.getHttpUrl().get_query_per_detail()+"&empcode="+instance.getIntent().getStringExtra("empcode"));
        mTvNum.setOnClickListener(instance);
    }


    @Override
    public void dataFinishedLinstern(String s) {
        if (s.startsWith("0")){
            //响应成功
//            String str = ChangeUtil.procRet(s);
//            str = str.substring(1,str.length());
            String str = s.split(String.valueOf((char) 1))[1];
            Map<String ,Object> data = GsonUtil.GsonToMaps(str);
            LogUtils.i(data.toString());
            mTvCom.setText(data.get("comname").toString());
            mTvName.setText(data.get("empname").toString());
            mTvNum.setText(data.get("empphone").toString());
            mTvSix.setText(data.get("sexname").toString());
            mTvNational.setText(data.get("nationname").toString());
            mTvIde.setText(data.get("empcertcode").toString());
            getData(data);

        }else{
            //响应失败
            Toast.makeText(instance, "未查询到有效数据", Toast.LENGTH_SHORT).show();
        }
        loadingDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_num_per_dt://拨电话号码
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+mTvNum.getText().toString()));
                startActivity(intent);
                break;
        }
    }

    Double atCount = 0.0;
    Double dvCount = 0.0;
    /**
     * 解析count
     * @param data
     */
    public void getData(Map<String ,Object> data){
        Map<String ,Object> data2 = GsonUtil.GsonToMaps(data.get("data").toString());
        at = new ArrayList<>();
        dv = new ArrayList<>();
        keys = new ArrayList<>();

        Set<String> keySet = data2.keySet();
        Iterator  iterator= keySet.iterator();
        while (iterator.hasNext()) {
            keys.add((String) iterator.next());
        }

        for(int i = 0;i<keys.size();i++){
            Map<String ,Object> day = GsonUtil.GsonToMaps(data2.get(keys.get(i)).toString());
            at.add((Double)day.get("atnum"));
            dv.add((Double)day.get("dvnum"));
        }

        for (int i = 0;i<at.size();i++){
            atCount +=at.get(i);
        }

        for (int i = 0;i<dv.size();i++){
            dvCount +=dv.get(i);
        }

        mTvGet.setText(atCount+"");
        mTvPost.setText(dvCount+"");

        // 获取完数据之后 制作7个数据点（沿x坐标轴）
        LineData mLineData = ChartUtil.makeLineData(7,dv,at,keys,"投递",Color.BLUE,"收寄",Color.RED);
        ChartUtil.setChartStyle(chart,mLineData, Color.WHITE);
    }


}