package com.xaqb.policescan.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xaqb.policescan.R;
import com.xaqb.policescan.Utils.ChangeUtil;
import com.xaqb.policescan.Utils.GsonUtil;
import com.xaqb.policescan.Utils.HttpUrlUtils;
import com.xaqb.policescan.Utils.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import okhttp3.Call;

public class ResultActivity extends BaseActivity {

    private ResultActivity instance;
    private String mCode;//扫描出来的运单号
    private Button mQuery;//查询的按钮
    private TextView mTvDate;//查询的日期

    private TextView mTvExpressType;//收寄类型
    private TextView mTvCode;//运单号
    private TextView mTvName;//物品名称
    private TextView mTvMancertCode;//证件号码
    private TextView mTvTime;//运单时间
    private TextView mTvAddress;//寄件地址
    private TextView mTvPhone;//联系电话
    private TextView mTvDestPhone;//收件人电话
    private TextView mTvCompany;//企业名称
    private TextView mTvCompanyCode;//企业编码
    private TextView mTvPerson;//从业人员名称
    private TextView mTvEmpcertCode;//从业人员名称
    private TextView mTvPersonCode;//从业人员编码
    private TextView mTvPersonExpCode;//从业人员快递编码

    @Override
    public void initTitleBar() {
        setTitle(R.string.query_recode);
        showBackwardView(true);
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_result);
        instance = this;
        mCode= instance.getIntent().getStringExtra("code");
        assignViews();
    }

    private void assignViews(){
        mQuery = (Button) instance.findViewById(R.id.bt_query_result);
        mTvCode = (TextView) findViewById(R.id.txt_code_result);
        mTvCode.setText(mCode);
        mTvDate = (TextView) findViewById(R.id.txt_date_result);
        mTvDate.setText(new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss").format(new Date()));
        mTvExpressType = (TextView) findViewById(R.id.txt_type_result);
        mTvName = (TextView) findViewById(R.id.txt_name_result);
        mTvMancertCode = (TextView) findViewById(R.id.txt_mancert_code_result);
        mTvTime = (TextView) findViewById(R.id.txt_time_result);
        mTvAddress= (TextView) findViewById(R.id.txt_address_result);
        mTvPhone = (TextView) findViewById(R.id.txt_phone_result);
        mTvDestPhone = (TextView) findViewById(R.id.txt_des_phone_result);
        mTvCompany = (TextView) findViewById(R.id.txt_company_result);
        mTvCompanyCode = (TextView) findViewById(R.id.txt_com_code_result);
        mTvEmpcertCode = (TextView) findViewById(R.id.txt_empcertcode_result);
        mTvPerson = (TextView) findViewById(R.id.txt_per_result);
        mTvPersonCode = (TextView) findViewById(R.id.txt_empcode_result);
        mTvPersonExpCode = (TextView) findViewById(R.id.txt_empempresscode_result);
    }


    @Override
    public void initData() {


        //进行网络请求查询订单
        OkHttpUtils
                .get()
                .url(HttpUrlUtils.getHttpUrl().quer_yCode()+"&code="+mCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        loadingDialog.dismiss();
                        showToast("网络访问异常");
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        LogUtils.i(HttpUrlUtils.getHttpUrl().quer_yCode()+"&code="+mCode);
                        if(s.startsWith("0")){
                            //suc
                            String str = ChangeUtil.procRet(s);//{"policeid":"xaqianbai","policename":"西安千百","socode":"610100000000","soname":"西安市公安局"}
                            str = str.substring(1,str.length());
                            LogUtils.i(str);
                            Map<String,Object> data =  GsonUtil.JsonToMap(str);

                            if (!mCode.equals(data.get("code").toString())){
                                mTvCode.setText(mCode+"(查无此单)");
                            }if (mCode.equals(data.get("code").toString())){
                                mTvExpressType.setText(data.get("expresstype").toString());//投递类型
                                mTvCode.setText(mCode);
                                mTvName.setText( data.get("name").toString());//物品名称
                                mTvMancertCode.setText( data.get("mancertcode").toString());//证件号码
                                mTvTime.setText( data.get("mantime").toString());//时间
                                mTvAddress.setText( data.get("manaddress").toString());//收寄地址
                                mTvPhone.setText( data.get("manphone").toString());//联系电话
                                mTvDestPhone.setText( data.get("destphone").toString());//收件人电话（只在收寄时有该字段）
                                mTvCompany.setText( data.get("comname").toString());//公司名称
                                mTvCompanyCode.setText( data.get("comcode").toString());//企业名称
                                mTvPersonCode.setText( data.get("empcode").toString());//企业编码
                                mTvEmpcertCode.setText( data.get("empcertcode").toString());//从业人员证件号
                                mTvPersonExpCode.setText( data.get("empexpresscode").toString());//从业人员快递编码
                                mTvPerson.setText(data.get("empname").toString());//从业人员名称
                            }

                        }else{
                            //failure
                        }
                    }
                });
    }

    @Override
    public void addListener() {
        mQuery.setOnClickListener(instance);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
       switch(v.getId()){
           case R.id.bt_query_result:
               intent = new Intent(instance, QueryWebviewActivity.class);
               intent.putExtra("url", "https://m.kuaidi100.com/index_all.html?postid=" + mCode);
               startActivity(intent);
            break;
       }
    }
}
