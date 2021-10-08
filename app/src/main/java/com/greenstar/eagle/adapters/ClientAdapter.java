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
import com.greenstar.eagle.model.CRForm;

import java.util.ArrayList;
import java.util.List;

public class ClientAdapter extends ArrayAdapter<CRForm> implements TextWatcher,SpinnerAdapter {
    private Activity mActivity;
    private List<CRForm> list = new ArrayList<>();
    private List<CRForm> backupList = new ArrayList<>();
    public ClientAdapter(@NonNull Activity activity, int resource, int textViewResourceId, List<CRForm> list) {
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
    public CRForm getItem(int position) {
        return list.get(position);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            v = inflater.inflate(R.layout.dropdown_view, null);
        }

        TextView lbl = (TextView) v.findViewById(R.id.tvDDName);
        String clientName = list.get(position).getClientName();
        if(clientName.equals("") || clientName.equals("0")){
            lbl.setText("Select Client");
        }else{
            lbl.setText(list.get(position).getClientName()+" - "+list.get(position).getHusbandName()+" - "+list.get(position).getContactNumber());
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

    public List<CRForm> getList() {
        return list;
    }

    public void setList(List<CRForm> list) {
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

        for(CRForm client : list){
            if(client.getClientName().contains(filter) ||
                    client.getHusbandName().contains(filter) ||
                    client.getContactNumber().contains(filter) ){
                backupList.add(client);
            }
        }
        this.setList(backupList);

    }
}
