package com.hasee.oracledatabase.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hasee.oracledatabase.MyListener;
import com.hasee.oracledatabase.R;

import net.sf.json.JSONArray;

public class SelectSingleFragment extends DialogFragment {
    private TextView selectSingleFragmentNumberTv, selectSingleFragmentNameTv, selectSingleFragmentAgeTv;
    private Button selectSingleSubmitButton;
    private Spinner selectSingleSpinner;
    private String[] selectItem = new String[]{"=",">","<",">=","<="};
    private ArrayAdapter<String> adapter;
    private String item = "=";
    private JSONArray jsonArray;
    private MyListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (MyListener)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_single_dialog,container,false);
        selectSingleFragmentNumberTv = (EditText)view.findViewById(R.id.selectSingleFragment_number_tv);
        selectSingleFragmentNameTv = (EditText)view.findViewById(R.id.selectSingleFragment_name_tv);
        selectSingleFragmentAgeTv = (EditText)view.findViewById(R.id.selectSingleFragment_age_tv);
        selectSingleSpinner = (Spinner)view.findViewById(R.id.selectSingle_spinner);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,selectItem);
        selectSingleSpinner.setAdapter(adapter);
        selectSingleSpinner.setOnItemSelectedListener(onItemSelectedListener);
        selectSingleSubmitButton = (Button)view.findViewById(R.id.selectSingle_submit_button);
        selectSingleSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonArray = new JSONArray();
                String number = selectSingleFragmentNumberTv.getText().toString().trim();
                String name = selectSingleFragmentNameTv.getText().toString().trim();
                String age = selectSingleFragmentAgeTv.getText().toString().trim();
                jsonArray.add("7");
                if(!"".equals(number)){
                    jsonArray.add("s_number="+"'"+number+"'");
                }
                if (!"".equals(name)){
                    jsonArray.add("s_name="+"'"+name+"'");
                }
                if(!"".equals(age)){
                    jsonArray.add("s_age"+item+age);
                }
                listener.sendMessageToServer(jsonArray.toString());
            }
        });
        return view;
    }

    public AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            switch (i){
                case 0:
                    item = "=";
                    break;
                case 1:
                    item = ">";
                    break;
                case 2:
                    item = "<";
                    break;
                case 3:
                    item = ">=";
                    break;
                case 4:
                    item = "<=";
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
}
