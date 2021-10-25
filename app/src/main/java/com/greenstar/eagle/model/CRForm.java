package com.greenstar.eagle.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class CRForm {
    @NonNull
    @PrimaryKey
    private long id;

    private String visitDate;

    private String clientName;

    private String husbandName;

    private String clientAge;

    private String address;

    private String contactNumber;

    private int canWeContact;

    /*
    Diarrhea Section
     */
    private int currentDiarrhea;
    private int isMedicineProvided;
    private int isCounseling;

    /*
    Diarrhea Section Ends
     */

    /*
    FP Planning Category
     */

    private int isCurrentUser;
    private String currentFPMethod;
    private int periodOfUsingCurrentMethod;
    private int isEverUser;
    private String everMethodUsed;
    private String reasonForDiscontinuation;
    private String reasonForNeverUser;

    /*
    FP Ends
     */

    private int isTokenGiven;

    private String followUpVisitDate;

    private int approvalStatus ;

    private int isSynced;

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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getHusbandName() {
        return husbandName;
    }

    public void setHusbandName(String husbandName) {
        this.husbandName = husbandName;
    }

    public String getClientAge() {
        return clientAge;
    }

    public void setClientAge(String clientAge) {
        this.clientAge = clientAge;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getCanWeContact() {
        return canWeContact;
    }

    public void setCanWeContact(int canWeContact) {
        this.canWeContact = canWeContact;
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

    public int getIsCurrentUser() {
        return isCurrentUser;
    }

    public void setIsCurrentUser(int isCurrentUser) {
        this.isCurrentUser = isCurrentUser;
    }

    public String getCurrentFPMethod() {
        return currentFPMethod;
    }

    public void setCurrentFPMethod(String currentFPMethod) {
        this.currentFPMethod = currentFPMethod;
    }

    public int getPeriodOfUsingCurrentMethod() {
        return periodOfUsingCurrentMethod;
    }

    public void setPeriodOfUsingCurrentMethod(int periodOfUsingCurrentMethod) {
        this.periodOfUsingCurrentMethod = periodOfUsingCurrentMethod;
    }

    public int getIsEverUser() {
        return isEverUser;
    }

    public void setIsEverUser(int isEverUser) {
        this.isEverUser = isEverUser;
    }

    public String getEverMethodUsed() {
        return everMethodUsed;
    }

    public void setEverMethodUsed(String everMethodUsed) {
        this.everMethodUsed = everMethodUsed;
    }

    public String getReasonForDiscontinuation() {
        return reasonForDiscontinuation;
    }

    public void setReasonForDiscontinuation(String reasonForDiscontinuation) {
        this.reasonForDiscontinuation = reasonForDiscontinuation;
    }

    public String getReasonForNeverUser() {
        return reasonForNeverUser;
    }

    public void setReasonForNeverUser(String reasonForNeverUser) {
        this.reasonForNeverUser = reasonForNeverUser;
    }

    public int getIsTokenGiven() {
        return isTokenGiven;
    }

    public void setIsTokenGiven(int isTokenGiven) {
        this.isTokenGiven = isTokenGiven;
    }

    public String getFollowUpVisitDate() {
        return followUpVisitDate;
    }

    public void setFollowUpVisitDate(String followUpVisitDate) {
        this.followUpVisitDate = followUpVisitDate;
    }

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public int getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(int isSynced) {
        this.isSynced = isSynced;
    }

    @Override
    public String toString() {
        if(husbandName==null && contactNumber==null){
            return this.clientName;
        }else {
            return this.clientName + " - " + this.husbandName + " - " + this.contactNumber;
        }
    }
}

