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
    private int didYouVisit;
    private String reasonForNotVisiting;
    private int haveYouAdopted;
    private String adoptedMethod;
    private int anySideEffects;
    private int didVisitAfterSideEffects;
    private String reasonsForNotAdoptingMethod;

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

    public int getDidYouVisit() {
        return didYouVisit;
    }

    public void setDidYouVisit(int didYouVisit) {
        this.didYouVisit = didYouVisit;
    }

    public String getReasonForNotVisiting() {
        return reasonForNotVisiting;
    }

    public void setReasonForNotVisiting(String reasonForNotVisiting) {
        this.reasonForNotVisiting = reasonForNotVisiting;
    }

    public int getHaveYouAdopted() {
        return haveYouAdopted;
    }

    public void setHaveYouAdopted(int haveYouAdopted) {
        this.haveYouAdopted = haveYouAdopted;
    }

    public String getAdoptedMethod() {
        return adoptedMethod;
    }

    public void setAdoptedMethod(String adoptedMethod) {
        this.adoptedMethod = adoptedMethod;
    }

    public int getAnySideEffects() {
        return anySideEffects;
    }

    public void setAnySideEffects(int anySideEffects) {
        this.anySideEffects = anySideEffects;
    }

    public int getDidVisitAfterSideEffects() {
        return didVisitAfterSideEffects;
    }

    public void setDidVisitAfterSideEffects(int didVisitAfterSideEffects) {
        this.didVisitAfterSideEffects = didVisitAfterSideEffects;
    }

    public String getReasonsForNotAdoptingMethod() {
        return reasonsForNotAdoptingMethod;
    }

    public void setReasonsForNotAdoptingMethod(String reasonsForNotAdoptingMethod) {
        this.reasonsForNotAdoptingMethod = reasonsForNotAdoptingMethod;
    }
}
