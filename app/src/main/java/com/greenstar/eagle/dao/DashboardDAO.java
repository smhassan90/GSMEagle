package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.Dashboard;

@Dao
public interface DashboardDAO {
    @Insert
    void insert (Dashboard dashboard);

    @Query("SELECT * FROM Dashboard")
    Dashboard getAll();

    @Query("DELETE FROM Dashboard")
    public void nukeTable();
}
