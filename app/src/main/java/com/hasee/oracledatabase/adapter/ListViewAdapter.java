package com.hasee.oracledatabase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.hasee.oracledatabase.R;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private List<JSONObject> lists = new ArrayList<>();
    private Context mContext;

    public ListViewAdapter(Context mContext,List<JSONObject> lists){
        this.mContext = mContext;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View converView, ViewGroup viewGroup) {
        JSONObject jsonObject = (JSONObject) getItem(i);
        String number = null;
        String name = null;
        int age = 0;
        View view = null;
        ViewHolder viewHolder = null;
        if(converView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_list_item,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.listIdTv = (TextView)view.findViewById(R.id.list_id_tv);
            viewHolder.numberListTv = (TextView)view.findViewById(R.id.number_list_tv);
            viewHolder.nameListTv = (TextView)view.findViewById(R.id.name_list_tv);
            viewHolder.ageListTv = (TextView)view.findViewById(R.id.age_list_tv);
            view.setTag(viewHolder);//将ViewHolder对象存储在View中
        }else{
            view = converView;
            viewHolder = (ViewHolder) view.getTag();//重新获取ViewHolder
        }
        try {
            number = jsonObject.getString("s_number");
            name = jsonObject.getString("s_name");
            age = jsonObject.getInt("s_age");
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewHolder.listIdTv.setText(String.valueOf(i+1));
        viewHolder.numberListTv.setText(number);
        viewHolder.nameListTv.setText(name);
        viewHolder.ageListTv.setText(String.valueOf(age));
        return view;
    }

    class ViewHolder {
        TextView listIdTv;
        TextView numberListTv;
        TextView nameListTv;
        TextView ageListTv;
    }
}
