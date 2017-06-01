package com.xaqb.policescan.Activity;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.xaqb.policescan.R;
import com.xaqb.policescan.Utils.ChartUtil;
import com.xaqb.policescan.Utils.GsonUtil;
import com.xaqb.policescan.Utils.HttpUrlUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 企业详情列表
 */
public class ComDetailActivity extends BaseActivity implements OnDataFinishedLinstern{
    private ComDetailActivity instance;
    private TextView mTvCom;
    private TextView mTvPlice;
    private TextView mTvPer;
    private TextView mTvGet;
    private TextView mTvPost;
    private TextView mTvPerName;
    private TextView mTvPerPhone;
    private LinearLayout mLayoutPlace;//定位
    private LinearLayout mLayoutPhone;//打电话
    private List<Double> at;//7天收寄数量
    private List<Double> dv;//7天投递数量
    private List<String> keys;//x轴的描述
    private LineChart chart;//曲线图
    private Double atCount = 0.0;//收寄数量
    private Double dvCount = 0.0;//投递数量
    private String mLongitude;//经度
    private String mLatitude;//纬度


    @Override
    public void initTitleBar() {
        setTitle("企业详情");
        showBackwardView(true);
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_com_detail);
        instance = this;
        chart = (LineChart) findViewById(R.id.chart_com_de);
        mTvCom = (TextView) findViewById(R.id.txt_com_com_dt);
        mTvPlice = (TextView) findViewById(R.id.txt_plice_com_dt);
        mTvPer = (TextView) findViewById(R.id.txt_per_com_dt);
        mTvGet = (TextView) findViewById(R.id.txt_get_com_dt);
        mTvPost = (TextView) findViewById(R.id.txt_post_com_dt);
        mTvPerName = (TextView) findViewById(R.id.txt_per_com_);
        mTvPerPhone = (TextView) findViewById(R.id.txt_per_phone_com_dt);
        mLayoutPlace = (LinearLayout) findViewById(R.id.layout_place);
        mLayoutPhone = (LinearLayout) findViewById(R.id.layout_phone);
    }

    @Override
    public void initData() {
    }

    @Override
    public void addListener() {
        setOnDataFinishedLinstern(instance);
        mLayoutPlace.setOnClickListener(instance);
        mLayoutPhone.setOnClickListener(instance);
        getOkConnection(HttpUrlUtils.getHttpUrl().get_query_com_detail()+ "&comcode="+instance.getIntent().getStringExtra("comcode"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_place://定位
                if (mLatitude.equals("0.000000")||mLongitude.equals("0.000000")){
                    showToast("地址有误");
                }else{
                Intent intent = new Intent(instance, MapActivity.class);
                intent.putExtra("Lat",mLatitude);
                intent.putExtra("Lng",mLongitude);
                intent.putExtra("title",mTvCom.getText().toString().trim());
                startActivity(intent);
                }
                break;
            case R.id.layout_phone://打电话
                if (!mPhone.equals("")){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+mPhone));
                    startActivity(intent);
                }
                break;
        }
    }

    private String mPhone;
    @Override
    public void dataFinishedLinstern(String s) {
        if (s.startsWith("0")){
            //响应成功
            String str = s.split(String.valueOf((char) 1))[1];
            Map<String,Object> data = GsonUtil.GsonToMaps(str);
            mTvCom.setText(data.get("comname").toString());
            mTvPlice.setText(data.get("comaddress").toString());
            mTvPer.setText(data.get("empnum").toString());
            mPhone = data.get("commanphone").toString();
            mTvPerPhone.setText(mPhone);
            mTvPerName.setText(data.get("comman").toString());
            mLongitude = data.get("comlng").toString();
            mLatitude = data.get("comlat").toString();
//            mLongitude = "121.763260";
//            mLatitude = "31.053959";
            getData(data);
        }else{
            //响应失败
            Toast.makeText(instance, "未查询到有效数据", Toast.LENGTH_SHORT).show();
        }

        loadingDialog.dismiss();
    }

    /**
     * 解析count并显示到曲线图上
     * @param data
     */
    public void getData(Map<String ,Object> data){
        Map<String ,Object> data2 = GsonUtil.GsonToMaps(data.get("data").toString());
        at = new ArrayList<>();
        dv = new ArrayList<>();
        keys = new ArrayList<>();

        Set<String> keySet = data2.keySet();
        Iterator iterator= keySet.iterator();
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
