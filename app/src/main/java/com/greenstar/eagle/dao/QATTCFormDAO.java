package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.greenstar.eagle.model.QATTCForm;

import java.util.List;

@Dao
public interface QATTCFormDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultiple (List<QATTCForm> forms);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(QATTCForm qattcForm);

    @Update
    void update(QATTCForm qattcForm);

    @Query("SELECT * FROM QATTCForm WHERE approvalStatus=1")
    List<QATTCForm> getAllSuccessful();

    @Query("SELECT * FROM QATTCForm WHERE approvalStatus=0")
    List<QATTCForm> getAllPending();

    @Query("SELECT * FROM QATTCForm WHERE providerCode=:providerCode")
    List<QATTCForm> isFormExist(String providerCode);

    @Query("SELECT * FROM QATTCForm WHERE providerCode=:providerCode and approvalStatus=1")
    QATTCForm getTCFormByProviderCode(String providerCode);

    @Query("DELETE FROM QATTCForm")
    public void nukeTable();
}
