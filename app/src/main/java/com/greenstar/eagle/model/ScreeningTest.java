package com.greenstar.eagle.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class ScreeningTest {
    @NonNull
    @PrimaryKey
    private long id;
    private long formId;
    private int areaId;
    private long testId;
    private String testOutcome;
    private String testDetail;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFormId() {
        return formId;
    }

    public void setFormId(long formId) {
        this.formId = formId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public String getTestDetail() {
        return testDetail;
    }

    public void setTestDetail(String testDetail) {
        this.testDetail = testDetail;
    }

    public String getTestOutcome() {
        return testOutcome;
    }

    public void setTestOutcome(String testOutcome) {
        this.testOutcome = testOutcome;
    }
}
