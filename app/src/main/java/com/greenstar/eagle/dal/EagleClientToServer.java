package com.greenstar.eagle.dal;
import com.greenstar.eagle.controller.IPCForms.FollowupForm;
import com.greenstar.eagle.model.CRForm;
import com.greenstar.eagle.model.ChildRegistrationForm;
import com.greenstar.eagle.model.FollowupModel;
import com.greenstar.eagle.model.NeighbourhoodAttendeesModel;
import com.greenstar.eagle.model.NeighbourhoodFormModel;
import com.greenstar.eagle.model.ProductService;
import com.greenstar.eagle.model.ScreeningAreaDetail;
import com.greenstar.eagle.model.ScreeningFormHeader;
import com.greenstar.eagle.model.ScreeningTest;
import com.greenstar.eagle.model.TokenModel;

import java.util.List;

public class EagleClientToServer {
    List<CRForm> crForms;
    List<ChildRegistrationForm> childRegistrationForms;
    List<FollowupModel> followupForms;
    List<NeighbourhoodFormModel> neighbourForms;
    List<NeighbourhoodAttendeesModel> neighbourAttendeesForms;
    List<TokenModel> tokenForms;
    List<ScreeningAreaDetail> screeningAreaDetails;
    List<ScreeningFormHeader> screeningFormHeaders;
    List<ScreeningTest> screeningTests;
    List<ProductService> productServices;

    public List<CRForm> getCrForms() {
        return crForms;
    }

    public void setCrForms(List<CRForm> crForms) {
        this.crForms = crForms;
    }

    public List<ChildRegistrationForm> getChildRegistrationForms() {
        return childRegistrationForms;
    }

    public void setChildRegistrationForms(List<ChildRegistrationForm> childRegistrationForms) {
        this.childRegistrationForms = childRegistrationForms;
    }

    public List<FollowupModel> getFollowupForms() {
        return followupForms;
    }

    public void setFollowupForms(List<FollowupModel> followupForms) {
        this.followupForms = followupForms;
    }

    public List<NeighbourhoodFormModel> getNeighbourForms() {
        return neighbourForms;
    }

    public void setNeighbourForms(List<NeighbourhoodFormModel> neighbourForms) {
        this.neighbourForms = neighbourForms;
    }

    public List<NeighbourhoodAttendeesModel> getNeighbourAttendeesForms() {
        return neighbourAttendeesForms;
    }

    public void setNeighbourAttendeesForms(List<NeighbourhoodAttendeesModel> neighbourAttendeesForms) {
        this.neighbourAttendeesForms = neighbourAttendeesForms;
    }

    public List<TokenModel> getTokenForms() {
        return tokenForms;
    }

    public void setTokenForms(List<TokenModel> tokenForms) {
        this.tokenForms = tokenForms;
    }

    public List<ScreeningAreaDetail> getScreeningAreaDetails() {
        return screeningAreaDetails;
    }

    public void setScreeningAreaDetails(List<ScreeningAreaDetail> screeningAreaDetails) {
        this.screeningAreaDetails = screeningAreaDetails;
    }

    public List<ScreeningFormHeader> getScreeningFormHeaders() {
        return screeningFormHeaders;
    }

    public void setScreeningFormHeaders(List<ScreeningFormHeader> screeningFormHeaders) {
        this.screeningFormHeaders = screeningFormHeaders;
    }

    public List<ScreeningTest> getScreeningTests() {
        return screeningTests;
    }

    public void setScreeningTests(List<ScreeningTest> screeningTests) {
        this.screeningTests = screeningTests;
    }

    public List<ProductService> getProductServices() {
        return productServices;
    }

    public void setProductServices(List<ProductService> productServices) {
        this.productServices = productServices;
    }
}
