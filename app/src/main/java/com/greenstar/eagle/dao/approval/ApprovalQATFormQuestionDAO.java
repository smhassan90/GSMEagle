package com.greenstar.eagle.dao.approval;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.eagle.model.approval.ApprovalQATFormQuestion;

import java.util.List;

@Dao
public interface ApprovalQATFormQuestionDAO {
    @Insert
    void insertMultiple(List<ApprovalQATFormQuestion> approvalQATFormQuestions);

    @Query("SELECT * FROM ApprovalQATFormQuestion")
    List<ApprovalQATFormQuestion> getAll();

    @Query("SELECT * FROM ApprovalQATFormQuestion where formId=:formId")
    List<ApprovalQATFormQuestion> getFormAllQuestions(long formId);

    @Query("DELETE FROM ApprovalQATFormQuestion")
    public void nukeTable();
}
