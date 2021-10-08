package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.NeighbourhoodFormModel;
import com.greenstar.eagle.model.Providers;

import java.util.List;

@Dao
public interface NeighbourhoodFormDAO {
    @Insert
    void insertMultiple (List<NeighbourhoodFormModel> neighbourhoodFormModels);

    @Insert
    void insert (NeighbourhoodFormModel neighbourhoodForm);

    @Query("SELECT * FROM NeighbourhoodFormModel")
    List<NeighbourhoodFormModel> getAll();

    @Query("SELECT count(*) FROM NeighbourhoodFormModel")
    int getCount();

    @Query("DELETE FROM NeighbourhoodFormModel")
    public void nukeTable();
}
