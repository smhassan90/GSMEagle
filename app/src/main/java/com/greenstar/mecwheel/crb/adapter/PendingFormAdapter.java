package com.greenstar.mecwheel.crb.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.greenstar.gsmdtc.R;
import com.greenstar.gsmdtc.dao.DTCFormDeleteListener;
import com.greenstar.gsmdtc.db.AppDatabase;
import com.greenstar.gsmdtc.model.DTCForm;

import java.util.ArrayList;
import java.util.List;

public class PendingFormAdapter extends ArrayAdapter<DTCForm> implements View.OnClickListener {
    private Activity mActivity;
    private List<DTCForm> list = new ArrayList<>();
    AppDatabase db =null;
    DTCFormDeleteListener deleteForm = null;
    public PendingFormAdapter(@NonNull Activity activity, List<DTCForm> list, DTCFormDeleteListener deleteForm) {
        super(activity, 0, 0, list);
        db = AppDatabase.getAppDatabase(activity);
        mActivity = activity;
        this.deleteForm = deleteForm;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size()==0?1:list.size();
    }

    @Nullable
    @Override
    public DTCForm getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return getView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        View view=null;
        if (v == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            v = inflater.inflate(R.layout.pending_form, null);
        }
        LinearLayout llInnerBasket = v.findViewById(R.id.llInnerBasket);

        TextView tvFormId = (TextView) v.findViewById(R.id.tvFormId);
        TextView tvProviderName = (TextView) v.findViewById(R.id.tvProviderName);
        TextView tvProviderCode = (TextView) v.findViewById(R.id.tvProviderCode);
        TextView tvVisitDate = (TextView) v.findViewById(R.id.tvVisitDate);
        ImageView btnDelete = v.findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(this);
        if(list!=null && list.size()>0){
            DTCForm i = list.get(position);
            if(i!=null){
                try {
                    tvFormId.setText("Form ID : " + i.getId());
                    tvVisitDate.setText("Meeting Date : " + i.getMeetingDate());
                    tvProviderName.setText("Chair person : "+i.getChairName());
                    tvProviderCode.setText("Attending With : "+i.getAttendingWithName());
                    btnDelete.setTag(i.getId());
                }catch(Exception e){
                    Crashlytics.logException(e);
                }
            }else{

                tvFormId.setText("There is no pending form");
                tvProviderName.setVisibility(View.GONE);
                tvProviderCode.setVisibility(View.GONE);
                tvVisitDate.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
            }

        }else{
            tvFormId.setText("There is no pending form");
            tvProviderName.setVisibility(View.GONE);
            tvProviderCode.setVisibility(View.GONE);
            tvVisitDate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnDelete){
            Object obj =  v.getTag()==null?"0":v.getTag();
            long formId = (long)obj;
            if(formId!=0){
                deleteForm.deleteDTCForm(formId);
            }else{
                Toast.makeText(mActivity, "Something went wrong while deleting Form",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
