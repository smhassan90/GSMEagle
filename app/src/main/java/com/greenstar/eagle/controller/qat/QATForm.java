package com.greenstar.eagle.controller.qat;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.greenstar.eagle.R;
import com.greenstar.eagle.adapters.ProviderAdapter;
import com.greenstar.eagle.controller.Codes;
import com.greenstar.eagle.dal.AreaQuestion;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.Area;
import com.greenstar.eagle.model.QATAreaDetail;
import com.greenstar.eagle.model.Providers;
import com.greenstar.eagle.model.QATFormHeader;
import com.greenstar.eagle.model.QATFormQuestion;
import com.greenstar.eagle.model.Question;
import com.greenstar.eagle.model.approval.ApprovalQATArea;
import com.greenstar.eagle.model.approval.ApprovalQATForm;
import com.greenstar.eagle.model.approval.ApprovalQATFormQuestion;
import com.greenstar.eagle.utils.Util;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QATForm extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    ProgressDialog progressBar = null;

    LinearLayout llHistoryArea = null;
    LinearLayout llQuestion = null;
    LinearLayout llQuestionBank = null;
    TextView tvAreaHeading = null;
    TextView tvTotalIndicators = null;
    TextView tvAreaId = null;
    TextView tvTotalCriticalIndicators = null;
    TextView tvTotalNonCriticalIndicators = null;
    //History
    EditText etHistoryComments = null;
    TextView tvHistoryTotalIndicators = null;
    TextView tvHistoryTotalCriticalIndicators = null;
    TextView tvHistoryTotalNonCriticalIndicators = null;
    //End history
    List<AreaQuestion> areaQuestions = new ArrayList<>();

    DatePickerDialog.OnDateSetListener date = null;

    final Calendar myCalendar = Calendar.getInstance();

    Button btnSubmit;
    AppDatabase db = null;

    EditText etDateOFAssessment;
    TextView tvQAMCode, tvQAMName, tvSupervisorName, tvSupervisorCode, tvRegion;

    SearchableSpinner spProviderCodeName;

    List<Providers> providers = new ArrayList<>();
    ProviderAdapter providerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qat_screen);
        db = AppDatabase.getAppDatabase(this);
        initializeForm();
        populateForm();
    }

    private void updateVisitDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

        etDateOFAssessment.setText(sdf.format(myCalendar.getTime()));
    }

    private void populateForm(){
        etDateOFAssessment.setOnClickListener(this);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateVisitDate();
            }

        };

        //Providers spinner populate
        providers = db.getProvidersDAO().getActiveProviders();

        Providers first = new Providers();
        first.setCode("0");
        first.setName("Please Select");
        providers.add(0, first);
        providerAdapter = new ProviderAdapter(this, R.layout.provider_town_list, R.id.tvProviderNamess, providers);
        spProviderCodeName.setAdapter(providerAdapter);

        SharedPreferences prefs = this.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String region = prefs.getString("region", "");
        String AMName = prefs.getString("AMName", "");
        String AMCode = prefs.getString("AMCode", "");
        String name = prefs.getString("name", "");
        String staffCode = prefs.getString("staffCode", "");

        tvRegion.setText(region);
        tvSupervisorName.setText(AMName);
        tvSupervisorCode.setText(AMCode);
        tvQAMName.setText(name);
        tvQAMCode.setText(staffCode);
        updateVisitDate();
    }

    private void initializeForm(){
        etDateOFAssessment = findViewById(R.id.etDateOFAssessment);
        tvQAMCode = findViewById(R.id.tvQAMCode);
        tvQAMName = findViewById(R.id.tvQAMName);
        tvSupervisorName = findViewById(R.id.tvSupervisorName);
        tvSupervisorCode = findViewById(R.id.tvSupervisorCode);
        tvRegion = findViewById(R.id.tvRegion);
        spProviderCodeName = findViewById(R.id.spProviderCodeName);
        spProviderCodeName.setOnItemSelectedListener(this);

        etDateOFAssessment.setOnClickListener(this);
    }

    private ApprovalQATArea getApprovalArea(List<ApprovalQATArea> approvalQATAreas, long areaId){
        ApprovalQATArea approvalQATArea = new ApprovalQATArea();

        if(approvalQATAreas!=null && approvalQATAreas.size()>0){
            for(ApprovalQATArea area:approvalQATAreas){
                if(area.getAreaId()==areaId){
                    approvalQATArea=area;
                    break;
                }
            }
        }


        return approvalQATArea;
    }

    private boolean getHistoryQuestionAnswer(List<ApprovalQATFormQuestion> questions, long questionId){
        boolean isYes = false;

        for(ApprovalQATFormQuestion question:questions){
            if(questionId==question.getQuestionId()){
                if(question.getAnswer()==1){
                    isYes = true;
                }
                break;
            }
        }

        return isYes;
    }

    private void populateQuestions() {
        areaQuestions.clear();
        areaQuestions = new ArrayList<>();
        Providers provider = (Providers) spProviderCodeName.getSelectedItem();
        ApprovalQATForm approvalQATForm = db.getApprovalQATFormDAO().getForm(provider.getCode());
        List<ApprovalQATArea> approvalQATAreas = new ArrayList<>();
        List<ApprovalQATFormQuestion> approvalQATFormQuestions = new ArrayList<>();
        if(approvalQATForm!=null){
            approvalQATAreas = db.getApprovalQATAreaDAO().getFormAllAreas(approvalQATForm.getId());
            approvalQATFormQuestions = db.getApprovalQATFormQuestionDAO().getFormAllQuestions(approvalQATForm.getId());
        }

        LinearLayout llAreas = null;

        LayoutInflater inflater = LayoutInflater.from(QATForm.this);

        llQuestionBank = findViewById(R.id.llQuestionBank);

        if(llQuestionBank.getChildCount() > 0)
            llQuestionBank.removeAllViews();

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        List<Question> questions = new ArrayList<>();
        List<Area> areas = new ArrayList<>();

        areas = db.getAreaDAO().getActiveAreas();
        AreaQuestion areaQuestion = new AreaQuestion();
        LinearLayout llArea = null;
        Question question = new Question();
        RadioButton rbAnswerYes = null;
        TextView tvQuestionText = null;
        Map<RadioButton,Question> mapRadioButtonQuestion = null;

        for(Area area : areas){
            ApprovalQATArea approvalQATArea = getApprovalArea(approvalQATAreas,area.getId());

            View areaView = inflater.inflate(R.layout.qat_question_area, null);
            //History data population
            llHistoryArea = areaView.findViewById(R.id.llHistoryArea);
            if(approvalQATArea.getId()==0){
                llHistoryArea.setVisibility(View.GONE);
            }else{
                tvHistoryTotalIndicators = areaView.findViewById(R.id.tvHistoryTotalIndicators);
                tvHistoryTotalCriticalIndicators = areaView.findViewById(R.id.tvHistoryTotalCriticalIndicators);
                tvHistoryTotalNonCriticalIndicators = areaView.findViewById(R.id.tvHistoryTotalNonCriticalIndicators);
                etHistoryComments = areaView.findViewById(R.id.etHistoryComments);

                tvHistoryTotalIndicators.setText(approvalQATArea.getTotalIndicatorsAchieved()+" / "+approvalQATArea.getTotalIndicators());
                tvHistoryTotalCriticalIndicators.setText(approvalQATArea.getTotalCriticalIndicatorsAchieved()+" / "+approvalQATArea.getTotalCriticalIndicators());
                tvHistoryTotalNonCriticalIndicators.setText(approvalQATArea.getTotalNonCriticalIndicatorsAchieved()+" / "+approvalQATArea.getTotalNonCriticalIndicators());
                etHistoryComments.setText(approvalQATArea.getComments());
            }

            //END history data population

            LinearLayout combineAreaQuestion = areaView.findViewById(R.id.llQuestionSeries);
            areaQuestion = new AreaQuestion();
            areaQuestion.setAreaId(area.getId());
            areaQuestion.setAreaName(area.getArea());

            questions = db.getQuestionsDAO().getActiveQuestionsOfArea(area.getId());
            mapRadioButtonQuestion = new HashMap<>();

            for(Question quest : questions){
                View rowQuestion = inflater.inflate(R.layout.qat_question, null);
                llQuestion = rowQuestion.findViewById(R.id.llQuestion);


                rbAnswerYes = rowQuestion.findViewById(R.id.rbAnswerYes);
                rbAnswerYes.setOnCheckedChangeListener(this);
                tvQuestionText = rowQuestion.findViewById(R.id.tvQuestionText);
                if(approvalQATFormQuestions.size()>0){
                    if(getHistoryQuestionAnswer(approvalQATFormQuestions,quest.getId())){
                        tvQuestionText.setTextColor(getResources().getColor(R.color.black));
                    }else{

                        tvQuestionText.setTextColor(getResources().getColor(R.color.darkOrange));
                    }
                }else{
                    tvQuestionText.setTextColor(getResources().getColor(R.color.black));
                }
                String questionStr =quest.getQuestion();

                tvQuestionText.setText(fromHtml(questionStr));
                if(quest.getIsCritical()==1){
                    tvQuestionText.setTypeface(null, Typeface.BOLD);
                }
                mapRadioButtonQuestion.put(rbAnswerYes,quest);

                combineAreaQuestion.addView(llQuestion);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) llQuestion.getLayoutParams();
                lp.setMargins(0, 10, 0, 0);
                llQuestion.setLayoutParams(lp);
            }

            tvAreaHeading = areaView.findViewById(R.id.tvAreaHeading);

            tvAreaHeading.setText(area.getArea());

            areaQuestion.setQuestionRadioButtons(mapRadioButtonQuestion);
            areaQuestions.add(areaQuestion);
            areaQuestion.setAreaView(areaView);

            calculateArea(areaQuestion);
            llQuestionBank.addView(areaView);
        }
    }

    private void calculateArea(AreaQuestion areaQuestion) {
        View areaView = areaQuestion.getAreaView();

        Map<RadioButton, Question> questionRadioButton = areaQuestion.getQuestionRadioButtons();
        RadioButton rbYes = null;
        Question question = null;

        int totalIndicators = 0;
        int totalYesIndicators=0;

        int totalCriticalIndicators = 0;
        int totalYesCriticalIndicators =0;

        int totalNonCriticalIndicators = 0;
        int totalYesNonCriticalIndicators =0;

        for (Map.Entry<RadioButton, Question> entry : questionRadioButton.entrySet()) {
            rbYes=(RadioButton) entry.getKey();
            question = (Question) entry.getValue();
            totalIndicators++;

            if(rbYes.isChecked()){
                totalYesIndicators++;
                if(question.getIsCritical()==1){
                    totalYesCriticalIndicators++;
                }else{
                    totalYesNonCriticalIndicators++;
                }
            }

            if(question.getIsCritical()==1){
                totalCriticalIndicators++;
            }
        }

        totalNonCriticalIndicators = totalIndicators - totalCriticalIndicators;

        tvTotalIndicators = areaView.findViewById(R.id.tvTotalIndicators);
        tvAreaId = areaView.findViewById(R.id.tvAreaId);
        tvAreaId.setText(String.valueOf(areaQuestion.getAreaId()));
        tvTotalCriticalIndicators = areaView.findViewById(R.id.tvTotalCriticalIndicators);
        tvTotalNonCriticalIndicators = areaView.findViewById(R.id.tvTotalNonCriticalIndicators);

        tvTotalIndicators.setText(totalYesIndicators+" / "+totalIndicators);
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

        }else if(v.getId()==R.id.etDateOFAssessment){
            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    private boolean isValid(){
        boolean isValid = true;

        if (spProviderCodeName.getSelectedItemPosition() == 0) {
            isValid = false;
            Toast.makeText(this, "Please select Provider", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    private void saveForm(){
        String ids = "";
        long formId = Util.getNextID(this,Codes.QATFORM);
        QATFormHeader qatFormHeader = new QATFormHeader();
        qatFormHeader.setId(formId);
        qatFormHeader.setApprovalStatus(0);
        qatFormHeader.setDateOfAssessment(etDateOFAssessment.getText().toString());
        Providers provider = (Providers) spProviderCodeName.getSelectedItem();

        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);
        qatFormHeader.setMobileDate(sdf.format(myCalendar.getTime()));
        qatFormHeader.setProviderCode(provider.getCode());
        qatFormHeader.setProviderName(provider.getName());
        qatFormHeader.setQAMCode(tvQAMCode.getText().toString());
        qatFormHeader.setQAMName(tvQAMName.getText().toString());

        qatFormHeader.setRegion(tvRegion.getText().toString());
        qatFormHeader.setSupervisorCode(tvSupervisorCode.getText().toString());
        qatFormHeader.setSupervisorName(tvSupervisorName.getText().toString());

        List<QATFormQuestion> qatFormQuestions = new ArrayList<>();
        Map<RadioButton, Question> questions = new HashMap<>();

        QATFormQuestion qatFormQuestion = null;

        RadioButton rbYes=null;
        Question question = new Question();
        int areaLoop=0;

        List<QATAreaDetail> areaDetails = new ArrayList<>();

        for(AreaQuestion areaQuestion : areaQuestions){
            long areaId=0;
            areaLoop++;
            areaId=Long.valueOf(String.valueOf(formId)+areaLoop);
            questions = areaQuestion.getQuestionRadioButtons();
            View areaView = areaQuestion.getAreaView();
            TextView tvTotalIndicators = areaView.findViewById(R.id.tvTotalIndicators);
            TextView tvTotalCriticalIndicators = areaView.findViewById(R.id.tvTotalCriticalIndicators);
            TextView tvTotalNonCriticalIndicators = areaView.findViewById(R.id.tvTotalNonCriticalIndicators);
            EditText etComments = areaView.findViewById(R.id.etComments);
            TextView tvAreaIdInner = areaView.findViewById(R.id.tvAreaId);

            int totalIndicators, totalAchievedIndicators;
            String[] totalIndicatorsArr = tvTotalIndicators.getText().toString().split(" / ");
            totalAchievedIndicators = Integer.valueOf(totalIndicatorsArr[0]);
            totalIndicators = Integer.valueOf(totalIndicatorsArr[1]);

            int totalCriticalIndicators, totalCriticalAchievedIndicators;
            String[] totalCriticalIndicatorsArr = tvTotalCriticalIndicators.getText().toString().split(" / ");
            totalCriticalAchievedIndicators = Integer.valueOf(totalCriticalIndicatorsArr[0]);
            totalCriticalIndicators = Integer.valueOf(totalCriticalIndicatorsArr[1]);

            int totalNonCriticalIndicators, totalNonCriticalAchievedIndicators;
            String[] totalNonCriticalIndicatorsArr = tvTotalNonCriticalIndicators.getText().toString().split(" / ");
            totalNonCriticalAchievedIndicators = Integer.valueOf(totalNonCriticalIndicatorsArr[0]);
            totalNonCriticalIndicators = Integer.valueOf(totalNonCriticalIndicatorsArr[1]);

            QATAreaDetail areaDetail = new QATAreaDetail();
            areaDetail.setFormId(formId);
            areaDetail.setId(0);
            areaDetail.setAreaId(Integer.valueOf(tvAreaIdInner.getText().toString()));
            areaDetail.setTotalIndicators(totalIndicators);
            areaDetail.setTotalIndicatorsAchieved(totalAchievedIndicators);

            areaDetail.setTotalCriticalIndicators(totalCriticalIndicators);
            areaDetail.setTotalCriticalIndicatorsAchieved(totalCriticalAchievedIndicators);

            areaDetail.setTotalNonCriticalIndicators(totalNonCriticalIndicators);
            areaDetail.setTotalNonCriticalIndicatorsAchieved(totalNonCriticalAchievedIndicators);

            areaDetail.setComments(etComments.getText().toString());
            areaDetails.add(areaDetail);
            int questionLoop=0;
            for (Map.Entry<RadioButton, Question> entry : questions.entrySet()) {
                questionLoop++;
                long questionFormId =Long.valueOf(String.valueOf(areaId)+questionLoop);
                ids +=String.valueOf(questionFormId)+",";
                qatFormQuestion = new QATFormQuestion();
                rbYes = (RadioButton) entry.getKey();
                question = (Question) entry.getValue();
                if(rbYes.isChecked()){
                    qatFormQuestion.setAnswer(1);
                }else{
                    qatFormQuestion.setAnswer(0);
                }
                qatFormQuestion.setAreaId(areaDetail.getAreaId());
                qatFormQuestion.setFormId(formId);
                qatFormQuestion.setQuestionId(question.getId());
                qatFormQuestion.setId(0);
                qatFormQuestions.add(qatFormQuestion);
            }
        }
        db.getQatFormHeaderDAO().insert(qatFormHeader);
        db.getAreaDetailDAO().insertMultiple(areaDetails);
        db.getQatFormQuestionDAO().insertMultiple(qatFormQuestions);

        Toast.makeText(this, "Form submitted successfully", Toast.LENGTH_SHORT).show();

        finish();
        Intent intent = new Intent(this, TCForm.class);
        intent.putExtra("providerCode", provider.getCode());
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        for(AreaQuestion areaQuestion:areaQuestions){
            calculateArea(areaQuestion);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==R.id.spProviderCodeName && spProviderCodeName.getSelectedItemPosition()!=0){
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