package com.greenstar.eagle.controller.qat;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.greenstar.eagle.R;
import com.greenstar.eagle.adapters.ProviderAdapter;
import com.greenstar.eagle.controller.Codes;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.Providers;
import com.greenstar.eagle.model.QATTCForm;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TCForm extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = null;
    SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

    Button btnSubmit;
    EditText etDateOFAssessment;
    TextView tvQAMCode, tvQAMName, tvSupervisorCode, tvSupervisorName, tvRegion;

    SearchableSpinner spProviderCodeName;
    ProviderAdapter providerAdapter;

    List<Providers> providers = new ArrayList<>();

    AppDatabase db = null;

    RadioButton rbPCCDYes,rbCDPFYes,rbPCPNCCYes, rbPFPNCCYes, rbPCGTCYes, rbGTCPFYes, rbPCMisoYes, rbPCMVAYes, rbMVAPFYes,
            rbPCPPIUCDYes, rbPPIUCDPFYes, rbPCImplantYes, rbImplantPFYes, rbPCLOAYes, rbLOAPFYes, rbPCPHCCYes, rbPHCCPFYes,
            rbPCQTVActionPlanYes, rbQTVActionPlanPFYes, rbPCRecruitmentFormYes, rbRecruitmentFormPFYes;

    RadioButton rbPCCDNo,rbCDPFNo,rbPCPNCCNo, rbPFPNCCNo, rbPCGTCNo, rbGTCPFNo, rbPCMisoNo, rbPCMVANo, rbMVAPFNo,
            rbPCPPIUCDNo, rbPPIUCDPFNo, rbPCImplantNo, rbImplantPFNo, rbPCLOANo, rbLOAPFNo, rbPCPHCCNo, rbPHCCPFNo,
            rbPCQTVActionPlanNo, rbQTVActionPlanPFNo, rbPCRecruitmentFormNo, rbRecruitmentFormPFNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tc_form);

        db = AppDatabase.getAppDatabase(this);

        initializeForm();
        popoulateForm();
        if(getIntent().getExtras()!=null){
            String providerCode = (String) getIntent().getExtras().get("providerCode");

            spProviderCodeName.setSelection(indexOf(providerCode,providers));
        }

    }

    private void popoulateForm(){
        SharedPreferences prefs = this.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String region = prefs.getString("region", "");
        String AMName = prefs.getString("AMName", "");
        String AMCode = prefs.getString("AMCode", "");
        String name = prefs.getString("name", "");
        String staffCode = prefs.getString("staffCode", "");

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateVisitDate();
            }

        };
        updateVisitDate();

        tvSupervisorName.setText(AMName.toString());
        tvSupervisorCode.setText(AMCode);
        tvQAMName.setText(name.toString());
        tvQAMCode.setText(staffCode);
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

    private void initializeForm(){
        rbPCCDNo = findViewById(R.id.rbPCCDNo);
        rbCDPFNo = findViewById(R.id.rbCDPFNo);
        rbPCPNCCNo = findViewById(R.id.rbPCPNCCNo);
        rbPFPNCCNo = findViewById(R.id.rbPFPNCCNo);
        rbPCGTCNo = findViewById(R.id.rbPCGTCNo);
        rbGTCPFNo = findViewById(R.id.rbGTCPFNo);
        rbPCMisoNo = findViewById(R.id.rbPCMisoNo);
        rbPCMVANo = findViewById(R.id.rbPCMVANo);
        rbMVAPFNo = findViewById(R.id.rbMVAPFNo);
        rbPCPPIUCDNo = findViewById(R.id.rbPCPPIUCDNo);
        rbPPIUCDPFNo = findViewById(R.id.rbPPIUCDPFNo);
        rbPCImplantNo = findViewById(R.id.rbPCImplantNo);
        rbImplantPFNo = findViewById(R.id.rbImplantPFNo);
        rbPCLOANo = findViewById(R.id.rbPCLOANo);
        rbLOAPFNo = findViewById(R.id.rbLOAPFNo);
        rbPCPHCCNo = findViewById(R.id.rbPCPHCCNo);
        rbPHCCPFNo = findViewById(R.id.rbPHCCPFNo);
        rbPCQTVActionPlanNo = findViewById(R.id.rbPCQTVActionPlanNo);
        rbQTVActionPlanPFNo = findViewById(R.id.rbQTVActionPlanPFNo);
        rbPCRecruitmentFormNo = findViewById(R.id.rbPCRecruitmentFormNo);
        rbRecruitmentFormPFNo = findViewById(R.id.rbRecruitmentFormPFNo);


        rbPCCDYes = findViewById(R.id.rbPCCDYes);
        rbCDPFYes = findViewById(R.id.rbCDPFYes);
        rbPCPNCCYes = findViewById(R.id.rbPCPNCCYes);
        rbPFPNCCYes = findViewById(R.id.rbPFPNCCYes);
        rbPCGTCYes = findViewById(R.id.rbPCGTCYes);
        rbGTCPFYes = findViewById(R.id.rbGTCPFYes);
        rbPCMisoYes = findViewById(R.id.rbPCMisoYes);
        rbPCMVAYes = findViewById(R.id.rbPCMVAYes);
        rbMVAPFYes = findViewById(R.id.rbMVAPFYes);
        rbPCPPIUCDYes = findViewById(R.id.rbPCPPIUCDYes);
        rbPPIUCDPFYes = findViewById(R.id.rbPPIUCDPFYes);
        rbPCImplantYes = findViewById(R.id.rbPCImplantYes);
        rbImplantPFYes = findViewById(R.id.rbImplantPFYes);
        rbPCLOAYes = findViewById(R.id.rbPCLOAYes);
        rbLOAPFYes = findViewById(R.id.rbLOAPFYes);
        rbPCPHCCYes = findViewById(R.id.rbPCPHCCYes);
        rbPHCCPFYes = findViewById(R.id.rbPHCCPFYes);
        rbPCQTVActionPlanYes = findViewById(R.id.rbPCQTVActionPlanYes);
        rbQTVActionPlanPFYes = findViewById(R.id.rbQTVActionPlanPFYes);
        rbPCRecruitmentFormYes = findViewById(R.id.rbPCRecruitmentFormYes);
        rbRecruitmentFormPFYes = findViewById(R.id.rbRecruitmentFormPFYes);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        etDateOFAssessment = findViewById(R.id.etDateOFAssessment);
        tvQAMCode = findViewById(R.id.tvQAMCode);
        tvQAMName = findViewById(R.id.tvQAMName);
        tvSupervisorCode = findViewById(R.id.tvSupervisorCode);
        tvSupervisorName = findViewById(R.id.tvSupervisorName);
        tvRegion = findViewById(R.id.tvRegion);
        spProviderCodeName = findViewById(R.id.spProviderCodeName);
        spProviderCodeName.setOnItemSelectedListener(this);

        etDateOFAssessment.setOnClickListener(this);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateAssessmentDate();
            }

        };
    }

    private void updateAssessmentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

        etDateOFAssessment.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnSubmit){
            if(isValid()){
                QATTCForm qattcForm = new QATTCForm();
                qattcForm.setDateOfAssessment(etDateOFAssessment.getText().toString());
                qattcForm.setMobileDate(sdf.format(myCalendar.getTime()));
                qattcForm.setQAMCode(tvQAMCode.getText().toString());
                qattcForm.setQAMName(tvQAMName.getText().toString());
                qattcForm.setRegion(tvRegion.getText().toString());
                qattcForm.setSupervisorCode(tvSupervisorCode.getText().toString());
                qattcForm.setSupervisorName(tvSupervisorName.getText().toString());
                Providers provider = (Providers) spProviderCodeName.getSelectedItem();
                qattcForm.setProviderCode(provider.getCode());
                qattcForm.setProviderName(provider.getName());
                qattcForm.setCDPF(rbCDPFYes.isChecked()==true?1:0);
                qattcForm.setPCCD(rbPCCDYes.isChecked()==true?1:0);
                qattcForm.setCDPF(rbCDPFYes.isChecked()==true?1:0);
                qattcForm.setPCPNCC(rbPCPNCCYes.isChecked()==true?1:0);
                qattcForm.setPFPNCC(rbPFPNCCYes.isChecked()==true?1:0);
                qattcForm.setPCGTC(rbPCGTCYes.isChecked()==true?1:0);
                qattcForm.setGTCPF(rbGTCPFYes.isChecked()==true?1:0);
                qattcForm.setPCMiso(rbPCMisoYes.isChecked()==true?1:0);
                qattcForm.setPCMVA(rbPCMVAYes.isChecked()==true?1:0);
                qattcForm.setMVAPF(rbMVAPFYes.isChecked()==true?1:0);
                qattcForm.setPCPPIUCD(rbPCPPIUCDYes.isChecked()==true?1:0);
                qattcForm.setPPIUCDPF(rbPPIUCDPFYes.isChecked()==true?1:0);
                qattcForm.setPCImplant(rbPCImplantYes.isChecked()==true?1:0);
                qattcForm.setImplantPF(rbImplantPFYes.isChecked()==true?1:0);
                qattcForm.setPCLOA(rbPCLOAYes.isChecked()==true?1:0);
                qattcForm.setLOAPF(rbLOAPFYes.isChecked()==true?1:0);
                qattcForm.setPCPHCC(rbPCPHCCYes.isChecked()==true?1:0);
                qattcForm.setPHCCPF(rbPHCCPFYes.isChecked()==true?1:0);
                qattcForm.setPCQTVActionPlan(rbPCQTVActionPlanYes.isChecked()==true?1:0);
                qattcForm.setQTVActionPlanPF(rbQTVActionPlanPFYes.isChecked()==true?1:0);
                qattcForm.setPCRecruitmentForm(rbPCRecruitmentFormYes.isChecked()==true?1:0);
                qattcForm.setRecruitmentFormPF(rbRecruitmentFormPFYes.isChecked()==true?1:0);
                db.getQattcFormDAO().insert(qattcForm);

                this.onBackPressed();
            }
        }else if(v.getId()==R.id.etDateOFAssessment){
            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }
    private void updateVisitDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

        etDateOFAssessment.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    private boolean isValid(){
        boolean isValid=true;
        if (spProviderCodeName.getSelectedItemPosition() == 0) {
            isValid = false;
            Toast.makeText(this, "Please select Provider", Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            populateForm();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void populateForm(){
        Providers provider = (Providers) spProviderCodeName.getSelectedItem();
        QATTCForm qattcForm = new QATTCForm();
        qattcForm = db.getQattcFormDAO().getTCFormByProviderCode(provider.getCode());

        if(qattcForm!=null){
            rbPCCDYes.setChecked(qattcForm.getPCCD()==1?true:false);
            rbCDPFYes.setChecked(qattcForm.getCDPF()==1?true:false);
            rbPCPNCCYes.setChecked(qattcForm.getPCPNCC()==1?true:false);
            rbPFPNCCYes.setChecked(qattcForm.getPFPNCC()==1?true:false);
            rbPCGTCYes.setChecked(qattcForm.getPCGTC()==1?true:false);
            rbGTCPFYes.setChecked(qattcForm.getGTCPF()==1?true:false);
            rbPCMisoYes.setChecked(qattcForm.getPCMiso()==1?true:false);
            rbPCMVAYes.setChecked(qattcForm.getPCMVA()==1?true:false);
            rbMVAPFYes.setChecked(qattcForm.getMVAPF()==1?true:false);
            rbPCPPIUCDYes.setChecked(qattcForm.getPCPPIUCD()==1?true:false);
            rbPPIUCDPFYes.setChecked(qattcForm.getPPIUCDPF()==1?true:false);
            rbPCImplantYes.setChecked(qattcForm.getPCImplant()==1?true:false);
            rbImplantPFYes.setChecked(qattcForm.getImplantPF()==1?true:false);
            rbPCLOAYes.setChecked(qattcForm.getPCLOA()==1?true:false);
            rbLOAPFYes.setChecked(qattcForm.getLOAPF()==1?true:false);
            rbPCPHCCYes.setChecked(qattcForm.getPCPHCC()==1?true:false);
            rbPHCCPFYes.setChecked(qattcForm.getPHCCPF()==1?true:false);
            rbPCQTVActionPlanYes.setChecked(qattcForm.getPCQTVActionPlan()==1?true:false);
            rbQTVActionPlanPFYes.setChecked(qattcForm.getQTVActionPlanPF()==1?true:false);
            rbPCRecruitmentFormYes.setChecked(qattcForm.getPCRecruitmentForm()==1?true:false);
            rbRecruitmentFormPFYes.setChecked(qattcForm.getRecruitmentFormPF()==1?true:false);
        }else{
            rbPCCDNo.setChecked(true);
            rbCDPFNo.setChecked(true);
            rbPCPNCCNo.setChecked(true);
            rbPFPNCCNo.setChecked(true);
            rbPCGTCNo.setChecked(true);
            rbGTCPFNo.setChecked(true);
            rbPCMisoNo.setChecked(true);
            rbPCMVANo.setChecked(true);
            rbMVAPFNo.setChecked(true);
            rbPCPPIUCDNo.setChecked(true);
            rbPPIUCDPFNo.setChecked(true);
            rbPCImplantNo.setChecked(true);
            rbImplantPFNo.setChecked(true);
            rbPCLOANo.setChecked(true);
            rbLOAPFNo.setChecked(true);
            rbPCPHCCNo.setChecked(true);
            rbPHCCPFNo.setChecked(true);
            rbPCQTVActionPlanNo.setChecked(true);
            rbQTVActionPlanPFNo.setChecked(true);
            rbPCRecruitmentFormNo.setChecked(true);
            rbRecruitmentFormPFNo.setChecked(true);
        }
    }
    private int indexOf(String providerCode, List<Providers> providers){
        int index=0;
        int loop=0;
        for(Providers provider: providers){
            if(provider.getCode().toLowerCase().equals(providerCode.toLowerCase())){
                index = loop;
                break;
            }
            loop++;
        }
        return index;
    }
}
