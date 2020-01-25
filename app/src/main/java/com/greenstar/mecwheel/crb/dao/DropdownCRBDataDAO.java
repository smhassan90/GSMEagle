package com.greenstar.mecwheel.crb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.mecwheel.crb.model.DropdownCRBData;

import java.util.List;

@Dao
public interface DropdownCRBDataDAO {
    @Insert
    void insertMultiple (List<DropdownCRBData> dropdownCRBData);

    @Query("SELECT * FROM DropdownCRBData")
    List<DropdownCRBData> getAll();

    @Query("SELECT * FROM DropdownCRBData where category='Referred By'")
    List<DropdownCRBData> getAllReferredBy();

    @Query("SELECT * FROM DropdownCRBData where category='Client Age'")
    List<DropdownCRBData> getAllClientAge();

    @Query("SELECT * FROM DropdownCRBData where category='Current Method'")
    List<DropdownCRBData> getAllCurrentMethod();

    @Query("SELECT * FROM DropdownCRBData where category='Timing of FP Service'")
    List<DropdownCRBData> getAllTimingFPService();

    @Query("SELECT * FROM DropdownCRBData where category='Service Type'")
    List<DropdownCRBData> getAllServiceType();

    @Query("DELETE FROM DropdownCRBData")
    public void nukeTable();
}
