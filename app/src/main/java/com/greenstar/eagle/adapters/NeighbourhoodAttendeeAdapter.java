package com.greenstar.eagle.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.greenstar.eagle.R;
import com.greenstar.eagle.controller.Codes;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.CRForm;
import com.greenstar.eagle.model.NeighbourhoodAttendeesModel;

import java.util.ArrayList;
import java.util.List;

public class NeighbourhoodAttendeeAdapter extends ArrayAdapter<NeighbourhoodAttendeesModel> {
    private Activity mActivity;
    private List<NeighbourhoodAttendeesModel> list = new ArrayList<>();
    AppDatabase db =null;
    public NeighbourhoodAttendeeAdapter(@NonNull Activity activity, int resource, int textViewResourceId, List<NeighbourhoodAttendeesModel> list) {
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
    public NeighbourhoodAttendeesModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return getView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        db = AppDatabase.getAppDatabase(mActivity);

        View v = convertView;

        CRForm client = db.getCrFormDAO().getFormByID(list.get(position).getClientId());
        if(client==null || client.getClientName().equals("") ||
        client.getClientName().equals(Codes.MESSAGE)){
            LayoutInflater inflater = mActivity.getLayoutInflater();
            v = inflater.inflate(R.layout.message, null);
            TextView tvMessage = v.findViewById(R.id.tvMessage);
            tvMessage.setText(Codes.MESSAGE);
        }else {
                LayoutInflater inflater = mActivity.getLayoutInflater();
                v = inflater.inflate(R.layout.neighbourhood_attendee_list, null);

            // inflate other items here :
            ImageButton deleteButton = (ImageButton) v.findViewById(R.id.btnDelete);
            deleteButton.setTag(position);

            deleteButton.setOnClickListener(
                    new Button.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Integer index = (Integer) v.getTag();
                            list.remove(index.intValue());

                            notifyDataSetChanged();
                        }
                    }
            );
            TextView tvContactNumber = (TextView) v.findViewById(R.id.tvContactNumber);
            TextView tvName = (TextView) v.findViewById(R.id.tvName);
            tvName.setText("Attendee Name : "+client.getClientName());
            tvContactNumber.setText(" : " +client.getContactNumber());
        }
        return v;
    }

    public List<NeighbourhoodAttendeesModel> getList() {
        return list;
    }

    public void setList(List<NeighbourhoodAttendeesModel> list) {
        this.list = list;
    }
}
