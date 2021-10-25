package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.FollowupModel;

import java.util.List;

@Dao
public interface FollowupModelDAO {
    @Insert
    void insert (FollowupModel followupModel);

    @Query("SELECT * FROM FollowupModel")
    List<FollowupModel> getAll();

    @Query("DELETE FROM FollowupModel")
    public void nukeTable();

    @Query("SELECT count(*) FROM FollowupModel")
    int getCount();
}
