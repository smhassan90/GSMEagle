package com.greenstar.eagle.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
@Entity
public class ChildRegistrationForm {
    @NonNull
    @PrimaryKey
    private long id;

    private String visitDate;

    private long parentId;

    private String childName;

    private String childAge;

    /*
    Diarrhea Section
     */
    private int currentDiarrhea;
    private int isMedicineProvided;
    private int isCounseling;

    /*
    Diarrhea Section Ends
     */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildAge() {
        return childAge;
    }

    public void setChildAge(String childAge) {
        this.childAge = childAge;
    }

    public int getCurrentDiarrhea() {
        return currentDiarrhea;
    }

    public void setCurrentDiarrhea(int currentDiarrhea) {
        this.currentDiarrhea = currentDiarrhea;
    }

    public int getIsMedicineProvided() {
        return isMedicineProvided;
    }

    public void setIsMedicineProvided(int isMedicineProvided) {
        this.isMedicineProvided = isMedicineProvided;
    }

    public int getIsCounseling() {
        return isCounseling;
    }

    public void setIsCounseling(int isCounseling) {
        this.isCounseling = isCounseling;
    }

}
