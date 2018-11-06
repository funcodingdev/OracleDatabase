package com.hasee.oracledatabase.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hasee.oracledatabase.MyListener;
import com.hasee.oracledatabase.R;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UpdateFragment extends DialogFragment {
    private EditText updateFragmentNumberEt, updateFragmentNameEt, updateFragmentAgeEt;
    private Button updateSubmitButton;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private MyListener listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (MyListener)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_dialog,container,false);
        savedInstanceState = this.getArguments();
        jsonObject = (JSONObject)savedInstanceState.getSerializable("jsonItem");
        updateFragmentNumberEt = (EditText)view.findViewById(R.id.updateFragment_number_et);
        updateFragmentNameEt = (EditText)view.findViewById(R.id.updateFragment_name_et);
        updateFragmentAgeEt = (EditText)view.findViewById(R.id.updateFragment_age_et);
        updateSubmitButton = (Button)view.findViewById(R.id.update_submit_button);
        updateSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonArray = new JSONArray();
                String number = updateFragmentNumberEt.getText().toString().trim();
                String name = updateFragmentNameEt.getText().toString().trim();
                String age = updateFragmentAgeEt.getText().toString().trim();
                if("".equals(number)&&"".equals(name)&&"".equals(age)){

                }else{
                    jsonArray.add("6");
                    JSONObject jsonObject1 = new JSONObject();
                    JSONObject jsonObject2 = new JSONObject();
                    if(!"".equals(number)){
                        jsonObject1.put("1","s_number="+"'"+number+"'");
                    }else {
                        jsonObject1.put("1","");
                    }
                    if(!"".equals(name)){
                        jsonObject1.put("2","s_name="+"'"+name+"'");
                    }else {
                        jsonObject1.put("2","");
                    }
                    if(!"".equals(age)){
                        jsonObject1.put("3","s_age="+age);
                    }else {
                        jsonObject1.put("3","");
                    }
                    jsonObject2.put("1","s_number="+"'"+jsonObject.getString("s_number")+"'");
                    jsonObject2.put("2","s_name="+"'"+jsonObject.getString("s_name")+"'");
                    jsonObject2.put("3","s_age="+jsonObject.getString("s_age"));
                    jsonArray.add(jsonObject1);
                    jsonArray.add(jsonObject2);
                    listener.sendMessageToServer(jsonArray.toString());
                }
            }
        });
        return view;
    }
}
