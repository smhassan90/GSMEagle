package com.greenstar.eagle.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class NeighbourhoodFormModel {
    @NonNull
    @PrimaryKey
    private long id;
    private String visitDate;
    /*
    type = 1 Neighborhood Meeting
    type = 2 Household Visit
    type = 3 Orientation Meeting
    type = 4 Sitara House
     */
    private int meetingType;
    private String communityName;
    private String remarks;

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

    public int getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(int meetingType) {
        this.meetingType = meetingType;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
