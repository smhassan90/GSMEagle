package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.Area;

import java.util.List;

@Dao
public interface AreaDAO {
    @Insert
    void insertMultiple(List<Area> areaList);

    @Query("SELECT * FROM Area")
    List<Area> getAll();

    @Query("SELECT count(*) FROM Area")
    int getCount();

    @Query("SELECT * FROM Area where status=1")
    List<Area> getActiveAreas();

    @Query("DELETE FROM Area")
    public void nukeTable();
}
