package com.example.personfinder.ui.profile;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.personfinder.GeneralClasses.AsteriskPasswordTransformationMethod;
import com.example.personfinder.GeneralClasses.Global;
import com.example.personfinder.GeneralClasses.NetworkHandler;
import com.example.personfinder.Networking.RetrofitClientInstance;
import com.example.personfinder.R;
import com.example.personfinder.databinding.FragmentProfileBinding;
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

public class ProfileFragment extends Fragment {

    private static ProfileFragment instance;
    FragmentProfileBinding binding;
    boolean isCellValid, passToggle, confPassToggle;
    String[] genderArr = {"Select Gender", "Male", "Female", "Other"};
    String from = "";
    final Calendar c = Calendar.getInstance();
    final Calendar myCalendar = Calendar.getInstance();
    int yearDb, dayDb, monthDb, yearNowDb, dayNowDb, monthNowDb;

    ProgressDialog progressDialog;

    public ProfileFragment() {
        instance = this;
    }

    public static ProfileFragment getInstance() {
        return instance;
    }

    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        init();
//        binding.confPasswordToggle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (confPassToggle) {
//                    binding.confPasswordToggle.setImageResource(R.drawable.ic_hide_pass);
//                    binding.confPass.setTransformationMethod(new AsteriskPasswordTransformationMethod());
//                } else {
//                    binding.confPasswordToggle.setImageResource(R.drawable.ic_show_pass);
//                    binding.confPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                }
//
//                String pass = binding.confPass.getText().toString();
//                binding.confPass.setText(pass);
//                binding.confPass.setSelection(pass.length());
//                confPassToggle = !confPassToggle;
//            }
//        });


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

        DatePickerDialog.OnDateSetListener date = (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateLabel();
        };

        binding.dueDate.setOnClickListener(view -> {
            if (binding.dueDate.getText().toString().equalsIgnoreCase("") || !from.equalsIgnoreCase("edit")) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DialogTheme2, date, yearNowDb, monthNowDb - 1, dayNowDb);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
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
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        binding.passwordToggle.setOnClickListener(view -> {
            if (passToggle) {
                binding.passwordToggle.setImageResource(R.drawable.ic_hide_pass);
                binding.pass.setTransformationMethod(new AsteriskPasswordTransformationMethod());
            } else {
                binding.passwordToggle.setImageResource(R.drawable.ic_show_pass);
                binding.pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }

            String pass = binding.pass.getText().toString();
            binding.pass.setText(pass);
            binding.pass.setSelection(pass.length());
            passToggle = !passToggle;
        });


        binding.updateProfile.setOnClickListener(v -> {
            checkEmptyFields();

            if (!TextUtils.isEmpty(binding.cellPhone.getText().toString()) && !isCellValid)
                Global.showSnackbar(getContext(), binding.profileContainer, getString(R.string.cell_invalid));

            else {
                if (binding.userName.getText().toString().equalsIgnoreCase(""))
                    Global.showSnackbar(getContext(), binding.profileContainer, getString(R.string.uname_error));

                else if (!Global.isValidEmail(binding.email.getText().toString()))
                    Global.showSnackbar(getContext(), binding.profileContainer, getString(R.string.valid_email_error));

                else if (binding.dueDate.getText().toString().equalsIgnoreCase(""))
                    Global.showSnackbar(getContext(), binding.profileContainer, getString(R.string.date_error));

                else if (binding.address.getText().toString().equalsIgnoreCase(""))
                    Global.showSnackbar(getContext(), binding.profileContainer, getString(R.string.address_error));

                else if (binding.pass.getText().toString().equalsIgnoreCase(""))
                    Global.showSnackbar(getContext(), binding.profileContainer, getString(R.string.pass_error));
                else {
                    if (NetworkHandler.isOnline()) {
                        if (!progressDialog.isShowing())
                            progressDialog.show();
                        submitResponse();
                    } else
                        Snackbar.make(binding.profileContainer, getString(R.string.no_internet), Snackbar.LENGTH_SHORT).show();
                }
            }
        });


        return binding.getRoot();
    }

    private void updateDateLabel() {
        String myFormat = "MMM dd, yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (!from.equalsIgnoreCase("edit"))
            binding.dueDate.setText(sdf.format(myCalendar.getTime()));
        else
            binding.dueDate.setText(sdf.format(c.getTime()));
    }


    private void submitResponse() {
        Call<JsonObject> call = RetrofitClientInstance.getInstance().getApi().upgradeAccount(binding.userName.getText().toString(),
                binding.email.getText().toString(),
                binding.pass.getText().toString(),
                Global.parseDateToAPIFormat(binding.dueDate.getText().toString()),
                binding.cellPhoneCcp.getFullNumberWithPlus(),
                binding.address.getText().toString(),
                binding.genderSpinner.getSelectedItem().toString(),
                "8",
                "123");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Global.mKProgressHUD.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().get("StatusCode").getAsString().equalsIgnoreCase("101")) {
                            Toast.makeText(getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
//                            Navigation.findNavController(binding.profileContainer).navigate(R.id.nav_home);
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Global.mKProgressHUD.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkEmptyFields() {
        checkString(binding.userName.getText().toString(), binding.userName);
        checkString(binding.email.getText().toString(), binding.email);

        if (!TextUtils.isEmpty(binding.cellPhone.getText().toString()) && !isCellValid)
            binding.cellPhone.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.error_button_reshaping, null));
        else
            binding.cellPhone.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_reshaping, null));


        if (TextUtils.isEmpty(binding.dueDate.getText().toString()))
            binding.dueDate.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.error_button_reshaping, null));
        else
            binding.dueDate.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_reshaping, null));


        checkString(binding.address.getText().toString(), binding.address);

        if (binding.genderSpinner.getSelectedItem().equals("Select Gender"))
            binding.genderSpinner.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.error_button_reshaping, null));
        else
            binding.genderSpinner.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.spinner_bg, null));


    }


    private void checkString(String s, EditText editText) {
        if (s.equalsIgnoreCase(""))
            editText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.error_button_reshaping, null));
        else
            editText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_reshaping, null));
    }


    public void init() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.pleaseWait));
        progressDialog.setCancelable(false);


        binding.cellPhoneCcp.registerCarrierNumberEditText(binding.cellPhone);
        binding.cellPhoneCcp.setDefaultCountryUsingNameCode("PK");
        binding.cellPhoneCcp.resetToDefaultCountry();


        ArrayAdapter<String> isActiveAdapter = new ArrayAdapter<String>(requireContext(), R.layout.spinner_item, genderArr);
        binding.genderSpinner.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.spinner_bg, null));
        binding.genderSpinner.setAdapter(isActiveAdapter);
    }
}
