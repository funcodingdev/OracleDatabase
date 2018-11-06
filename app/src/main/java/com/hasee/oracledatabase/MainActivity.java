package com.hasee.oracledatabase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hasee.oracledatabase.adapter.ListViewAdapter;
import com.hasee.oracledatabase.fragment.AddFragment;
import com.hasee.oracledatabase.fragment.DeleteFragment;
import com.hasee.oracledatabase.fragment.HandleDialog;
import com.hasee.oracledatabase.fragment.ShowMsgFragment;
import com.hasee.oracledatabase.fragment.UpdateFragment;
import com.hasee.oracledatabase.util.SocketUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyListener {
    private static final String TAG = "MainActivity";
    private ProgressDialog progressDialog = null;
    private Toolbar toolbar = null;
    private DrawerLayout drawerLayout = null;
    private TextView settingTv = null;
    private TextView aboutTv = null;
    private TextView listViewTip = null;
    private LinearLayout tableTileLayout = null;
    private ListView listView = null;
    private ListViewAdapter adapter = null;
    private SwipeRefreshLayout refreshLayout = null;
    private List<JSONObject> objectList = new ArrayList<>();//存放查询的所有数据
    private List<String> stringList = new ArrayList<>();//存放表名
    private SocketUtil socketUtil = null;
    private Myhandler myhandler = new Myhandler();
    private boolean isDrawer = false;
    private AddFragment addFragment;//添加页面
    private DeleteFragment deleteFragment;//删除页面
    private UpdateFragment updateFragment;//更新页面
    private HandleDialog handleDialog = new HandleDialog();//处理框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerListener(drawerListener);
        drawerLayout.setTag("main1_layout");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
//        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
//        refreshLayout.setColorSchemeColors(Color.WHITE);
//        refreshLayout.setOnRefreshListener(onRefreshListener);
        settingTv = (TextView) findViewById(R.id.setting_tv);
        settingTv.setOnClickListener(this);
        aboutTv = (TextView) findViewById(R.id.about_tv);
        aboutTv.setOnClickListener(this);
        tableTileLayout = (LinearLayout) findViewById(R.id.tableTitle_layout);
        listViewTip = (TextView) findViewById(R.id.listView_tip);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(onItemClickListener);
        adapter = new ListViewAdapter(MainActivity.this, objectList);
        listView.setAdapter(adapter);
        listView.setEmptyView(listViewTip);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.clear_data://清空本地缓存
                objectList.clear();
                adapter.notifyDataSetChanged();
                showToast("缓存已清空");
                break;
            case R.id.add_menu://添加数据
                addFragment = new AddFragment();
                addFragment.show(getSupportFragmentManager(), "add_dialog");
                break;
            case R.id.delete_menu:
                deleteFragment = new DeleteFragment();
                deleteFragment.show(getSupportFragmentManager(), "delete_dialog");
                break;
            case R.id.update_menu:
//                updateFragment = new UpdateFragment();
//                updateFragment.show(getSupportFragmentManager(), "update_dialog");
                break;
            case R.id.select_menu_all:
                JSONArray jsonArray = new JSONArray();
                jsonArray.add(3);
                sendMessageToServer(jsonArray.toString());
                break;
            case R.id.select_menu_single:
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        JSONArray jsonArray = new JSONArray();
//                        jsonArray.add("1");
//                        socketUtil.connected();
//                        String message = socketUtil.send_receive(jsonArray.toString());
//                        Log.d(TAG, "run: " + message);
//                        if ("error".equals(message)) {//查询失败
//
//                        } else {//查询成功则把数据传送给Fragment
//                            Bundle bundle = new Bundle();
//                            bundle.putString("tableName", message);
//                            SelectFragment selectFragment = new SelectFragment();
////                            selectFragment.setCancelable(false);
//                            selectFragment.setArguments(bundle);
//                            selectFragment.show(getSupportFragmentManager(), "select_dialog");
//                        }
//                    }
//                }).start();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    /*
    * 更新所有数据
    * */
    public void selectAllData(){
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(3);
        sendMessageToServer(jsonArray.toString());
    }

    /*
     * 主页面listview事件处理
     * */
    public AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("jsonItem",objectList.get(i));
            handleDialog = new HandleDialog();
            handleDialog.setArguments(bundle);
            handleDialog.show(getSupportFragmentManager(),"handle_dialog");
        }
    };

    /*
     * 事件点击
     * */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_tv:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.about_tv:
                Intent intent1 = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent1);
                break;
        }
    }

//    /*
//     * 刷新事件监听器
//     * */
//    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
//        @Override
//        public void onRefresh() {
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.add(3);
//            sendMessageToServer(jsonArray.toString());
//        }
//    };

    /*
     * 页面滑动效果
     * */
    public DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View view, float v) {
            isDrawer = true;
            if ("main1_layout".equals(drawerLayout.getTag())) {
                View content = drawerLayout.getChildAt(0);
                int offSet = (int) (view.getWidth() * v);
                content.setTranslationX(offSet);
//                content.setScaleX(1 - v * 0.5f);
//                content.setScaleY(1 - v * 0.5f);
            }
        }

        @Override
        public void onDrawerOpened(@NonNull View view) {

        }

        @Override
        public void onDrawerClosed(@NonNull View view) {
            isDrawer = false;
        }

        @Override
        public void onDrawerStateChanged(int i) {

        }
    };

    /*
     * 显示进度对话框
     * */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /*
     * 关闭进度对话框
     * */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /*
     * 处理Fragment传过来的数据
     * */
    @Override
    public void sendMessageToServer(final String message) {
        if(handleDialog.isVisible()){
            handleDialog.dismiss();
        }
        showProgressDialog();
        socketUtil = new SocketUtil();
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONArray jsonArray = null;
                socketUtil.connected();
                if (socketUtil.getFlag()) {//连接成功
                    String receive_meaasge = socketUtil.send_receive(message);
                    Log.d(TAG, "run: " + receive_meaasge);
                    jsonArray = JSONArray.fromObject(receive_meaasge);
                    socketUtil.closeAll();
                } else {//连接失败
                    jsonArray = new JSONArray();
                    jsonArray.add("0");
                }
                Message msg = myhandler.obtainMessage();
                msg.obj = jsonArray;
                myhandler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    public void sendMessage(JSONObject jsonObject) {
        handleDialog.dismiss();
        int flag = jsonObject.getInt("handle");
        Bundle bundle = new Bundle();
        bundle.putSerializable("jsonItem",jsonObject);
        switch (flag){
            case 0:
                ShowMsgFragment showMsgFragment = new ShowMsgFragment();
                showMsgFragment.setArguments(bundle);
                showMsgFragment.show(getSupportFragmentManager(),"showMsg_dialog");
                break;
            case 1:
                updateFragment = new UpdateFragment();
                updateFragment.setArguments(bundle);
                updateFragment.show(getSupportFragmentManager(),"update_dialog");
                break;
        }
    }

    /*
     * 处理数据显示UI
     * */
    class Myhandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
//            refreshLayout.setRefreshing(false);
            JSONArray jsonArray = (JSONArray) msg.obj;
            switch (Integer.parseInt(jsonArray.getString(0))) {
                case 0://操作失败
                    if (progressDialog.isShowing()) {
                        showToast("操作失败！");
                    }
                    closeProgressDialog();
                    break;
                case 1:
                    if (addFragment != null) {
                        addFragment.dismiss();
                    }
                    if (deleteFragment != null) {
                        deleteFragment.dismiss();
                    }
                    if(updateFragment != null){
                        updateFragment.dismiss();
                    }
                    closeProgressDialog();
                    selectAllData();
                    showToast("操作成功");
                    break;
                case 103:
                    //查询全部信息
                    objectList.clear();
                    for (int i = 1; i < jsonArray.size(); i++) {
                        JSONObject object = JSONObject.fromObject(jsonArray.get(i));
                        objectList.add(object);
                    }
                    adapter.notifyDataSetChanged();
                    listView.setSelection(0);
//                    showToast("查询到" + (jsonArray.size() - 1) + "条");
                    closeProgressDialog();
                    break;
            }
        }
    }

    /*
     * 显示提示信息
     * */
    public void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

