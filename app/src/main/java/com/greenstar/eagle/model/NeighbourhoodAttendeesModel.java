package com.greenstar.eagle.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class NeighbourhoodAttendeesModel {
    @NonNull
    @PrimaryKey
    private long id;
    private long clientId;
    private long neighbourhoodId;
    private int isFPBrochureGiven;
    private int isDiarrheaBrochureGiven;
    private String otherIECMaterial;
    private String remarks;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getNeighbourhoodId() {
        return neighbourhoodId;
    }

    public void setNeighbourhoodId(long neighbourhoodId) {
        this.neighbourhoodId = neighbourhoodId;
    }

    public int getIsFPBrochureGiven() {
        return isFPBrochureGiven;
    }

    public void setIsFPBrochureGiven(int isFPBrochureGiven) {
        this.isFPBrochureGiven = isFPBrochureGiven;
    }

    public int getIsDiarrheaBrochureGiven() {
        return isDiarrheaBrochureGiven;
    }

    public void setIsDiarrheaBrochureGiven(int isDiarrheaBrochureGiven) {
        this.isDiarrheaBrochureGiven = isDiarrheaBrochureGiven;
    }

    public String getOtherIECMaterial() {
        return otherIECMaterial;
    }

    public void setOtherIECMaterial(String otherIECMaterial) {
        this.otherIECMaterial = otherIECMaterial;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
