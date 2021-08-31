package com.greenstar.eagle.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.greenstar.eagle.R;
import com.greenstar.eagle.dao.FormDeleteListener;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.QTVForm;

import java.util.ArrayList;
import java.util.List;

public class PendingFormAdapter extends ArrayAdapter<QTVForm> implements View.OnClickListener {
    private Activity mActivity;
    private List<QTVForm> list = new ArrayList<>();
    AppDatabase db =null;
    FormDeleteListener deleteForm = null;
    public PendingFormAdapter(@NonNull Activity activity,  List<QTVForm> list, FormDeleteListener deleteForm) {
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
    public QTVForm getItem(int position) {
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
        Button btnSyncSingle = v.findViewById(R.id.btnSyncSingle);
        btnSyncSingle.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        if(list!=null && list.size()>0){
            QTVForm i = list.get(position);
            if(i!=null){
                try {
                    tvFormId.setText("Form ID : " + i.getId());
                    tvProviderName.setText("Provider Name : " + i.getProviderName());
                    tvProviderCode.setText("Provider Code : " + i.getProviderCode());
                    tvVisitDate.setText("Visit Date : " + i.getVisitDate());

                    btnDelete.setTag(i.getId());
                    btnSyncSingle.setTag(i.getId());
                }catch(Exception e){
                    Crashlytics.logException(e);
                }
            }else{

                tvFormId.setText("There is no pending form");
                tvProviderName.setVisibility(View.GONE);
                tvProviderCode.setVisibility(View.GONE);
                tvVisitDate.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                btnSyncSingle.setVisibility(View.GONE);
            }

        }else{
            tvFormId.setText("There is no pending form");
            tvProviderName.setVisibility(View.GONE);
            tvProviderCode.setVisibility(View.GONE);
            tvVisitDate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
            btnSyncSingle.setVisibility(View.GONE);
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnDelete){
            Object obj =  v.getTag()==null?"0":v.getTag();
            long formId = (long)obj;
            if(formId!=0){
                deleteForm.deleteForm(formId);
            }else{
                Toast.makeText(mActivity, "Something went wrong while deleting Form",Toast.LENGTH_SHORT).show();
            }
        }else if(v.getId()==R.id.btnSyncSingle){
            Object obj =  v.getTag()==null?"0":v.getTag();
            long formId = (long)obj;
            if(formId!=0){
                deleteForm.SyncForm(formId);
            }else{
                Toast.makeText(mActivity, "Something went wrong while Syncing Form",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
