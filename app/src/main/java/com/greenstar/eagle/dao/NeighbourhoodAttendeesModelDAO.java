package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.NeighbourhoodAttendeesModel;

import java.util.List;

@Dao
public interface NeighbourhoodAttendeesModelDAO {
    @Insert
    void insertMultiple (List<NeighbourhoodAttendeesModel> neighbourhoodAttendeesModels);

    @Query("SELECT * FROM NeighbourhoodAttendeesModel")
    List<NeighbourhoodAttendeesModel> getAll();

    @Query("SELECT count(*) FROM NeighbourhoodAttendeesModel")
    int getCount();

    @Query("DELETE FROM NeighbourhoodAttendeesModel")
    public void nukeTable();
}
