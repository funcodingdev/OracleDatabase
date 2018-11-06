package com.hasee.oracledatabase.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hasee.oracledatabase.R;

import net.sf.json.JSONObject;


public class ShowMsgFragment extends DialogFragment {
    private JSONObject jsonObject;
    private TextView showDialog_key1,showDialog_key2,showDialog_key3;
    private TextView showDialog_value1,showDialog_value2,showDialog_value3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.showmsg_dialog,container,false);
        savedInstanceState = getArguments();
        jsonObject = (JSONObject)savedInstanceState.getSerializable("jsonItem");
        showDialog_key1 = (TextView)view.findViewById(R.id.showDialog_key1);
        showDialog_key2 = (TextView)view.findViewById(R.id.showDialog_key2);
        showDialog_key3 = (TextView)view.findViewById(R.id.showDialog_key3);
        showDialog_value1 = (TextView)view.findViewById(R.id.showDialog_value1);
        showDialog_value2 = (TextView)view.findViewById(R.id.showDialog_value2);
        showDialog_value3 = (TextView)view.findViewById(R.id.showDialog_value3);
        init();
        return view;
    }

    private void init() {
        showDialog_key1.setText("s_number");
        showDialog_key2.setText("s_name");
        showDialog_key3.setText("s_age");
        showDialog_value1.setText(jsonObject.getString("s_number"));
        showDialog_value2.setText(jsonObject.getString("s_name"));
        showDialog_value3.setText(jsonObject.getString("s_age"));
    }
}
