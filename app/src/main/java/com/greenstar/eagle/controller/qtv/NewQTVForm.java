package com.greenstar.eagle.controller.qtv;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.greenstar.eagle.R;
import com.greenstar.eagle.adapters.ProviderAdapter;
import com.greenstar.eagle.controller.Codes;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.Providers;
import com.greenstar.eagle.model.QTVForm;
import com.greenstar.eagle.utils.Util;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewQTVForm extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    final Calendar myCalendar = Calendar.getInstance();
    String staffCode = "";
    String AMCode="";
    //In which you need put here
    DatePickerDialog.OnDateSetListener date = null;
    /*
    First Section declaration
     */
    TextView tvStaffCodeName, tvSupervisorName, tvRegion, tvProjectCode;
    EditText etReportingMonth;
    SearchableSpinner spProviderCodeName;
    List<Providers> providers = new ArrayList<>();
    AppDatabase db = null;
    ProviderAdapter providerAdapter = null;
    Button btnSubmit;
    EditText etComments;
    /*
    Matrix Common elements initialization STARTS
     */
    final static int COLUMN_LENGTH = 6;
    final static int ROW_LENGTH = 9;

    final static int COLUMN_LENGTH_DIARRHEA = 7;
    final static int ROW_LENGTH_DIARRHEA = 3;

    final static int COLUMN_EDITTEXT_LENGTH = 5;
    final static int ROW_EDITTEXT_AVAILABILITY_LENGTH = 10;
    final static int ROW_EDITTEXT_EXPIRY_LENGTH = 9;

    private static final int[] tvTotalRowsIds = {R.id.total1, R.id.total2, R.id.total3, R.id.total4, R.id.total5,
            R.id.total6, R.id.total7, R.id.total8, R.id.total9, R.id.total10};
    private static final int[] tvTotalColumnsIds = {R.id.tv81, R.id.tv82, R.id.tv83, R.id.tv84, R.id.tv85, R.id.tv86};

    /*
    Matrix Common elements initialization ENDS
     */
    /*
    Matrix 0 - Diarrhea matrix
    Matrix1 - Overall Method Mix Disaggregated by Age
    Matrix2 - Post- Partum Family Planning (PPIUD Clients)
    Matrix3 - Post PAC Family Planning (Both Miso and MVA Clients)
    Matrix4 - New Users Method Mix
     */

    /*
    Diarrhea matrix Starts

     */


    Button btnMatrixDiarrhea[][] = new Button[ROW_LENGTH_DIARRHEA][COLUMN_LENGTH_DIARRHEA];

    View glMatrixDiarrhea;

    TextView[] totalRowDiarrhea = new TextView[COLUMN_LENGTH_DIARRHEA + 1];

    //Diarrhea matrix ends here

    // Matrix1 - Overall Method Mix Disaggregated by Age
    //Declaration Starts
    private static final int[][] btnMatrix1Ids = {
            {R.id.btn111, R.id.btn112, R.id.btn113, R.id.btn114, R.id.btn115, R.id.btn116},
            {R.id.btn121, R.id.btn122, R.id.btn123, R.id.btn124, R.id.btn125, R.id.btn126},
            {R.id.btn131, R.id.btn132, R.id.btn133, R.id.btn134, R.id.btn135, R.id.btn136},
            {R.id.btn141, R.id.btn142, R.id.btn143, R.id.btn144, R.id.btn145, R.id.btn146},
            {R.id.btn151, R.id.btn152, R.id.btn153, R.id.btn154, R.id.btn155, R.id.btn156},
            {R.id.btn161, R.id.btn162, R.id.btn163, R.id.btn164, R.id.btn165, R.id.btn166},
            {R.id.btn171, R.id.btn172, R.id.btn173, R.id.btn174, R.id.btn175, R.id.btn176},
            {R.id.btn181, R.id.btn182, R.id.btn183, R.id.btn184, R.id.btn185, R.id.btn186},
            {R.id.btn191, R.id.btn192, R.id.btn193, R.id.btn194, R.id.btn195, R.id.btn196}
    };



    Button btnMatrix1[][] = new Button[ROW_LENGTH][COLUMN_LENGTH];

    TextView[] totalRow1 = new TextView[ROW_LENGTH + 1];
    TextView[] totalColumn1 = new TextView[COLUMN_LENGTH];

    View glMatrix1;

    // Matrix1 - Overall Method Mix Disaggregated by Age
    //Declaration ends

    // Matrix2 - Post- Partum Family Planning (PPIUD Clients)
    //Declaration Starts

    private static final int[][] btnMatrix2Ids = {
            {R.id.btn211, R.id.btn212, R.id.btn213, R.id.btn214, R.id.btn215, R.id.btn216},
            {R.id.btn221, R.id.btn222, R.id.btn223, R.id.btn224, R.id.btn225, R.id.btn226},
            {R.id.btn231, R.id.btn232, R.id.btn233, R.id.btn234, R.id.btn235, R.id.btn236},
            {R.id.btn241, R.id.btn242, R.id.btn243, R.id.btn244, R.id.btn245, R.id.btn246},
            {R.id.btn251, R.id.btn252, R.id.btn253, R.id.btn254, R.id.btn255, R.id.btn256},
            {R.id.btn261, R.id.btn262, R.id.btn263, R.id.btn264, R.id.btn265, R.id.btn266},
            {R.id.btn271, R.id.btn272, R.id.btn273, R.id.btn274, R.id.btn275, R.id.btn276},
            {R.id.btn281, R.id.btn282, R.id.btn283, R.id.btn284, R.id.btn285, R.id.btn286},
            {R.id.btn291, R.id.btn292, R.id.btn293, R.id.btn294, R.id.btn295, R.id.btn296}
    };

    Button btnMatrix2[][] = new Button[ROW_LENGTH][COLUMN_LENGTH];

    TextView[] totalRow2 = new TextView[ROW_LENGTH + 1];
    TextView[] totalColumn2 = new TextView[COLUMN_LENGTH];

    View glMatrix2;
    //Matrix 2 - Ends

    // Matrix3 - Post PAC Family Planning (Both Miso and MVA Clients)
    //Declaration Starts
    private static final int[][] btnMatrix3Ids = {
            {R.id.btn311, R.id.btn312, R.id.btn313, R.id.btn314, R.id.btn315, R.id.btn316},
            {R.id.btn321, R.id.btn322, R.id.btn323, R.id.btn324, R.id.btn325, R.id.btn326},
            {R.id.btn331, R.id.btn332, R.id.btn333, R.id.btn334, R.id.btn335, R.id.btn336},
            {R.id.btn341, R.id.btn342, R.id.btn343, R.id.btn344, R.id.btn345, R.id.btn346},
            {R.id.btn351, R.id.btn352, R.id.btn353, R.id.btn354, R.id.btn355, R.id.btn356},
            {R.id.btn361, R.id.btn362, R.id.btn363, R.id.btn364, R.id.btn365, R.id.btn366},
            {R.id.btn371, R.id.btn372, R.id.btn373, R.id.btn374, R.id.btn375, R.id.btn376},
            {R.id.btn381, R.id.btn382, R.id.btn383, R.id.btn384, R.id.btn385, R.id.btn386},
            {R.id.btn391, R.id.btn392, R.id.btn393, R.id.btn394, R.id.btn395, R.id.btn396}
    };

    Button btnMatrix3[][] = new Button[ROW_LENGTH][COLUMN_LENGTH];

    TextView[] totalRow3 = new TextView[ROW_LENGTH + 1];
    TextView[] totalColumn3 = new TextView[COLUMN_LENGTH];

    View glMatrix3;

    // Matrix3 - Post PAC Family Planning (Both Miso and MVA Clients)
    //Declaration ends

    // Matrix4 - New Users Method Mix
    //Declaration Starts
    private static final int[][] btnMatrix4Ids = {
            {R.id.btn411, R.id.btn412, R.id.btn413, R.id.btn414, R.id.btn415, R.id.btn416},
            {R.id.btn421, R.id.btn422, R.id.btn423, R.id.btn424, R.id.btn425, R.id.btn426},
            {R.id.btn431, R.id.btn432, R.id.btn433, R.id.btn434, R.id.btn435, R.id.btn436},
            {R.id.btn441, R.id.btn442, R.id.btn443, R.id.btn444, R.id.btn445, R.id.btn446},
            {R.id.btn451, R.id.btn452, R.id.btn453, R.id.btn454, R.id.btn455, R.id.btn456},
            {R.id.btn461, R.id.btn462, R.id.btn463, R.id.btn464, R.id.btn465, R.id.btn466},
            {R.id.btn471, R.id.btn472, R.id.btn473, R.id.btn474, R.id.btn475, R.id.btn476},
            {R.id.btn481, R.id.btn482, R.id.btn483, R.id.btn484, R.id.btn485, R.id.btn486},
            {R.id.btn491, R.id.btn492, R.id.btn493, R.id.btn494, R.id.btn495, R.id.btn496}

    };

    Button btnMatrix4[][] = new Button[ROW_LENGTH][COLUMN_LENGTH];

    TextView[] totalRow4 = new TextView[ROW_LENGTH + 1];
    TextView[] totalColumn4 = new TextView[COLUMN_LENGTH];

    View glMatrix4;

    // Matrix4 - New Users Method Mix
    //Declaration ends

    /*
        Declare all variables of form
    */
    //Section 1 Declaration
    EditText etCurrentUser, etMethodSwitcher, etMisoCases, etPACCases, etEverUsersLess, etEverUsersGreater, etNeverUsers, etDeliveriesConducted, etTotalCounselingClients;

    TextView tvNewUsers, tvFPClients, tvTotalClients, tvCondomClients, tvPillClients, tvIUDClients, tvImplantClients, tvInjectableClients;
    TextView tvVSCClients, tvPPIUDClients, tvTotalInjectableClients;

    EditText etOneMonth, etTwoMonths, etThreeMonths, etPACLTMAdoptedOneWeek, etPACLTMAdoptedHours, etPACFPAdoptedOneWeek, etPACFPAdoptedHours;

    //Section 2 Declaration
    EditText etIUDRemovedSide, etIUDRemovedDesire, etIUDRemovedAdverse, etIUDRemovedOther;
    TextView tvTotalIUDRemovalCases;

    //Section 3 declaration
    EditText etPlacentalInsertion, etImmediatePostPartumInsertion, etPostPartumInsertion48Hours,
            etExtendedPostPartumInsertion, etImmediateExpulsion, etDelayedExpulsion;

    //Section 4 declaration
    RadioGroup rgIECMaterial;
    RadioButton rbIECMaterialYes, rbIECMaterialNo;

    RadioGroup rgLastQAT;
    RadioButton rbLastQATYes, rbLastQATNo;

    RadioGroup rgRecordBook;
    RadioButton rbRecordBookYes, rbRecordBookNo;

    RadioGroup rgDetailFilled;
    RadioButton rbDetailFilledYes, rbDetailFilledNo;

    RadioGroup rgTrainingCert;
    RadioButton rbTrainingCertYes, rbTrainingCertNo;

    RadioGroup rgAdverseEvent;
    RadioButton rbAdverseEventYes, rbAdverseEventNo;

    RadioGroup rgFlipChart;
    RadioButton rbFlipChartYes, rbFlipChartNo;

    //Section 5
    RadioGroup rgAutoclave;
    RadioButton rbAutoclaveYes, rbAutoclaveNo;

    RadioGroup rgChlorine;
    RadioButton rbChlorineYes, rbChlorineNo;

    RadioGroup rgInstrument;
    RadioButton rbInstrumentYes, rbInstrumentNo;

    RadioGroup rgBoilingInst;
    RadioButton rbBoilingInstYes, rbBoilingInstNo;

    RadioGroup rgGloveUse;
    RadioButton rbGloveUseYes, rbGloveUseNo;

    RadioGroup rgSafetyBox;
    RadioButton rbSafetyBoxYes, rbSafetyBoxNo;

    RadioGroup rgDustbin;
    RadioButton rbDustbinYes, rbDustbinNo;
    /*
        End declaration
    */

    /*
        Section6
    */
    private static final int[][] etMatrixIDS = {
            {R.id.et11, R.id.et12, R.id.et13, R.id.et14, R.id.et15},
            {R.id.et21, R.id.et22, R.id.et23, R.id.et24, R.id.et25},
            {R.id.et31, R.id.et32, R.id.et33, R.id.et34, R.id.et35},
            {R.id.et41, R.id.et42, R.id.et43, R.id.et44, R.id.et45},
            {R.id.et51, R.id.et52, R.id.et53, R.id.et54, R.id.et55},
            {R.id.et61, R.id.et62, R.id.et63, R.id.et64, R.id.et65},
            {R.id.et71, R.id.et72, R.id.et73, R.id.et74, R.id.et75},
            {R.id.et81, R.id.et82, R.id.et83, R.id.et84, R.id.et85},
            {R.id.et91, R.id.et92, R.id.et93, R.id.et94, R.id.et95},
            {R.id.et101, R.id.et102, R.id.et103, R.id.et104, R.id.et105}
    };

    EditText etMatrixAvailabilityStock[][] = new EditText[ROW_EDITTEXT_AVAILABILITY_LENGTH][COLUMN_EDITTEXT_LENGTH];
    EditText etMatrixStockPurchase[][] = new EditText[ROW_EDITTEXT_EXPIRY_LENGTH][COLUMN_EDITTEXT_LENGTH];
    View glAvailabilityStock, glStockPurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_qtv_screen);
        db = AppDatabase.getAppDatabase(this);
        //First section initialization
        etReportingMonth = findViewById(R.id.etReportingMonth);
        updateVisitDate();
        etReportingMonth.setOnClickListener(this);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateVisitDate();
            }

        };
        tvStaffCodeName = findViewById(R.id.tvStaffCodeName);
        tvSupervisorName = findViewById(R.id.tvSupervisorName);
        tvRegion = findViewById(R.id.tvRegion);
        tvProjectCode = findViewById(R.id.tvProjectCode);
        spProviderCodeName = findViewById(R.id.spProviderCodeName);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        etComments = findViewById(R.id.etComments);
        populateFirstSection();
        //End of First section initialization
        glMatrixDiarrhea =  findViewById(R.id.glMatrixDiarrhea);

        glMatrix1 = findViewById(R.id.glMatrix1);
        glMatrix2 = findViewById(R.id.glMatrix2);
        glMatrix3 = findViewById(R.id.glMatrix3);
        glMatrix4 = findViewById(R.id.glMatrix4);
        glAvailabilityStock = findViewById(R.id.glAvailabilityStock);
        glStockPurchase = findViewById(R.id.glStockPurchase);

        // Total FP clients initialize elements in a matrix
        initializeMatrix1Elements();

        initializeSection1();
        initializeSection2();
        initializeSection3();
        initializeSection4();
        initializeSection5();
        initializeSection6();

        attachingListeners();

    }

    private void updateVisitDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

        etReportingMonth.setText(sdf.format(myCalendar.getTime()));
    }

    private void populateFirstSection() {
        SharedPreferences prefs = this.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String region = prefs.getString("region", "");
        String AMName = prefs.getString("AMName", "");
        AMCode = prefs.getString("AMCode", "");
        String name = prefs.getString("name", "");
        staffCode = prefs.getString("staffCode", "");

        tvSupervisorName.setText(AMName.toString());
        tvStaffCodeName.setText(name.toString());
        tvRegion.setText(region.toString());

        //Providers spinner populate
        providers = db.getProvidersDAO().getActiveProviders();

        Providers first = new Providers();
        first.setCode("0");
        first.setName("Please Select");
        providers.add(0, first);
        providerAdapter = new ProviderAdapter(this, R.layout.provider_town_list, R.id.tvProviderNamess, providers);
        spProviderCodeName.setAdapter(providerAdapter);
    }

    private void attachFocusListener(final EditText[] editText) {
        for (int i = 0; i < editText.length; i++) {
            final int loop = i;
            editText[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        if ("0".equals(editText[loop].getText().toString())) {
                            editText[loop].setText("");
                        }
                    } else {
                        if ("".equals(editText[loop].getText().toString())) {
                            editText[loop].setText("0");
                        }
                    }
                }
            });
        }

    }

    private void calculateTotalUsers(String total) {
        if(total.length()>0) {
            int number = Integer.valueOf(total) + Integer.valueOf(tvFPClients.getText().toString());
            tvTotalClients.setText(String.valueOf(number));
        }

    }

    //Greater , never user
    private void calculateTotalNewUsers(String greater, String never) {
        int total = 0;

        if (greater.length() > 0) {
            total += Integer.valueOf(greater);
        }
        if (never.length() > 0) {
            total += Integer.valueOf(never);
        }
        tvNewUsers.setText(String.valueOf(total));

    }

    /*

     */
    private void attachingListeners() {

        etTotalCounselingClients.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                calculateTotalUsers(s.toString());
            }
        });

        etEverUsersGreater.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                //Greater, Never
                calculateTotalNewUsers(s.toString(), etNeverUsers.getText().toString());
            }
        });

        etNeverUsers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Greater, Never
                calculateTotalNewUsers(etEverUsersGreater.getText().toString(), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etOneMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Greater, Never
                calculateTotalInjectables();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etTwoMonths.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Greater, Never
                calculateTotalInjectables();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etThreeMonths.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Greater, Never
                calculateTotalInjectables();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etIUDRemovedSide.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Greater, Never
                calculateTotalIUDRemoval();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etIUDRemovedDesire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Greater, Never
                calculateTotalIUDRemoval();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etIUDRemovedAdverse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Greater, Never
                calculateTotalIUDRemoval();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etIUDRemovedOther.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Greater, Never
                calculateTotalIUDRemoval();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void calculateTotalInjectables() {
        int total = 0;

        if (etOneMonth.getText().toString().length() > 0) {
            total += Integer.valueOf(etOneMonth.getText().toString());
        }

        if (etTwoMonths.getText().toString().length() > 0) {
            total += Integer.valueOf(etTwoMonths.getText().toString());
        }

        if (etThreeMonths.getText().toString().length() > 0) {
            total += Integer.valueOf(etThreeMonths.getText().toString());
        }
        tvTotalInjectableClients.setText(String.valueOf(total));
    }

    private void calculateTotalIUDRemoval() {
        int total = 0;

        if (etIUDRemovedOther.getText().toString().length() > 0) {
            total += Integer.valueOf(etIUDRemovedOther.getText().toString());
        }

        if (etIUDRemovedAdverse.getText().toString().length() > 0) {
            total += Integer.valueOf(etIUDRemovedAdverse.getText().toString());
        }

        if (etIUDRemovedDesire.getText().toString().length() > 0) {
            total += Integer.valueOf(etIUDRemovedDesire.getText().toString());
        }

        if (etIUDRemovedSide.getText().toString().length() > 0) {
            total += Integer.valueOf(etIUDRemovedSide.getText().toString());
        }
        tvTotalIUDRemovalCases.setText(String.valueOf(total));
    }


    /*
    Initialize elements of section 1
     */
    private void initializeSection1() {

        etCurrentUser = findViewById(R.id.etTotalCurrentUser);
        etMethodSwitcher = findViewById(R.id.etMethodSwitcher);
        etMisoCases = findViewById(R.id.etMisoCases);
        etPACCases = findViewById(R.id.etPACCases);
        etEverUsersLess = findViewById(R.id.etEverUserLess);
        etEverUsersGreater = findViewById(R.id.etEverUserGreater);
        etNeverUsers = findViewById(R.id.etNeverUser);
        etDeliveriesConducted = findViewById(R.id.etDeliveriesConducted);
        etTotalCounselingClients = findViewById(R.id.etTotalCounselingClients);
        etOneMonth = findViewById(R.id.etOneMonth);
        etTwoMonths = findViewById(R.id.etTwoMonth);
        etThreeMonths = findViewById(R.id.etThreeMonths);
        etPACLTMAdoptedOneWeek = findViewById(R.id.etPACLTMAdoptedOneWeek);
        etPACLTMAdoptedHours = findViewById(R.id.etPACLTMAdoptedHours);
        etPACFPAdoptedOneWeek = findViewById(R.id.etPACFPAdoptedOneWeek);
        etPACFPAdoptedHours = findViewById(R.id.etPACFPAdoptedHours);
        EditText[] arr = {etCurrentUser, etMethodSwitcher, etMisoCases, etPACCases, etEverUsersLess, etEverUsersGreater,
                etNeverUsers, etDeliveriesConducted, etTotalCounselingClients, etOneMonth, etTwoMonths, etThreeMonths, etPACLTMAdoptedOneWeek, etPACLTMAdoptedHours, etPACFPAdoptedOneWeek, etPACFPAdoptedHours};
        attachFocusListener(arr);

        tvNewUsers = findViewById(R.id.tvTotalNewUsers);
        tvFPClients = findViewById(R.id.tvTotalFPClients);
        tvTotalClients = findViewById(R.id.tvTotalClients);
        tvCondomClients = findViewById(R.id.tvTotalCondomClients);
        tvPillClients = findViewById(R.id.tvTotalPillClients);
        tvIUDClients = findViewById(R.id.tvTotalIUDClients);
        tvImplantClients = findViewById(R.id.tvTotalImplantClients);
        tvInjectableClients = findViewById(R.id.tvTotalInjectablesClientsSum);
        tvVSCClients = findViewById(R.id.tvTotalVSCClients);
        tvPPIUDClients = findViewById(R.id.tvTotalPPIUCDClients);
        tvTotalInjectableClients = findViewById(R.id.tvTotalInjectablesClients);
    }


    /*
    Initialize elements of section 2
     */
    private void initializeSection2() {

        etIUDRemovedSide = findViewById(R.id.etIUDRemovedSide);
        etIUDRemovedDesire = findViewById(R.id.etIUDRemovedDesire);
        etIUDRemovedAdverse = findViewById(R.id.etIUDRemovedAdverse);
        etIUDRemovedOther = findViewById(R.id.etIUDRemovedOther);
        EditText[] arr = {etIUDRemovedSide, etIUDRemovedDesire, etIUDRemovedAdverse, etIUDRemovedOther};
        attachFocusListener(arr);


        tvTotalIUDRemovalCases = findViewById(R.id.tvTotalIUDRemoved);
    }

    /*
    Initialize elements of section 3
     */
    private void initializeSection3() {

        etPlacentalInsertion = findViewById(R.id.etPlacentalInsertion);
        etImmediatePostPartumInsertion = findViewById(R.id.etImmediatePostPartumInsertion);
        etPostPartumInsertion48Hours = findViewById(R.id.etPostPartumInsertion48Hours);
        etExtendedPostPartumInsertion = findViewById(R.id.etExtendedPostPartumInsertion);
        etImmediateExpulsion = findViewById(R.id.etImmediateExpulsion);
        etDelayedExpulsion = findViewById(R.id.etDelayedExpulsion);

        EditText[] arr = {etPlacentalInsertion, etImmediatePostPartumInsertion, etPostPartumInsertion48Hours,
                etExtendedPostPartumInsertion, etImmediateExpulsion, etDelayedExpulsion};
        attachFocusListener(arr);
    }

    /*
    Initialize elements of section 4
    Yes/NO
     */
    private void initializeSection4() {

        rgIECMaterial = findViewById(R.id.rgIECMaterial);
        rbIECMaterialYes = findViewById(R.id.rbIECMaterialYes);
        rbIECMaterialNo = findViewById(R.id.rbIECMaterialNo);

        rgLastQAT = findViewById(R.id.rgLastQAT);
        rbLastQATNo = findViewById(R.id.rbLastQATNo);
        rbLastQATYes = findViewById(R.id.rbLastQATYes);

        rgRecordBook = findViewById(R.id.rgRecordBook);
        rbRecordBookYes = findViewById(R.id.rbRecordBookYes);
        rbRecordBookNo = findViewById(R.id.rbRecordBookNo);

        rgDetailFilled = findViewById(R.id.rgDetailFilled);
        rbDetailFilledYes = findViewById(R.id.rbDetailFilledYes);
        rbDetailFilledNo = findViewById(R.id.rbDetailFilledNo);

        rgTrainingCert = findViewById(R.id.rgTrainingCert);
        rbTrainingCertYes = findViewById(R.id.rbTrainingCertYes);
        rbTrainingCertNo = findViewById(R.id.rbTrainingCertNo);

        rgAdverseEvent = findViewById(R.id.rgAdverseEvent);
        rbAdverseEventYes = findViewById(R.id.rbAdverseEventYes);
        rbAdverseEventNo = findViewById(R.id.rbAdverseEventNo);

        rgFlipChart = findViewById(R.id.rgFlipChart);
        rbFlipChartYes = findViewById(R.id.rbFlipChartYes);
        rbFlipChartNo = findViewById(R.id.rbFlipChartNo);
    }

    /*
    Initialize elements of section 5
    Yes/NO
     */
    private void initializeSection5() {

        rgAutoclave = findViewById(R.id.rgAutoclave);
        rbAutoclaveYes = findViewById(R.id.rbAutoclaveYes);
        rbAutoclaveNo = findViewById(R.id.rbAutoclaveNo);

        rgChlorine = findViewById(R.id.rgChlorine);
        rbChlorineYes = findViewById(R.id.rbChlorineYes);
        rbChlorineNo = findViewById(R.id.rbChlorineNo);

        rgInstrument = findViewById(R.id.rgInstrument);
        rbInstrumentYes = findViewById(R.id.rbInstrumentYes);
        rbInstrumentNo = findViewById(R.id.rbInstrumentNo);

        rgBoilingInst = findViewById(R.id.rgBoilingInst);
        rbBoilingInstYes = findViewById(R.id.rbBoilingInstYes);
        rbBoilingInstNo = findViewById(R.id.rbBoilingInstNo);

        rgGloveUse = findViewById(R.id.rgGloveUse);
        rbGloveUseYes = findViewById(R.id.rbGloveUseYes);
        rbGloveUseNo = findViewById(R.id.rbGloveUseNo);

        rgSafetyBox = findViewById(R.id.rgSafetyBox);
        rbSafetyBoxYes = findViewById(R.id.rbSafetyBoxYes);
        rbSafetyBoxNo = findViewById(R.id.rbSafetyBoxNo);

        rgDustbin = findViewById(R.id.rgDustbin);
        rbDustbinYes = findViewById(R.id.rbDustbinYes);
        rbDustbinNo = findViewById(R.id.rbDustbinNo);
    }

    /*
    Initialize elements of section 6
    Matrix edit text
     */
    private void initializeSection6() {
        EditText[] editTexts = new EditText[95];
        int loop = 0;
        for (int i = 0; i < ROW_EDITTEXT_AVAILABILITY_LENGTH; i++) {
            for (int j = 0; j < COLUMN_EDITTEXT_LENGTH; j++) {
                //Edit Text Matrix initialization
                etMatrixAvailabilityStock[i][j] = glAvailabilityStock.findViewById(etMatrixIDS[i][j]);

                editTexts[loop] = etMatrixAvailabilityStock[i][j];
                loop++;
            }
        }
        for (int i = 0; i < ROW_EDITTEXT_EXPIRY_LENGTH; i++) {
            for (int j = 0; j < COLUMN_EDITTEXT_LENGTH; j++) {
                //Edit Text Matrix initialization
                etMatrixStockPurchase[i][j] = glStockPurchase.findViewById(etMatrixIDS[i][j]);
                editTexts[loop] = etMatrixStockPurchase[i][j];
                loop++;
            }
        }
        attachFocusListener(editTexts);
    }


    /*
        Total FP clients initialize elements in a matrix1
     */
    private void initializeMatrix1Elements() {
        /*
        Diarrhea matrix initialization
        Its structure is different than other matrix so its declaration will be in different loop

         */

        //Ends declaration

        for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < COLUMN_LENGTH; j++) {


                //Matrix1 initialization
                btnMatrix1[i][j] = glMatrix1.findViewById(btnMatrix1Ids[i][j]);
                btnMatrix1[i][j].setOnLongClickListener(this);
                btnMatrix1[i][j].setOnClickListener(this);

                btnMatrix2[i][j] = glMatrix2.findViewById(btnMatrix2Ids[i][j]);
                btnMatrix2[i][j].setOnLongClickListener(this);
                btnMatrix2[i][j].setOnClickListener(this);

                btnMatrix3[i][j] = glMatrix3.findViewById(btnMatrix3Ids[i][j]);
                btnMatrix3[i][j].setOnLongClickListener(this);
                btnMatrix3[i][j].setOnClickListener(this);

                btnMatrix4[i][j] = glMatrix4.findViewById(btnMatrix4Ids[i][j]);
                btnMatrix4[i][j].setOnLongClickListener(this);
                btnMatrix4[i][j].setOnClickListener(this);
            }
        }

        for (int i = 0; i <= ROW_LENGTH; i++) {
            totalRow1[i] = glMatrix1.findViewById(tvTotalRowsIds[i]);
            totalRow2[i] = glMatrix2.findViewById(tvTotalRowsIds[i]);
            totalRow3[i] = glMatrix3.findViewById(tvTotalRowsIds[i]);
            totalRow4[i] = glMatrix4.findViewById(tvTotalRowsIds[i]);
        }

        for (int i = 0; i < COLUMN_LENGTH; i++) {
            totalColumn1[i] = glMatrix1.findViewById(tvTotalColumnsIds[i]);
            totalColumn2[i] = glMatrix2.findViewById(tvTotalColumnsIds[i]);
            totalColumn3[i] = glMatrix3.findViewById(tvTotalColumnsIds[i]);
            totalColumn4[i] = glMatrix4.findViewById(tvTotalColumnsIds[i]);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.etReportingMonth) {
            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        } else if (v.getId() == R.id.btnSubmit) {
            if (isValid()) {
                new AlertDialog.Builder(this)
                        .setTitle("Save Form")
                        .setMessage("Once submitted, you will not be able to edit this form. Are you sure you want to submit?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                saveFormData();

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        }
        try {
            handleMatrixClick(v.getId());
        }catch(Exception e){

        }
    }

    private boolean isValid() {
        int totalMaleFemale = 0;
        int totalDiarrheaNonDiarrhea = 0;
        int medicationNonMedication = 0;
        int totalCounseling = 0;

        totalMaleFemale = Integer.valueOf(totalRowDiarrhea[0].getText().toString()) +
                Integer.valueOf(totalRowDiarrhea[1].getText().toString());
        totalDiarrheaNonDiarrhea = Integer.valueOf(totalRowDiarrhea[2].getText().toString()) +
                Integer.valueOf(totalRowDiarrhea[3].getText().toString());
        medicationNonMedication = Integer.valueOf(totalRowDiarrhea[4].getText().toString()) +
                Integer.valueOf(totalRowDiarrhea[5].getText().toString());
        totalCounseling = Integer.valueOf(totalRowDiarrhea[6].getText().toString());

        boolean isValid = true;

        if (spProviderCodeName.getSelectedItemPosition() == 0) {
            isValid = false;
            Toast.makeText(this, "Please select Provider", Toast.LENGTH_SHORT).show();
        }else if(totalMaleFemale!=totalCounseling){
            isValid = false;
            Toast.makeText(this, "Sum of Male and Female patients should be equal to Total Counseling.", Toast.LENGTH_SHORT).show();
        }else if(medicationNonMedication!=totalCounseling){
            isValid = false;
            Toast.makeText(this, "Sum of ORS + Zinc prescribed patients and no medication patients should be equal to Total Counseling.", Toast.LENGTH_SHORT).show();
        }else if(totalCounseling<totalDiarrheaNonDiarrhea){
            isValid = false;
            Toast.makeText(this, "Total Counseling should always be greater than sum of Diarrhea and Non Diarrhea patients", Toast.LENGTH_SHORT).show();
        }else if(totalDiarrheaNonDiarrhea<medicationNonMedication){
            isValid = false;
            Toast.makeText(this,
                    "Diarrhea + non Diarrhea patients should always be greater than or equal to sum of ORS + Zinc patients and no medication patients",
                    Toast.LENGTH_LONG).show();
        }else if(totalMaleFemale!=totalDiarrheaNonDiarrhea){
            isValid = false;
            Toast.makeText(this,
                    "Male patients + Female patients should always be equal to sum of Diarrhea and non diarrhea patients",
                    Toast.LENGTH_LONG).show();
        }else if (Integer.valueOf(totalRow4[ROW_LENGTH].getText().toString()) > Integer.valueOf(tvNewUsers.getText().toString())) {
            Toast.makeText(this, "Total new User Method mix from matrix is greater than Total New users. Both should be equal", Toast.LENGTH_LONG).show();
            isValid = false;
        } else if (Integer.valueOf(totalRow4[ROW_LENGTH].getText().toString()) < Integer.valueOf(tvNewUsers.getText().toString())) {
            isValid = false;
            Toast.makeText(this, "Total new User is greater than Total New User Method mix from matrix. Both should be equal", Toast.LENGTH_LONG).show();
        } else if(Integer.valueOf(totalRow1[ROW_LENGTH].getText().toString()) < Integer.valueOf(totalRow2[ROW_LENGTH].getText().toString())){
            isValid = false;
            Toast.makeText(this, "Total FP clients matrix total is less than Post partum FP total clients matrix2. Matrix1 total should be greater than Matrix2 total.", Toast.LENGTH_LONG).show();
        }else if(Integer.valueOf(totalRow1[ROW_LENGTH].getText().toString()) < Integer.valueOf(totalRow3[ROW_LENGTH].getText().toString())){
            isValid = false;
            Toast.makeText(this, "Total FP clients matrix total is less than Post PAC FP total clients matrix3. Matrix1 total should be greater than Matrix3 total.", Toast.LENGTH_LONG).show();
        }else if(Integer.valueOf(totalRow1[ROW_LENGTH].getText().toString()) < Integer.valueOf(totalRow4[ROW_LENGTH].getText().toString())){
            isValid = false;
            Toast.makeText(this, "Total FP clients matrix total is less than New User total clients matrix4. Matrix1 total should be greater than Matrix4 total.", Toast.LENGTH_LONG).show();
        }else if(Integer.valueOf(totalRow3[ROW_LENGTH].getText().toString()) > Integer.valueOf("".equals(etPACCases.getText().toString())?"0":etPACCases.getText().toString())){
            isValid = false;
            Toast.makeText(this, "Total PAC cases should be greater than matrix 3 total(MVA)", Toast.LENGTH_LONG).show();
        }

        return isValid;

    }

    private void saveFormData() {
        QTVForm qtvForm = new QTVForm();
        qtvForm.setChoName(tvStaffCodeName.getText().toString());
        qtvForm.setRegion(tvRegion.getText().toString());
        qtvForm.setSupervisorCode(AMCode);
        qtvForm.setSupervisorName(tvSupervisorName.getText().toString());
        Providers provider = (Providers) spProviderCodeName.getSelectedItem();
        qtvForm.setProviderCode(provider.getCode());
        qtvForm.setProviderName(provider.getName());

        /*
        Diarrhea Matrix
         */

        String count = "";
        for (int i = 0; i < COLUMN_LENGTH_DIARRHEA; i++) {
            if (count != "")
                count += "-";
            count += btnMatrixDiarrhea[0][i].getText().toString();
        }
        //qtvForm.setDiarrhea2To5(String.valueOf(count));

        count = "";
        for (int i = 0; i < COLUMN_LENGTH_DIARRHEA; i++) {
            if (count != "")
                count += "-";
            count += btnMatrixDiarrhea[1][i].getText().toString();
        }
        //qtvForm.setDiarrhea6To10(String.valueOf(count));

        count = "";
        for (int i = 0; i < COLUMN_LENGTH_DIARRHEA; i++) {
            if (count != "")
                count += "-";
            count += btnMatrixDiarrhea[2][i].getText().toString();
        }
        //qtvForm.setDiarrhea11To14(String.valueOf(count));

        count = "";


        for (int i = 0; i < COLUMN_LENGTH_DIARRHEA; i++) {
            if (count != "")
                count += "-";
            count += totalRowDiarrhea[i].getText().toString();
        }
      //  qtvForm.setDiarrheaTotal(String.valueOf(count));

        /*
        ENDS Matrix Diarrhea
         */

        /*
        Count condoms of 1st matrix
         */
        count="";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix1[i][0].getText().toString();
        }
        qtvForm.setDeliveryDataCondoms(String.valueOf(count));

        /*
        Count pills of 1st matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix1[i][1].getText().toString();
        }
        qtvForm.setDeliveryDataPills(count);

        /*
        Count IUD of 1st matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix1[i][2].getText().toString();
        }
        qtvForm.setDeliveryDataIUCD(count);

        /*
        Count Implants of 1st matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix1[i][3].getText().toString();
        }
        qtvForm.setDeliveryDataImplants(count);

        /*
        Count Injectables of 1st matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix1[i][4].getText().toString();
        }
        qtvForm.setDeliveryDataInjectables(count);

        /*
        Count VSC of 1st matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix1[i][5].getText().toString();
        }
        qtvForm.setDeliveryDataVSC(count);

        /*
        Count total column of 1st matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += totalRow1[i].getText().toString();
        }
        qtvForm.setDeliveryDataTotalColumn(String.valueOf(count));

        /*
        Count total Row of 1st matrix
         */
        count = "";
        for (int i = 0; i < COLUMN_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += Integer.valueOf(totalColumn1[i].getText().toString());
        }
        qtvForm.setDeliveryDataTotalRow(String.valueOf(count));

        //Total of all total of matrix 1
        qtvForm.setDeliveryDataTotal(Integer.valueOf(totalRow1[ROW_LENGTH].getText().toString()));

        /*
        Count condoms of 2nd matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix2[i][0].getText().toString();
        }
        qtvForm.setPostPartumCondoms(String.valueOf(count));

        /*
        Count pills of 2nd matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix2[i][1].getText().toString();
        }
        qtvForm.setPostPartumPills(count);

        /*
        Count IUD of 2nd matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix2[i][2].getText().toString();
        }
        qtvForm.setPostPartumIUCD(count);

        /*
        Count Implants of 2nd matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix2[i][3].getText().toString();
        }
        qtvForm.setPostPartumImplants(count);

        /*
        Count Injectables of 2nd matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix2[i][4].getText().toString();
        }
        qtvForm.setPostPartumInjectables(count);

        /*
        Count VSC of 2nd matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix2[i][5].getText().toString();
        }
        qtvForm.setPostPartumVSC(count);

        /*
        Count total column of 2nd matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += totalRow2[i].getText().toString();
        }
        qtvForm.setPostPartumTotalColumn(String.valueOf(count));

        /*
        Count total Row of 2nd matrix
         */
        count = "";
        for (int i = 0; i < COLUMN_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += Integer.valueOf(totalColumn2[i].getText().toString());
        }
        qtvForm.setPostPartumTotalRow(String.valueOf(count));

        //Total of all total of matrix 2
        qtvForm.setPostPartumTotal(Integer.valueOf(totalRow2[ROW_LENGTH ].getText().toString()));

        /*
        Count condoms of 3rd matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix3[i][0].getText().toString();
        }
        qtvForm.setPostPACCondoms(String.valueOf(count));

        /*
        Count pills of 3rd matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix3[i][1].getText().toString();
        }
        qtvForm.setPostPACPills(count);

        /*
        Count IUD of 3rd matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix3[i][2].getText().toString();
        }
        qtvForm.setPostPACIUCD(count);

        /*
        Count Implants of 3rd matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix3[i][3].getText().toString();
        }
        qtvForm.setPostPACImplants(count);

        /*
        Count Injectables of 3rd matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix3[i][4].getText().toString();
        }
        qtvForm.setPostPACInjectables(count);

        /*
        Count VSC of 3rd matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix3[i][5].getText().toString();
        }
        qtvForm.setPostPACVSC(count);

        /*
        Count total column of 3rd matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += totalRow3[i].getText().toString();
        }
        qtvForm.setPostPACTotalColumn(String.valueOf(count));

        /*
        Count total Row of 3rd matrix
         */
        count = "";
        for (int i = 0; i < COLUMN_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += Integer.valueOf(totalColumn3[i].getText().toString());
        }
        qtvForm.setPostPACTotalRow(String.valueOf(count));

        //Total of all total of matrix 3
        qtvForm.setPostPACTotal(Integer.valueOf(totalRow3[ROW_LENGTH].getText().toString()));



        /*
        Count condoms of 4th matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix4[i][0].getText().toString();
        }
        qtvForm.setNewUserCondoms(String.valueOf(count));

        /*
        Count pills of 4th matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix4[i][1].getText().toString();
        }
        qtvForm.setNewUserPills(count);

        /*
        Count IUD of 4th matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix4[i][2].getText().toString();
        }
        qtvForm.setNewUserIUCD(count);

        /*
        Count Implants of 4th matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix4[i][3].getText().toString();
        }
        qtvForm.setNewUserImplants(count);

        /*
        Count Injectables of 4th matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix4[i][4].getText().toString();
        }
        qtvForm.setNewUserInjectables(count);

        /*
        Count VSC of 4th matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += btnMatrix4[i][5].getText().toString();
        }
        qtvForm.setNewUserVSC(count);

        /*
        Count total column of 4th matrix
         */
        count = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += totalRow4[i].getText().toString();
        }
        qtvForm.setNewUserTotalColumn(String.valueOf(count));

        /*
        Count total Row of 4th matrix
         */
        count = "";
        for (int i = 0; i < COLUMN_LENGTH; i++) {
            if (count != "")
                count += "-";
            count += Integer.valueOf(totalColumn4[i].getText().toString());
        }
        qtvForm.setNewUserTotalRow(String.valueOf(count));

        //Total of all total of matrix 4
        qtvForm.setNewUserTotal(Integer.valueOf(totalRow4[ROW_LENGTH].getText().toString()));

        qtvForm.setTotalCurrentUsers(Integer.valueOf("".equals(etCurrentUser.getText().toString())?"0":etCurrentUser.getText().toString()));
        qtvForm.setTotalMethodSwitcher(Integer.valueOf("".equals(etMethodSwitcher.getText().toString())?"0":etMethodSwitcher.getText().toString()));
        qtvForm.setTotalMisoCases(Integer.valueOf("".equals(etMisoCases.getText().toString())?"0":etMisoCases.getText().toString()));
        qtvForm.setTotalPACCases(Integer.valueOf("".equals(etPACCases.getText().toString())?"0":etPACCases.getText().toString()));
        qtvForm.setTotalEverUsersLess(Integer.valueOf("".equals(etEverUsersLess.getText().toString())?"0":etEverUsersLess.getText().toString()));
        qtvForm.setTotalEverUsersGreater(Integer.valueOf("".equals(etEverUsersGreater.getText().toString())?"0":etEverUsersGreater.getText().toString()));
        qtvForm.setTotalNeverUsers(Integer.valueOf("".equals(etNeverUsers.getText().toString())?"0":etNeverUsers.getText().toString()));
        qtvForm.setTotalNewUsers(Integer.valueOf("".equals(tvNewUsers.getText().toString())?"0":tvNewUsers.getText().toString()));
        qtvForm.setTotalDeliveryConducted(Integer.valueOf("".equals(etDeliveriesConducted.getText().toString())?"0":etDeliveriesConducted.getText().toString()));
        qtvForm.setTotalFPClients(Integer.valueOf("".equals(tvFPClients.getText().toString())?"0":tvFPClients.getText().toString()));
        qtvForm.setTotalClients(Integer.valueOf("".equals(tvTotalClients.getText().toString())?"0":tvTotalClients.getText().toString()));
        qtvForm.setTotalCounselingClients(Integer.valueOf("".equals(etTotalCounselingClients.getText().toString())?"0":etTotalCounselingClients.getText().toString()));
        qtvForm.setTotalCondomClients(Integer.valueOf("".equals(tvCondomClients.getText().toString())?"0":tvCondomClients.getText().toString()));
        qtvForm.setTotalPillsClient(Integer.valueOf("".equals(tvPillClients.getText().toString())?"0":tvPillClients.getText().toString()));
        qtvForm.setTotalIUDClients(Integer.valueOf("".equals(tvIUDClients.getText().toString())?"0":tvIUDClients.getText().toString()));
        qtvForm.setTotalImplantClients(Integer.valueOf("".equals(tvImplantClients.getText().toString())?"0":tvImplantClients.getText().toString()));
        qtvForm.setTotalInjectableClients(Integer.valueOf("".equals(tvInjectableClients.getText().toString())?"0":tvInjectableClients.getText().toString()));
        qtvForm.setTotalVSCClients(Integer.valueOf("".equals(tvVSCClients.getText().toString())?"0":tvVSCClients.getText().toString()));
        qtvForm.setTotalPPIUDClients(Integer.valueOf("".equals(tvPPIUDClients.getText().toString())?"0":tvPPIUDClients.getText().toString()));
        qtvForm.setTotalInjectableClients(Integer.valueOf("".equals(tvInjectableClients.getText().toString())?"0":tvInjectableClients.getText().toString()));
        qtvForm.setOneMonthInjectables(Integer.valueOf("".equals(etOneMonth.getText().toString())?"0":etOneMonth.getText().toString()));
        qtvForm.setTwoMonthsInjectables(Integer.valueOf("".equals(etTwoMonths.getText().toString())?"0":etTwoMonths.getText().toString()));
        qtvForm.setThreeMonthsInjectables(Integer.valueOf("".equals(etThreeMonths.getText().toString())?"0":etThreeMonths.getText().toString()));
        qtvForm.setTotalPACLTMAdoptedOneWeek(Integer.valueOf("".equals(etPACLTMAdoptedOneWeek.getText().toString())?"0":etPACLTMAdoptedOneWeek.getText().toString()));
        qtvForm.setTotalPACLTMAdoptedHours(Integer.valueOf("".equals(etPACLTMAdoptedHours.getText().toString())?"0":etPACLTMAdoptedHours.getText().toString()));
        qtvForm.setTotalPACFPAdoptedOneWeek(Integer.valueOf("".equals(etPACFPAdoptedOneWeek.getText().toString())?"0":etPACFPAdoptedOneWeek.getText().toString()));
        qtvForm.setTotalPACFPAdoptedHours(Integer.valueOf("".equals(etPACFPAdoptedHours.getText().toString())?"0":etPACFPAdoptedHours.getText().toString()));

        //2nd Section
        qtvForm.setIUDRemovedSideEffects(Integer.valueOf("".equals(etIUDRemovedSide.getText().toString())?"0":etIUDRemovedSide.getText().toString()));
        qtvForm.setIUDRemovedDesire(Integer.valueOf("".equals(etIUDRemovedDesire.getText().toString())?"0":etIUDRemovedDesire.getText().toString()));
        qtvForm.setIUDRemovedAdverse(Integer.valueOf("".equals(etIUDRemovedAdverse.getText().toString())?"0":etIUDRemovedAdverse.getText().toString()));
        qtvForm.setIUDRemovedOther(Integer.valueOf("".equals(etIUDRemovedOther.getText().toString())?"0":etIUDRemovedOther.getText().toString()));
        qtvForm.setTotalIUDRemovedCases(Integer.valueOf("".equals(tvTotalIUDRemovalCases.getText().toString())?"0":tvTotalIUDRemovalCases.getText().toString()));

        //3rd Section
        qtvForm.setPlacentalInsertion(Integer.valueOf("".equals(etPlacentalInsertion.getText().toString())?"0":etPlacentalInsertion.getText().toString()));
        qtvForm.setImmediatePostPartum(Integer.valueOf("".equals(etImmediatePostPartumInsertion.getText().toString())?"0":etImmediatePostPartumInsertion.getText().toString()));
        qtvForm.setPostPartumInsertion48Hours(Integer.valueOf("".equals(etPostPartumInsertion48Hours.getText().toString())?"0":etPostPartumInsertion48Hours.getText().toString()));
        qtvForm.setExtendedPostPartumInsertion(Integer.valueOf("".equals(etExtendedPostPartumInsertion.getText().toString())?"0":etExtendedPostPartumInsertion.getText().toString()));
        qtvForm.setImmediateExpulsion(Integer.valueOf("".equals(etImmediateExpulsion.getText().toString())?"0":etImmediateExpulsion.getText().toString()));
        qtvForm.setDelayedExpulsion(Integer.valueOf("".equals(etImmediateExpulsion.getText().toString())?"0":etImmediateExpulsion.getText().toString()));

        //4th Section
        qtvForm.setIECMaterial(rbIECMaterialYes.isSelected() == true ? 1 : 0);
        qtvForm.setLastQATAvailable(rbLastQATYes.isSelected() == true ? 1 : 0);
        qtvForm.setClientRecordBook(rbRecordBookYes.isSelected() == true ? 1 : 0);
        qtvForm.setClientDetails(rbDetailFilledYes.isSelected() == true ? 1 : 0);
        qtvForm.setTrainingCertificates(rbTrainingCertYes.isSelected() == true ? 1 : 0);
        qtvForm.setAdverseEventReferrals(rbAdverseEventYes.isSelected() == true ? 1 : 0);
        qtvForm.setCounselingFlipChart(rbFlipChartYes.isSelected() == true ? 1 : 0);

        //5th Section
        qtvForm.setAutoClave(rbAutoclaveYes.isSelected() == true ? 1 : 0);
        qtvForm.setChlorineUsed(rbChlorineYes.isSelected() == true ? 1 : 0);
        qtvForm.setInstrumentStored(rbInstrumentYes.isSelected() == true ? 1 : 0);
        qtvForm.setBoilingInstrument(rbBoilingInstYes.isSelected() == true ? 1 : 0);
        qtvForm.setGlovesInUse(rbGloveUseYes.isSelected() == true ? 1 : 0);
        qtvForm.setSafetyBoxInUse(rbSafetyBoxYes.isSelected() == true ? 1 : 0);
        qtvForm.setDustbinDisposable(rbDustbinYes.isSelected() == true ? 1 : 0);

        //6.1 Section etMatrixAvailabilityStock

        //Greenstar Column
        count = "";
        for (int i = 0; i < ROW_EDITTEXT_AVAILABILITY_LENGTH; i++) {
            if (!"".equals(count))
                count += "-";
            count += etMatrixAvailabilityStock[i][0].getText().toString();
        }
        qtvForm.setAvailabilityGreenstar(count);

        //Govt. Column
        count = "";
        for (int i = 0; i < ROW_EDITTEXT_AVAILABILITY_LENGTH; i++) {
            if (!"".equals(count))
                count += "-";
            count += etMatrixAvailabilityStock[i][1].getText().toString();
        }
        qtvForm.setAvailabilityGovt(count);

        //MSS. Column
        count = "";
        for (int i = 0; i < ROW_EDITTEXT_AVAILABILITY_LENGTH; i++) {
            if (!"".equals(count))
                count += "-";
            count += etMatrixAvailabilityStock[i][2].getText().toString();
        }
        qtvForm.setAvailabilityMSS(count);

        //DKT. Column
        count = "";
        for (int i = 0; i < ROW_EDITTEXT_AVAILABILITY_LENGTH; i++) {
            if (!"".equals(count))
                count += "-";
            count += etMatrixAvailabilityStock[i][3].getText().toString();
        }
        qtvForm.setAvailabilityDKT(count);

        //Other. Column
        count = "";
        for (int i = 0; i < ROW_EDITTEXT_AVAILABILITY_LENGTH; i++) {
            if (!"".equals(count))
                count += "-";
            count += etMatrixAvailabilityStock[i][4].getText().toString();
        }
        qtvForm.setAvailabilityOther(count);

        //6.2 Section etMatrixStockPurchase

        //Greenstar Column
        count = "";
        for (int i = 0; i < ROW_EDITTEXT_EXPIRY_LENGTH; i++) {
            if (!"".equals(count))
                count += "-";
            count += etMatrixStockPurchase[i][0].getText().toString();
        }
        qtvForm.setStockGreenstar(count);

        //Govt. Column
        count = "";
        for (int i = 0; i < ROW_EDITTEXT_EXPIRY_LENGTH; i++) {
            if (!"".equals(count))
                count += "-";
            count += etMatrixStockPurchase[i][1].getText().toString();
        }
        qtvForm.setStockGovt(count);

        //MSS. Column
        count = "";
        for (int i = 0; i < ROW_EDITTEXT_EXPIRY_LENGTH; i++) {
            if (!"".equals(count))
                count += "-";
            count += etMatrixStockPurchase[i][2].getText().toString();
        }
        qtvForm.setStockMSS(count);

        //DKT. Column
        count = "";
        for (int i = 0; i < ROW_EDITTEXT_EXPIRY_LENGTH; i++) {
            if (!"".equals(count))
                count += "-";
            count += etMatrixStockPurchase[i][3].getText().toString();
        }
        qtvForm.setStockDKT(count);

        //Other. Column
        count = "";
        for (int i = 0; i < ROW_EDITTEXT_EXPIRY_LENGTH; i++) {
            if (!"".equals(count))
                count += "-";
            count += etMatrixStockPurchase[i][4].getText().toString();
        }
        qtvForm.setStockOther(count);

        qtvForm.setComments(etComments.getText().toString());
        qtvForm.setApprovalStatus(0);

        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

        qtvForm.setMobileSystemDate(sdf.format(myCalendar.getTime()));
        qtvForm.setId(Util.getNextID(this,Codes.QTVFORM));
        qtvForm.setVisitDate(etReportingMonth.getText().toString());
        qtvForm.setChoCode(staffCode);
        Location location = getLastKnownLocation();
        if (location != null) {
            qtvForm.setLatLong(location.getLatitude() + "," + location.getLongitude());
        }

        db = AppDatabase.getAppDatabase(this);
        db.getQTVFormDAO().insert(qtvForm);

        Toast.makeText(this, "Form submitted successfully", Toast.LENGTH_SHORT).show();
        finish();

    }

    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                continue;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    /*
        It will handle all clicks on the matrix and calculate the total
     */
    private void handleMatrixClick(int id) {
        int count=0;
        boolean isDiarrheaMatrix = true;

        for(int i=0; i<ROW_LENGTH && isDiarrheaMatrix==true;i++){
            for(int j=0; j<COLUMN_LENGTH;j++){
                if(id == btnMatrix1[i][j].getId()){
                    count = Integer.valueOf(btnMatrix1[i][j].getText().toString())+1;
                    btnMatrix1[i][j].setText(String.valueOf(count));
                    // Calculate Total.
                    calculateTotalMatrix1();
                    isDiarrheaMatrix = false;
                    break;
                }else if(id == btnMatrix2[i][j].getId()){
                    count = Integer.valueOf(btnMatrix2[i][j].getText().toString())+1;
                    btnMatrix2[i][j].setText(String.valueOf(count));
                    // Calculate Total.
                    calculateTotalMatrix2();
                    isDiarrheaMatrix = false;
                    break;
                }else if(id == btnMatrix3[i][j].getId()){
                    count = Integer.valueOf(btnMatrix3[i][j].getText().toString())+1;
                    btnMatrix3[i][j].setText(String.valueOf(count));
                    // Calculate Total.
                    calculateTotalMatrix3();
                    isDiarrheaMatrix = false;
                    break;
                }else if(id == btnMatrix4[i][j].getId()){
                    count = Integer.valueOf(btnMatrix4[i][j].getText().toString())+1;
                    btnMatrix4[i][j].setText(String.valueOf(count));
                    // Calculate Total.
                    calculateTotalMatrix4();
                    isDiarrheaMatrix = false;
                    break;
                }
            }
        }

        if(isDiarrheaMatrix){
            for(int i=0; i<ROW_LENGTH_DIARRHEA;i++) {
                for (int j = 0; j < COLUMN_LENGTH_DIARRHEA; j++) {
                    if(id == btnMatrixDiarrhea[i][j].getId()) {
                        count = Integer.valueOf(btnMatrixDiarrhea[i][j].getText().toString()) + 1;
                        btnMatrixDiarrhea[i][j].setText(String.valueOf(count));
                        // Calculate Total.
                        calculateTotalDiarrheaMatrix();
                    }
                }
            }
        }
    }

    /*
        Decrement cell value
    */
    private void decrementValue(int id){

            int count=0;
            int currentValue=0;
        boolean isDiarrhea = false;
        for(int i=0; i<ROW_LENGTH_DIARRHEA;i++){
            for(int j=0; j<COLUMN_LENGTH_DIARRHEA;j++){
                if(id == btnMatrixDiarrhea[i][j].getId()){
                    currentValue = Integer.valueOf(btnMatrixDiarrhea[i][j].getText().toString());
                    if(currentValue>0) {
                        count = currentValue-1;
                        btnMatrixDiarrhea[i][j].setText(String.valueOf(count));

                        // Calculate Total.
                        calculateTotalDiarrheaMatrix();
                        isDiarrhea = true;
                        break;
                    }
                }
            }
        }
        if(!isDiarrhea) {
            for (int i = 0; i < ROW_LENGTH; i++) {
                for (int j = 0; j < COLUMN_LENGTH; j++) {
                    if (id == btnMatrix1[i][j].getId()) {
                        currentValue = Integer.valueOf(btnMatrix1[i][j].getText().toString());
                        if (currentValue > 0) {
                            count = currentValue - 1;
                            btnMatrix1[i][j].setText(String.valueOf(count));

                            // Calculate Total.
                            calculateTotalMatrix1();
                            break;
                        }
                    } else if (id == btnMatrix2[i][j].getId()) {
                        currentValue = Integer.valueOf(btnMatrix2[i][j].getText().toString());
                        if (currentValue > 0) {
                            count = currentValue - 1;
                            btnMatrix2[i][j].setText(String.valueOf(count));

                            // Calculate Total.
                            calculateTotalMatrix2();
                            break;
                        }
                    } else if (id == btnMatrix3[i][j].getId()) {
                        currentValue = Integer.valueOf(btnMatrix3[i][j].getText().toString());
                        if (currentValue > 0) {
                            count = currentValue - 1;
                            btnMatrix3[i][j].setText(String.valueOf(count));

                            // Calculate Total.
                            calculateTotalMatrix3();
                            break;
                        }
                    } else if (id == btnMatrix4[i][j].getId()) {
                        currentValue = Integer.valueOf(btnMatrix4[i][j].getText().toString());
                        if (currentValue > 0) {
                            count = currentValue - 1;
                            btnMatrix4[i][j].setText(String.valueOf(count));

                            // Calculate Total.
                            calculateTotalMatrix4();
                            break;
                        }
                    }
                }
            }
        }

    }


    /*
        Calculate all totals of Matrix 1
    */
    private void calculateTotalMatrix1() {
        int count = 0;
        //Sum all columns
        for(int i =0 ; i<COLUMN_LENGTH;i++){
            count = 0;
            for(int j=0; j < ROW_LENGTH; j++){
                count += Integer.valueOf(btnMatrix1[j][i].getText().toString());
            }
            totalColumn1[i].setText(String.valueOf(count));
        }

        //Sum all rows
        for(int i =0 ; i<ROW_LENGTH;i++){
            count = 0;
            for(int j=0; j < COLUMN_LENGTH; j++){
                count += Integer.valueOf(btnMatrix1[i][j].getText().toString());
            }
            totalRow1[i].setText(String.valueOf(count));
        }

        count=0;
        //Sum all sums in last row
        for(int i = 0; i<COLUMN_LENGTH;i++){
            count += Integer.valueOf(totalColumn1[i].getText().toString());
        }

        totalRow1[ROW_LENGTH].setText(String.valueOf(count));

        //Total result of matrix1
        tvFPClients.setText(String.valueOf(count));

        tvTotalClients.setText(String.valueOf(count+("".equals(etTotalCounselingClients.getText().toString())?0:Integer.valueOf(etTotalCounselingClients.getText().toString()))));

        //Matrix1 column 1 total
        tvCondomClients.setText(totalColumn1[0].getText().toString());

        //Matrix1 column 2 total
        tvPillClients.setText(totalColumn1[1].getText().toString());

        //Matrix1 column 3 total
        tvIUDClients.setText(totalColumn1[2].getText().toString());

        //Matrix1 column 4 total
        tvImplantClients.setText(totalColumn1[3].getText().toString());

        //Matrix1 column 5 total
        tvInjectableClients.setText(totalColumn1[4].getText().toString());

        //Matrix1 column 6 total
        tvVSCClients.setText(totalColumn1[5].getText().toString());
    }


    /*
        Calculate all totals of Matrix 2
    */
    private void calculateTotalMatrix2() {
        int count = 0;
        //Sum all columns
        for(int i =0 ; i<COLUMN_LENGTH;i++){
            count = 0;
            for(int j=0; j < ROW_LENGTH; j++){
                count += Integer.valueOf(btnMatrix2[j][i].getText().toString());
            }
            totalColumn2[i].setText(String.valueOf(count));
        }

        //Sum all rows
        for(int i =0 ; i<ROW_LENGTH;i++){
            count = 0;
            for(int j=0; j < COLUMN_LENGTH; j++){
                count += Integer.valueOf(btnMatrix2[i][j].getText().toString());
            }
            totalRow2[i].setText(String.valueOf(count));
        }

        count=0;
        //Sum all sums in last row
        for(int i = 0; i<COLUMN_LENGTH;i++){
            count += Integer.valueOf(totalColumn2[i].getText().toString());
        }

        totalRow2[ROW_LENGTH].setText(String.valueOf(count));

        //Total of matrix2
        tvPPIUDClients.setText(String.valueOf(count));
    }

    /*
        Calculate all totals of Matrix 3
    */
    private void calculateTotalMatrix3() {
        int count = 0;
        //Sum all columns
        for(int i =0 ; i<COLUMN_LENGTH;i++){
            count = 0;
            for(int j=0; j < ROW_LENGTH; j++){
                count += Integer.valueOf(btnMatrix3[j][i].getText().toString());
            }
            totalColumn3[i].setText(String.valueOf(count));
        }

        //Sum all rows
        for(int i =0 ; i<ROW_LENGTH;i++){
            count = 0;
            for(int j=0; j < COLUMN_LENGTH; j++){
                count += Integer.valueOf(btnMatrix3[i][j].getText().toString());
            }
            totalRow3[i].setText(String.valueOf(count));
        }

        count=0;
        //Sum all sums in last row
        for(int i = 0; i<COLUMN_LENGTH;i++){
            count += Integer.valueOf(totalColumn3[i].getText().toString());
        }

        totalRow3[ROW_LENGTH].setText(String.valueOf(count));
    }

    /*
        Calculate all totals of Matrix 4
    */
    private void calculateTotalMatrix4() {
        int count = 0;
        //Sum all columns
        for(int i =0 ; i<COLUMN_LENGTH;i++){
            count = 0;
            for(int j=0; j < ROW_LENGTH; j++){
                count += Integer.valueOf(btnMatrix4[j][i].getText().toString());
            }
            totalColumn4[i].setText(String.valueOf(count));
        }

        //Sum all rows
        for(int i =0 ; i<ROW_LENGTH;i++){
            count = 0;
            for(int j=0; j < COLUMN_LENGTH; j++){
                count += Integer.valueOf(btnMatrix4[i][j].getText().toString());
            }
            totalRow4[i].setText(String.valueOf(count));
        }

        count=0;
        //Sum all sums in last row
        for(int i = 0; i<COLUMN_LENGTH;i++){
            count += Integer.valueOf(totalColumn4[i].getText().toString());
        }

        totalRow4[ROW_LENGTH].setText(String.valueOf(count));
    }

    private void calculateTotalDiarrheaMatrix(){
        int count = 0;

        //Sum all rows
        for(int i =0 ; i<COLUMN_LENGTH_DIARRHEA;i++){
            count = 0;
            for(int j=0; j < ROW_LENGTH_DIARRHEA; j++){
                count += Integer.valueOf(btnMatrixDiarrhea[j][i].getText().toString());
            }
            totalRowDiarrhea[i].setText(String.valueOf(count));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        try{
            decrementValue(v.getId());
        }catch(Exception e){
            Crashlytics.logException(e);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        final Activity activity = this;
        new AlertDialog.Builder(this)
                .setTitle("Discard Form")
                .setMessage("Are you sure you want to discard this form?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}