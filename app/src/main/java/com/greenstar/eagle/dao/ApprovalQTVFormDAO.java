package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.approval.ApprovalQTVForm;

import java.util.List;

@Dao
public interface ApprovalQTVFormDAO {
    @Insert
    void insertMultiple (List<ApprovalQTVForm> qtvForms);

    @Insert
    void insert (ApprovalQTVForm qtvForms);

    @Query("SELECT * FROM ApprovalQTVForm")
    List<ApprovalQTVForm> getAll();

    @Query("SELECT * FROM ApprovalQTVForm WHERE status = 1")
    List<ApprovalQTVForm> getAllSuccessfulForms();

    @Query("SELECT * FROM ApprovalQTVForm WHERE status = 2")
    List<ApprovalQTVForm> getAllRejectedForms();

    @Query("SELECT * FROM ApprovalQTVForm WHERE status = 0")
    List<ApprovalQTVForm> getAllPendingForms();

    @Query("DELETE FROM ApprovalQTVForm")
    public void nukeTable();

    @Query("DELETE FROM ApprovalQTVForm WHERE id=:id")
    public void deleteQtvFormById(long id);

    @Query("UPDATE ApprovalQTVForm SET status=1 WHERE id=:id")
    public void markQTVSuccessful(int id);

    @Query("UPDATE ApprovalQTVForm SET status=2 WHERE id=:id")
    public void markQTVRejected(int id);
}
