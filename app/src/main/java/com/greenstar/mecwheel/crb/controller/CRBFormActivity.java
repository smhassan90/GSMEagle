package com.greenstar.mecwheel.crb.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.greenstar.mecwheel.crb.db.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CRBFormActivity extends AppCompatActivity implements View.OnClickListener {

    AppDatabase db =null;

    EditText etMeetingDate;
    EditText etPersonName, etPersonDesignation, etPersonECode, etPersonTeam;
    EditText etChairName, etChairDesignation, etChairCellNumber, etChairEmailAddress, etChairMeetingAddress;
    EditText etPersonalMinutes;

    TextView tvStaffCodeName;

    SearchableSpinner spDistricts;

    Button btnSubmit;

    DatePickerDialog.OnDateSetListener date = null;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etMeetingDate = findViewById(R.id.etMeetingDate);
        etMeetingDate.setOnClickListener(this);

        tvStaffCodeName = findViewById(R.id.tvStaffCodeName);

        spDistricts = findViewById(R.id.spDistricts);

        etPersonName = findViewById(R.id.etName);
        etPersonDesignation = findViewById(R.id.etDesignation);
        etPersonECode = findViewById(R.id.etPersonCode);
        etPersonTeam = findViewById(R.id.etTeam);

        etChairName = findViewById(R.id.etChairName);
        etChairDesignation = findViewById(R.id.etChairDesignation);
        etChairCellNumber = findViewById(R.id.etChairCellNumber);
        etChairEmailAddress = findViewById(R.id.etChairEmail);
        etChairMeetingAddress = findViewById(R.id.etAddressMeetingOffice);

        etPersonalMinutes = findViewById(R.id.etPersonalMinutes);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        populateForm();
    }

    private void populateForm(){
        updateMeetingDate();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateMeetingDate();
            }

        };

        SharedPreferences shared = getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String staffCode = shared.getString("code", "");
        String staffName = shared.getString("name", "");
        tvStaffCodeName.setText(staffName);

        List<District> districtList = new ArrayList<>();
        District districtDummy = new District();
        districtDummy.setDistCode(0);
        districtDummy.setDistName("Please Select");

        try{
            db = AppDatabase.getAppDatabase(this);
            districtList = db.getDistrictDAO().getAll();
        }catch(Exception e){

        }

        districtList.add(0, districtDummy);
        DistrictAdapter districtAdapter = new DistrictAdapter(this, R.layout.dropdown_design, R.id.tvNames, districtList);
        spDistricts.setAdapter(districtAdapter);

    }

    private void updateMeetingDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

        etMeetingDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void submitForm(){
        DTCForm dtcForm = new DTCForm();
        dtcForm.setAttendingWithDesignation(etPersonDesignation.getText().toString());
        dtcForm.setAttendingWithECode(etPersonECode.getText().toString());
        dtcForm.setAttendingWithName(etPersonName.getText().toString());
        dtcForm.setAttendingWithTeam(etPersonTeam.getText().toString());
        dtcForm.setChairCellNumber(etChairCellNumber.getText().toString());
        dtcForm.setChairDesignation(etChairDesignation.getText().toString());
        dtcForm.setChairEmailAddress(etChairEmailAddress.getText().toString());
        dtcForm.setChairMeetingAddress(etChairMeetingAddress.getText().toString());
        dtcForm.setChairName(etChairName.getText().toString());
        dtcForm.setMeetingDate(etMeetingDate.getText().toString());
        dtcForm.setDistrictCode("12");
        dtcForm.setPersonalMinutes(etPersonalMinutes.getText().toString());
        dtcForm.setPersonDesignation(etPersonDesignation.getText().toString());
        dtcForm.setPersonECode(etPersonECode.getText().toString());
        dtcForm.setPersonName(etPersonName.getText().toString());
        dtcForm.setPersonTeam(etPersonTeam.getText().toString());
        dtcForm.setId(Util.getNextDTCFormID(this));
        AppDatabase.getAppDatabase(this).getDTCFormDAO().insert(dtcForm);
        Toast.makeText(this,"Form successfully submitted!",Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void onClick(View v) {


        if(v.getId()==R.id.btnSubmit){
            new AlertDialog.Builder(this)
                    .setTitle("Save Form")
                    .setMessage("Once submitted, you will not be able to edit this form. Are you sure you want to submit?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            submitForm();

                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


        }
        else if(v.getId()==R.id.etMeetingDate){
            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
