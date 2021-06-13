package com.example.personfinder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personfinder.GeneralClasses.Global;
import com.example.personfinder.Model.LoginResponse;
import com.example.personfinder.Networking.ApiInterface;
import com.example.personfinder.Networking.RetrofitClientInstance;
import com.kaopiz.kprogresshud.KProgressHUD;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

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
    private ApiInterface service;
    private String txt_user_name_et_txt;
    private String txt_user_id_et_txt;
    private String txt_password_et_txt;
    private String txt_re_password_et_et;
    private String txt_dob_et_et;
    private String txt_mob_no_et_et;
    private String txt_user_address_et_et;
    private String txt_gender_et_et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_content_sign_up);
        initViews();
    }

    private void initViews(){
        user_name_et = findViewById(R.id.user_name_et);
        user_id_et = findViewById(R.id.user_id_et);
        password_et = findViewById(R.id.password_et);
        re_password_et = findViewById(R.id.re_password_et);
        dob_et = findViewById(R.id.dob_et);
        mob_no_et = findViewById(R.id.mob_no_et);
        user_address_et = findViewById(R.id.user_address_et);
        gender_et = findViewById(R.id.gender_et);
        sign_up_btn = findViewById(R.id.sign_up_btn);
        sign_up_btn.setOnClickListener(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
    }

    private void register() {
        Global.mKProgressHUD = KProgressHUD.create(SignUpActivity.this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.7f).setAnimationSpeed(2).setLabel("Please Wait").setCancellable(true);
        Global.mKProgressHUD.show();
        Call<LoginResponse> call = service.register(user_name_et.getText().toString(),
                user_id_et.getText().toString(),password_et.getText().toString(),
                "2000-01-01T00:00:00",mob_no_et.getText().toString(),user_address_et.getText().toString(),
                gender_et.getText().toString());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Global.mKProgressHUD.dismiss();
                if(response.body().getStatus()){
                    Toast.makeText(SignUpActivity.this, "success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SignUpActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Global.mKProgressHUD.dismiss();
                Toast.makeText(SignUpActivity.this, "Something went wrong...Please try later!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean fields_validation_login() {
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
        }else if (txt_re_password_et_et.isEmpty()) {
            Toast.makeText(SignUpActivity.this, R.string.rd_repeat_pass, Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (!txt_password_et_txt.equals(txt_re_password_et_et)) {
            Toast.makeText(SignUpActivity.this, R.string.rd_passwords_not_match, Toast.LENGTH_SHORT).show();
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
                if(fields_validation_login()){
                    }
                    if (Global.CheckInternetConnectivity(SignUpActivity.this)){
                        register();
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}