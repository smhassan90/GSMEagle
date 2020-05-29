package com.greenstar.mecwheel.crb.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class CRBForm {
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

    private double durationOfMarriage;

    private int noOfSons;

    private int noOfDaughters;

    private int numberOfAbortion;

    private String referredBy;

    private int ipcReferralStatus;

    private int isEverUser;

    //More 1
    //Less 0
    private int methodNotInUse;

    private int isCurrentUser;

    private double currentUseYear;

    private String currentMethod;

    private String timingOfService;

    private String serviceType;

    private int methodWithin12Months ;

    private String providerCode;

    private String providerName ;

    private int status ;

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
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

    public int getNoOfDaughters() {
        return noOfDaughters;
    }

    public void setNoOfDaughters(int noOfDaughters) {
        this.noOfDaughters = noOfDaughters;
    }

    public int getNoOfSons() {
        return noOfSons;
    }

    public void setNoOfSons(int noOfSons) {
        this.noOfSons = noOfSons;
    }

    public int getNumberOfAbortion() {
        return numberOfAbortion;
    }

    public void setNumberOfAbortion(int numberOfAbortion) {
        this.numberOfAbortion = numberOfAbortion;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public int getIpcReferralStatus() {
        return ipcReferralStatus;
    }

    public void setIpcReferralStatus(int ipcReferralStatus) {
        this.ipcReferralStatus = ipcReferralStatus;
    }

    public int getIsEverUser() {
        return isEverUser;
    }

    public void setIsEverUser(int isEverUser) {
        this.isEverUser = isEverUser;
    }

    public int getMethodNotInUse() {
        return methodNotInUse;
    }

    public void setMethodNotInUse(int methodNotInUse) {
        this.methodNotInUse = methodNotInUse;
    }

    public int getIsCurrentUser() {
        return isCurrentUser;
    }

    public void setIsCurrentUser(int isCurrentUser) {
        this.isCurrentUser = isCurrentUser;
    }

    public double getCurrentUseYear() {
        return currentUseYear;
    }

    public void setCurrentUseYear(double currentUseYear) {
        this.currentUseYear = currentUseYear;
    }

    public String getCurrentMethod() {
        return currentMethod;
    }

    public void setCurrentMethod(String currentMethod) {
        this.currentMethod = currentMethod;
    }

    public String getTimingOfService() {
        return timingOfService;
    }

    public void setTimingOfService(String timingOfService) {
        this.timingOfService = timingOfService;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getMethodWithin12Months() {
        return methodWithin12Months;
    }

    public void setMethodWithin12Months(int methodWithin12Months) {
        this.methodWithin12Months = methodWithin12Months;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getDurationOfMarriage() {
        return durationOfMarriage;
    }

    public void setDurationOfMarriage(double durationOfMarriage) {
        this.durationOfMarriage = durationOfMarriage;
    }
}

