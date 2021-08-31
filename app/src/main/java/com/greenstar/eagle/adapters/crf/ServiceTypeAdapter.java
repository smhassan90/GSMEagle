package com.greenstar.eagle.adapters.crf;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.greenstar.eagle.R;
import com.greenstar.eagle.model.DropdownCRBData;

import java.util.ArrayList;
import java.util.List;

public class ServiceTypeAdapter extends ArrayAdapter<DropdownCRBData> {
    private Activity mActivity;
    private List<DropdownCRBData> list = new ArrayList<>();
    public ServiceTypeAdapter(@NonNull Activity activity, int resource, int textViewResourceId, List<DropdownCRBData> list) {
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

        if(temp.getDetailEnglish().equals("FP Counseling")){
            desc = "فیملی پلاننگ کے متعلق مشورہ";
        }else if(temp.getDetailEnglish().equals("Condom")){
            desc = "کنڈوم";
        }else if(temp.getDetailEnglish().equals("OCP")){
            desc = "مانع حمل گولیاں";
        }else if(temp.getDetailEnglish().equals("Injectable")){
            desc = "انجیکشن";
        }else if(temp.getDetailEnglish().equals("IUCD")){
            desc = "چھلہ";
        }else if(temp.getDetailEnglish().equals("Implant")){
            desc = "بازو میں رکھا جانے والا کیپسول";
        }else if(temp.getDetailEnglish().equals("Tubal Ligation")){
            desc = "نل بندی";
        }else if(temp.getDetailEnglish().equals("Vasectomy")){
            desc = "نس بندی";
        }else if(temp.getDetailEnglish().equals("ECP")){
            desc = "ایمرجنسی مانع حمل گولیاں";
        }else if(temp.getDetailEnglish().equals("PAC-MVA")){
            desc = "اسقاطِ حمل کے بعد  انجیکشن کے ذریعے صفائی";
        }else if(temp.getDetailEnglish().equals("PAC-MISO")){
            desc = "اسقاطِ حمل کے لئے  MISO  کا استعمال";
        }else if(temp.getDetailEnglish().equals("PAC-D&C")){
            desc = "اسقاطِ حمل اوزاروں کے ذریعے";
        }else if(temp.getDetailEnglish().equals("Antenatal")){
            desc = "قبل از پیدائش";
        }else if(temp.getDetailEnglish().equals("Delivery")){
            desc = "پیدائش";
        }else if(temp.getDetailEnglish().equals("Post Natal")){
            desc = "بعد از پیدائش";
        }

        lbl.setText(temp.getDetailEnglish()+" / "+desc);
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
