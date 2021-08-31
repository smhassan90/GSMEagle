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
import com.greenstar.eagle.model.Providers;

import java.util.ArrayList;
import java.util.List;

public class ProviderAdapter extends ArrayAdapter<Providers> implements TextWatcher,SpinnerAdapter {
    private Activity mActivity;
    private List<Providers> list = new ArrayList<>();
    private List<Providers> backupList = new ArrayList<>();
    public ProviderAdapter(@NonNull Activity activity, int resource, int textViewResourceId, List<Providers> list) {
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
    public Providers getItem(int position) {
        return list.get(position);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            v = inflater.inflate(R.layout.dropdown_view, null);
        }

        TextView lbl = (TextView) v.findViewById(R.id.tvDDName);
        String code = list.get(position).getCode();
        if(code.equals("") || code.equals("0")){
            lbl.setText("Select Provider");
        }else{
            lbl.setText(list.get(position).getName()+" - "+list.get(position).getCode());
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

    public List<Providers> getList() {
        return list;
    }

    public void setList(List<Providers> list) {
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

        for(Providers provider : list){
            if(provider.getName().contains(filter) ||
                    provider.getCode().contains(filter) ){
                backupList.add(provider);
            }
        }
        this.setList(backupList);

    }
}
