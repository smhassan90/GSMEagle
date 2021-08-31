package com.greenstar.eagle.dal;

import com.greenstar.eagle.model.QATTCForm;
import com.greenstar.eagle.model.approval.ApprovalQATArea;
import com.greenstar.eagle.model.approval.ApprovalQATForm;
import com.greenstar.eagle.model.approval.ApprovalQATFormQuestion;
import com.greenstar.eagle.model.approval.ApprovalQTVForm;
import com.greenstar.eagle.model.Area;
import com.greenstar.eagle.model.Dashboard;
import com.greenstar.eagle.model.Providers;
import com.greenstar.eagle.model.Question;

import java.io.Serializable;
import java.util.List;

public class HSData implements Serializable {
    private String name;
    private String code;
    private String AMName;
    private String AMCode;
    private String region;
    private Dashboard dashboard;
    private int isQTVAllowed;
    private int isQATAllowed;
    private int isQATAMAllowed;

    List<Providers> providers;
    List<ApprovalQTVForm> qtvForms;
    List<ApprovalQATArea> approvalQATAreas;
    List<ApprovalQATForm> approvalQATForms;
    List<ApprovalQATFormQuestion> approvalQATFormQuestions;
    List<Question> questions;
    List<Area> areas;
    List<QATTCForm> qattcForms;

    public int getIsQATAMAllowed() {
        return isQATAMAllowed;
    }

    public void setIsQATAMAllowed(int isQATAMAllowed) {
        this.isQATAMAllowed = isQATAMAllowed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAMName() {
        return AMName;
    }

    public void setAMName(String AMName) {
        this.AMName = AMName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<Providers> getProviders() {
        return providers;
    }

    public void setProviders(List<Providers> providers) {
        this.providers = providers;
    }

    public List<ApprovalQTVForm> getQtvForms() {
        return qtvForms;
    }

    public void setQtvForms(List<ApprovalQTVForm> qtvForms) {
        this.qtvForms = qtvForms;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public String getAMCode() {
        return AMCode;
    }

    public void setAMCode(String AMCode) {
        this.AMCode = AMCode;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public int getIsQTVAllowed() {
        return isQTVAllowed;
    }

    public void setIsQTVAllowed(int isQTVAllowed) {
        this.isQTVAllowed = isQTVAllowed;
    }

    public int getIsQATAllowed() {
        return isQATAllowed;
    }

    public void setIsQATAllowed(int isQATAllowed) {
        this.isQATAllowed = isQATAllowed;
    }

    public List<ApprovalQATArea> getApprovalQATAreas() {
        return approvalQATAreas;
    }

    public void setApprovalQATAreas(List<ApprovalQATArea> approvalQATAreas) {
        this.approvalQATAreas = approvalQATAreas;
    }

    public List<ApprovalQATForm> getApprovalQATForms() {
        return approvalQATForms;
    }

    public void setApprovalQATForms(List<ApprovalQATForm> approvalQATForms) {
        this.approvalQATForms = approvalQATForms;
    }

    public List<ApprovalQATFormQuestion> getApprovalQATFormQuestions() {
        return approvalQATFormQuestions;
    }

    public void setApprovalQATFormQuestions(List<ApprovalQATFormQuestion> approvalQATFormQuestions) {
        this.approvalQATFormQuestions = approvalQATFormQuestions;
    }

    public List<QATTCForm> getQattcForms() {
        return qattcForms;
    }

    public void setQattcForms(List<QATTCForm> qattcForms) {
        this.qattcForms = qattcForms;
    }
}
