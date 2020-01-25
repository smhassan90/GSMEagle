package com.greenstar.mecwheel.crb.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class DropdownCRBData {
    @NonNull
    @PrimaryKey
    private long id;

    private String category;
    private String detailEnglish;
    private String detailUrdu;
    private int status;

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetailEnglish() {
        return detailEnglish;
    }

    public void setDetailEnglish(String detailEnglish) {
        this.detailEnglish = detailEnglish;
    }

    public String getDetailUrdu() {
        return detailUrdu;
    }

    public void setDetailUrdu(String detailUrdu) {
        this.detailUrdu = detailUrdu;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
