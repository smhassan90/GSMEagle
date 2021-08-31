package com.greenstar.eagle.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class CRFForm {
    @NonNull
    @PrimaryKey
    private long id;

    private String sitaraBajiCode;

    private String sitaraBajiName;

    private String providerName ;

    private String providerCode;

    private String region;

    private String district;

    private String supervisorCode ;

    private String supervisorName ;

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
    private String fpClientType;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSitaraBajiCode() {
        return sitaraBajiCode;
    }

    public void setSitaraBajiCode(String sitaraBajiCode) {
        this.sitaraBajiCode = sitaraBajiCode;
    }

    public String getSitaraBajiName() {
        return sitaraBajiName;
    }

    public void setSitaraBajiName(String sitaraBajiName) {
        this.sitaraBajiName = sitaraBajiName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSupervisorCode() {
        return supervisorCode;
    }

    public void setSupervisorCode(String supervisorCode) {
        this.supervisorCode = supervisorCode;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
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

    public String getFpClientType() {
        return fpClientType;
    }

    public void setFpClientType(String fpClientType) {
        this.fpClientType = fpClientType;
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
}
