package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.QTVForm;

import java.util.List;

@Dao
public interface QTVFormDAO {
    @Insert
    void insertMultiple (List<QTVForm> qtvForms);

    @Insert
    void insert (QTVForm qtvForms);

    @Query("SELECT * FROM QTVForm")
    List<QTVForm> getAll();

    @Query("SELECT * FROM QTVForm WHERE approvalStatus = 1")
    List<QTVForm> getAllSuccessfulForms();

    @Query("SELECT * FROM QTVForm WHERE approvalStatus=2 OR approvalStatus=20")
    List<QTVForm> getAllRejectedForms();

    @Query("SELECT * FROM QTVForm WHERE approvalStatus = 0")
    List<QTVForm> getAllPendingForms();

    @Query("SELECT * FROM QTVForm WHERE id =:formId")
    List<QTVForm> getQTVFormByID(long formId);

    @Query("DELETE FROM QTVForm")
    public void nukeTable();

    @Query("DELETE FROM QTVForm WHERE id=:id")
    public void deleteQtvFormById(long id);

    @Query("UPDATE QTVForm SET approvalStatus=1 WHERE id=:id")
    public void markQTVSuccessful(int id);

    @Query("UPDATE QTVForm SET approvalStatus=20 WHERE id=:id")
    public void markQTVRejected(long id);

}
