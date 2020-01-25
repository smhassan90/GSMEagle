package com.greenstar.mecwheel.crb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.mecwheel.crb.model.CRBForm;
import com.greenstar.mecwheel.crb.model.Providers;

import java.util.List;

@Dao
public interface CRBFormDAO {
    @Insert
    void insertMultiple (List<CRBForm> crbForms);

    @Insert
    void insert (CRBForm form);

    @Query("SELECT * FROM CRBForm")
    List<CRBForm> getAll();

    @Query("SELECT * FROM CRBForm where status=0")
    List<CRBForm> getPendingCRBForms();

    @Query("SELECT * FROM CRBForm where status=1")
    List<CRBForm> getSuccessfulRBForms();

    @Query("DELETE FROM CRBForm WHERE id=:id")
    public void deleteFormById(long id);

    @Query("DELETE FROM CRBForm")
    public void nukeTable();
}
