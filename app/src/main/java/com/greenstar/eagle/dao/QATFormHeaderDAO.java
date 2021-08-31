package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.greenstar.eagle.model.QATFormHeader;

import java.util.List;

@Dao
public interface QATFormHeaderDAO {
    @Insert
    void insertMultiple(List<QATFormHeader> qatFormHeaders);

    @Insert
    void insert(QATFormHeader qatFormHeaders);

    @Query("SELECT * FROM QATFormHeader")
    List<QATFormHeader> getAll();

    @Query("SELECT * FROM QATFormHeader WHERE approvalStatus=0")
    List<QATFormHeader> getAllPending();

    @Query("SELECT * FROM QATFormHeader WHERE approvalStatus=1")
    List<QATFormHeader> getAllSuccessful();

    @Query("SELECT * FROM QATFormHeader WHERE approvalStatus=2 OR approvalStatus=20")
    List<QATFormHeader> getAllRejected();

    @Query("DELETE FROM QATFormHeader WHERE id=:id")
    public void deleteFormById(long id);

    @Query("SELECT * FROM QATFormHeader WHERE id=:id")
    public List<QATFormHeader> getFormById(long id);

    @Query("SELECT * FROM QATFormHeader")
    List<QATFormHeader> getQatFormHeaders();

    @Query("DELETE FROM QATFormHeader")
    public void nukeTable();

    @Query("UPDATE QATFormHeader SET approvalStatus=1 WHERE id=:id")
    public void markQATSuccessful(long id);

    @Query("UPDATE QATFormHeader SET approvalStatus=20 WHERE id=:id")
    public void markQATRejected(long id);
}