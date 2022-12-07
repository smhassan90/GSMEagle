package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.Providers;
import com.greenstar.eagle.model.Questions;

import java.util.List;
@Dao
public interface QuestionsDAO {
    @Insert
    void insert (Questions questions);

    @Query("SELECT * FROM Questions where status=1 AND areaId=:areaId")
    List<Questions> getActiveQuestionsOfArea(int areaId);

    @Insert
    void insertMultiple (List<Questions> questions);

    @Query("SELECT * FROM Questions")
    List<Questions> getAll();

    @Query("DELETE FROM Questions")
    public void nukeTable();

    @Query("SELECT count(*) FROM Questions")
    int getCount();
}
