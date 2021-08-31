package com.greenstar.eagle.dao.approval;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.approval.ApprovalQATForm;

import java.util.List;

@Dao
public interface ApprovalQATFormDAO {
    @Insert
    void insertMultiple(List<ApprovalQATForm> approvalQATForms);

    @Query("SELECT * FROM ApprovalQATForm")
    List<ApprovalQATForm> getAll();

    @Query("SELECT * FROM ApprovalQATForm where providerCode=:providerCode")
    ApprovalQATForm getForm(String providerCode);

    @Query("DELETE FROM ApprovalQATForm")
    public void nukeTable();
}
