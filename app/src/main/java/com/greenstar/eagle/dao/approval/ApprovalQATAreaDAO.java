package com.greenstar.eagle.dao.approval;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.greenstar.eagle.model.approval.ApprovalQATArea;

import java.util.List;

@Dao
public interface ApprovalQATAreaDAO {
    @Insert
    void insertMultiple(List<ApprovalQATArea> approvalQATAreas);

    @Query("SELECT * FROM ApprovalQATArea")
    List<ApprovalQATArea> getAll();

    @Query("SELECT * FROM ApprovalQATArea Where formId=:formId")
    List<ApprovalQATArea> getFormAllAreas(long formId);

    @Query("DELETE FROM ApprovalQATArea")
    public void nukeTable();
}
