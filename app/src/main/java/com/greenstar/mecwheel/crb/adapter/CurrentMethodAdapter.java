package com.greenstar.mecwheel.crb.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.greenstar.mecwheel.R;
import com.greenstar.mecwheel.crb.model.DropdownCRBData;

import java.util.ArrayList;
import java.util.List;

public class CurrentMethodAdapter extends ArrayAdapter<DropdownCRBData> {
    private Activity mActivity;
    private List<DropdownCRBData> list = new ArrayList<>();
    public CurrentMethodAdapter(@NonNull Activity activity, int resource, int textViewResourceId, List<DropdownCRBData> list) {
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
    public DropdownCRBData getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            v = inflater.inflate(R.layout.dropdown_layout, null);
        }
        TextView lbl = (TextView) v.findViewById(R.id.tvNames);
        DropdownCRBData temp = list.get(position);

        String desc="";
        if(temp.getDetailEnglish().equals("Condom")){
            desc = "کنڈوم";
        }else if(temp.getDetailEnglish().equals("Oral Contraceptil Pill")){
            desc = "مانع حمل گولیاں";
        }else if(temp.getDetailEnglish().equals("Injectable")){
            desc = "انجیکشن";
        }else if(temp.getDetailEnglish().equals("IUCD")){
            desc = "چھلہ";
        }else if(temp.getDetailEnglish().equals("Implant")){
            desc = "بازو میں رکھا جانے والا کیپسول";
        }
        lbl.setText(temp.getDetailEnglish() + " / "+ desc);
        return v;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            v = inflater.inflate(R.layout.dropdown_view, null);
        }
        TextView lbl = (TextView) v.findViewById(R.id.tvDDName);
        lbl.setText(list.get(position).getDetailEnglish());
        return v;
    }

    public List<DropdownCRBData> getList() {
        return list;
    }

    public void setList(List<DropdownCRBData> list) {
        this.list = list;
    }
}
