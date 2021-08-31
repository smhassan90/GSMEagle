package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.Question;

import java.util.List;

@Dao
public interface QuestionsDAO {
    @Insert
    void insertMultiple(List<Question> questionBankList);

    @Query("SELECT * FROM Question")
    List<Question> getAll();

    @Query("SELECT * FROM Question where status=1")
    List<Question> getActiveQuestions();

    @Query("SELECT * FROM Question where status=1 AND areaId=:areaId")
    List<Question> getActiveQuestionsOfArea(int areaId);

    @Query("DELETE FROM Question")
    public void nukeTable();

    @Query("SELECT count(*) FROM Question")
    public int getCount();
}
