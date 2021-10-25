package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.ChildRegistrationForm;

import java.util.List;

@Dao
public interface ChildRegistrationFormDAO {
    @Insert
    void insert (ChildRegistrationForm childRegistrationForm);

    @Query("SELECT * FROM ChildRegistrationForm")
    List<ChildRegistrationForm> getAll();

    @Query("SELECT count(*) FROM ChildRegistrationForm")
    int getCount();

    @Query("DELETE FROM ChildRegistrationForm")
    public void nukeTable();
}
