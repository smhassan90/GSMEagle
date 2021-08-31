package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.greenstar.eagle.model.QATAreaDetail;

import java.util.List;

@Dao
public interface AreaDetailDAO {
    @Insert
    void insertMultiple(List<QATAreaDetail> areaDetails);

    @Query("SELECT * FROM QATAreaDetail")
    List<QATAreaDetail> getAll();

    @Query("SELECT * FROM QATAreaDetail WHERE formId IN(:formIds)")
    List<QATAreaDetail> getAllPending(List<Long> formIds);

    @Query("SELECT * FROM QATAreaDetail WHERE formId =:formId")
    List<QATAreaDetail> getSingleFormAreas(long formId);

    @Query("SELECT * FROM QATAreaDetail")
    List<QATAreaDetail> getAreaDetails();

    @Query("DELETE FROM QATAreaDetail")
    public void nukeTable();
}
