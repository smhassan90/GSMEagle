package com.greenstar.eagle.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class QTVForm {
    @NonNull
    @PrimaryKey
    private long id;
    /*
    Status = 0 means Pending
    Status = 1 means successfully synced
     */
    private int status;
    private String choName;
    private String choCode;
    private String region;
    private String mobileSystemDate;
    private String visitDate;
    private int donor;
    private String providerCode;
    private String providerName;
    private String supervisorCode;
    private String supervisorName;

    /*
    Matrix Diarrhea
    */
    /*
    private String diarrhea2To5;
    private String diarrhea6To10;
    private String diarrhea11To14;
    private String diarrheaTotal;
    */
    /*
    Matrix Ends
     */

    /*
    Matrix 1
     */
    private String deliveryDataCondoms;
    private String deliveryDataPills;
    private String deliveryDataIUCD;
    private String deliveryDataImplants;
    private String deliveryDataInjectables;
    private String deliveryDataVSC;
    private int deliveryDataTotal;
    private String deliveryDataTotalColumn;
    private String deliveryDataTotalRow;
    /*
    Matrix 2
     */
    private String postPartumCondoms;
    private String postPartumPills;
    private String postPartumIUCD;
    private String postPartumImplants;
    private String postPartumInjectables;
    private String postPartumVSC;
    private int postPartumTotal;
    private String postPartumTotalColumn;
    private String postPartumTotalRow;

    /*
    Matrix 3
     */
    private String postPACCondoms;
    private String postPACPills;
    private String postPACIUCD;
    private String postPACImplants;
    private String postPACInjectables;
    private String postPACVSC;
    private int postPACTotal;
    private String postPACTotalColumn;
    private String postPACTotalRow;

    /*
    Matrix 4
     */
    private String newUserCondoms;
    private String newUserPills;
    private String newUserIUCD;
    private String newUserImplants;
    private String newUserInjectables;
    private String newUserVSC;
    private int newUserTotal;
    private String newUserTotalColumn;
    private String newUserTotalRow;

    private int totalCurrentUsers;
    private int totalMethodSwitcher;
    private int totalMisoCases;
    private int totalPACCases;
    private int totalEverUsersLess;
    private int totalEverUsersGreater;
    private int totalNeverUsers;
    private int totalNewUsers;
    private int totalDeliveryConducted;
    private int totalFPClients;
    private int totalCounselingClients;
    private int totalClients;
    private int totalCondomClients;
    private int totalPillsClient;
    private int totalIUDClients;
    private int totalImplantClients;
    private int totalInjectableClients;
    private int totalVSCClients;
    private int totalPPIUDClients;
    private int oneMonthInjectables;
    private int twoMonthsInjectables;
    private int threeMonthsInjectables;
    private int totalInjectableClientsMonth;
    private int totalPACLTMAdoptedOneWeek;
    private int totalPACLTMAdoptedHours;
    private int totalPACFPAdoptedOneWeek;
    private int totalPACFPAdoptedHours;
    private String latLong;


    //Second Section
    private int IUDRemovedSideEffects;
    private int IUDRemovedDesire;
    private int IUDRemovedAdverse;
    private int IUDRemovedOther;
    private int totalIUDRemovedCases;


    //Third Section
    private int placentalInsertion;
    private int immediatePostPartum;
    private int postPartumInsertion48Hours;
    private int extendedPostPartumInsertion;
    private int immediateExpulsion;
    private int delayedExpulsion;


    //Fourth Section YES/NO
    private int IECMaterial;
    private int lastQATAvailable;
    private int clientRecordBook;
    private int clientDetails;
    private int trainingCertificates;
    private int adverseEventReferrals;
    private int counselingFlipChart;

    //Fifth Section YES/NO
    private int autoClave;
    private int chlorineUsed;
    private int instrumentStored;
    private int boilingInstrument;
    private int glovesInUse;
    private int safetyBoxInUse;
    private int dustbinDisposable;


    //Section 6.1
    private String availabilityGreenstar;
    private String availabilityGovt;
    private String availabilityMSS;
    private String availabilityDKT;
    private String availabilityOther;

    //Section 6.2
    private String stockGreenstar;
    private String stockGovt;
    private String stockMSS;
    private String stockDKT;
    private String stockOther;

    private String comments;

    private int approvalStatus;

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public String getChoName() {
        return choName;
    }

    public void setChoName(String choName) {
        this.choName = choName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMobileSystemDate() {
        return mobileSystemDate;
    }

    public void setMobileSystemDate(String mobileSystemDate) {
        this.mobileSystemDate = mobileSystemDate;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public int getDonor() {
        return donor;
    }

    public void setDonor(int donor) {
        this.donor = donor;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getDeliveryDataCondoms() {
        return deliveryDataCondoms;
    }

    public void setDeliveryDataCondoms(String deliveryDataCondoms) {
        this.deliveryDataCondoms = deliveryDataCondoms;
    }

    public String getDeliveryDataPills() {
        return deliveryDataPills;
    }

    public void setDeliveryDataPills(String deliveryDataPills) {
        this.deliveryDataPills = deliveryDataPills;
    }

    public String getDeliveryDataIUCD() {
        return deliveryDataIUCD;
    }

    public void setDeliveryDataIUCD(String deliveryDataIUCD) {
        this.deliveryDataIUCD = deliveryDataIUCD;
    }

    public String getDeliveryDataImplants() {
        return deliveryDataImplants;
    }

    public void setDeliveryDataImplants(String deliveryDataImplants) {
        this.deliveryDataImplants = deliveryDataImplants;
    }

    public String getDeliveryDataInjectables() {
        return deliveryDataInjectables;
    }

    public void setDeliveryDataInjectables(String deliveryDataInjectables) {
        this.deliveryDataInjectables = deliveryDataInjectables;
    }

    public String getDeliveryDataVSC() {
        return deliveryDataVSC;
    }

    public void setDeliveryDataVSC(String deliveryDataVSC) {
        this.deliveryDataVSC = deliveryDataVSC;
    }

    public int getDeliveryDataTotal() {
        return deliveryDataTotal;
    }

    public void setDeliveryDataTotal(int deliveryDataTotal) {
        this.deliveryDataTotal = deliveryDataTotal;
    }

    public String getPostPartumCondoms() {
        return postPartumCondoms;
    }

    public void setPostPartumCondoms(String postPartumCondoms) {
        this.postPartumCondoms = postPartumCondoms;
    }

    public String getPostPartumPills() {
        return postPartumPills;
    }

    public void setPostPartumPills(String postPartumPills) {
        this.postPartumPills = postPartumPills;
    }

    public String getPostPartumIUCD() {
        return postPartumIUCD;
    }

    public void setPostPartumIUCD(String postPartumIUCD) {
        this.postPartumIUCD = postPartumIUCD;
    }

    public String getPostPartumImplants() {
        return postPartumImplants;
    }

    public void setPostPartumImplants(String postPartumImplants) {
        this.postPartumImplants = postPartumImplants;
    }

    public String getPostPartumInjectables() {
        return postPartumInjectables;
    }

    public void setPostPartumInjectables(String postPartumInjectables) {
        this.postPartumInjectables = postPartumInjectables;
    }

    public String getPostPartumVSC() {
        return postPartumVSC;
    }

    public void setPostPartumVSC(String postPartumVSC) {
        this.postPartumVSC = postPartumVSC;
    }

    public int getPostPartumTotal() {
        return postPartumTotal;
    }

    public void setPostPartumTotal(int postPartumTotal) {
        this.postPartumTotal = postPartumTotal;
    }

    public String getPostPACCondoms() {
        return postPACCondoms;
    }

    public void setPostPACCondoms(String postPACCondoms) {
        this.postPACCondoms = postPACCondoms;
    }

    public String getPostPACPills() {
        return postPACPills;
    }

    public void setPostPACPills(String postPACPills) {
        this.postPACPills = postPACPills;
    }

    public String getPostPACIUCD() {
        return postPACIUCD;
    }

    public void setPostPACIUCD(String postPACIUCD) {
        this.postPACIUCD = postPACIUCD;
    }

    public String getPostPACImplants() {
        return postPACImplants;
    }

    public void setPostPACImplants(String postPACImplants) {
        this.postPACImplants = postPACImplants;
    }

    public String getPostPACInjectables() {
        return postPACInjectables;
    }

    public void setPostPACInjectables(String postPACInjectables) {
        this.postPACInjectables = postPACInjectables;
    }

    public String getPostPACVSC() {
        return postPACVSC;
    }

    public void setPostPACVSC(String postPACVSC) {
        this.postPACVSC = postPACVSC;
    }

    public int getPostPACTotal() {
        return postPACTotal;
    }

    public void setPostPACTotal(int postPACTotal) {
        this.postPACTotal = postPACTotal;
    }

    public String getNewUserCondoms() {
        return newUserCondoms;
    }

    public void setNewUserCondoms(String newUserCondoms) {
        this.newUserCondoms = newUserCondoms;
    }

    public String getNewUserPills() {
        return newUserPills;
    }

    public void setNewUserPills(String newUserPills) {
        this.newUserPills = newUserPills;
    }

    public String getNewUserIUCD() {
        return newUserIUCD;
    }

    public void setNewUserIUCD(String newUserIUCD) {
        this.newUserIUCD = newUserIUCD;
    }

    public String getNewUserImplants() {
        return newUserImplants;
    }

    public void setNewUserImplants(String newUserImplants) {
        this.newUserImplants = newUserImplants;
    }

    public String getNewUserInjectables() {
        return newUserInjectables;
    }

    public void setNewUserInjectables(String newUserInjectables) {
        this.newUserInjectables = newUserInjectables;
    }

    public String getNewUserVSC() {
        return newUserVSC;
    }

    public void setNewUserVSC(String newUserVSC) {
        this.newUserVSC = newUserVSC;
    }

    public int getNewUserTotal() {
        return newUserTotal;
    }

    public void setNewUserTotal(int newUserTotal) {
        this.newUserTotal = newUserTotal;
    }

    public int getTotalCurrentUsers() {
        return totalCurrentUsers;
    }

    public void setTotalCurrentUsers(int totalCurrentUsers) {
        this.totalCurrentUsers = totalCurrentUsers;
    }

    public int getTotalMethodSwitcher() {
        return totalMethodSwitcher;
    }

    public void setTotalMethodSwitcher(int totalMethodSwitcher) {
        this.totalMethodSwitcher = totalMethodSwitcher;
    }

    public int getTotalMisoCases() {
        return totalMisoCases;
    }

    public void setTotalMisoCases(int totalMisoCases) {
        this.totalMisoCases = totalMisoCases;
    }

    public int getTotalPACCases() {
        return totalPACCases;
    }

    public void setTotalPACCases(int totalPACCases) {
        this.totalPACCases = totalPACCases;
    }

    public int getTotalEverUsersLess() {
        return totalEverUsersLess;
    }

    public void setTotalEverUsersLess(int totalEverUsersLess) {
        this.totalEverUsersLess = totalEverUsersLess;
    }

    public int getTotalEverUsersGreater() {
        return totalEverUsersGreater;
    }

    public void setTotalEverUsersGreater(int totalEverUsersGreater) {
        this.totalEverUsersGreater = totalEverUsersGreater;
    }

    public int getTotalNeverUsers() {
        return totalNeverUsers;
    }

    public void setTotalNeverUsers(int totalNeverUsers) {
        this.totalNeverUsers = totalNeverUsers;
    }

    public int getTotalNewUsers() {
        return totalNewUsers;
    }

    public void setTotalNewUsers(int totalNewUsers) {
        this.totalNewUsers = totalNewUsers;
    }

    public int getTotalDeliveryConducted() {
        return totalDeliveryConducted;
    }

    public void setTotalDeliveryConducted(int totalDeliveryConducted) {
        this.totalDeliveryConducted = totalDeliveryConducted;
    }

    public int getTotalFPClients() {
        return totalFPClients;
    }

    public void setTotalFPClients(int totalFPClients) {
        this.totalFPClients = totalFPClients;
    }

    public int getTotalCondomClients() {
        return totalCondomClients;
    }

    public void setTotalCondomClients(int totalCondomClients) {
        this.totalCondomClients = totalCondomClients;
    }

    public int getTotalPillsClient() {
        return totalPillsClient;
    }

    public void setTotalPillsClient(int totalPillsClient) {
        this.totalPillsClient = totalPillsClient;
    }

    public int getTotalIUDClients() {
        return totalIUDClients;
    }

    public void setTotalIUDClients(int totalIUDClients) {
        this.totalIUDClients = totalIUDClients;
    }

    public int getTotalImplantClients() {
        return totalImplantClients;
    }

    public void setTotalImplantClients(int totalImplantClients) {
        this.totalImplantClients = totalImplantClients;
    }

    public int getTotalInjectableClients() {
        return totalInjectableClients;
    }

    public void setTotalInjectableClients(int totalInjectableClients) {
        this.totalInjectableClients = totalInjectableClients;
    }

    public int getTotalVSCClients() {
        return totalVSCClients;
    }

    public void setTotalVSCClients(int totalVSCClients) {
        this.totalVSCClients = totalVSCClients;
    }

    public int getTotalPPIUDClients() {
        return totalPPIUDClients;
    }

    public void setTotalPPIUDClients(int totalPPIUDClients) {
        this.totalPPIUDClients = totalPPIUDClients;
    }

    public int getOneMonthInjectables() {
        return oneMonthInjectables;
    }

    public void setOneMonthInjectables(int oneMonthInjectables) {
        this.oneMonthInjectables = oneMonthInjectables;
    }

    public int getTwoMonthsInjectables() {
        return twoMonthsInjectables;
    }

    public void setTwoMonthsInjectables(int twoMonthsInjectables) {
        this.twoMonthsInjectables = twoMonthsInjectables;
    }

    public int getThreeMonthsInjectables() {
        return threeMonthsInjectables;
    }

    public void setThreeMonthsInjectables(int threeMonthsInjectables) {
        this.threeMonthsInjectables = threeMonthsInjectables;
    }

    public int getTotalInjectableClientsMonth() {
        return totalInjectableClientsMonth;
    }

    public void setTotalInjectableClientsMonth(int totalInjectableClientsMonth) {
        this.totalInjectableClientsMonth = totalInjectableClientsMonth;
    }

    public int getIUDRemovedSideEffects() {
        return IUDRemovedSideEffects;
    }

    public void setIUDRemovedSideEffects(int IUDRemovedSideEffects) {
        this.IUDRemovedSideEffects = IUDRemovedSideEffects;
    }

    public int getIUDRemovedDesire() {
        return IUDRemovedDesire;
    }

    public void setIUDRemovedDesire(int IUDRemovedDesire) {
        this.IUDRemovedDesire = IUDRemovedDesire;
    }

    public int getIUDRemovedAdverse() {
        return IUDRemovedAdverse;
    }

    public void setIUDRemovedAdverse(int IUDRemovedAdverse) {
        this.IUDRemovedAdverse = IUDRemovedAdverse;
    }

    public int getIUDRemovedOther() {
        return IUDRemovedOther;
    }

    public void setIUDRemovedOther(int IUDRemovedOther) {
        this.IUDRemovedOther = IUDRemovedOther;
    }

    public int getTotalIUDRemovedCases() {
        return totalIUDRemovedCases;
    }

    public void setTotalIUDRemovedCases(int totalIUDRemovedCases) {
        this.totalIUDRemovedCases = totalIUDRemovedCases;
    }

    public int getPlacentalInsertion() {
        return placentalInsertion;
    }

    public void setPlacentalInsertion(int placentalInsertion) {
        this.placentalInsertion = placentalInsertion;
    }

    public int getImmediatePostPartum() {
        return immediatePostPartum;
    }

    public void setImmediatePostPartum(int immediatePostPartum) {
        this.immediatePostPartum = immediatePostPartum;
    }

    public int getPostPartumInsertion48Hours() {
        return postPartumInsertion48Hours;
    }

    public void setPostPartumInsertion48Hours(int postPartumInsertion48Hours) {
        this.postPartumInsertion48Hours = postPartumInsertion48Hours;
    }

    public int getExtendedPostPartumInsertion() {
        return extendedPostPartumInsertion;
    }

    public void setExtendedPostPartumInsertion(int extendedPostPartumInsertion) {
        this.extendedPostPartumInsertion = extendedPostPartumInsertion;
    }

    public int getImmediateExpulsion() {
        return immediateExpulsion;
    }

    public void setImmediateExpulsion(int immediateExpulsion) {
        this.immediateExpulsion = immediateExpulsion;
    }

    public int getDelayedExpulsion() {
        return delayedExpulsion;
    }

    public void setDelayedExpulsion(int delayedExpulsion) {
        this.delayedExpulsion = delayedExpulsion;
    }

    public int getIECMaterial() {
        return IECMaterial;
    }

    public void setIECMaterial(int IECMaterial) {
        this.IECMaterial = IECMaterial;
    }

    public int getLastQATAvailable() {
        return lastQATAvailable;
    }

    public void setLastQATAvailable(int lastQATAvailable) {
        this.lastQATAvailable = lastQATAvailable;
    }

    public int getClientRecordBook() {
        return clientRecordBook;
    }

    public void setClientRecordBook(int clientRecordBook) {
        this.clientRecordBook = clientRecordBook;
    }

    public int getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(int clientDetails) {
        this.clientDetails = clientDetails;
    }

    public int getTrainingCertificates() {
        return trainingCertificates;
    }

    public void setTrainingCertificates(int trainingCertificates) {
        this.trainingCertificates = trainingCertificates;
    }

    public int getAdverseEventReferrals() {
        return adverseEventReferrals;
    }

    public void setAdverseEventReferrals(int adverseEventReferrals) {
        this.adverseEventReferrals = adverseEventReferrals;
    }

    public int getCounselingFlipChart() {
        return counselingFlipChart;
    }

    public void setCounselingFlipChart(int counselingFlipChart) {
        this.counselingFlipChart = counselingFlipChart;
    }

    public int getAutoClave() {
        return autoClave;
    }

    public void setAutoClave(int autoClave) {
        this.autoClave = autoClave;
    }

    public int getChlorineUsed() {
        return chlorineUsed;
    }

    public void setChlorineUsed(int chlorineUsed) {
        this.chlorineUsed = chlorineUsed;
    }

    public int getInstrumentStored() {
        return instrumentStored;
    }

    public void setInstrumentStored(int instrumentStored) {
        this.instrumentStored = instrumentStored;
    }

    public int getBoilingInstrument() {
        return boilingInstrument;
    }

    public void setBoilingInstrument(int boilingInstrument) {
        this.boilingInstrument = boilingInstrument;
    }

    public int getGlovesInUse() {
        return glovesInUse;
    }

    public void setGlovesInUse(int glovesInUse) {
        this.glovesInUse = glovesInUse;
    }

    public int getSafetyBoxInUse() {
        return safetyBoxInUse;
    }

    public void setSafetyBoxInUse(int safetyBoxInUse) {
        this.safetyBoxInUse = safetyBoxInUse;
    }

    public int getDustbinDisposable() {
        return dustbinDisposable;
    }

    public void setDustbinDisposable(int dustbinDisposable) {
        this.dustbinDisposable = dustbinDisposable;
    }

    public String getAvailabilityGreenstar() {
        return availabilityGreenstar;
    }

    public void setAvailabilityGreenstar(String availabilityGreenstar) {
        this.availabilityGreenstar = availabilityGreenstar;
    }

    public String getAvailabilityGovt() {
        return availabilityGovt;
    }

    public void setAvailabilityGovt(String availabilityGovt) {
        this.availabilityGovt = availabilityGovt;
    }

    public String getAvailabilityMSS() {
        return availabilityMSS;
    }

    public void setAvailabilityMSS(String availabilityMSS) {
        this.availabilityMSS = availabilityMSS;
    }

    public String getAvailabilityDKT() {
        return availabilityDKT;
    }

    public void setAvailabilityDKT(String availabilityDKT) {
        this.availabilityDKT = availabilityDKT;
    }

    public String getAvailabilityOther() {
        return availabilityOther;
    }

    public void setAvailabilityOther(String availabilityOther) {
        this.availabilityOther = availabilityOther;
    }

    public String getStockGreenstar() {
        return stockGreenstar;
    }

    public void setStockGreenstar(String stockGreenstar) {
        this.stockGreenstar = stockGreenstar;
    }

    public String getStockGovt() {
        return stockGovt;
    }

    public void setStockGovt(String stockGovt) {
        this.stockGovt = stockGovt;
    }

    public String getStockMSS() {
        return stockMSS;
    }

    public void setStockMSS(String stockMSS) {
        this.stockMSS = stockMSS;
    }

    public String getStockDKT() {
        return stockDKT;
    }

    public void setStockDKT(String stockDKT) {
        this.stockDKT = stockDKT;
    }

    public String getStockOther() {
        return stockOther;
    }

    public void setStockOther(String stockOther) {
        this.stockOther = stockOther;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDeliveryDataTotalColumn() {
        return deliveryDataTotalColumn;
    }

    public void setDeliveryDataTotalColumn(String deliveryDataTotalColumn) {
        this.deliveryDataTotalColumn = deliveryDataTotalColumn;
    }

    public String getDeliveryDataTotalRow() {
        return deliveryDataTotalRow;
    }

    public void setDeliveryDataTotalRow(String deliveryDataTotalRow) {
        this.deliveryDataTotalRow = deliveryDataTotalRow;
    }

    public String getPostPartumTotalColumn() {
        return postPartumTotalColumn;
    }

    public void setPostPartumTotalColumn(String postPartumTotalColumn) {
        this.postPartumTotalColumn = postPartumTotalColumn;
    }

    public String getPostPartumTotalRow() {
        return postPartumTotalRow;
    }

    public void setPostPartumTotalRow(String postPartumTotalRow) {
        this.postPartumTotalRow = postPartumTotalRow;
    }

    public String getPostPACTotalColumn() {
        return postPACTotalColumn;
    }

    public void setPostPACTotalColumn(String postPACTotalColumn) {
        this.postPACTotalColumn = postPACTotalColumn;
    }

    public String getPostPACTotalRow() {
        return postPACTotalRow;
    }

    public void setPostPACTotalRow(String postPACTotalRow) {
        this.postPACTotalRow = postPACTotalRow;
    }

    public String getNewUserTotalColumn() {
        return newUserTotalColumn;
    }

    public void setNewUserTotalColumn(String newUserTotalColumn) {
        this.newUserTotalColumn = newUserTotalColumn;
    }

    public String getNewUserTotalRow() {
        return newUserTotalRow;
    }

    public void setNewUserTotalRow(String newUserTotalRow) {
        this.newUserTotalRow = newUserTotalRow;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public int getTotalCounselingClients() {
        return totalCounselingClients;
    }

    public void setTotalCounselingClients(int totalCounselingClients) {
        this.totalCounselingClients = totalCounselingClients;
    }

    public int getTotalClients() {
        return totalClients;
    }

    public void setTotalClients(int totalClients) {
        this.totalClients = totalClients;
    }

    public int getTotalPACLTMAdoptedOneWeek() {
        return totalPACLTMAdoptedOneWeek;
    }

    public void setTotalPACLTMAdoptedOneWeek(int totalPACLTMAdoptedOneWeek) {
        this.totalPACLTMAdoptedOneWeek = totalPACLTMAdoptedOneWeek;
    }

    public int getTotalPACLTMAdoptedHours() {
        return totalPACLTMAdoptedHours;
    }

    public void setTotalPACLTMAdoptedHours(int totalPACLTMAdoptedHours) {
        this.totalPACLTMAdoptedHours = totalPACLTMAdoptedHours;
    }

    public int getTotalPACFPAdoptedOneWeek() {
        return totalPACFPAdoptedOneWeek;
    }

    public void setTotalPACFPAdoptedOneWeek(int totalPACFPAdoptedOneWeek) {
        this.totalPACFPAdoptedOneWeek = totalPACFPAdoptedOneWeek;
    }

    public int getTotalPACFPAdoptedHours() {
        return totalPACFPAdoptedHours;
    }

    public void setTotalPACFPAdoptedHours(int totalPACFPAdoptedHours) {
        this.totalPACFPAdoptedHours = totalPACFPAdoptedHours;
    }

    public String getChoCode() {
        return choCode;
    }

    public void setChoCode(String choCode) {
        this.choCode = choCode;
    }

}
