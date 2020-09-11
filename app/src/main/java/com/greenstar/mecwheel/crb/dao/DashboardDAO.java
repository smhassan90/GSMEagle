package com.greenstar.mecwheel.crb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.mecwheel.crb.model.Dashboard;

import java.util.List;

@Dao
public interface DashboardDAO {
    @Insert
    void insertMultiple (List<Dashboard> dashboards);

    @Insert
    void insertMultiple (Dashboard dashboard);

    @Query("SELECT * FROM Dashboard WHERE ID=1")
    Dashboard getDashboard();

    @Query("DELETE FROM DropdownCRBData")
    public void nukeTable();
}
