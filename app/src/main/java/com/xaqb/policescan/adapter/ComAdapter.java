package com.xaqb.policescan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xaqb.policescan.R;
import com.xaqb.policescan.entity.Company;
import com.xaqb.policescan.entity.Person;

import java.util.List;

/**
 * Created by lenovo on 2017/3/15.
 */

public class ComAdapter extends BaseAdapter{

    private Context mContext;
    private List<Company> mCompany;
    public ComAdapter(Context context, List<Company> company) {
        mContext = context;
        mCompany = company;
    }

    @Override
    public int getCount() {
        return mCompany.size();
    }

    @Override
    public Object getItem(int i) {
        return mCompany.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    MyViewHolder holder;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            holder = new MyViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.com_list,null);
            holder.tvCom = (TextView) view.findViewById(R.id.tv_com_com_list);
            view.setTag(holder);
        }else{
            holder = (MyViewHolder) view.getTag();
        }
        holder.tvCom.setText(mCompany.get(i).getCom());

        return view;
    }
}
class MyViewHolder {
    TextView tvCom;
}
