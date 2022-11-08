package com.greenstar.eagle.controller;

public interface Codes {
    public static final String PREF_NAME = "Eagle";
    public static final String ALL_OK = "200";
    final public static String STAFFTYPE = "5";
    final public static String TIMEOUT = "Timeout";
    final public static String SOMETHINGWENTWRONG = "2001";
    final public static String myFormat = "MM/dd/yy";
    final public static String notificationFormat = "dd-MMM-yy";
    //Partial Sync
    public static final String ALL_OK_PS_BASICINFO = "2001";
    public static final String ERROR_PS_BASICINFO = "5021";

    public static final String PS_TYPE_ALL = "699";
    public static final String PS_TYPE_BASIC_INFO = "700";
    public static final String PS_TYPE_Providers = "705";
    public static final String PS_TYPE_Client = "710";
    public static final String PS_TYPE_Children = "715";
    public static final String PS_TYPE_Followup = "720";
    public static final String PS_TYPE_Neighbour = "725";
    public static final String PS_TYPE_Token = "730";
    public static final String PS_PULL_CLIENTS = "735";
    public static final String PS_TYPE_Neighbour_attendees = "735";


    public static final String PullAllEagleData = "PULLEAGLE";
    public static final String  SINGLE_CR_FORM = "1";

    public static final String CRFORMID = "CRForm";
    public static final String CHILDRENREGISTRATIONFORMID = "CHILDRENREGISTRATIONFORMID";
    public static final String TOKENFORM = "TOKENFORM";
    public static final String NEIGHBOURHOODFORM = "NEIGHBOURHOODFORM";
    public static final String NEIGHBOURHOODATTENDEESFORM = "NEIGHBOURHOODATTENDEESFORM";
    public static final String FOLLOWUPFORM = "FOLLOWUPFORM";

    final public static String MESSAGE = "No neighbourhood attendee added yet...";
}
