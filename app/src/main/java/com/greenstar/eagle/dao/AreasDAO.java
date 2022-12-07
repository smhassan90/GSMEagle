package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.hardware.Camera;

import com.greenstar.eagle.model.Areas;
import com.greenstar.eagle.model.Questions;

import java.util.List;
@Dao
public interface AreasDAO {
    @Insert
    void insert (Areas areas);

    @Insert
    void insertMultiple (List<Areas> areas);

    @Query("SELECT * FROM Areas")
    List<Areas> getAll();

    @Query("DELETE FROM Areas")
    public void nukeTable();

    @Query("SELECT count(*) FROM Areas")
    int getCount();
}
