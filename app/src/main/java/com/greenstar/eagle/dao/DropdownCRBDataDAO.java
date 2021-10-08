package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.DropdownCRBData;

import java.util.List;

@Dao
public interface DropdownCRBDataDAO {
    @Insert
    void insertMultiple (List<DropdownCRBData> dropdownCRBData);

    @Query("SELECT * FROM DropdownCRBData")
    List<DropdownCRBData> getAll();

    @Query("SELECT * FROM DropdownCRBData where category=:type")
    List<DropdownCRBData> getDropdownData(String type);

    @Query("DELETE FROM DropdownCRBData")
    public void nukeTable();

}
