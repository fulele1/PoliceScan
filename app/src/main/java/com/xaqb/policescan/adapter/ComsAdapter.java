package com.xaqb.policescan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xaqb.policescan.R;

import java.util.List;

/**
 * Created by lenovo on 2017/3/22.
 */

public class ComsAdapter extends BaseAdapter {
    private Context context;
    private List<String> coms;
    public ComsAdapter(Context context, List<String> coms) {
        this.context = context;
        this.coms = coms;
    }

    @Override
    public int getCount() {
        return coms.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
     ViewHolders holders;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (null==view){
            holders = new ViewHolders();
            view = LayoutInflater.from(context).inflate(R.layout.list_coms,null);
            holders.txt = (TextView) view.findViewById(R.id.textView_list_coms);
            view.setTag(holders);
        }else {
            holders = (ViewHolders) view.getTag();
        }
        holders.txt.setText(coms.get(i));
        return view;
    }
}

class ViewHolders{
    TextView txt;
}