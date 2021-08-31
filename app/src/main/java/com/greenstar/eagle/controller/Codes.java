package com.greenstar.eagle.controller;

public interface Codes {
    public static final String ALL_OK = "200";
    public static final String PREF_NAME = "GreenStarSales";
    final public static String MESSAGE = "No product added yet...";
    final public static String STAFFTYPE = "5";
    final public static String TIMEOUT = "Timeout";
    final public static String SOMETHINGWENTWRONG = "2001";
    final public static String myFormat = "MM/dd/yy";
    final public static int QTVFORM = 1;
    final public static int QATFORM = 2;
    final public static int QATAREA = 3;
    final public static int QATQUESTION = 4;
    public static final String SINGLE_QAT_FORM = "QAT_FORM";
    public static final String SINGLE_QTV_FORM = "QTV_FORM";
    //Partial Sync
    public static final String ALL_OK_PS_BASICINFO = "2001";
    public static final String ERROR_PS_BASICINFO = "5021";

    public static final String PS_TYPE_ALL = "699";
    public static final String PS_TYPE_BASIC_INFO = "700";
    public static final String PS_TYPE_ApprovalQATForm = "702";
    public static final String PS_TYPE_ApprovalQTVForm = "704";
    public static final String PS_TYPE_Providers = "705";
    public static final String PS_TYPE_Question = "706";
    public static final String PS_TYPE_Area = "707";
    public static final String PS_TYPE_QATTCForm = "708";

    final public static int QATISSUESTATUS_INCOMPLETEAREAS = 10;
    final public static int QATISSUESTATUS_INCOMPLETEQUESTIONS = 20;
    final public static int QATISSUESTATUS_INCOMPLETEQUESTIONSANDAREAS = 30;

    final public static int QATISSUESTATUS_INCOMPLETEQUESTIONSPULL = 40;
    final public static int QATISSUESTATUS_INCOMPLETEAREASPULL = 50;
    final public static int QATISSUESTATUS_INCOMPLETEQUESTIONSANDAREASPULL = 60;

    final public static String ISQATAMALLOWED = "isQATAMAllowed";
}
