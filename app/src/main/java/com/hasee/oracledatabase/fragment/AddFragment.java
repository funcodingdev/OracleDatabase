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

public class AddFragment extends DialogFragment {
    private static final String TAG = "AddFragment";
    private EditText addFragmentNumberEt, addFragmentNameEt, addFragmentAgeEt;
    private Button addSubmitButton;
    private JSONArray jsonArray;
    private MyListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (MyListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_dialog,container,false);
        addFragmentNumberEt = (EditText)view.findViewById(R.id.addFragment_number_et);
        addFragmentNameEt = (EditText)view.findViewById(R.id.addFragment_name_et);
        addFragmentAgeEt = (EditText)view.findViewById(R.id.addFragment_age_et);
        addSubmitButton = (Button)view.findViewById(R.id.add_submit_button);
        addSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonArray = new JSONArray();
                String number = addFragmentNumberEt.getText().toString().trim();
                String name = addFragmentNameEt.getText().toString().trim();
                String age = addFragmentAgeEt.getText().toString().trim();
                if("".equals(number)||"".equals(name)||"".equals(age)){

                }else{
                    jsonArray.add("4");
                    jsonArray.add(number);
                    jsonArray.add(name);
                    jsonArray.add(age);
                    listener.sendMessageToServer(jsonArray.toString());
                }
            }
        });
        return view;
    }

}
