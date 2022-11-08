package com.greenstar.eagle.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class FollowupModel {
    @NonNull
    @PrimaryKey
    private long id;
    private String visitDate;
    private long clientId;
    private int isSupportSitaraHouse;
    private int isSupportProvider;
    private int isSupportCompleted;
    private String service;
    private String followupDate;

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

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public int getIsSupportSitaraHouse() {
        return isSupportSitaraHouse;
    }

    public void setIsSupportSitaraHouse(int isSupportSitaraHouse) {
        this.isSupportSitaraHouse = isSupportSitaraHouse;
    }

    public int getIsSupportProvider() {
        return isSupportProvider;
    }

    public void setIsSupportProvider(int isSupportProvider) {
        this.isSupportProvider = isSupportProvider;
    }

    public int getIsSupportCompleted() {
        return isSupportCompleted;
    }

    public void setIsSupportCompleted(int isSupportCompleted) {
        this.isSupportCompleted = isSupportCompleted;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getFollowupDate() {
        return followupDate;
    }

    public void setFollowupDate(String followupDate) {
        this.followupDate = followupDate;
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
}
