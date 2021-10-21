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

    EditText etVisitDate, etFollowupDate;

    Spinner spClient, spReasonForNotVisiting, spAdoptedMethod, spReasonsForNotAdoptingMethod;

    Button btnSubmit;

    DatePickerDialog.OnDateSetListener date = null;
    DatePickerDialog.OnDateSetListener followupDate = null;

    final Calendar myCalendar = Calendar.getInstance();

    RadioGroup rgDidYouVisit;
    RadioButton rbDidYouVisitYes;
    RadioButton rbDidYouVisitNo;

    RadioGroup rgAnySideEffects;
    RadioButton rbAnySideEffectsYes;
    RadioButton rbAnySideEffectsNo;

    RadioGroup rgDidVisitAfterSideEffects;
    RadioButton rbDidVisitAfterSideEffectsYes;
    RadioButton rbDidVisitAfterSideEffectsNo;

    RadioGroup rgHaveYouAdopted;
    RadioButton rbHaveYouAdoptedYes;
    RadioButton rbHaveYouAdoptedNo;

    TableRow trReasonForNotVisiting, trAdoptedMethod, trAnySideEffects, trDidVisitAfterSideEffects, trReasonsForNotAdoptingMethod, trHaveYouAdopted;

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

        etFollowupDate = findViewById(R.id.etFollowupDate);
        etFollowupDate.setOnClickListener(this);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        spClient = findViewById(R.id.spClient);
        spReasonForNotVisiting = findViewById(R.id.spReasonForNotVisiting);
        spAdoptedMethod = findViewById(R.id.spAdoptedMethod);
        spReasonsForNotAdoptingMethod = findViewById(R.id.spReasonsForNotAdoptingMethod);

        rgDidYouVisit = findViewById(R.id.rgDidYouVisit);
        rbDidYouVisitYes = findViewById(R.id.rbDidYouVisitYes);
        rbDidYouVisitNo = findViewById(R.id.rbDidYouVisitNo);
        rgDidYouVisit.setOnCheckedChangeListener(this);

        rgAnySideEffects = findViewById(R.id.rgAnySideEffects);
        rbAnySideEffectsYes = findViewById(R.id.rbAnySideEffectsYes);
        rbAnySideEffectsNo = findViewById(R.id.rbAnySideEffectsNo);
        rgAnySideEffects.setOnCheckedChangeListener(this);

        rgDidVisitAfterSideEffects = findViewById(R.id.rgDidVisitAfterSideEffects);
        rbDidVisitAfterSideEffectsYes = findViewById(R.id.rbDidVisitAfterSideEffectsYes);
        rbDidVisitAfterSideEffectsNo = findViewById(R.id.rbDidVisitAfterSideEffectsNo);
        rgDidVisitAfterSideEffects.setOnCheckedChangeListener(this);

        rgHaveYouAdopted = findViewById(R.id.rgHaveYouAdopted);
        rbHaveYouAdoptedYes = findViewById(R.id.rbHaveYouAdoptedYes);
        rbHaveYouAdoptedNo = findViewById(R.id.rbHaveYouAdoptedNo);
        rgHaveYouAdopted.setOnCheckedChangeListener(this);

        trReasonForNotVisiting = findViewById(R.id.trReasonForNotVisiting);
        trAdoptedMethod = findViewById(R.id.trAdoptedMethod);
        trDidVisitAfterSideEffects = findViewById(R.id.trDidVisitAfterSideEffects);
        trAnySideEffects = findViewById(R.id.trAnySideEffects);
        trReasonsForNotAdoptingMethod = findViewById(R.id.trReasonsForNotAdoptingMethod);
        trHaveYouAdopted = findViewById(R.id.trHaveYouAdopted);
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

        updateFollowupDate();
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
        spAdoptedMethod.setAdapter(getGeneralDropdownAdapter("Adopted Method", "Current Method"));
        spReasonForNotVisiting.setAdapter(getGeneralDropdownAdapter("Reasons For Not Visiting", "ReasonsNotVisitingProvider"));
        spReasonsForNotAdoptingMethod.setAdapter(getGeneralDropdownAdapter("Reasons For Not Adopting Method", "ReasonsForNotAdopting"));
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

        form.setId(Util.getNextID(this,Codes.FOLLOWUPFORM));
        form.setVisitDate(etVisitDate.getText().toString());

        CRForm clientForm = (CRForm) spClient.getSelectedItem();
        form.setClientId(clientForm.getId());
        if(rbDidYouVisitYes.isChecked()){
            form.setDidYouVisit(1);
        }else{
            form.setDidYouVisit(0);
        }
        DropdownCRBData dropdownCRBData = (DropdownCRBData) spReasonForNotVisiting.getSelectedItem();

        form.setReasonForNotVisiting(dropdownCRBData.getDetailEnglish());
        if(rbHaveYouAdoptedYes.isChecked()){
            form.setHaveYouAdopted(1);
        }else{
            form.setHaveYouAdopted(0);
        }
        dropdownCRBData = new DropdownCRBData();
        dropdownCRBData = (DropdownCRBData) spAdoptedMethod.getSelectedItem();

        form.setAdoptedMethod(dropdownCRBData.getDetailEnglish());

        if(rbAnySideEffectsYes.isChecked()){
            form.setAnySideEffects(1);
        }else{
            form.setAnySideEffects(0);
        }
        if(rbDidYouVisitYes.isChecked()){
            form.setDidYouVisit(1);
        }else{
            form.setDidYouVisit(0);
        }

        dropdownCRBData = new DropdownCRBData();
        dropdownCRBData = (DropdownCRBData) spReasonsForNotAdoptingMethod.getSelectedItem();

        form.setReasonsForNotAdoptingMethod(dropdownCRBData.getDetailEnglish());

        AppDatabase.getAppDatabase(this).getFollowupModelDAO().insert(form);

        Toast.makeText(this,"Form successfully submitted!",Toast.LENGTH_SHORT).show();
        this.finish();
    }

    private boolean isValid(){
        boolean isValid=true;

        if(!rbDidYouVisitYes.isChecked() && !rbDidYouVisitNo.isChecked()){
            rbDidYouVisitYes.setTextColor(getResources().getColor( R.color.darkestOrange));
            rbDidYouVisitNo.setTextColor(getResources().getColor( R.color.darkestOrange));
            isValid = false;
        }else{
            rbDidYouVisitYes.setTextColor(getResources().getColor( R.color.darkGreen));
            rbDidYouVisitNo.setTextColor(getResources().getColor( R.color.darkGreen));
        }

        if(spClient.getSelectedItemId()==0){
            isValid = false;
            View view = spClient.getSelectedView();
            (view).setBackgroundColor(getResources().getColor(R.color.darkestOrange));
        }else{
            View view = spClient.getSelectedView();
            (view).setBackgroundColor(getResources().getColor(R.color.whiteColor));
        }

        isValid = checkRadioButton(trHaveYouAdopted, rbHaveYouAdoptedYes, rbHaveYouAdoptedNo, isValid);
        isValid = checkRadioButton(trAnySideEffects, rbAnySideEffectsYes, rbAnySideEffectsNo, isValid);
        isValid = checkRadioButton(trDidVisitAfterSideEffects, rbDidVisitAfterSideEffectsYes, rbDidVisitAfterSideEffectsNo, isValid);
        isValid = checkSpinner(trReasonForNotVisiting, spReasonForNotVisiting, isValid);
        isValid = checkSpinner(trAdoptedMethod, spAdoptedMethod, isValid);
        isValid = checkSpinner(trReasonsForNotAdoptingMethod, spReasonsForNotAdoptingMethod, isValid);

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
        else if(v.getId()==R.id.etFollowupDate){
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

        if(rgDidYouVisit.getId() == group.getId()){
            hideAll();
            clearAll();
            if(rbDidYouVisitYes.isChecked()){
                trHaveYouAdopted.setVisibility(View.VISIBLE);

            }else{
                trReasonForNotVisiting.setVisibility(View.VISIBLE);
            }
        }else if(rgHaveYouAdopted.getId()==group.getId()){
            if(rbHaveYouAdoptedYes.isChecked()){
                trReasonsForNotAdoptingMethod.setVisibility(View.GONE);
                spReasonsForNotAdoptingMethod.setSelection(0);
                trAnySideEffects.setVisibility(View.GONE);
                clearCheck(rgAnySideEffects);
                trDidVisitAfterSideEffects.setVisibility(View.GONE);
                clearCheck(rgDidVisitAfterSideEffects);

                trAdoptedMethod.setVisibility(View.VISIBLE);
                trAnySideEffects.setVisibility(View.VISIBLE);
            }else{
                trAdoptedMethod.setVisibility(View.GONE);
                spAdoptedMethod.setSelection(0);
                trAnySideEffects.setVisibility(View.GONE);

                trReasonsForNotAdoptingMethod.setVisibility(View.VISIBLE);
            }
        }else if(rgAnySideEffects.getId()==group.getId()){
            if(rbAnySideEffectsYes.isChecked()){
                trDidVisitAfterSideEffects.setVisibility(View.VISIBLE);
            }else{
                trDidVisitAfterSideEffects.setVisibility(View.GONE);
                clearCheck(rgDidVisitAfterSideEffects);
            }
        }
    }

    private void clearAll() {
        spReasonForNotVisiting.setSelection(0);
        clearCheck(rgHaveYouAdopted);
        spAdoptedMethod.setSelection(0);
        clearCheck(rgDidVisitAfterSideEffects);
        spReasonsForNotAdoptingMethod.setSelection(0);
        clearCheck(rgAnySideEffects);
        clearCheck(rgDidVisitAfterSideEffects);
    }

    private void hideAll(){
        trReasonForNotVisiting.setVisibility(View.GONE);
        trReasonsForNotAdoptingMethod.setVisibility(View.GONE);
        trHaveYouAdopted.setVisibility(View.GONE);
        trAdoptedMethod.setVisibility(View.GONE);
        trDidVisitAfterSideEffects.setVisibility(View.GONE);
        trAnySideEffects.setVisibility(View.GONE);
    }
}
