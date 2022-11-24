package com.greenstar.eagle.controller.IPCForms;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
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

    EditText etVisitDate, etFollowupDate, etClientName, etHusbandName, etAddress, etContact, etPersonalMinutes;

    TextView tvContactNumber, tvClientName, tvHusbandName, tvAddress;

    Spinner spClientAge, spHistory;

    RadioGroup rgCanWeContact;
    RadioButton rbCanWeContactYes, rbCanWeContactNo;

    RadioGroup rgRegisteredAt;
    RadioButton rbNHM, rbHHV, rbOM, rbSH;

    RadioGroup rgTokenGiven;
    RadioButton rbTokenGivenYes, rbTokenGivenNo;

    Button btnSubmit;

    DatePickerDialog.OnDateSetListener date = null;
    DatePickerDialog.OnDateSetListener followupDate = null;
    final Calendar myCalendar1 = Calendar.getInstance();

    TableRow trNeverUseReason,trPeriodOfCurrentYears, trCurrentMethod, trEverUsed, trEverMethod, trEverUseReason, trFollowupDate;

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
        tvHusbandName = findViewById(R.id.tvHusbandName);
        tvAddress = findViewById(R.id.tvAddress);

        spClientAge = findViewById(R.id.spClientAge);
        spHistory = findViewById(R.id.spHistory);

        etAddress = findViewById(R.id.etAddress);
        etContact = findViewById(R.id.etContact);
        etPersonalMinutes = findViewById(R.id.etPersonalMinutes);
        tvClientName = findViewById(R.id.tvClientName);
        tvContactNumber = findViewById(R.id.tvContactNumber);

        rgCanWeContact = findViewById(R.id.rgCanWeContact);
        rbCanWeContactYes = findViewById(R.id.rbCanWeContactYes);
        rbCanWeContactNo = findViewById(R.id.rbCanWeContactNo);

        rgRegisteredAt = findViewById(R.id.rgRegisteredAt);
        rbHHV = findViewById(R.id.rbHHV);
        rbNHM = findViewById(R.id.rbNHM);
        rbOM = findViewById(R.id.rbOM);
        rbSH = findViewById(R.id.rbSH);

        rgTokenGiven = findViewById(R.id.rgTokenGiven);
        rbTokenGivenYes = findViewById(R.id.rbTokenGivenYes);
        rbTokenGivenNo = findViewById(R.id.rbTokenGivenNo);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        trEverUsed = findViewById(R.id.trEverUsed);
        trEverMethod = findViewById(R.id.trEverMethod);
        trEverUseReason = findViewById(R.id.trEverUseReason);
        trPeriodOfCurrentYears = findViewById(R.id.trPeriodOfCurrentYears);
        trCurrentMethod = findViewById(R.id.trCurrentMethod);
        trNeverUseReason = findViewById(R.id.trNeverUseReason);

        trFollowupDate = findViewById(R.id.trFollowupDate);
        trFollowupDate.setOnClickListener(this);
    }

    private void populateForm(){

        updateVisitDate();
       // updateFollowupDate();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, month);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateVisitDate();
            }

        };
        followupDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, month);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
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

        spClientAge.setAdapter(getGeneralDropdownAdapter("Client Age", "Client Age"));
        spHistory.setAdapter(getGeneralDropdownAdapter("Reproductive History","EagleHistory"));
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

        etVisitDate.setText(sdf.format(myCalendar1.getTime()));
    }

    private void updateFollowupDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

        etFollowupDate.setText(sdf.format(myCalendar1.getTime()));
    }

    public void submitForm(){
        long clientId = Util.getNextID(this,Codes.CRFORMID);
        CRForm form = new CRForm();
        form.setId(clientId);
        form.setVisitDate(etVisitDate.getText().toString());
        form.setClientName(etClientName.getText().toString());
        form.setHusbandName(etHusbandName.getText().toString());
        DropdownCRBData dropdownCRBData = (DropdownCRBData) spClientAge.getSelectedItem();
        form.setClientAge(dropdownCRBData.getDetailEnglish());
        form.setAddress(etAddress.getText().toString());
        form.setContactNumber(etContact.getText().toString());
        form.setRemarks(etPersonalMinutes.getText().toString());
        form.setFollowUpVisitDate(etFollowupDate.getText().toString());
        if(rbCanWeContactYes.isChecked()){
            form.setCanWeContact(1);
        }else{
            form.setCanWeContact(0);
        }

        if(rbTokenGivenYes.isChecked()){
            form.setIsTokenGiven(1);
        }else{
            form.setIsTokenGiven(0);
        }

        if(rbTokenGivenYes.isChecked()){
            form.setIsTokenGiven(1);
        }else{
            form.setIsTokenGiven(0);
        }

        if(rbHHV.isChecked()){
            form.setRegisteredAt("HHV");
        }else if(rbNHM.isChecked()){
            form.setRegisteredAt("NHM");
        }else  if (rbOM.isChecked()){
            form.setRegisteredAt("OM");
        }else if(rbSH.isChecked()){
            form.setRegisteredAt("SH");
        }

        DropdownCRBData dropdown = (DropdownCRBData) spHistory.getSelectedItem();
        form.setReproductiveHistory(dropdown.getDetailEnglish());

        Location location = Util.getLastKnownLocation(this);
        if (location != null) {
            form.setLatLong(location.getLatitude() + "," + location.getLongitude());
        }
        form.setMobileSystemDate(Util.sdf.format(Calendar.getInstance().getTime()));
        AppDatabase.getAppDatabase(this).getCrFormDAO().insert(form);

        Toast.makeText(this,"Form successfully submitted!",Toast.LENGTH_SHORT).show();

        if(rbTokenGivenYes.isChecked()){
            Intent i = new Intent(this, TokenForm.class);
            i.putExtra("clientId",clientId);
            startActivity(i);
        }
        this.finish();

    }

    private boolean isValid(){
        boolean isValid=true;
        if("".equals(etClientName.getText().toString()) ||
                "".equals(etContact.getText().toString()) ||
                        "".equals(etAddress.getText().toString()) ||
                        "".equals(etHusbandName.getText().toString())){

            isValid = false;
        }
        if(isValid && spClientAge.getSelectedItemId()==0){
            isValid=false;
        }
        if(isValid && spHistory.getSelectedItemId()==0){
            isValid=false;
        }
        if("".equals(etContact.getText().toString()) ){
            tvContactNumber.setTextColor(getResources().getColor(R.color.darkestOrange));
        }else{
            tvContactNumber.setTextColor(getResources().getColor(R.color.black));
        }

        if("".equals(etClientName.getText().toString())){
            tvClientName.setTextColor(getResources().getColor(R.color.darkestOrange));
        }else{
            tvClientName.setTextColor(getResources().getColor(R.color.black));
        }
        if("".equals(etHusbandName.getText().toString())){
            tvHusbandName.setTextColor(getResources().getColor(R.color.darkestOrange));
        }else{
            tvHusbandName.setTextColor(getResources().getColor(R.color.black));
        }

        if("".equals(etAddress.getText().toString())){
            tvAddress.setTextColor(getResources().getColor(R.color.darkestOrange));
        }else{
            tvAddress.setTextColor(getResources().getColor(R.color.black));
        }

        if(spClientAge.getSelectedItemId()==0) {
            View view = spClientAge.getSelectedView();
            (view).setBackgroundColor(getResources().getColor(R.color.darkestOrange));
            isValid = false;
        }else{
            View view = spClientAge.getSelectedView();
            ( view).setBackgroundColor(getResources().getColor(R.color.whiteColor));
        }

        if(spHistory.getSelectedItemId()==0) {
            View view = spHistory.getSelectedView();
            (view).setBackgroundColor(getResources().getColor(R.color.darkestOrange));
            isValid = false;
        }else{
            View view = spHistory.getSelectedView();
            ( view).setBackgroundColor(getResources().getColor(R.color.whiteColor));
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
                                if(isValid()){
                                    submitForm();
                                }

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
            new DatePickerDialog(this, date, myCalendar1
                    .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                    myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
        }
        else if(v.getId()==R.id.etFollowupDate ||
                v.getId()==R.id.trFollowupDate){
            new DatePickerDialog(this, followupDate, myCalendar1
                    .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                    myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
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
