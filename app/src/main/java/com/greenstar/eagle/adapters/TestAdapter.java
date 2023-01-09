package com.greenstar.eagle.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.greenstar.eagle.R;
import com.greenstar.eagle.dao.DeleteListener;
import com.greenstar.eagle.model.ScreeningTest;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder>  {
    private List<ScreeningTest> data;
    private DeleteListener deleteListener;
    private RecyclerView rvTestDetails;

    public TestAdapter(List<ScreeningTest> data, DeleteListener deleteListener, RecyclerView rvTestDetails){
        this.data = data;
        this.deleteListener = deleteListener;
        this.rvTestDetails = rvTestDetails;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_test_detail_row, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ScreeningTest obj = data.get(position);
        final int posFinal = position;
        holder.tvTestDetail.setText(obj.getTestDetail());
        holder.tvTestOutcome.setText(String.valueOf(obj.getTestOutcome()));
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListener.delete(posFinal, obj.getAreaId(), obj.getTestId(), rvTestDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTestDetail;
        private TextView tvTestOutcome;
        private Button btnDelete;

        public ViewHolder(View view) {
            super(view);
            this.tvTestDetail = view.findViewById(R.id.tvTestDetail);
            this.tvTestOutcome = view.findViewById(R.id.tvTestOutcome);
            this.btnDelete = view.findViewById(R.id.btnDelete);
        }
    }
}