package com.example.personfinder.ui.sign_in;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personfinder.GeneralClasses.Global;
import com.example.personfinder.GeneralClasses.PreferencesHandler;
import com.example.personfinder.Networking.ApiInterface;
import com.example.personfinder.Networking.RetrofitClientInstance;
import com.example.personfinder.databinding.ActivitySignInBinding;
import com.example.personfinder.ui.main_activity.MainActivity;
import com.example.personfinder.ui.sign_up.SignUpActivity;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = SignInActivity.class.getSimpleName();
    ActivitySignInBinding binding;
    private ApiInterface service;
    private String txt_username_txt;
    private String txt_password_txt;
    private Global global;
    private PreferencesHandler preferencesHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (fields_validation_login()) {
//                    if (Global.CheckInternetConnectivity(SignInActivity.this)) {
//                        login();
//                    }
//                }
            }
        });

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        binding.tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
            }
        });
    }

    private void init() {
        service = RetrofitClientInstance.getInstance().getApi();
    }

//    private void login() {
//        preferencesHandler = new PreferencesHandler(SignInActivity.this);
//        Global.mKProgressHUD = KProgressHUD.create(SignInActivity.this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.7f).setAnimationSpeed(2).setLabel("Please Wait").setCancellable(true);
//        Global.mKProgressHUD.show();
//
//        Call<LoginResponse> call = service.login(binding.userIdEt.getText().toString(),binding.passwordEt.getText().toString());
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                Global.mKProgressHUD.dismiss();
//                if(response.body().getStatus()){
//                    Log.d("signin","token : "+response.body().getApiToken());
//                    preferencesHandler.setLoginstatus(true);
//                    preferencesHandler.setApiToken(response.body().getApiToken());
//                    Toast.makeText(SignInActivity.this, "success", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(SignInActivity.this,MainActivity.class));
//                    finish();
//                }else{
//                    Toast.makeText(SignInActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Global.mKProgressHUD.dismiss();
//                Toast.makeText(SignInActivity.this, "Something went wrong...Please try later!" + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    public boolean fields_validation_login() {
//        boolean valid = true;
//
//        txt_username_txt = binding.userIdEt.getText().toString();
//        txt_password_txt = binding.passwordEt.getText().toString();
//
//        if(txt_username_txt.isEmpty() && txt_password_txt.isEmpty()){
//            Toast.makeText(SignInActivity.this, R.string.rd_email_pass, Toast.LENGTH_SHORT).show();
//            valid = false;
//        }else if (txt_username_txt.isEmpty()) {
//            Toast.makeText(SignInActivity.this, R.string.rd_email, Toast.LENGTH_SHORT).show();
//            valid = false;
//        }else if(!txt_username_txt.matches(global.emailPattern)){
//            Toast.makeText(SignInActivity.this, R.string.rd_email_incorrect, Toast.LENGTH_SHORT).show();
//            valid = false;
//        }else if (txt_password_txt.isEmpty()) {
//            Toast.makeText(SignInActivity.this, R.string.rd_password, Toast.LENGTH_SHORT).show();
//            valid = false;
//        }
//        return valid;
//    }

}