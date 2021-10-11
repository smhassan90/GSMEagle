package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.FollowupModel;

@Dao
public interface FollowupModelDAO {
    @Insert
    void insert (FollowupModel followupModel);

    @Query("SELECT * FROM FollowupModel")
    FollowupModel getAll();

    @Query("DELETE FROM FollowupModel")
    public void nukeTable();
}
