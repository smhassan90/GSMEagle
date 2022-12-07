package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.ScreeningAreaDetail;

import java.util.List;

@Dao
public interface ScreeningAreaDetailDAO {
    @Insert
    void insertMultiple(List<ScreeningAreaDetail> areaDetails);

    @Query("SELECT * FROM ScreeningAreaDetail")
    List<ScreeningAreaDetail> getAll();

    @Query("SELECT * FROM ScreeningAreaDetail WHERE formId IN(:formIds)")
    List<ScreeningAreaDetail> getAllPending(List<Long> formIds);

    @Query("SELECT * FROM ScreeningAreaDetail WHERE formId =:formId")
    List<ScreeningAreaDetail> getSingleFormAreas(long formId);

    @Query("SELECT * FROM ScreeningAreaDetail")
    List<ScreeningAreaDetail> getAreaDetails();

    @Query("DELETE FROM ScreeningAreaDetail")
    public void nukeTable();
}
