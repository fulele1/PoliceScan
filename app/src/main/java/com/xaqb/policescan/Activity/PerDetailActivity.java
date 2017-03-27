package com.xaqb.policescan.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.xaqb.policescan.R;
import com.xaqb.policescan.Utils.ChangeUtil;
import com.xaqb.policescan.Utils.GsonUtil;
import com.xaqb.policescan.Utils.HttpUrlUtils;
import com.xaqb.policescan.Utils.LogUtils;
import com.xaqb.policescan.entity.Person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.StrictMath.E;

public class PerDetailActivity extends BaseActivity implements OnDataFinishedLinstern{

    PerDetailActivity instance;
    private TextView mTvCom;
    private TextView mTvName;
    private TextView mTvNum;
    private TextView mTvSix;
    private TextView mTvNational;
    private TextView mTvIde;
    private TextView mTvHome;
    private TextView mTvState;
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
        mTvHome = (TextView) findViewById(R.id.txt_home_per_dt);
        mTvState = (TextView) findViewById(R.id.txt_state_per_dt);
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
    }


    @Override
    public void dataFinishedLinstern(String s) {
        if (s.startsWith("0")){
            //响应成功
            loadingDialog.dismiss();
//            String str = ChangeUtil.procRet(s);
//            str = str.substring(1,str.length());
            String str = s.split(String.valueOf((char) 1))[1];
            Map<String ,Object> data = GsonUtil.GsonToMaps(str);
            mTvCom.setText(data.get("comname").toString());
            mTvName.setText(data.get("empname").toString());
            mTvNum.setText(data.get("empphone").toString());
            mTvSix.setText(data.get("sexname").toString());
            mTvNational.setText(data.get("nationname").toString());
            mTvIde.setText(data.get("empcertcode").toString());
            mTvHome.setText("");
            mTvState.setText("");
            getData(data);

        }else{
            //响应失败
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

        mTvGet.setText(dvCount+"");
        mTvPost.setText(atCount+"");

        // 获取完数据之后 制作7个数据点（沿x坐标轴）
        LineData mLineData = makeLineData(7);
        setChartStyle(chart, mLineData, Color.WHITE);
    }



    // 设置chart显示的样式
    private void setChartStyle(LineChart mLineChart, LineData lineData, int color) {
        // 是否在折线图上添加边框
        mLineChart.setDrawBorders(false);

        mLineChart.setDescription("收件数量");// 数据描述

        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        mLineChart.setNoDataTextDescription("如果传给MPAndroidChart的数据为空，那么你将看到这段文字。@Zhang Phil");

        // 是否绘制背景颜色。
        // 如果mLineChart.setDrawGridBackground(false)，
        // 那么mLineChart.setGridBackgroundColor(Color.CYAN)将失效;
        mLineChart.setDrawGridBackground(false);
        mLineChart.setGridBackgroundColor(Color.CYAN);

        // 触摸
        mLineChart.setTouchEnabled(true);

        // 拖拽
        mLineChart.setDragEnabled(true);

        // 缩放
        mLineChart.setScaleEnabled(true);

        mLineChart.setPinchZoom(false);
        // 隐藏右边 的坐标轴
        mLineChart.getAxisRight().setEnabled(false);
        // 让x轴在下面
        mLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        // // 隐藏左边坐标轴横网格线
        // mLineChart.getAxisLeft().setDrawGridLines(false);
        // // 隐藏右边坐标轴横网格线
        // mLineChart.getAxisRight().setDrawGridLines(false);
        // // 隐藏X轴竖网格线
        // mLineChart.getXAxis().setDrawGridLines(false);

        // 设置背景
        mLineChart.setBackgroundColor(color);

        // 设置x,y轴的数据
        mLineChart.setData(lineData);

        // 设置比例图标示，就是那个一组y的value的
        Legend mLegend = mLineChart.getLegend();

        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(15.0f);// 字体
        mLegend.setTextColor(Color.BLUE);// 颜色

        // 沿x轴动画，时间2000毫秒。
        mLineChart.animateX(2000);
    }


    /**
     * @param count 数据点的数量。
     * @return
     */
    private LineData makeLineData(int count) {

        List<String> x = keys;



        //y轴的数据
        ArrayList<Entry> y = new ArrayList<>();
        double dVal1=0.0d;
        for (int i = 0; i < count; i++) {
            dVal1=dv.get(i);
            Entry entry = new Entry((float)dVal1, i);
            y.add(entry);
        }


        //y轴的数据
        ArrayList<Entry> y2 = new ArrayList<>();
        double dVal=0.0d;
        for (int i = 0; i < count; i++) {
            dVal=at.get(i);
            Entry entry = new Entry((float) dVal, i);
            y2.add(entry);
        }



        // y轴数据集
        LineDataSet mLineDataSet = new LineDataSet(y, "员工一周收递数量折线图");

        // 用y轴的集合来设置参数
        // 线宽
        mLineDataSet.setLineWidth(3.0f);

        // 显示的圆形大小
        mLineDataSet.setCircleSize(5.0f);

        // 折线的颜色
        mLineDataSet.setColor(Color.RED);

        // 圆球的颜色
        mLineDataSet.setCircleColor(Color.GREEN);

        // 设置mLineDataSet.setDrawHighlightIndicators(false)后，
        // Highlight的十字交叉的纵横线将不会显示，
        // 同时，mLineDataSet.setHighLightColor(Color.CYAN)失效。
        mLineDataSet.setDrawHighlightIndicators(true);

        // 按击后，十字交叉线的颜色
        mLineDataSet.setHighLightColor(Color.CYAN);

        // 设置这项上显示的数据点的字体大小。
        mLineDataSet.setValueTextSize(10.0f);

        // mLineDataSet.setDrawCircleHole(true);

        // 改变折线样式，用曲线。
        //mLineDataSet.setDrawCubic(true);
        // 默认是直线
        // 曲线的平滑度，值越大越平滑。
        //mLineDataSet.setCubicIntensity(0.2f);

        // 填充曲线下方的区域，红色，半透明。
//         mLineDataSet.setDrawFilled(true);
//         mLineDataSet.setFillAlpha(128);
//         mLineDataSet.setFillColor(Color.RED);

        // 填充折线上数据点、圆球里面包裹的中心空白处的颜色。
        mLineDataSet.setCircleColorHole(Color.YELLOW);

        // 设置折线上显示数据的格式。如果不设置，将默认显示float数据格式。
        mLineDataSet.setValueFormatter(new ValueFormatter() {

//			@Override
//			public String getFormattedValue(float value) {
//				int n = (int) value;
//				String s = "y:" + n;
//				return s;
//			}

            //            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                // TODO Auto-generated method stub
                int n = (int) value;
                // String s = "y:" + n;
                String s = "";
                return s;
            }
        });

        // y轴数据集
        LineDataSet mLineDataSet2 = new LineDataSet(y2, "员工一周投递数量折线图");

        // 用y轴的集合来设置参数
        // 线宽
        mLineDataSet2.setLineWidth(3.0f);

        // 显示的圆形大小
        mLineDataSet2.setCircleSize(5.0f);

        // 折线的颜色
        mLineDataSet2.setColor(Color.DKGRAY);

        // 圆球的颜色
        mLineDataSet2.setCircleColor(Color.GREEN);

        // 设置mLineDataSet.setDrawHighlightIndicators(false)后，
        // Highlight的十字交叉的纵横线将不会显示，
        // 同时，mLineDataSet.setHighLightColor(Color.CYAN)失效。
        mLineDataSet2.setDrawHighlightIndicators(true);

        // 按击后，十字交叉线的颜色
        mLineDataSet2.setHighLightColor(Color.CYAN);

        // 设置这项上显示的数据点的字体大小。
        mLineDataSet2.setValueTextSize(10.0f);

        // mLineDataSet.setDrawCircleHole(true);

        // 改变折线样式，用曲线。
        //mLineDataSet2.setDrawCubic(true);
        // 默认是直线
        // 曲线的平滑度，值越大越平滑。
        //mLineDataSet2.setCubicIntensity(0.2f);

        // 填充曲线下方的区域，红色，半透明。
//         mLineDataSet.setDrawFilled(true);
//         mLineDataSet.setFillAlpha(128);
//         mLineDataSet.setFillColor(Color.RED);

        // 填充折线上数据点、圆球里面包裹的中心空白处的颜色。
        mLineDataSet2.setCircleColorHole(Color.YELLOW);

        // 设置折线上显示数据的格式。如果不设置，将默认显示float数据格式。
        mLineDataSet2.setValueFormatter(new ValueFormatter() {

//			@Override
//			public String getFormattedValue(float value) {
//				int n = (int) value;
//				String s = "y:" + n;
//				return s;
//			}

            //            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                // TODO Auto-generated method stub


                int n = (int) value;
                // String s = "y:" + n;
                String s = "";
                return s;
            }
        });


        ArrayList<LineDataSet> mLineDataSets = new ArrayList<>();
        mLineDataSets.add(mLineDataSet);
        mLineDataSets.add(mLineDataSet2);

        LineData mLineData = new LineData(x, mLineDataSets);

        return mLineData;
    }



}
