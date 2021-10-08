package com.greenstar.eagle.controller.IPCForms;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.greenstar.eagle.R;
import com.greenstar.eagle.adapters.GeneralDropdownAdapter;
import com.greenstar.eagle.controller.Codes;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.CRForm;
import com.greenstar.eagle.model.DropdownCRBData;
import com.greenstar.eagle.utils.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ClientRegistrationForm extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener, View.OnFocusChangeListener {

    AppDatabase db =null;

    TextView tvSitarabajiCode, tvSitarabajiName, tvProviderCode, tvProviderName, tvSupervisorName, tvRegion, tvDistrict;

    EditText etVisitDate, etFollowupDate, etClientName, etHusbandName, etAddress, etContact, etCurrentMethodYears, etDiscontinuationReasonOther, etNeverUsedReasonOther;

    TextView tvContactNumber, tvClientName;

    Spinner spClientAge, spCurrentMethod, spReasonForDiscontinuation, spEverMethod, spReasonForNeverUser;

    RadioGroup rgCanWeContact;
    RadioButton rbCanWeContactYes, rbCanWeContactNo;

    RadioGroup rgIsEverUser;
    RadioButton rbIsEverUserYes, rbIsEverUserNo;

    RadioGroup rgIsCurrentUser;
    RadioButton rbIsCurrentUserYes, rbIsCurrentUserNo;

    RadioGroup rgTokenGiven;
    RadioButton rbTokenGivenYes, rbTokenGivenNo;

    Button btnSubmit;

    DatePickerDialog.OnDateSetListener date = null;
    DatePickerDialog.OnDateSetListener followupDate = null;
    final Calendar myCalendar = Calendar.getInstance();

    TableRow trNeverUseReason,trPeriodOfCurrentYears, trCurrentMethod, trEverUsed, trEverMethod, trEverUseReason;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crf_form_activity);

        initializeVariables();
        populateFirstSection();
        populateForm();
    }

    private void populateFirstSection() {
        SharedPreferences prefs = this.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String sitarabajiCode = prefs.getString("sitaraBajiCode", "");;
        String sitarabajiName = prefs.getString("sitaraBajiName", "");;
        String providerCode = prefs.getString("providerCode", "");;
        String providerName = prefs.getString("providerName", "");;
        String supervisorName = prefs.getString("AMName", "");;
        String region = prefs.getString("region", "");;
        String district = prefs.getString("district", "");;

        tvSitarabajiCode.setText(sitarabajiCode);
        tvSitarabajiName.setText(sitarabajiName);
        tvProviderCode.setText(providerCode);
        tvProviderName.setText(providerName);
        tvSupervisorName.setText(supervisorName);
        tvRegion.setText(region);
        tvDistrict.setText(district);
    }

    private void initializeVariables(){
        //Fixed portion
        tvSitarabajiCode = findViewById(R.id.tvSitarabajiCode);
        tvSitarabajiName = findViewById(R.id.tvSitarabajiName);
        tvProviderCode = findViewById(R.id.tvProviderCode);
        tvProviderName = findViewById(R.id.tvProviderName);
        tvSupervisorName = findViewById(R.id.tvSupervisorName);
        tvRegion = findViewById(R.id.tvRegion);
        tvDistrict = findViewById(R.id.tvDistrict);

        etVisitDate = findViewById(R.id.etVisitDate);
        etVisitDate.setOnClickListener(this);

        etFollowupDate = findViewById(R.id.etFollowupDate);
        etFollowupDate.setOnClickListener(this);

        etClientName = findViewById(R.id.etClientName);
        etHusbandName = findViewById(R.id.etHusbandName);

        spClientAge = findViewById(R.id.spClientAge);
        spReasonForDiscontinuation = findViewById(R.id.spReasonForDiscontinuation);
        spEverMethod = findViewById(R.id.spEverMethod);
        spReasonForNeverUser = findViewById(R.id.spNeverUseReason);

        etAddress = findViewById(R.id.etAddress);
        etContact = findViewById(R.id.etContact);
        tvClientName = findViewById(R.id.tvClientName);
        tvContactNumber = findViewById(R.id.tvContactNumber);

        rgCanWeContact = findViewById(R.id.rgCanWeContact);
        rbCanWeContactYes = findViewById(R.id.rbCanWeContactYes);
        rbCanWeContactNo = findViewById(R.id.rbCanWeContactNo);

        rgIsEverUser = findViewById(R.id.rgIsEverUser);
        rbIsEverUserYes = findViewById(R.id.rbIsEverUserYes);
        rbIsEverUserNo = findViewById(R.id.rbIsEverUserNo);

        rgIsCurrentUser = findViewById(R.id.rgIsCurrentUser);
        rbIsCurrentUserYes = findViewById(R.id.rbIsCurrentUserYes);
        rbIsCurrentUserNo = findViewById(R.id.rbIsCurrentUserNo);
        rgIsCurrentUser.setOnCheckedChangeListener(this);

        rgTokenGiven = findViewById(R.id.rgTokenGiven);
        rbTokenGivenYes = findViewById(R.id.rbTokenGivenYes);
        rbTokenGivenNo = findViewById(R.id.rbTokenGivenNo);

        etCurrentMethodYears = findViewById(R.id.etCurrentMethodYears);
        etDiscontinuationReasonOther = findViewById(R.id.etDiscontinuationReasonOther);
        etNeverUsedReasonOther = findViewById(R.id.etNeverUsedReasonOther);

        spCurrentMethod = findViewById(R.id.spCurrentMethod);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        trEverUsed = findViewById(R.id.trEverUsed);
        trEverMethod = findViewById(R.id.trEverMethod);
        trEverUseReason = findViewById(R.id.trEverUseReason);
        trPeriodOfCurrentYears = findViewById(R.id.trPeriodOfCurrentYears);
        trCurrentMethod = findViewById(R.id.trCurrentMethod);
        trNeverUseReason = findViewById(R.id.trNeverUseReason);

        rgIsEverUser.setOnCheckedChangeListener(this);
    }

    private void populateForm(){

        updateVisitDate();
        updateFollowupDate();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateVisitDate();
            }

        };
        followupDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateFollowupDate();
            }

        };

        SharedPreferences shared = getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String providerName = shared.getString("providerName", "");
        String providerCode = shared.getString("providerCode", "");
        String district = shared.getString("district", "");
        String region = shared.getString("region", "");
        tvProviderName.setText(providerName);
        tvProviderCode.setText(providerCode);
        tvRegion.setText(region);
        tvDistrict.setText(district);

        etCurrentMethodYears.setText("");
        etDiscontinuationReasonOther.setText("");
        etNeverUsedReasonOther.setText("");
        etCurrentMethodYears.setOnFocusChangeListener(this);

        spClientAge.setAdapter(getGeneralDropdownAdapter("Client Age", "Client Age"));
        spCurrentMethod.setAdapter(getGeneralDropdownAdapter("Current Method","Current Method"));
        spReasonForDiscontinuation.setAdapter(getGeneralDropdownAdapter("Reasons of Discontinuation","Reasons for discontinuation"));
        spEverMethod.setAdapter(getGeneralDropdownAdapter("Ever Method","CurrentEverMethod"));
        spReasonForNeverUser.setAdapter(getGeneralDropdownAdapter("Reasons of Never Use","Reasons of never use"));
    }


    private GeneralDropdownAdapter getGeneralDropdownAdapter(String title, String type) {
        List<DropdownCRBData> dropdownData = new ArrayList<>();
        DropdownCRBData dummy = new DropdownCRBData();
        dummy.setDetailEnglish(title);
        dummy.setId(0);

        try{
            db = AppDatabase.getAppDatabase(this);
            dropdownData = db.getDropdownCRBDataDAO().getDropdownData(type);
        }catch(Exception e){

        }
        dropdownData.add(0,dummy);
        GeneralDropdownAdapter generalDropdownAdapter = new GeneralDropdownAdapter(this, R.layout.dropdown_layout, R.id.tvNames, dropdownData);
        return generalDropdownAdapter;
    }

    private void updateVisitDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

        etVisitDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateFollowupDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

        etFollowupDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void submitForm(){
        CRForm form = new CRForm();
        form.setId(Util.getNextID(this,Codes.CRFORMID));
        form.setVisitDate(etVisitDate.getText().toString());
        form.setClientName(etClientName.getText().toString());
        form.setHusbandName(etHusbandName.getText().toString());
        form.setClientAge(spClientAge.toString());
        form.setAddress(etAddress.getText().toString());
        form.setContactNumber(etContact.getText().toString());
        if(rbCanWeContactYes.isChecked()){
            form.setCanWeContact(1);
        }else{
            form.setCanWeContact(0);
        }

        if(rbIsCurrentUserYes.isChecked()){
            form.setIsCurrentUser(1);
        }else{
            form.setIsCurrentUser(0);
        }

        form.setCurrentFPMethod(spCurrentMethod.toString());

        if(rbTokenGivenYes.isChecked()){
            form.setIsTokenGiven(1);
        }else{
            form.setIsTokenGiven(0);
        }

        form.setPeriodOfUsingCurrentMethod(Integer.valueOf(etCurrentMethodYears.getText().toString()));

        if(rbIsEverUserYes.isChecked()){
            form.setIsEverUser(1);
        }else{
            form.setIsEverUser(0);
        }

        form.setEverMethodUsed(spEverMethod.toString());
        form.setReasonForDiscontinuation(etDiscontinuationReasonOther.getText().toString());
        form.setReasonForNeverUser(etNeverUsedReasonOther.getText().toString());

        if(rbTokenGivenYes.isChecked()){
            form.setIsTokenGiven(1);
        }else{
            form.setIsTokenGiven(0);
        }

        AppDatabase.getAppDatabase(this).getCrFormDAO().insert(form);

        Toast.makeText(this,"Form successfully submitted!",Toast.LENGTH_SHORT).show();
        this.finish();
    }

    private boolean isValid(){
        boolean isValid=true;
        if("".equals(etClientName.getText().toString()) ||
                "".equals(etContact.getText().toString()) ){

            isValid = false;
        }
        if(isValid && spCurrentMethod.getSelectedItemId()==0  && rbIsCurrentUserYes.isChecked()){
            isValid=false;
        }
        if("".equals(etContact.getText().toString()) ){
            tvContactNumber.setTextColor(getResources().getColor(R.color.darkestOrange));
        }else{
            tvContactNumber.setTextColor(getResources().getColor(R.color.whiteColor));
        }

        if("".equals(etClientName.getText().toString())){
            tvClientName.setTextColor(getResources().getColor(R.color.darkestOrange));
        }else{
            tvClientName.setTextColor(getResources().getColor(R.color.whiteColor));
        }

        if(spClientAge.getSelectedItemId()==0) {
            View view = spClientAge.getSelectedView();
            (view).setBackgroundColor(getResources().getColor(R.color.darkestOrange));
            isValid = false;
        }else{
            View view = spClientAge.getSelectedView();
            ( view).setBackgroundColor(getResources().getColor(R.color.whiteColor));
        }

        if(!rbIsCurrentUserYes.isChecked() && !rbIsCurrentUserNo.isChecked() ){
            rbIsCurrentUserYes.setTextColor(getResources().getColor( R.color.darkestOrange));
            rbIsCurrentUserNo.setTextColor(getResources().getColor( R.color.darkestOrange));
            isValid = false;
        }else{
            rbIsCurrentUserYes.setTextColor(getResources().getColor( R.color.darkGreen));
            rbIsCurrentUserNo.setTextColor(getResources().getColor( R.color.darkGreen));
        }
        if(rbIsCurrentUserYes.isChecked()){
            if(spCurrentMethod.getSelectedItemId()==0) {
                View view = spCurrentMethod.getSelectedView();
                (view).setBackgroundColor(getResources().getColor(R.color.darkestOrange));
                isValid = false;
            }else{
                View view = spCurrentMethod.getSelectedView();
                (view).setBackgroundColor(getResources().getColor(R.color.whiteColor));
            }
        }else if(rbIsCurrentUserNo.isChecked()){
            if(!rbIsEverUserYes.isChecked() && !rbIsEverUserNo.isChecked()){
                isValid=false;
                rbIsEverUserYes.setTextColor(getResources().getColor( R.color.darkestOrange));
                rbIsEverUserNo.setTextColor(getResources().getColor( R.color.darkestOrange));
            }else{
                rbIsEverUserYes.setTextColor(getResources().getColor( R.color.darkGreen));
                rbIsEverUserNo.setTextColor(getResources().getColor( R.color.darkGreen));
            }

        }

        return isValid;
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
                                submitForm();

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }else{
                Toast.makeText(this,"Form is incomplete", Toast.LENGTH_SHORT).show();
            }



        }
        else if(v.getId()==R.id.etVisitDate){
            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        else if(v.getId()==R.id.etFollowupDate){
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(group.getId()==R.id.rgIsCurrentUser){
            if(rbIsCurrentUserYes.isChecked()){
                trPeriodOfCurrentYears.setVisibility(View.VISIBLE);
                trCurrentMethod.setVisibility(View.VISIBLE);

                trEverUsed.setVisibility(View.GONE);
                rgIsEverUser.clearCheck();

                trNeverUseReason.setVisibility(View.GONE);
            }else{
                trPeriodOfCurrentYears.setVisibility(View.GONE);
                etCurrentMethodYears.setText("0");
                trCurrentMethod.setVisibility(View.GONE);
                spCurrentMethod.setSelection(0);

                trEverUsed.setVisibility(View.VISIBLE);
            }
            etDiscontinuationReasonOther.setText("");
            etNeverUsedReasonOther.setText("");
        } else if(group.getId()==R.id.rgIsEverUser){
            if(rbIsEverUserYes.isChecked()){
                trEverMethod.setVisibility(View.VISIBLE);
                trEverUseReason.setVisibility(View.VISIBLE);

                trNeverUseReason.setVisibility(View.GONE);
            }else{
                trEverMethod.setVisibility(View.GONE);
                trEverUseReason.setVisibility(View.GONE);

                trNeverUseReason.setVisibility(View.VISIBLE);
            }
            etDiscontinuationReasonOther.setText("");
            etNeverUsedReasonOther.setText("");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        DropdownCRBData dropdownCRBData = new DropdownCRBData();
        dropdownCRBData = (DropdownCRBData) parent.getSelectedItem();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;
        if (hasFocus) {
            if ("0".equals(editText.getText().toString())) {
                editText.setText("");
            }
        } else {
            if ("".equals(editText.getText().toString())) {
                editText.setText("0");
            }
        }
    }
}
