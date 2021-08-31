package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.QATFormQuestion;

import java.util.List;

@Dao
public interface QATFormQuestionDAO {
    @Insert
    void insertMultiple(List<QATFormQuestion> qatFormQuestions);

    @Query("SELECT * FROM QATFormQuestion")
    List<QATFormQuestion> getAll();

    @Query("SELECT * FROM QATFormQuestion WHERE formId IN(:formIds)")
    List<QATFormQuestion> getAllPending(List<Long> formIds);

    @Query("SELECT * FROM QATFormQuestion WHERE formId =:formId")
    List<QATFormQuestion> getFormQuestions(long formId);

    @Query("SELECT * FROM QATFormQuestion")
    List<QATFormQuestion> getQatFormQuestions();

    @Query("DELETE FROM QATFormQuestion")
    public void nukeTable();
}
