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
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.greenstar.eagle.R;
import com.greenstar.eagle.adapters.ClientAdapter;
import com.greenstar.eagle.adapters.NeighbourhoodAttendeeAdapter;
import com.greenstar.eagle.controller.Codes;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.CRForm;
import com.greenstar.eagle.model.DropdownCRBData;
import com.greenstar.eagle.model.NeighbourhoodAttendeesModel;
import com.greenstar.eagle.model.NeighbourhoodFormModel;
import com.greenstar.eagle.utils.ScrollableNestedListview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NeighbourhoodForm extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnFocusChangeListener {

    AppDatabase db =null;

    TextView tvSitarabajiCode, tvSitarabajiName, tvProviderCode, tvProviderName, tvSupervisorName, tvRegion, tvDistrict;

    EditText etVisitDate, etCommunityName, etOtherIECMaterial;

    Spinner spClient;

    Button btnSubmit, btnAdd;

    CheckBox cbIsFPBrochureGiven,cbIsDiarrheaBrochureGiven;

    DatePickerDialog.OnDateSetListener date = null;
    final Calendar myCalendar = Calendar.getInstance();

    ScrollableNestedListview lvNeighbourhoodAttendee;

    NeighbourhoodAttendeeAdapter adapter = null;
    List<NeighbourhoodAttendeesModel> neighbourhoodAttendeesModels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.neighbourhood_form_activity);

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

        spClient = findViewById(R.id.spClient);

        etCommunityName = findViewById(R.id.etCommunityName);
        etOtherIECMaterial = findViewById(R.id.etOtherIECMaterial);

        etVisitDate = findViewById(R.id.etVisitDate);
        etVisitDate.setOnClickListener(this);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        cbIsDiarrheaBrochureGiven = findViewById(R.id.cbIsDiarrheaBrochureGiven);
        cbIsFPBrochureGiven = findViewById(R.id.cbIsFPBrochureGiven);

        lvNeighbourhoodAttendee = findViewById(R.id.lvNeighbourhoodAttendee);
        lvNeighbourhoodAttendee.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        adapter = new NeighbourhoodAttendeeAdapter(this, R.layout.neighbourhood_attendee_list, R.id.tvName, neighbourhoodAttendeesModels);
        lvNeighbourhoodAttendee.setAdapter(adapter);
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
        parentDummy.setClientName("Select MWRA Name");
        parentDummy.setId(0);

        try{
            db = AppDatabase.getAppDatabase(this);
            dropdownCRF = db.getCrFormDAO().getAll();
        }catch(Exception e){

        }

        if(dropdownCRF.size()==0){
            dropdownCRF.add(parentDummy);
        }else{
            dropdownCRF.add(0,parentDummy);
        }

        ClientAdapter clientAdapter = new ClientAdapter(this, R.layout.provider_town_list, R.id.tvProviderNamess, dropdownCRF);
        spClient.setAdapter(clientAdapter);

    }

    private void updateVisitDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

        etVisitDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void submitForm(){
        NeighbourhoodFormModel mainForm = new NeighbourhoodFormModel();

        mainForm.setVisitDate(etVisitDate.getText().toString());

        mainForm.setCommunityName(etCommunityName.getText().toString());

        AppDatabase.getAppDatabase(this).getNeighbourhoodFormDAO().insert(mainForm);

        Toast.makeText(this,"Form successfully submitted!",Toast.LENGTH_SHORT).show();
        this.finish();
    }

    private boolean isValid(){
        boolean isValid=true;


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
        }else if(v.getId() == R.id.btnAdd){
            CRForm form = (CRForm) spClient.getSelectedItem();
            long clientId = form.getId();
            int isFPBrochureGiven =0;
            if(cbIsFPBrochureGiven.isChecked()){
                isFPBrochureGiven = 1;
            }

            int isDiarrheaBrochureGiven =0;
            if(cbIsDiarrheaBrochureGiven.isChecked()){
                isDiarrheaBrochureGiven = 1;
            }

            NeighbourhoodAttendeesModel neighbourhoodAttendeesModel = new NeighbourhoodAttendeesModel();
            neighbourhoodAttendeesModel.setClientId(clientId);
            neighbourhoodAttendeesModel.setIsDiarrheaBrochureGiven(isDiarrheaBrochureGiven);
            neighbourhoodAttendeesModel.setIsFPBrochureGiven(isFPBrochureGiven);
            neighbourhoodAttendeesModel.setOtherIECMaterial(etOtherIECMaterial.getText().toString());

            neighbourhoodAttendeesModels.add(neighbourhoodAttendeesModel);

            adapter.setList(neighbourhoodAttendeesModels);
            adapter.notifyDataSetChanged();
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
