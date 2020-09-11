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

public class TimingFPServiceAdapter extends ArrayAdapter<DropdownCRBData> {
    private Activity mActivity;
    private List<DropdownCRBData> list = new ArrayList<>();
    public TimingFPServiceAdapter(@NonNull Activity activity, int resource, int textViewResourceId, List<DropdownCRBData> list) {
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

        String desc = "";

        if(temp.getDetailEnglish().equals("Interval")){
            desc = "وقفہ";
        }else if(temp.getDetailEnglish().equals("Intraceasarian")){
            desc = "آپریشن کے دوران";
        }else if(temp.getDetailEnglish().equals("Post Placental Within 10mins")){
            desc = "آنول نکلنے کے دس منٹ کے اندر";
        }else if(temp.getDetailEnglish().equals("Post Partum 10mins to 48 hours")){
            desc = "بچے کی پیدائش کے بعد 10منٹ سے 48گھنٹے کے دوران";
        }else if(temp.getDetailEnglish().equals("Post Partum 48 hours to 40 days")){
            desc = "بچے کی پیدائش کے 48گھنٹے سے 40دن کے اندر";
        }else if(temp.getDetailEnglish().equals("Post Partum 40 Days to 1 Year")){
            desc = "بچے کی پیدائش کے 40دن سے ایک سال کے دوران";
        }else if(temp.getDetailEnglish().equals("Post Abortion (Within 24 Hours)")){
            desc = "اسقاطِ حمل کے 24گھنٹے کے اندر";
        }else if(temp.getDetailEnglish().equals("Post Abortion (Within 7 Days)")){
            desc = "اسقاطِ حمل کے بعد 7 دن کے دوران";
        }else if(temp.getDetailEnglish().equals("Emergency FP")){
            desc = "ایمرجنسی مانع حمل  طریقہ";
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
