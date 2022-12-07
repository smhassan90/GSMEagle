package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.ScreeningFormHeader;

import java.util.List;

@Dao
public interface ScreeningFormHeaderDAO {
    @Insert
    void insertMultiple(List<ScreeningFormHeader> screeningFormHeaders);

    @Insert
    void insert(ScreeningFormHeader screeningFormHeader);

    @Query("SELECT * FROM ScreeningFormHeader")
    List<ScreeningFormHeader> getAll();

    @Query("SELECT * FROM ScreeningFormHeader WHERE approvalStatus=0")
    List<ScreeningFormHeader> getAllPending();

    @Query("SELECT count(*) FROM ScreeningFormHeader WHERE approvalStatus=0")
    int getAllPendingCount();

    @Query("SELECT * FROM ScreeningFormHeader WHERE approvalStatus=1")
    List<ScreeningFormHeader> getAllSuccessful();

    @Query("SELECT * FROM ScreeningFormHeader WHERE approvalStatus=2 OR approvalStatus=20")
    List<ScreeningFormHeader> getAllRejected();

    @Query("DELETE FROM ScreeningFormHeader WHERE id=:id")
    public void deleteFormById(long id);

    @Query("SELECT * FROM ScreeningFormHeader WHERE id=:id")
    public List<ScreeningFormHeader> getFormById(long id);

    @Query("SELECT * FROM ScreeningFormHeader")
    List<ScreeningFormHeader> getQatFormHeaders();

    @Query("DELETE FROM ScreeningFormHeader")
    public void nukeTable();

    @Query("UPDATE ScreeningFormHeader SET approvalStatus=1 WHERE id=:id")
    public void markFormSuccessful(long id);

    @Query("UPDATE ScreeningFormHeader SET approvalStatus=20 WHERE id=:id")
    public void markFormRejected(long id);
}
