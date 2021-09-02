package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.CRForm;

import java.util.List;

@Dao
public interface CRFormDAO {
    @Insert
    void insertMultiple (List<CRForm> forms);

    @Insert
    void insert (CRForm form);

    @Query("SELECT * FROM CRForm")
    List<CRForm> getAll();

    @Query("SELECT * FROM CRForm WHERE approvalStatus = 1")
    List<CRForm> getAllSuccessfulForms();

    @Query("SELECT * FROM CRForm WHERE approvalStatus=2 OR approvalStatus=20")
    List<CRForm> getAllRejectedForms();

    @Query("SELECT * FROM CRForm WHERE approvalStatus = 0")
    List<CRForm> getAllPendingForms();

    @Query("SELECT * FROM CRForm WHERE id =:formId")
    List<CRForm> getFormByID(long formId);

    @Query("DELETE FROM CRForm")
    public void nukeTable();

    @Query("DELETE FROM CRForm WHERE id=:id")
    public void deleteFormById(long id);

    @Query("UPDATE CRForm SET approvalStatus=1 WHERE id=:id")
    public void markSuccessful(long id);

    @Query("UPDATE CRForm SET approvalStatus=20 WHERE id=:id")
    public void markRejected(long id);
}
