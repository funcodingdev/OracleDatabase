package com.hasee.oracledatabase.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hasee.oracledatabase.R;
import com.hasee.oracledatabase.util.SocketUtil;

import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class SelectFragment extends DialogFragment {
    private static final String TAG = "SelectFragment";
    private final int LEVEL_TABLENAME = 0;
    private final int LEVEL_COLNAME = 1;
    private int currentLevel = 0;
    private Button selectFragmentBackButton;
    private TextView selectFragmentTv;
    private ListView selectListview;
    private ArrayAdapter<String> adapter;
    private List<String> lists = new ArrayList<>();
    private SocketUtil socketUtil;
    private JSONArray jsonArray;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_dialog, container, false);
        selectFragmentBackButton = (Button) view.findViewById(R.id.selectFragment_back_button);
        selectFragmentBackButton.setOnClickListener(onClickListener);
        selectListview = (ListView) view.findViewById(R.id.select_dialog_listview);
        selectFragmentTv = (TextView) view.findViewById(R.id.selectFragment_tv);
        init(savedInstanceState);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, lists);
        selectListview.setAdapter(adapter);
        selectListview.setOnItemClickListener(onItemClickListener);
        return view;
    }

    public AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
            if (currentLevel == LEVEL_TABLENAME) {
                currentLevel = 1;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String tabelName = lists.get(i);
                        jsonArray = new JSONArray();
                        jsonArray.add("2");
                        jsonArray.add(tabelName);
                        socketUtil = new SocketUtil();
                        socketUtil.connected();
                        String receive_meaasge = socketUtil.send_receive(jsonArray.toString());
                        Log.d(TAG, "run: "+receive_meaasge);
                        jsonArray = JSONArray.fromObject(receive_meaasge);
                        lists.clear();
                        Log.d(TAG, "listsSize: "+lists.size());
                        for (int j = 0; j < jsonArray.size(); j++) {
                            lists.add(jsonArray.getString(j));
                        }
                        Log.d(TAG, "listsSize: "+lists.size());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                selectFragmentBackButton.setVisibility(View.VISIBLE);
                                selectFragmentTv.setText("列名");
                                adapter.notifyDataSetChanged();
                                selectListview.setSelection(0);
                            }
                        });
                    }
                }).start();
            }else if(currentLevel == LEVEL_COLNAME){

            }
        }
    };

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.selectFragment_back_button:
                    break;
            }
        }
    };


    //根据Activity传送的数据初始化lists
    public void init(Bundle savedInstanceState) {
        selectFragmentTv.setText("表名");
        savedInstanceState = getArguments();
        String message = savedInstanceState.getString("tableName");
        Log.d(TAG, "init: " + message);
        JSONArray jsonArray = JSONArray.fromObject(message);
        for (int i = 0; i < jsonArray.size(); i++) {
            lists.add(jsonArray.getString(i));
        }
        currentLevel = 0;
    }

}
