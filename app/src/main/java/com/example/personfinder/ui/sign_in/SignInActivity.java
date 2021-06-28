package com.example.personfinder.ui.sign_in;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personfinder.GeneralClasses.Global;
import com.example.personfinder.GeneralClasses.PreferencesHandler;
import com.example.personfinder.Model.LoginResponse;
import com.example.personfinder.Model.User;
import com.example.personfinder.Networking.ApiInterface;
import com.example.personfinder.Networking.RetrofitClientInstance;
import com.example.personfinder.R;
import com.example.personfinder.ui.main_activity.MainActivity;
import com.example.personfinder.ui.sign_up.SignUpActivity;
import com.kaopiz.kprogresshud.KProgressHUD;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SignInActivity.class.getSimpleName();
    private EditText user_id_et;
    private EditText password_et;
    private Button sign_in_btn;
    private ApiInterface service;
    private String txt_username_txt;
    private String txt_password_txt;
    private TextView register;
    private TextView forgot_password_tv;
    private Global global;
    private PreferencesHandler preferencesHandler;
    private TextView tv_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_content_sign_in);
        initViews();

    }

    private void initViews(){
        tv_skip = findViewById(R.id.tv_skip);
        tv_skip.setOnClickListener(this);
        user_id_et = findViewById(R.id.user_id_et);
        password_et = findViewById(R.id.password_et);
        sign_in_btn = findViewById(R.id.sign_in_btn);
        sign_in_btn.setOnClickListener(this);
        service = RetrofitClientInstance.getInstance().getApi();
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        forgot_password_tv = findViewById(R.id.register);
        forgot_password_tv.setOnClickListener(this);

    }

    private void login() {
        preferencesHandler = new PreferencesHandler(SignInActivity.this);
        Global.mKProgressHUD = KProgressHUD.create(SignInActivity.this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.7f).setAnimationSpeed(2).setLabel("Please Wait").setCancellable(true);
        Global.mKProgressHUD.show();

        Call<LoginResponse> call = service.login(user_id_et.getText().toString(),password_et.getText().toString(),"123");
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Global.mKProgressHUD.dismiss();
                if (response.body().getStatus() != null) {
                    if (response.body().getStatus()) {
                        LoginResponse loginResponse = response.body();
                        User user = loginResponse.getUser();
                        preferencesHandler.setLoginstatus(true);
                        preferencesHandler.setUserid(user.getUserId());
                        preferencesHandler.setApiToken(user.getToken());
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SignInActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Global.mKProgressHUD.dismiss();
                Toast.makeText(SignInActivity.this, "Something went wrong...Please try later!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean fields_validation_login() {
        boolean valid = true;

        txt_username_txt = user_id_et.getText().toString();
        txt_password_txt = password_et.getText().toString();

        if(txt_username_txt.isEmpty() && txt_password_txt.isEmpty()){
            Toast.makeText(SignInActivity.this, R.string.rd_email_pass, Toast.LENGTH_SHORT).show();
            valid = false;
        }else if (txt_username_txt.isEmpty()) {
            Toast.makeText(SignInActivity.this, R.string.rd_email, Toast.LENGTH_SHORT).show();
            valid = false;
        }
//        else if(!txt_username_txt.matches(global.emailPattern)){
//            Toast.makeText(SignInActivity.this, R.string.rd_email_incorrect, Toast.LENGTH_SHORT).show();
//            valid = false;
//        }
        else if (txt_password_txt.isEmpty()) {
            Toast.makeText(SignInActivity.this, R.string.rd_password, Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_btn:
                if(fields_validation_login()){
                    if (Global.CheckInternetConnectivity(SignInActivity.this)){
                        login();
                    }
                }
                break;
            case R.id.register:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.tv_skip:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}