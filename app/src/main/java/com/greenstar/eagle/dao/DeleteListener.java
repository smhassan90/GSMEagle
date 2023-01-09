package com.greenstar.eagle.dao;

import android.support.v7.widget.RecyclerView;

public interface DeleteListener {
    public void delete(int id, int areaId, long testId, RecyclerView rvTestDetails);
}
