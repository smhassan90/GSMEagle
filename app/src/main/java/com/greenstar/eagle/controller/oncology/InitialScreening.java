package com.greenstar.eagle.controller.oncology;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.greenstar.eagle.R;
import com.greenstar.eagle.adapters.ClientAdapter;
import com.greenstar.eagle.adapters.ProviderAdapter;
import com.greenstar.eagle.controller.Codes;
import com.greenstar.eagle.dal.AreaQuestion;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.Areas;
import com.greenstar.eagle.model.CRForm;
import com.greenstar.eagle.model.Providers;
import com.greenstar.eagle.model.Questions;
import com.greenstar.eagle.model.ScreeningAreaDetail;
import com.greenstar.eagle.model.ScreeningFormHeader;
import com.greenstar.eagle.utils.Util;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitialScreening extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    ProgressDialog progressBar = null;

    LinearLayout llQuestion = null;
    LinearLayout llQuestionBank = null;
    TextView tvAreaHeading = null;
    TextView tvTotalIndicators = null;
    TextView tvTotalPoints = null;
    TextView tvAreaId = null;
    TextView tvTotalCriticalIndicators = null;
    TextView tvTotalNonCriticalIndicators = null;
    
    List<AreaQuestion> areaQuestions = new ArrayList<>();

    DatePickerDialog.OnDateSetListener date = null;

    final Calendar myCalendar = Calendar.getInstance();

    Button btnSubmit;
    AppDatabase db = null;

    TextView tvSitarabajiCode, tvSitarabajiName, tvProviderCode, tvProviderName, tvSupervisorName, tvRegion, tvDistrict;

    EditText etVisitDate,etPersonalMinutes;

    Spinner spClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screening);
        db = AppDatabase.getAppDatabase(this);
        initializeForm();
        populateForm();
    }

    private void updateVisitDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

        etVisitDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void populateForm(){
        etVisitDate.setOnClickListener(this);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateVisitDate();
            }

        };
        populateClientSpinner();

        SharedPreferences prefs = this.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String sitarabajiCode = prefs.getString("sitaraBajiCode", "");
        String sitarabajiName = prefs.getString("sitaraBajiName", "");
        String region = prefs.getString("region", "");

        tvSitarabajiCode.setText(sitarabajiCode);
        tvSitarabajiName.setText(sitarabajiName);
        tvRegion.setText(region);
        updateVisitDate();
    }

    private void populateClientSpinner(){
        List<CRForm> dropdownCRF = new ArrayList<>();

        CRForm clientDummy = new CRForm();
        clientDummy.setClientName("Select Client Name");
        clientDummy.setId(0);

        try{
            if(db==null){
                db = AppDatabase.getAppDatabase(this);
            }

            dropdownCRF = db.getCrFormDAO().getAll();
        }catch(Exception e){

        }

        if(dropdownCRF.size()==0){
            dropdownCRF.add(clientDummy);
        }else{
            dropdownCRF.add(0,clientDummy);
        }

        ClientAdapter clientAdapter = new ClientAdapter(this, R.layout.provider_town_list, R.id.tvProviderNamess, dropdownCRF);
        spClient.setAdapter(clientAdapter);
    }

    private void initializeForm(){
        etVisitDate = findViewById(R.id.etVisitDate);
        tvSitarabajiCode = findViewById(R.id.tvSitarabajiCode);
        tvSitarabajiName = findViewById(R.id.tvSitarabajiName);
        tvSupervisorName = findViewById(R.id.tvSupervisorName);
        tvRegion = findViewById(R.id.tvRegion);
        spClient = findViewById(R.id.spClient);
        spClient.setOnItemSelectedListener(this);

        etPersonalMinutes = findViewById(R.id.etPersonalMinutes);
        etVisitDate.setOnClickListener(this);
    }



    private void populateQuestions() {
        areaQuestions.clear();
        areaQuestions = new ArrayList<>();
        CRForm crForm = (CRForm) spClient.getSelectedItem();
        LinearLayout llAreas = null;

        LayoutInflater inflater = LayoutInflater.from(InitialScreening.this);

        llQuestionBank = findViewById(R.id.llQuestionBank);

        if(llQuestionBank.getChildCount() > 0)
            llQuestionBank.removeAllViews();

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        List<Questions> questions = new ArrayList<>();
        List<Areas> areas = new ArrayList<>();

        areas = db.getAreasDAO().getAll();
        AreaQuestion areaQuestion = new AreaQuestion();
        LinearLayout llArea = null;
        Questions question = new Questions();
        RadioButton rbAnswerYes = null;
        TextView tvQuestionText = null;
        Map<RadioButton,Questions> mapRadioButtonQuestion = null;

        for(Areas area : areas){

            View areaView = inflater.inflate(R.layout.screening_questions_area, null);


            LinearLayout combineAreaQuestion = areaView.findViewById(R.id.llQuestionSeries);
            areaQuestion = new AreaQuestion();
            areaQuestion.setAreaId(area.getId());
            areaQuestion.setAreaName(area.getDetail());

            questions = db.getQuestionsDAO().getActiveQuestionsOfArea(area.getId());
            mapRadioButtonQuestion = new HashMap<>();

            for(Questions quest : questions){
                View rowQuestion = inflater.inflate(R.layout.screening_question, null);
                llQuestion = rowQuestion.findViewById(R.id.llQuestion);


                rbAnswerYes = rowQuestion.findViewById(R.id.rbAnswerYes);
                rbAnswerYes.setOnCheckedChangeListener(this);
                tvQuestionText = rowQuestion.findViewById(R.id.tvQuestionText);

                String questionStr =quest.getDetail();

                tvQuestionText.setText(fromHtml(questionStr));
                if(quest.getPoints()>1){
                    tvQuestionText.setTypeface(null, Typeface.BOLD);
                    tvQuestionText.setTextColor(getResources().getColor(R.color.darkOrange));
                }
                mapRadioButtonQuestion.put(rbAnswerYes,quest);

                combineAreaQuestion.addView(llQuestion);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) llQuestion.getLayoutParams();
                lp.setMargins(0, 10, 0, 0);
                llQuestion.setLayoutParams(lp);
            }

            tvAreaHeading = areaView.findViewById(R.id.tvAreaHeading);

            tvAreaHeading.setText(area.getDetail());

            areaQuestion.setQuestionRadioButtons(mapRadioButtonQuestion);
            areaQuestions.add(areaQuestion);
            areaQuestion.setAreaView(areaView);

            calculateArea(areaQuestion);
            llQuestionBank.addView(areaView);
        }
    }

    private void calculateArea(AreaQuestion areaQuestion) {
        View areaView = areaQuestion.getAreaView();

        Map<RadioButton, Questions> questionRadioButton = areaQuestion.getQuestionRadioButtons();
        RadioButton rbYes = null;
        Questions question = null;

        int totalIndicators = 0;
        int totalYesIndicators=0;

        int totalPoints=0;

        int totalCriticalIndicators = 0;
        int totalYesCriticalIndicators =0;

        int totalNonCriticalIndicators = 0;
        int totalYesNonCriticalIndicators =0;

        for (Map.Entry<RadioButton, Questions> entry : questionRadioButton.entrySet()) {
            rbYes=(RadioButton) entry.getKey();
            question = (Questions) entry.getValue();
            totalIndicators++;


            if(rbYes.isChecked()){
                totalYesIndicators++;
                if(question.getPoints()>1){
                    totalYesCriticalIndicators++;
                }else{
                    totalYesNonCriticalIndicators++;
                }
                totalPoints = totalPoints + question.getPoints();
            }

            if(question.getPoints()>1){
                totalCriticalIndicators++;
            }
        }

        totalNonCriticalIndicators = totalIndicators - totalCriticalIndicators;

        tvTotalIndicators = areaView.findViewById(R.id.tvTotalIndicators);
        tvTotalPoints = areaView.findViewById(R.id.tvTotalPoints);
        tvAreaId = areaView.findViewById(R.id.tvAreaId);
        tvAreaId.setText(String.valueOf(areaQuestion.getAreaId()));
        tvTotalCriticalIndicators = areaView.findViewById(R.id.tvTotalCriticalIndicators);
        tvTotalNonCriticalIndicators = areaView.findViewById(R.id.tvTotalNonCriticalIndicators);

        tvTotalIndicators.setText(totalYesIndicators+" / "+totalIndicators);
        tvTotalPoints.setText(String.valueOf(totalPoints));
        tvTotalCriticalIndicators.setText(totalYesCriticalIndicators+" / "+totalCriticalIndicators);
        tvTotalNonCriticalIndicators.setText(totalYesNonCriticalIndicators+" / "+totalNonCriticalIndicators);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnSubmit){
            if(isValid()){
                new AlertDialog.Builder(this)
                        .setTitle("Save Form")
                        .setMessage("Once submitted, you will not be able to edit this form. Are you sure you want to submit?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                saveForm();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

        }else if(v.getId()==R.id.etVisitDate){
            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    private boolean isValid(){
        boolean isValid = true;

        if (spClient.getSelectedItemPosition() == 0) {
            isValid = false;
            Toast.makeText(this, "Please select Provider", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    private void saveForm(){
        SharedPreferences prefs = this.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        int type = prefs.getInt("type", 0);

        CRForm crForm = (CRForm) spClient.getSelectedItem();

        String ids = "";
        long formId = Util.getNextID(this,Codes.INITIALSCREENINGFORM);
        ScreeningFormHeader screeningFormHeader = new ScreeningFormHeader();
        screeningFormHeader.setId(formId);
        screeningFormHeader.setType(type);
        screeningFormHeader.setApprovalStatus(0);
        screeningFormHeader.setMobileSystemDate(Util.sdf.format(Calendar.getInstance().getTime()));
        screeningFormHeader.setVisitDate(etVisitDate.getText().toString());
        screeningFormHeader.setClientId(crForm.getId());
        screeningFormHeader.setRemarks(etPersonalMinutes.getText().toString());

        RadioButton rbYes=null;
        Questions question = new Questions();
        List<Questions> questions = new ArrayList<>();
        int areaLoop=0;

        List<ScreeningAreaDetail> areaDetails = new ArrayList<>();

        for(AreaQuestion areaQuestion : areaQuestions){
            long areaId=0;
            int points = 0;
            areaLoop++;
            areaId=Util.getNextID(this,Codes.INITIALSCREENINGAREA);
            View areaView = areaQuestion.getAreaView();
            TextView tvTotalIndicators = areaView.findViewById(R.id.tvTotalIndicators);
            TextView tvTotalCriticalIndicators = areaView.findViewById(R.id.tvTotalCriticalIndicators);
            EditText etComments = areaView.findViewById(R.id.etComments);
            TextView tvAreaIdInner = areaView.findViewById(R.id.tvAreaId);
            TextView tvTotalPoints = areaView.findViewById(R.id.tvTotalPoints);

            int totalIndicators, totalAchievedIndicators;
            String[] totalIndicatorsArr = tvTotalIndicators.getText().toString().split(" / ");
            totalAchievedIndicators = Integer.valueOf(totalIndicatorsArr[0]);
            totalIndicators = Integer.valueOf(totalIndicatorsArr[1]);

            points = Integer.valueOf(tvTotalPoints.getText().toString());

            int totalCriticalIndicators, totalCriticalAchievedIndicators;
            String[] totalCriticalIndicatorsArr = tvTotalCriticalIndicators.getText().toString().split(" / ");
            totalCriticalAchievedIndicators = Integer.valueOf(totalCriticalIndicatorsArr[0]);
            totalCriticalIndicators = Integer.valueOf(totalCriticalIndicatorsArr[1]);

            ScreeningAreaDetail areaDetail = new ScreeningAreaDetail();
            areaDetail.setFormId(formId);
            areaDetail.setId(areaId);
            areaDetail.setTotalPoints(points);
            areaDetail.setAreaId(Integer.valueOf(tvAreaIdInner.getText().toString()));
            areaDetail.setTotalIndicators(totalIndicators);
            areaDetail.setTotalIndicatorsAchieved(totalAchievedIndicators);
            areaDetail.setTotalCriticalIndicators(totalCriticalIndicators);
            areaDetail.setTotalCriticalIndicatorsAchieved(totalCriticalAchievedIndicators);

            areaDetail.setComments(etComments.getText().toString());
            areaDetails.add(areaDetail);
            int questionLoop=0;

        }
        db.getScreeningFormHeaderDAO().insert(screeningFormHeader);
        db.getScreeningAreaDetailDAO().insertMultiple(areaDetails);

        Toast.makeText(this, "Form submitted successfully", Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        for(AreaQuestion areaQuestion:areaQuestions){
            calculateArea(areaQuestion);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==R.id.spClient && spClient.getSelectedItemPosition()!=0){
            progressBar = new ProgressDialog(this);
            progressBar.setCancelable(false);//you can cancel it by pressing back button
            progressBar.setMessage("Populating questions..");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();//displays the progress bar
            populateQuestions();
            progressBar.dismiss();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}