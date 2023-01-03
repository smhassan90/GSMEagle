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
import com.greenstar.eagle.adapters.ClientAdapter;
import com.greenstar.eagle.adapters.GeneralDropdownAdapter;
import com.greenstar.eagle.controller.Codes;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.CRForm;
import com.greenstar.eagle.model.ChildRegistrationForm;
import com.greenstar.eagle.model.DropdownCRBData;
import com.greenstar.eagle.model.FollowupModel;
import com.greenstar.eagle.utils.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FollowupForm extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnFocusChangeListener, RadioGroup.OnCheckedChangeListener {

    AppDatabase db =null;

    TextView tvSitarabajiCode, tvSitarabajiName, tvProviderCode, tvProviderName, tvSupervisorName, tvRegion, tvDistrict;

    EditText etVisitDate, etFollowupDate, etPersonalMinutes;

    Spinner spClient, spAdoptedMethod;

    Button btnSubmit;

    DatePickerDialog.OnDateSetListener date = null;
    DatePickerDialog.OnDateSetListener followupDate = null;

    final Calendar myCalendar = Calendar.getInstance();

    TableRow trSupportProvider, trSupportCompleted, trAdoptedMethod, trFollowupDate;

    RadioGroup rgSupportSitaraHouse;
    RadioButton rbSupportSitaraHouseYes,rbSupportSitaraHouseNo;

    RadioGroup rgSupportProvider;
    RadioButton rbSupportProviderYes,rbSupportProviderNo;

    RadioGroup rgSupportCompleted;
    RadioButton rbSupportCompletedYes,rbSupportCompletedNo;

    RadioGroup rgTokenGiven;
    RadioButton rbTokenGivenYes, rbTokenGivenNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followup_form_activity);

        initializeVariables();
        populateFirstSection();
        populateForm();
    }

    private void populateFirstSection() {
        SharedPreferences prefs = this.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String sitarabajiCode = prefs.getString("sitaraBajiCode", "");
        String sitarabajiName = prefs.getString("sitaraBajiName", "");
        String providerCode = prefs.getString("providerCode", "");
        String providerName = prefs.getString("providerName", "");
        String supervisorName = prefs.getString("AMName", "");
        String region = prefs.getString("region", "");
        String district = prefs.getString("district", "");

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

        etPersonalMinutes = findViewById(R.id.etPersonalMinutes);

        etFollowupDate = findViewById(R.id.etFollowupDate);
        etFollowupDate.setOnClickListener(this);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        spClient = findViewById(R.id.spClient);

        trAdoptedMethod = findViewById(R.id.trAdoptedMethod);
        trSupportCompleted = findViewById(R.id.trSupportCompleted);
        trSupportProvider = findViewById(R.id.trSupportProvider);

        rgSupportCompleted = findViewById(R.id.rgSupportCompleted);
        rbSupportCompletedYes = findViewById(R.id.rbSupportCompletedYes);
        rbSupportCompletedNo = findViewById(R.id.rbSupportCompletedNo);
        rgSupportCompleted.setOnCheckedChangeListener(this);

        rgSupportProvider = findViewById(R.id.rgSupportProvider);
        rbSupportProviderYes = findViewById(R.id.rbSupportProviderYes);
        rbSupportProviderNo = findViewById(R.id.rbSupportProviderNo);
        rgSupportProvider.setOnCheckedChangeListener(this);

        rgSupportSitaraHouse = findViewById(R.id.rgSupportSitaraHouse);
        rbSupportSitaraHouseYes = findViewById(R.id.rbSupportSitaraHouseYes);
        rbSupportSitaraHouseNo = findViewById(R.id.rbSupportCompletedNo);
        rgSupportSitaraHouse.setOnCheckedChangeListener(this);

        spAdoptedMethod = findViewById(R.id.spAdoptedMethod);

        rgTokenGiven = findViewById(R.id.rgTokenGiven);
        rbTokenGivenYes = findViewById(R.id.rbTokenGivenYes);
        rbTokenGivenNo = findViewById(R.id.rbTokenGivenNo);

        trFollowupDate = findViewById(R.id.trFollowupDate);
        trFollowupDate.setOnClickListener(this);


    }

    private void clearCheck(RadioGroup radioGroup){
        radioGroup.setOnCheckedChangeListener(null);
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void populateForm(){

        updateVisitDate();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateVisitDate();
            }

        };

       // updateFollowupDate();
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

        populateClientSpinner();


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

    private void updateVisitDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

        etVisitDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateFollowupDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

        etFollowupDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void submitForm(){
        FollowupModel form = new FollowupModel();
        form.setRemarks(etPersonalMinutes.getText().toString());
        form.setId(Util.getNextID(this,Codes.FOLLOWUPFORM));
        form.setVisitDate(etVisitDate.getText().toString());

        CRForm clientForm = (CRForm) spClient.getSelectedItem();
        if(clientForm!=null){
            form.setClientId(clientForm.getId());
        }else{
            form.setClientId(0);
        }


        DropdownCRBData dropdownCRBData = null;

        dropdownCRBData = new DropdownCRBData();
        dropdownCRBData = (DropdownCRBData) spAdoptedMethod.getSelectedItem();
        if(dropdownCRBData!=null){
            form.setService(dropdownCRBData.getDetailEnglish());
        }else{
            form.setService("");
        }

        form.setFollowupDate(etFollowupDate.getText().toString());
        if(rbSupportCompletedYes.isChecked()){
            form.setIsSupportCompleted(1);
        }else{
            form.setIsSupportCompleted(0);
        }
        if(rbSupportSitaraHouseYes.isChecked()){
            form.setIsSupportSitaraHouse(1);
        }else{
            form.setIsSupportSitaraHouse(0);
        }
        if(rbSupportProviderYes.isChecked()){
            form.setIsSupportProvider(1);
        }else{
            form.setIsSupportProvider(0);
        }


        Location location = Util.getLastKnownLocation(this);
        if (location != null) {
            form.setLatLong(location.getLatitude() + "," + location.getLongitude());
        }
        form.setMobileSystemDate(Util.sdf.format(Calendar.getInstance().getTime()));
        AppDatabase.getAppDatabase(this).getFollowupModelDAO().insert(form);

        Toast.makeText(this,"Form successfully submitted!",Toast.LENGTH_SHORT).show();

        if(rbTokenGivenYes.isChecked()){
            Intent i = new Intent(this, TokenForm.class);
            i.putExtra("clientId",clientForm.getId());
            startActivity(i);
        }

        this.finish();
    }

    private boolean isValid(){
        boolean isValid=true;

        if(spClient.getSelectedItemId()==0){
            isValid = false;
            View view = spClient.getSelectedView();
            (view).setBackgroundColor(getResources().getColor(R.color.darkestOrange));
        }else{
            View view = spClient.getSelectedView();
            (view).setBackgroundColor(getResources().getColor(R.color.whiteColor));
        }
/*
        if(rbSupportSitaraHouseYes.isChecked() && rbSupportProviderYes.isChecked() && spAdoptedMethod.getSelectedItemId()==0){
            isValid = false;
            View view = spAdoptedMethod.getSelectedView();
            (view).setBackgroundColor(getResources().getColor(R.color.darkestOrange));
        }else{
            View view = spAdoptedMethod.getSelectedView();
            (view).setBackgroundColor(getResources().getColor(R.color.whiteColor));
        }

        if(rbSupportProviderYes.isChecked() && rbSupportProviderYes.isChecked() && spAdoptedMethod.getSelectedItemId()==0){
            isValid = false;
            View view = spAdoptedMethod.getSelectedView();
            (view).setBackgroundColor(getResources().getColor(R.color.darkestOrange));
        }else{
            View view = spAdoptedMethod.getSelectedView();
            (view).setBackgroundColor(getResources().getColor(R.color.whiteColor));
        }
        
*/

        if(spAdoptedMethod.getVisibility()==View.VISIBLE){
            View view = spAdoptedMethod.getSelectedView();
            if(spAdoptedMethod.getSelectedItemId()==0){

                if(view!=null){
                    isValid = false;
                    (view).setBackgroundColor(getResources().getColor(R.color.darkestOrange));
                }

            }else{
                if(view!=null){
                    (view).setBackgroundColor(getResources().getColor(R.color.whiteColor));
                }
            }
        }
        return isValid;
    }

    private boolean checkSpinner(TableRow tr, Spinner sp, boolean valid){
        if(tr.getVisibility() == View.VISIBLE &&
                sp.getSelectedItemId()==0){
            View view = sp.getSelectedView();
            view.setBackgroundColor(getResources().getColor(R.color.darkestOrange));
            valid = false;
        }else {
            View view = sp.getSelectedView();
            if(view!=null)
                view.setBackgroundColor(getResources().getColor(R.color.whiteColor));
        }

        return valid;
    }

    private boolean checkRadioButton(TableRow tr, RadioButton rbYes, RadioButton rbNo, boolean valid){
        if(tr.getVisibility()==View.VISIBLE &&
                !rbYes.isChecked() &&
                !rbNo.isChecked()){
            rbYes.setTextColor(getResources().getColor( R.color.darkestOrange));
            rbNo.setTextColor(getResources().getColor( R.color.darkestOrange));
            valid = false;
        }else{
            rbYes.setTextColor(getResources().getColor( R.color.darkGreen));
            rbNo.setTextColor(getResources().getColor( R.color.darkGreen));
        }

        return valid;
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
        else if(v.getId()==R.id.trFollowupDate ||
                v.getId()==R.id.etFollowupDate){
            new DatePickerDialog(this, followupDate, myCalendar
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(group.getId()==R.id.rgSupportSitaraHouse){
            if(rbSupportSitaraHouseYes.isChecked()){
                //Was support completed?
                hideAll();
                clearAll();
                trSupportCompleted.setVisibility(View.VISIBLE);
            }else{
                hideAll();
                clearAll();
                trSupportProvider.setVisibility(View.VISIBLE);
            }
        }else if(group.getId()==R.id.rgSupportProvider){
            if(rbSupportProviderYes.isChecked()){
                trSupportCompleted.setVisibility(View.VISIBLE);
            }else{
                trSupportCompleted.setVisibility(View.GONE);
                clearCheck(rgSupportCompleted);
                trAdoptedMethod.setVisibility(View.GONE);
            }
        }else if(group.getId()==R.id.rgSupportCompleted){
            if(rbSupportCompletedYes.isChecked()){
                trAdoptedMethod.setVisibility(View.VISIBLE);
                if(rbSupportSitaraHouseYes.isChecked()){
                    spAdoptedMethod.setAdapter(getGeneralDropdownAdapter("Select Product", "SitaraHouseMethod"));
                }else{
                    spAdoptedMethod.setAdapter(getGeneralDropdownAdapter("Select Product", "ProviderMethod"));
                }

            }else{
                trAdoptedMethod.setVisibility(View.GONE);
            }
        }

    }

    private void clearAll() {
        clearCheck(rgSupportCompleted);
        clearCheck(rgSupportProvider);

    }

    private void hideAll(){
        trAdoptedMethod.setVisibility(View.GONE);
        trSupportProvider.setVisibility(View.GONE);
        trSupportCompleted.setVisibility(View.GONE);
    }


}
