package com.greenstar.eagle.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.greenstar.eagle.R;
import com.greenstar.eagle.model.Questions;

import java.util.ArrayList;
import java.util.List;

public class TestAdapter extends ArrayAdapter<Questions> implements TextWatcher, SpinnerAdapter {
    private Activity mActivity;
    private List<Questions> list = new ArrayList<>();
    private List<Questions> backupList = new ArrayList<>();
    public TestAdapter(@NonNull Activity activity, int resource, int textViewResourceId, List<Questions> list) {
        super(activity, resource, textViewResourceId, list);
        mActivity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Questions getItem(int position) {
        return list.get(position);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            v = inflater.inflate(R.layout.dropdown_view, null);
        }

        TextView lbl = (TextView) v.findViewById(R.id.tvDDName);
        String clientName = list.get(position).getDetail();
        if(clientName.equals("") || clientName.equals("0")){
            lbl.setText("Select Client");
        }else{
            lbl.setText(list.get(position).getDetail());
        }

        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return initView(position,convertView,parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    public List<Questions> getList() {
        return list;
    }

    public void setList(List<Questions> list) {
        this.list = list;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        filter(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    private void filter(String filter){

        //list = new ArrayList<>();
        list = getList();
        if("".equals(filter)){
            list = list;
            return;
        }

        for(Questions client : list){
            if(client.getDetail().contains(filter) ){
                backupList.add(client);
            }
        }
        this.setList(backupList);

    }
}
