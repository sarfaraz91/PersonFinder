package com.example.personfinder.ui.sign_up;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personfinder.GeneralClasses.Global;
import com.example.personfinder.Model.LoginResponse;
import com.example.personfinder.Networking.ApiInterface;
import com.example.personfinder.Networking.RetrofitClientInstance;
import com.example.personfinder.R;
import com.example.personfinder.databinding.ActivitySignUpBinding;
import com.example.personfinder.ui.sign_in.SignInActivity;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = SignInActivity.class.getSimpleName();
    private EditText user_name_et;
    private EditText user_id_et;
    private EditText password_et;
    private EditText re_password_et;
    private EditText dob_et;
    private EditText mob_no_et;
    private EditText user_address_et;
    private EditText gender_et;
    private Button sign_up_btn;
    private ImageView back;
    ActivitySignUpBinding binding;
    private ApiInterface service;
    private String txt_user_name_et_txt;
    private String txt_user_id_et_txt;
    private String txt_password_et_txt;
    private String txt_re_password_et_et;
    private String txt_dob_et_et;
    private String txt_mob_no_et_et;
    private String txt_user_address_et_et;
    private String txt_gender_et_et;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Calendar myCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_content_sign_up);
        initViews();
    }

    private void initViews() {
        user_name_et = findViewById(R.id.user_name_et);
        user_id_et = findViewById(R.id.user_id_et);
        password_et = findViewById(R.id.password_et);
        re_password_et = findViewById(R.id.re_password_et);
        dob_et = findViewById(R.id.dob_et);
        dob_et.setOnClickListener(this);
        mob_no_et = findViewById(R.id.mob_no_et);
        user_address_et = findViewById(R.id.user_address_et);
        gender_et = findViewById(R.id.gender_et);
        gender_et.setOnClickListener(this);
        sign_up_btn = findViewById(R.id.sign_up_btn);
        sign_up_btn.setOnClickListener(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        service = RetrofitClientInstance.getInstance().getApi();
        myCalendar = Calendar.getInstance();

    }


    private void register() {
        Global.mKProgressHUD = KProgressHUD.create(SignUpActivity.this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.7f).setAnimationSpeed(2).setLabel("Please Wait").setCancellable(true);
        Global.mKProgressHUD.show();
        Call<LoginResponse> call = service.register(user_name_et.getText().toString(),
                user_id_et.getText().toString(),password_et.getText().toString(),
                dob_et.getText().toString()+"T00:00:00",mob_no_et.getText().toString(),user_address_et.getText().toString(),
                gender_et.getText().toString());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Global.mKProgressHUD.dismiss();
                if(response.body().getStatus() != null){
                    if(response.body().getStatus()){
                        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                        finish();
                    }else{
                        Toast.makeText(SignUpActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Global.mKProgressHUD.dismiss();
                Toast.makeText(SignUpActivity.this, "Something went wrong...Please try later!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean fields_validation_register() {
        boolean valid = true;

        txt_user_name_et_txt = user_name_et.getText().toString();
        txt_user_id_et_txt = user_id_et.getText().toString();
        txt_password_et_txt = password_et.getText().toString();
        txt_re_password_et_et = re_password_et.getText().toString();
        txt_dob_et_et = dob_et.getText().toString();
        txt_mob_no_et_et = mob_no_et.getText().toString();
        txt_user_address_et_et = user_address_et.getText().toString();
        txt_gender_et_et = gender_et.getText().toString();

        if(txt_user_name_et_txt.isEmpty()){
            Toast.makeText(SignUpActivity.this, R.string.rd_username, Toast.LENGTH_SHORT).show();
            valid = false;
        }else if (txt_user_id_et_txt.isEmpty()) {
            Toast.makeText(SignUpActivity.this, R.string.rd_email, Toast.LENGTH_SHORT).show();
            valid = false;
        }else if(!txt_user_id_et_txt.matches(Global.emailPattern)){
            Toast.makeText(SignUpActivity.this, R.string.rd_email_incorrect, Toast.LENGTH_SHORT).show();
            valid = false;
        }else if (txt_password_et_txt.isEmpty()) {
            Toast.makeText(SignUpActivity.this, R.string.rd_password, Toast.LENGTH_SHORT).show();
            valid = false;
        }else if (txt_password_et_txt.length() < 6) {
            Toast.makeText(SignUpActivity.this, R.string.rd_password_length, Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (txt_re_password_et_et.isEmpty()) {
            Toast.makeText(SignUpActivity.this, R.string.rd_repeat_pass, Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (!txt_password_et_txt.equals(txt_re_password_et_et)) {
            Toast.makeText(SignUpActivity.this, R.string.rd_passwords_not_match, Toast.LENGTH_SHORT).show();
            valid = false;
        }else if(txt_dob_et_et.isEmpty()){
            Toast.makeText(SignUpActivity.this, R.string.rd_dob, Toast.LENGTH_SHORT).show();
            valid = false;
        }else if(txt_mob_no_et_et.isEmpty()){
            Toast.makeText(SignUpActivity.this, R.string.rd_mob, Toast.LENGTH_SHORT).show();
            valid = false;
        }else if(txt_user_address_et_et.isEmpty()){
            Toast.makeText(SignUpActivity.this, R.string.rd_address, Toast.LENGTH_SHORT).show();
            valid = false;
        }else if(txt_gender_et_et.isEmpty()){
            Toast.makeText(SignUpActivity.this, R.string.rd_gender, Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_btn:
                if(fields_validation_register()) {
                    if (Global.CheckInternetConnectivity(SignUpActivity.this)) {
                        register();
                    }
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.dob_et:
                setDob();
                break;
            case R.id.gender_et:
                selectGender();
                break;
        }
    }

    private void selectGender() {
        final CharSequence[] gender = {"Male","Female"};
        AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this,R.style.datepicker);
        alert.setTitle("Select Gender");
        alert.setSingleChoiceItems(gender,-1, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                final Handler handler = new Handler(Looper.getMainLooper());
                if(gender[which]=="Male")
                {
                    gender_et.setText("Male");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    }, 500);
                }
                else if (gender[which]=="Female")
                {
                    gender_et.setText("Female");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    }, 500);
                }
            }
        });
        alert.show();
    }

    private void setDob() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this,R.style.datepicker,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dob_et.setText(year + "-"  + (monthOfYear + 1) + "-" + dayOfMonth);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}