package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.TokenModel;

import java.util.List;

@Dao
public interface TokenModelDAO {
    @Insert
    void insert (TokenModel tokenModel);

    @Query("SELECT * FROM TokenModel")
    List<TokenModel> getAll();

    @Query("DELETE FROM TokenModel")
    public void nukeTable();
}
