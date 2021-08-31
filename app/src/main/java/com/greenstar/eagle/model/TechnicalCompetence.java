package com.greenstar.eagle.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class TechnicalCompetence {
    @NonNull
    @PrimaryKey
    private long id;
    private String supervisorCode;
    private String supervisorName;
    private String QAMCode;
    private String QAMName;
    private String dateOfAssessment;
    private String providerCode;
    private String providerName;
    private String region;
    private String mobileDate;
    private int PCCD;
    private int CDPF;
    private int PCPNCC;
    private int PFPNCC;
    private int PCGTC;
    private int GTCPF;
    private int PCMiso;
    private int PCMVA;
    private int MVAPF;
    private int PCPPIUCD;
    private int PPIUCDPF;
    private int PCImplant;
    private int ImplantPF;
    private int PCLOA;
    private int LOAPF;
    private int PCPHCC;
    private int PHCCPF;
    private int PCQTVActionPlan;
    private int QTVActionPlanPF;
    private int PCRecruitmentForm;
    private int RecruitmentFormPF;

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
        this.id = id;
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

    public String getQAMCode() {
        return QAMCode;
    }

    public void setQAMCode(String QAMCode) {
        this.QAMCode = QAMCode;
    }

    public String getQAMName() {
        return QAMName;
    }

    public void setQAMName(String QAMName) {
        this.QAMName = QAMName;
    }

    public String getDateOfAssessment() {
        return dateOfAssessment;
    }

    public void setDateOfAssessment(String dateOfAssessment) {
        this.dateOfAssessment = dateOfAssessment;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMobileDate() {
        return mobileDate;
    }

    public void setMobileDate(String mobileDate) {
        this.mobileDate = mobileDate;
    }

    public int getPCCD() {
        return PCCD;
    }

    public void setPCCD(int PCCD) {
        this.PCCD = PCCD;
    }

    public int getCDPF() {
        return CDPF;
    }

    public void setCDPF(int CDPF) {
        this.CDPF = CDPF;
    }

    public int getPCPNCC() {
        return PCPNCC;
    }

    public void setPCPNCC(int PCPNCC) {
        this.PCPNCC = PCPNCC;
    }

    public int getPFPNCC() {
        return PFPNCC;
    }

    public void setPFPNCC(int PFPNCC) {
        this.PFPNCC = PFPNCC;
    }

    public int getPCGTC() {
        return PCGTC;
    }

    public void setPCGTC(int PCGTC) {
        this.PCGTC = PCGTC;
    }

    public int getGTCPF() {
        return GTCPF;
    }

    public void setGTCPF(int GTCPF) {
        this.GTCPF = GTCPF;
    }

    public int getPCMiso() {
        return PCMiso;
    }

    public void setPCMiso(int PCMiso) {
        this.PCMiso = PCMiso;
    }

    public int getPCMVA() {
        return PCMVA;
    }

    public void setPCMVA(int PCMVA) {
        this.PCMVA = PCMVA;
    }

    public int getMVAPF() {
        return MVAPF;
    }

    public void setMVAPF(int MVAPF) {
        this.MVAPF = MVAPF;
    }

    public int getPCPPIUCD() {
        return PCPPIUCD;
    }

    public void setPCPPIUCD(int PCPPIUCD) {
        this.PCPPIUCD = PCPPIUCD;
    }

    public int getPPIUCDPF() {
        return PPIUCDPF;
    }

    public void setPPIUCDPF(int PPIUCDPF) {
        this.PPIUCDPF = PPIUCDPF;
    }

    public int getPCImplant() {
        return PCImplant;
    }

    public void setPCImplant(int PCImplant) {
        this.PCImplant = PCImplant;
    }

    public int getImplantPF() {
        return ImplantPF;
    }

    public void setImplantPF(int implantPF) {
        ImplantPF = implantPF;
    }

    public int getPCLOA() {
        return PCLOA;
    }

    public void setPCLOA(int PCLOA) {
        this.PCLOA = PCLOA;
    }

    public int getLOAPF() {
        return LOAPF;
    }

    public void setLOAPF(int LOAPF) {
        this.LOAPF = LOAPF;
    }

    public int getPCPHCC() {
        return PCPHCC;
    }

    public void setPCPHCC(int PCPHCC) {
        this.PCPHCC = PCPHCC;
    }

    public int getPHCCPF() {
        return PHCCPF;
    }

    public void setPHCCPF(int PHCCPF) {
        this.PHCCPF = PHCCPF;
    }

    public int getPCQTVActionPlan() {
        return PCQTVActionPlan;
    }

    public void setPCQTVActionPlan(int PCQTVActionPlan) {
        this.PCQTVActionPlan = PCQTVActionPlan;
    }

    public int getQTVActionPlanPF() {
        return QTVActionPlanPF;
    }

    public void setQTVActionPlanPF(int QTVActionPlanPF) {
        this.QTVActionPlanPF = QTVActionPlanPF;
    }

    public int getPCRecruitmentForm() {
        return PCRecruitmentForm;
    }

    public void setPCRecruitmentForm(int PCRecruitmentForm) {
        this.PCRecruitmentForm = PCRecruitmentForm;
    }

    public int getRecruitmentFormPF() {
        return RecruitmentFormPF;
    }

    public void setRecruitmentFormPF(int recruitmentFormPF) {
        RecruitmentFormPF = recruitmentFormPF;
    }
}
