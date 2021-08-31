package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.Providers;

import java.util.List;

@Dao
public interface ProvidersDAO {
    @Insert
    void insertMultiple (List<Providers> providers);

    @Query("SELECT * FROM Providers")
    List<Providers> getAll();

    @Query("SELECT count(*) FROM Providers")
    int getCount();

    @Query("SELECT * FROM Providers where status=1")
    List<Providers> getActiveProviders();

    @Query("DELETE FROM Providers")
    public void nukeTable();
}
