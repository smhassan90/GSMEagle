package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.Questions;
import com.greenstar.eagle.model.ScreeningTest;

import java.util.List;
@Dao
public interface ScreeningTestDAO {
    @Insert
    void insert (ScreeningTest screeningTest);

    @Query("SELECT * FROM ScreeningTest")
    List<ScreeningTest> getAll();

    @Insert
    void insertMultiple (List<ScreeningTest> screeningTests);


    @Query("DELETE FROM ScreeningTest")
    public void nukeTable();

    @Query("SELECT count(*) FROM ScreeningTest")
    int getCount();
}
