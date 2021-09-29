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
import com.greenstar.eagle.adapters.crf.ClientAgeAdapter;
import com.greenstar.eagle.adapters.crf.CurrentMethodAdapter;
import com.greenstar.eagle.controller.Codes;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.DropdownCRBData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ClientRegistrationForm extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener, View.OnFocusChangeListener {

    AppDatabase db =null;

    TextView tvSitarabajiCode, tvSitarabajiName, tvProviderCode, tvProviderName, tvSupervisorName, tvRegion, tvDistrict;

    EditText etVisitDate, etFollowupDate, etClientName, etHusbandName, etAddress, etContact, etCurrentUseYear;

    TextView tvContactNumber, tvClientName;

    Spinner spClientAge, spCurrentMethod;

    RadioGroup rgCanWeContact;
    RadioButton rbCanWeContactYes, rbCanWeContactNo;

    RadioGroup rgIsEverUser;
    RadioButton rbIsEverUserYes, rbIsEverUserNo;

    RadioGroup rgNotInUse;
    RadioButton rbNotInUseMore, rbNotInUseLess;

    RadioGroup rgIsCurrentUser;
    RadioButton rbIsCurrentUserYes, rbIsCurrentUserNo;

    Button btnSubmit;

    DatePickerDialog.OnDateSetListener date = null;
    DatePickerDialog.OnDateSetListener followupDate = null;
    final Calendar myCalendar = Calendar.getInstance();

    TableRow trIPCReferralStatus, trNotInUse,trPeriodOfCurrentYears, trCurrentMethod, trIsMethodUseIn12Months, trEverUsed;

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

        rgNotInUse = findViewById(R.id.rgNotInUse);
        rbNotInUseMore = findViewById(R.id.rbNotInUseMore);
        rbNotInUseLess = findViewById(R.id.rbNotInUseLess);

        rgIsCurrentUser = findViewById(R.id.rgIsCurrentUser);
        rbIsCurrentUserYes = findViewById(R.id.rbIsCurrentUserYes);
        rbIsCurrentUserNo = findViewById(R.id.rbIsCurrentUserNo);
        rgIsCurrentUser.setOnCheckedChangeListener(this);

        etCurrentUseYear = findViewById(R.id.etCurrentMethodYears);

        spCurrentMethod = findViewById(R.id.spCurrentMethod);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        trNotInUse = findViewById(R.id.trNotInUse);
        trEverUsed = findViewById(R.id.trEverUsed);
        trPeriodOfCurrentYears = findViewById(R.id.trPeriodOfCurrentYears);
        trCurrentMethod = findViewById(R.id.trCurrentMethod);

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

        List<DropdownCRBData> dropdownCRBDataClientAge = new ArrayList<>();
        List<DropdownCRBData> dropdownCRBDataCurrentMethod = new ArrayList<>();
        List<DropdownCRBData> dropdownCRBDataTimingFPUser = new ArrayList<>();
        List<DropdownCRBData> dropdownCRBDataServiceType = new ArrayList<>();

        DropdownCRBData clientAgeDummy = new DropdownCRBData();
        clientAgeDummy.setDetailEnglish("Client Age");
        clientAgeDummy.setId(0);

        DropdownCRBData currentMethodDummy = new DropdownCRBData();
        currentMethodDummy.setDetailEnglish("Current Method");
        currentMethodDummy.setId(0);

        DropdownCRBData timingOfFPDummy = new DropdownCRBData();
        timingOfFPDummy.setDetailEnglish("Timing of service");
        timingOfFPDummy.setId(0);

        DropdownCRBData serviceTypeDummy = new DropdownCRBData();
        serviceTypeDummy.setDetailEnglish("Service taken on this visit");
        serviceTypeDummy.setId(0);

        try{
            db = AppDatabase.getAppDatabase(this);
            dropdownCRBDataClientAge = db.getDropdownCRBDataDAO().getAllClientAge();
            dropdownCRBDataCurrentMethod = db.getDropdownCRBDataDAO().getAllCurrentMethod();
            dropdownCRBDataTimingFPUser = db.getDropdownCRBDataDAO().getAllTimingFPService();
            dropdownCRBDataServiceType = db.getDropdownCRBDataDAO().getAllServiceType();
        }catch(Exception e){

        }

        dropdownCRBDataClientAge.add(0,clientAgeDummy);
        ClientAgeAdapter clientAgeAdapter = new ClientAgeAdapter(this, R.layout.dropdown_layout, R.id.tvNames, dropdownCRBDataClientAge);
        spClientAge.setAdapter(clientAgeAdapter);

        dropdownCRBDataCurrentMethod.add(0,currentMethodDummy);
        CurrentMethodAdapter currentMethodAdapter = new CurrentMethodAdapter(this, R.layout.dropdown_layout, R.id.tvNames, dropdownCRBDataCurrentMethod);
        spCurrentMethod.setAdapter(currentMethodAdapter);

        etCurrentUseYear.setText("0");
        etCurrentUseYear.setOnFocusChangeListener(this);

    }

    private void updateVisitDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

        etVisitDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateFollowupDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

        etFollowupDate.setText(sdf.format(myCalendar.getTime()));
    }
    private void submitForm(){
        Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
    }
/*
    public void submitForm(){

        CRFForm form = new CRFForm();

        DropdownCRBData data = (DropdownCRBData)spClientAge.getSelectedItem();
        form.setClientAge(data.getDetailEnglish());

        DropdownCRBData dataServiceType = (DropdownCRBData)spServiceType.getSelectedItem();
        form.setServiceType(dataServiceType.getDetailEnglish());

        DropdownCRBData dataTimingFPService = (DropdownCRBData)spTimingFPService.getSelectedItem();
        form.setTimingOfService(dataTimingFPService.getDetailEnglish());

        DropdownCRBData dataCurrentMethod = (DropdownCRBData)spCurrentMethod.getSelectedItem();
        form.setCurrentMethod(dataCurrentMethod.getDetailEnglish());

        form.setProviderCode(tvProviderCode.getText().toString());
        form.setProviderName(tvProviderName.getText().toString());
        form.setClientName(etClientName.getText().toString());
        form.setHusbandName(etHusbandName.getText().toString());
        form.setAddress(etAddress.getText().toString());
        form.setContactNumber(etContact.getText().toString());
        form.setAddress(etAddress.getText().toString());

        if(rbCanWeContactYes.isChecked()){
            form.setCanWeContact(1);
        }else{
            form.setCanWeContact(2);
        }
        double marriageDuration = 0;
        int currentSons =0;
        int currentDaughters = 0;
        int abortions = 0;

        if("".equals(etMarriageDuration.getText().toString())){
            marriageDuration = 0;
        }else{
            marriageDuration = Double.valueOf(etMarriageDuration.getText().toString());
        }
        if("".equals(etNoOfCurrentSons.getText().toString())){
            currentSons = 0;
        }else{
            currentSons = Integer.valueOf(etNoOfCurrentSons.getText().toString());
        }
        if("".equals(etNoOfCurrentDaughter.getText().toString())){
            currentDaughters = 0;
        }else{
            currentDaughters = Integer.valueOf(etNoOfCurrentDaughter.getText().toString());
        }
        if("".equals(etNumberOfAbortions.getText().toString())){
            abortions = 0;
        }else{
            abortions = Integer.valueOf(etNumberOfAbortions.getText().toString());
        }
        form.setDurationOfMarriage(marriageDuration);
        form.setNoOfSons(currentSons);
        form.setNoOfDaughters(currentDaughters);
        form.setNumberOfAbortion(abortions);

        if(rbIPCReferralStatusFirst.isChecked()){
            form.setIpcReferralStatus(1);
        }else{
            form.setIpcReferralStatus(2);
        }

        if(rbIsEverUserYes.isChecked()){
            form.setIsEverUser(1);
        }else{
            form.setIsEverUser(2);
        }

        if(rbNotInUseMore.isChecked()){
            form.setMethodNotInUse(1);
        }else{
            form.setMethodNotInUse(2);
        }

        if(rbIsCurrentUserYes.isChecked()){
            form.setIsCurrentUser(1);
        }else{
            form.setIsCurrentUser(0);
        }
        double currentUseYear = 0;

        if("".equals(etCurrentUseYear.getText().toString())){
            currentUseYear = 0;
        }else{
            currentUseYear = Double.valueOf(etCurrentUseYear.getText().toString());
        }
        form.setCurrentUseYear(currentUseYear);
        form.setVisitDate(etVisitDate.getText().toString());

        form.setId(Util.getNextCRBFormID(this));
        AppDatabase.getAppDatabase(this).getCRBFormDAO().insert(form);

        Toast.makeText(this,"Form successfully submitted!",Toast.LENGTH_SHORT).show();
        this.finish();
    }
*/
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

            if(!rbNotInUseMore.isChecked() && !rbNotInUseLess.isChecked() && rbIsEverUserYes.isChecked()){
                isValid=false;
                rbNotInUseMore.setTextColor(getResources().getColor( R.color.darkestOrange));
                rbNotInUseLess.setTextColor(getResources().getColor( R.color.darkestOrange));
            }else{
                rbNotInUseMore.setTextColor(getResources().getColor( R.color.darkGreen));
                rbNotInUseLess.setTextColor(getResources().getColor( R.color.darkGreen));
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
                trNotInUse.setVisibility(View.GONE);
                rgNotInUse.clearCheck();
            }else{
                trPeriodOfCurrentYears.setVisibility(View.GONE);
                etCurrentUseYear.setText("0");
                trCurrentMethod.setVisibility(View.GONE);
                spCurrentMethod.setSelection(0);

                trEverUsed.setVisibility(View.VISIBLE);
                trNotInUse.setVisibility(View.VISIBLE);
            }
        } else if(group.getId()==R.id.rgIsEverUser){
            if(rbIsEverUserYes.isChecked()){
                trNotInUse.setVisibility(View.VISIBLE);
            }else{
                trNotInUse.setVisibility(View.GONE);
                rgNotInUse.clearCheck();
            }
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
