package com.greenstar.eagle.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.ChildRegistrationForm;

@Dao
public interface ChildRegistrationFormDAO {
    @Insert
    void insert (ChildRegistrationForm childRegistrationForm);

    @Query("SELECT * FROM ChildRegistrationForm")
    ChildRegistrationForm getAll();

    @Query("DELETE FROM ChildRegistrationForm")
    public void nukeTable();
}
