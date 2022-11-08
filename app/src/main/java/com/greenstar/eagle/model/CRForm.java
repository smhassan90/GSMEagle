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
    FP Ends
     */

    private int isTokenGiven;

    private String followUpVisitDate;

    private int approvalStatus ;

    private int isSynced;

    private String registeredAt;

    private String reproductiveHistory;

    private String latLong;
    private String mobileSystemDate;

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

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public String getMobileSystemDate() {
        return mobileSystemDate;
    }

    public void setMobileSystemDate(String mobileSystemDate) {
        this.mobileSystemDate = mobileSystemDate;
    }

    public String getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(String registeredAt) {
        this.registeredAt = registeredAt;
    }

    public String getReproductiveHistory() {
        return reproductiveHistory;
    }

    public void setReproductiveHistory(String reproductiveHistory) {
        this.reproductiveHistory = reproductiveHistory;
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

