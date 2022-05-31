package com.greenstar.eagle.adapters.crf.submittedforms;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greenstar.eagle.R;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.CRForm;

import java.util.ArrayList;
import java.util.List;

public class RejectedFormAdapter extends ArrayAdapter<CRForm> {
    private Activity mActivity;
    private List<CRForm> list = new ArrayList<>();
    AppDatabase db =null;
    public RejectedFormAdapter(@NonNull Activity activity, List<CRForm> list) {
        super(activity, 0, 0, list);
        db = AppDatabase.getAppDatabase(activity);
        mActivity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size()==0?1:list.size();
    }

    @Nullable
    @Override
    public CRForm getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return getView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            v = inflater.inflate(R.layout.rejected_form, null);
        }
        LinearLayout llInnerBasket = v.findViewById(R.id.llInnerBasket);

        TextView tvFormId = (TextView) v.findViewById(R.id.tvFormId);
        TextView tvProviderName = (TextView) v.findViewById(R.id.tvProviderName);
        TextView tvProviderCode = (TextView) v.findViewById(R.id.tvProviderCode);
        TextView tvVisitDate = (TextView) v.findViewById(R.id.tvVisitDate);
        if(list!=null && list.size()>0){
            CRForm i = list.get(position);
            if(i!=null){
                try {
                    tvFormId.setText("Form ID : " + i.getId());
//                    tvProviderName.setText("Provider Name : " + i.getProviderName());
//                    tvProviderCode.setText("Provider Code : " + i.getProviderCode());
                    tvVisitDate.setText("Visit Date : " + i.getVisitDate());
                }catch(Exception e){
                }
            }else{

                tvFormId.setText("There is no Rejected form");
                tvProviderName.setVisibility(View.GONE);
                tvProviderCode.setVisibility(View.GONE);
                tvVisitDate.setVisibility(View.GONE);
            }

        }else{
            tvFormId.setText("There is no Rejected form");
            tvProviderName.setVisibility(View.GONE);
            tvProviderCode.setVisibility(View.GONE);
            tvVisitDate.setVisibility(View.GONE);
        }
        return v;
    }
}
