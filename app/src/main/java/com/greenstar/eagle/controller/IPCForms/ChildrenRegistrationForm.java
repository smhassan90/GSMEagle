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
import com.greenstar.eagle.utils.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChildrenRegistrationForm extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnFocusChangeListener {

    AppDatabase db =null;

    TextView tvSitarabajiCode, tvSitarabajiName, tvProviderCode, tvProviderName, tvSupervisorName, tvRegion, tvDistrict;

    EditText etVisitDate;

    Spinner spChildAge, spSelectParent;

    RadioGroup rgSufferingDiarrhea;
    RadioButton rbSufferingDiarrheaYes, rbSufferingDiarrheaNo;

    RadioGroup rgMedicineProvided;
    RadioButton rbMedicineProvidedYes, rbMedicineProvidedNo;

    RadioGroup rgCounselingDone;
    RadioButton rbCounselingDoneYes, rbCounselingDoneNo;

    Button btnSubmit;

    DatePickerDialog.OnDateSetListener date = null;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.children_form_activity);

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

        spChildAge = findViewById(R.id.spChildAge);
        spSelectParent = findViewById(R.id.spSelectParent);

        rgSufferingDiarrhea = findViewById(R.id.rgSufferingDiarrhea);
        rbSufferingDiarrheaYes = findViewById(R.id.rbSufferingDiarrheaYes);
        rbSufferingDiarrheaNo = findViewById(R.id.rbSufferingDiarrheaNo);

        rgMedicineProvided = findViewById(R.id.rgMedicineProvided);
        rbMedicineProvidedYes = findViewById(R.id.rbMedicineProvidedYes);
        rbMedicineProvidedNo = findViewById(R.id.rbMedicineProvidedNo);

        rgCounselingDone = findViewById(R.id.rgCounselingDone);
        rbCounselingDoneYes = findViewById(R.id.rbCounselingDoneYes);
        rbCounselingDoneNo = findViewById(R.id.rbCounselingDoneNo);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
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

        SharedPreferences shared = getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String providerName = shared.getString("providerName", "");
        String providerCode = shared.getString("providerCode", "");
        String district = shared.getString("district", "");
        String region = shared.getString("region", "");
        tvProviderName.setText(providerName);
        tvProviderCode.setText(providerCode);
        tvRegion.setText(region);
        tvDistrict.setText(district);

        List<CRForm> dropdownCRF = new ArrayList<>();

        CRForm parentDummy = new CRForm();
        parentDummy.setClientName("Select Parent Name");
        parentDummy.setId(0);

        try{
            db = AppDatabase.getAppDatabase(this);
            dropdownCRF = db.getCrFormDAO().getAll();
        }catch(Exception e){

        }

        spChildAge.setAdapter(getGeneralDropdownAdapter("Child Age", "Client Age"));

        ClientAdapter clientAdapter = new ClientAdapter(this, R.layout.provider_town_list, R.id.tvProviderNamess, dropdownCRF);
        spSelectParent.setAdapter(clientAdapter);


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

    public void submitForm(){
        ChildRegistrationForm form = new ChildRegistrationForm();

        form.setId(Util.getNextID(this,Codes.CHILDRENREGISTRATIONFORMID));
        form.setVisitDate(etVisitDate.getText().toString());

        DropdownCRBData selectedAge = new DropdownCRBData();
        selectedAge = (DropdownCRBData) spChildAge.getSelectedItem();

        form.setChildAge(selectedAge.getDetailEnglish());

        if(rbSufferingDiarrheaYes.isChecked()){
            form.setCurrentDiarrhea(1);
        }else{
            form.setCurrentDiarrhea(0);
        }

        if(rbMedicineProvidedYes.isChecked()){
            form.setIsMedicineProvided(1);
        }else{
            form.setIsMedicineProvided(0);
        }

        if(rbCounselingDoneYes.isChecked()){
            form.setIsCounseling(1);
        }else{
            form.setIsCounseling(0);
        }
        CRForm clientForm = (CRForm) spSelectParent.getSelectedItem();
        form.setParentId(clientForm.getId());

        AppDatabase.getAppDatabase(this).getChildRegistrationFormDAO().insert(form);

        Toast.makeText(this,"Form successfully submitted!",Toast.LENGTH_SHORT).show();
        this.finish();
    }

    private boolean isValid(){
        boolean isValid=true;

        if(spChildAge.getSelectedItemId()==0) {
            View view = spChildAge.getSelectedView();
            (view).setBackgroundColor(getResources().getColor(R.color.darkestOrange));
            isValid = false;
        }else{
            View view = spChildAge.getSelectedView();
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
