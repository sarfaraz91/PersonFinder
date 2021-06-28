package com.example.personfinder.ui.create_post;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.personfinder.GeneralClasses.Global;
import com.example.personfinder.GeneralClasses.NetworkHandler;
import com.example.personfinder.GeneralClasses.PreferencesHandler;
import com.example.personfinder.Networking.RetrofitClientInstance;
import com.example.personfinder.R;
import com.example.personfinder.databinding.FragmentCreatePostBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.hbb20.CountryCodePicker;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostFragment extends Fragment {

    private static CreatePostFragment instance;
    FragmentCreatePostBinding binding;
    View view;
    boolean isCellValid;
    ProgressDialog progressDialog;
    String[] genderArr = {"Select Gender", "Male", "Female", "Other"};
    String[] personsStatusArr = {"--Select--", "Found", "Missing", "Mentally Disturbed", "Dead"};
    final Calendar c = Calendar.getInstance();
    final Calendar myCalendar = Calendar.getInstance();
    int yearDb, dayDb, monthDb, yearNowDb, dayNowDb, monthNowDb;
    String from = "";
    boolean isMissing, isFounded, isMentallyDisturb, isDeath;
    private PreferencesHandler preferencesHandler;

    public CreatePostFragment() {
        instance = this;
    }

    public static CreatePostFragment getInstance() {
        return instance;
    }


    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false);
        init();


        binding.cellPhoneCcp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                binding.cellValidity.setVisibility(View.GONE);
            }
        });


        binding.cellPhoneCcp.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                isCellValid = isValidNumber;

                if (TextUtils.isEmpty(binding.cellPhone.getText().toString()))
                    binding.cellValidity.setVisibility(View.GONE);
                else
                    binding.cellValidity.setVisibility(View.VISIBLE);

                if (isValidNumber)
                    Objects.requireNonNull(binding.cellValidity).setImageResource(R.drawable.check);
                else
                    Objects.requireNonNull(binding.cellValidity).setImageResource(R.drawable.cancel);
            }
        });


        binding.cellPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.cellPhone.getText().toString().matches("^0")) {
//                    if (isFirstTime)
                    binding.cellPhone.setText("");
//                    isFirstTime = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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

        DatePickerDialog.OnDateSetListener date = (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateLabel();
        };

        binding.dob.setOnClickListener(view -> {
            Calendar selectedDate = Calendar.getInstance();
            if (binding.dob.getText().toString().equalsIgnoreCase("") || !from.equalsIgnoreCase("edit")) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DialogTheme2, date,
                        selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

            } else if (from.equalsIgnoreCase("edit")) {
                DatePickerDialog.OnDateSetListener date2 = (datePicker, year, monthOfYear, dayOfMonth) -> {
                    c.set(Calendar.YEAR, year);
                    c.set(Calendar.MONTH, monthOfYear);
                    c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDateLabel();

                    yearDb = year;
                    monthDb = monthOfYear;
                    dayDb = dayOfMonth;
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DialogTheme2, date2, yearDb, monthDb - 1, dayDb);
                datePickerDialog.show();
            }
        });


        binding.createPost.setOnClickListener(v -> {
            checkEmptyFields();

            if (binding.firstName.getText().toString().equalsIgnoreCase(""))
                Global.showSnackbar(getContext(), binding.container, getString(R.string.fname_error));

            else if (binding.fathersName.getText().toString().equalsIgnoreCase(""))
                Global.showSnackbar(getContext(), binding.container, getString(R.string.faname_error));

            else if (binding.dob.getText().toString().equalsIgnoreCase(""))
                Global.showSnackbar(getContext(), binding.container, getString(R.string.dob_error));

            else if (binding.cnic.getText().toString().equalsIgnoreCase(""))
                Global.showSnackbar(getContext(), binding.container, getString(R.string.cnic_error));

            else if (binding.personAge.getText().toString().equalsIgnoreCase(""))
                Global.showSnackbar(getContext(), binding.container, getString(R.string.person_age_error));

            else if (binding.personHeight.getText().toString().equalsIgnoreCase(""))
                Global.showSnackbar(getContext(), binding.container, getString(R.string.person_height_error));

            else if (!Global.isValidEmail(binding.email.getText().toString()))
                Global.showSnackbar(getContext(), binding.container, getString(R.string.valid_email_error));


            else if (binding.address.getText().toString().equalsIgnoreCase(""))
                Global.showSnackbar(getContext(), binding.container, getString(R.string.address_error));

            else if (binding.genderSpinner.getSelectedItem().toString().equalsIgnoreCase("Select Gender"))
                Global.showSnackbar(getContext(), binding.container, getString(R.string.gender_error));

            else if (binding.personStatusSpinner.getSelectedItem().toString().equalsIgnoreCase("--Select--"))
                Global.showSnackbar(getContext(), binding.container, getString(R.string.person_status_error));


            else if (binding.desc.getText().toString().equalsIgnoreCase(""))
                Global.showSnackbar(getContext(), binding.container, getString(R.string.desc_error));

            else if (TextUtils.isEmpty(binding.cellPhone.getText().toString()) && !isCellValid)
                Global.showSnackbar(getContext(), binding.container, getString(R.string.cell_invalid));
            else if (binding.landline.getText().toString().equalsIgnoreCase(""))
                Global.showSnackbar(getContext(), binding.container, getString(R.string.landline_error));

            else {
                if (NetworkHandler.isOnline()) {
                    if (!progressDialog.isShowing())
                        progressDialog.show();
                    submitPost();
                } else
                    Snackbar.make(binding.container, getString(R.string.no_internet), Snackbar.LENGTH_SHORT).show();
            }
        });


        return binding.getRoot();
    }

    private void init() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.pleaseWait));
        progressDialog.setCancelable(false);
        preferencesHandler = new PreferencesHandler(getContext());

        binding.cellPhoneCcp.registerCarrierNumberEditText(binding.cellPhone);
        binding.cellPhoneCcp.setDefaultCountryUsingNameCode("PK");
        binding.cellPhoneCcp.resetToDefaultCountry();


        ArrayAdapter<String> genderArray = new ArrayAdapter<String>(requireContext(), R.layout.spinner_item, genderArr);
        binding.genderSpinner.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.spinner_bg, null));
        binding.genderSpinner.setAdapter(genderArray);

        ArrayAdapter<String> personStatusArray = new ArrayAdapter<String>(requireContext(), R.layout.spinner_item, personsStatusArr);
        binding.personStatusSpinner.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.spinner_bg, null));
        binding.personStatusSpinner.setAdapter(personStatusArray);
    }

    private void updateDateLabel() {
        String myFormat = "MMM dd, yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (!from.equalsIgnoreCase("edit"))
            binding.dob.setText(sdf.format(myCalendar.getTime()));
        else
            binding.dob.setText(sdf.format(c.getTime()));
    }

    private void checkEmptyFields() {
        checkString(binding.firstName.getText().toString(), binding.firstName);
        checkString(binding.fathersName.getText().toString(), binding.fathersName);

        if (TextUtils.isEmpty(binding.dob.getText().toString()))
            binding.dob.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.error_button_reshaping, null));
        else
            binding.dob.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_reshaping, null));


        checkString(binding.cnic.getText().toString(), binding.cnic);
        checkString(binding.personAge.getText().toString(), binding.personAge);
        checkString(binding.personHeight.getText().toString(), binding.personHeight);
        checkString(binding.email.getText().toString(), binding.email);
        checkString(binding.address.getText().toString(), binding.address);

        if (binding.genderSpinner.getSelectedItem().equals("Select Gender"))
            binding.genderSpinner.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.error_button_reshaping, null));
        else
            binding.genderSpinner.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.spinner_bg, null));


        if (binding.personStatusSpinner.getSelectedItem().equals("--Select--"))
            binding.personStatusSpinner.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.error_button_reshaping, null));
        else
            binding.personStatusSpinner.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.spinner_bg, null));


        checkString(binding.desc.getText().toString(), binding.desc);


        if (TextUtils.isEmpty(binding.cellPhone.getText().toString()) && !isCellValid)
            binding.cellPhone.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.error_button_reshaping, null));
        else
            binding.cellPhone.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_reshaping, null));

        checkString(binding.landline.getText().toString(), binding.landline);

    }

    private void checkString(String s, EditText editText) {
        if (s.equalsIgnoreCase(""))
            editText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.error_button_reshaping, null));
        else
            editText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_reshaping, null));
    }

    public void setBooleansFalse() {
        isMissing = false;
        isFounded = false;
        isMentallyDisturb = false;
        isDeath = false;
    }


    private void submitPost() {

        Call<JsonObject> call = RetrofitClientInstance.getInstance().getApi().createPost(
                "0",
                Global.parseDateToAPIFormat(binding.dob.getText().toString()),
                binding.firstName.getText().toString(),
                binding.fathersName.getText().toString(),
                binding.cnic.getText().toString(),
                binding.personAge.getText().toString(),
                binding.personHeight.getText().toString(),
                binding.genderSpinner.getSelectedItem().toString(),
                binding.cellPhoneCcp.getFullNumberWithPlus(),
                binding.landline.getText().toString(),
                binding.email.getText().toString(),
                binding.desc.getText().toString(),
                binding.address.getText().toString(),
                isMissing,
                isFounded,
                isMentallyDisturb,
                isDeath,
                //TODO change 123 to getAPIToken when login is returning right token123 123
                //                String.valueOf(preferencesHandler.getApiToken()),
                "123",
                String.valueOf(preferencesHandler.getUserid())

        );
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().get("StatusCode").getAsString().equalsIgnoreCase("101")) {
                            Toast.makeText(getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
//                            Navigation.findNavController(binding.profileContainer).navigate(R.id.nav_home);
                        }
                    }
                } else
                    Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
