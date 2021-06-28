package com.example.personfinder.ui.person_listing;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import com.example.personfinder.GeneralClasses.Global;
import com.example.personfinder.R;
import com.example.personfinder.databinding.ActivityAddPostInformationBinding;
import com.example.personfinder.ui.person_listing.model.PersonComplaintData;

import java.text.MessageFormat;

public class AddPostInformationActivity extends AppCompatActivity {


    ActivityAddPostInformationBinding binding;
    PersonComplaintData personComplaintData;
    String[] genderArr = {"Select Gender", "Male", "Female", "Other"};
    String[] personsStatusArr = {"--Select--", "Found", "Missing", "Mentally Disturbed", "Dead"};
    boolean isMissing, isFounded, isMentallyDisturb, isDeath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_post_information);
        init();
        binding.onBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.personStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setBooleansFalse();
                switch (personsStatusArr[position]) {
                    case "--Select--":
                        setBooleansFalse();
                        break;

                    case "Found":
                        isFounded = true;
                        break;

                    case "Missing":
                        isMissing = true;
                        break;

                    case "Mentally Disturbed":
                        isMentallyDisturb = true;
                        break;

                    case "Dead":
                        isDeath = true;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setBooleansFalse();

            }
        });
    }

    public void setBooleansFalse() {
        isMissing = false;
        isFounded = false;
        isMentallyDisturb = false;
        isDeath = false;
    }


    public String getSelectedPersonStatus() {
        if (isFounded)
            return "Found";
        else if (isMissing)
            return "Missing";
        else if (isMentallyDisturb)
            return "Mentally Disturbed";
        else if (isDeath)
            return "Dead";
        else return "  --Select--";
    }


    private int getSpinnerPosition(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) return i;
        }

        return 0;
    }


    public void init() {

        binding.cellPhoneCcp.registerCarrierNumberEditText(binding.cellPhone);
        binding.cellPhoneCcp.setDefaultCountryUsingNameCode("PK");
        binding.cellPhoneCcp.resetToDefaultCountry();

        ArrayAdapter<String> genderArray = new ArrayAdapter<String>(this, R.layout.spinner_item, genderArr);
        binding.genderSpinner.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.spinner_bg, null));
        binding.genderSpinner.setAdapter(genderArray);

        ArrayAdapter<String> personStatusArray = new ArrayAdapter<String>(this, R.layout.spinner_item, personsStatusArr);
        binding.personStatusSpinner.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.spinner_bg, null));
        binding.personStatusSpinner.setAdapter(personStatusArray);


        personComplaintData = (PersonComplaintData) getIntent().getSerializableExtra("data");
        if (personComplaintData != null) {
            binding.firstName.setText(personComplaintData.getPersonName());
            binding.fathersName.setText(personComplaintData.getFatherName());
            binding.cnic.setText(personComplaintData.getCnic());
            binding.personAge.setText(String.valueOf(personComplaintData.getPersonAge()));
            binding.personHeight.setText(String.valueOf(personComplaintData.getPersonHeight()));
            binding.landline.setText(String.valueOf(personComplaintData.getLandLineNumber()));
            binding.email.setText(String.valueOf(personComplaintData.getEmail()));
            binding.desc.setText(String.valueOf(personComplaintData.getDescription()));
            binding.address.setText(String.valueOf(personComplaintData.getAddress()));
            if (personComplaintData.getMobileNo() != null)
                binding.cellPhone.setText(MessageFormat.format("{0}", Global.getCountryIsoCode(this, personComplaintData.getMobileNo(), binding.cellPhoneCcp)));
            binding.dob.setText(Global.parseDate(personComplaintData.getDateOfBirth()));
            binding.genderSpinner.setSelection(getSpinnerPosition(binding.genderSpinner, personComplaintData.getGenderName()));
            isDeath = personComplaintData.getIsFounded();
            isMissing = personComplaintData.getIsMissing();
            isMentallyDisturb = personComplaintData.getIsMentallyDisturb();
            isFounded = personComplaintData.getIsFounded();
            binding.personStatusSpinner.setSelection(getSpinnerPosition(binding.personStatusSpinner, getSelectedPersonStatus()));

        }
    }
}