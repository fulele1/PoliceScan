package com.xaqb.policescan.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
import com.xaqb.policescan.adapter.ComsAdapter;
import com.xaqb.policescan.db.SQLdm;
import com.xaqb.policescan.entity.Coms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchComsActivity extends BaseActivity {


    private List<Coms> mComsList;
    private ListView mList;
    private SearchComsActivity instance;
    @Override
    public void initTitleBar() {
        setTitle("品牌列表");
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

        mComsList = new ArrayList<>();
        SQLdm s = new SQLdm();
        SQLiteDatabase db =s.openDatabase(getApplicationContext());

        Cursor cursor = db.query("BRAND",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            Coms coms = new Coms();
            coms.setBccode(cursor.getString(cursor.getColumnIndex("BCCODE")));
            coms.setBcname(cursor.getString(cursor.getColumnIndex("BCNAME")));
            mComsList.add(coms);
        }
        cursor.close();
        mList.setAdapter(new ComsAdapter(instance,mComsList));
    }


    @Override
    public void addListener() {

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("coms",mComsList.get(i).getBcname());
                bundle.putString("code",mComsList.get(i).getBccode());
                intent.putExtras(bundle);
                instance.setResult(RESULT_OK,intent);
                SearchComsActivity.this.finish();
            }
        });
    }
}
