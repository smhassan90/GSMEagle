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

    private String address;

    private String contactNumber;

    private int noOfChildren;

    private String referredBy;

    private String clientAge;

    private int fbUserCategory;

    private String currentMethod;

    private String timingFPService;

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

    public int getNoOfChildren() {
        return noOfChildren;
    }

    public void setNoOfChildren(int noOfChildren) {
        this.noOfChildren = noOfChildren;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public String getClientAge() {
        return clientAge;
    }

    public void setClientAge(String clientAge) {
        this.clientAge = clientAge;
    }

    public int getFbUserCategory() {
        return fbUserCategory;
    }

    public void setFbUserCategory(int fbUserCategory) {
        this.fbUserCategory = fbUserCategory;
    }

    public String getCurrentMethod() {
        return currentMethod;
    }

    public void setCurrentMethod(String currentMethod) {
        this.currentMethod = currentMethod;
    }

    public String getTimingFPService() {
        return timingFPService;
    }

    public void setTimingFPService(String timingFPService) {
        this.timingFPService = timingFPService;
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
}
