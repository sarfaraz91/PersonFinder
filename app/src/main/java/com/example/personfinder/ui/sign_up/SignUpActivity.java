package com.example.personfinder.ui.sign_up;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personfinder.Networking.ApiInterface;
import com.example.personfinder.Networking.RetrofitClientInstance;
import com.example.personfinder.databinding.ActivitySignUpBinding;
import com.example.personfinder.ui.sign_in.SignInActivity;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignInActivity.class.getSimpleName();
    ActivitySignUpBinding binding;
    private ApiInterface service;
    private String txt_email_txt;
    private String txt_password_txt;
    private String txt_re_password_txt;
    private String txt_user_name_et;
    private String txt_company_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();


        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (fields_validation_login()) {
//                    if (Global.CheckInternetConnectivity(SignUpActivity.this)) {
//                        register();
//                    }
//                }
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void init() {
        service = RetrofitClientInstance.getInstance().getApi();
    }

//    private void register() {
//        Global.mKProgressHUD = KProgressHUD.create(SignUpActivity.this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.7f).setAnimationSpeed(2).setLabel("Please Wait").setCancellable(true);
//        Global.mKProgressHUD.show();
//        Call<LoginResponse> call = service.register(binding.userIdEt.getText().toString(),
//                binding.passwordEt.getText().toString(),binding.userNameEt.getText().toString(),
//                binding.companyEt.getText().toString(),"Asia/Karachi");
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                Global.mKProgressHUD.dismiss();
//                if(response.body().getStatus()){
//                    Toast.makeText(SignUpActivity.this, "success", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(SignUpActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Global.mKProgressHUD.dismiss();
//                Toast.makeText(SignUpActivity.this, "Something went wrong...Please try later!" + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
////    }
//
//    public boolean fields_validation_login() {
//        boolean valid = true;
//
//        txt_email_txt = binding.userIdEt.getText().toString();
//        txt_password_txt = binding.passwordEt.getText().toString();
//        txt_re_password_txt = binding.rePasswordEt.getText().toString();
//        txt_user_name_et = binding.userNameEt.getText().toString();
//        txt_company_et = binding.companyEt.getText().toString();
//
//        if (txt_user_name_et.isEmpty()) {
//            Toast.makeText(SignUpActivity.this, R.string.rd_username, Toast.LENGTH_SHORT).show();
//            valid = false;
//        } else if (txt_email_txt.isEmpty()) {
//            Toast.makeText(SignUpActivity.this, R.string.rd_email, Toast.LENGTH_SHORT).show();
//            valid = false;
//        } else if (!txt_email_txt.matches(Global.emailPattern)) {
//            Toast.makeText(SignUpActivity.this, R.string.rd_email_incorrect, Toast.LENGTH_SHORT).show();
//            valid = false;
//        } else if (txt_password_txt.isEmpty()) {
//            Toast.makeText(SignUpActivity.this, R.string.rd_password, Toast.LENGTH_SHORT).show();
//            valid = false;
//        } else if (txt_re_password_txt.isEmpty()) {
//            Toast.makeText(SignUpActivity.this, R.string.rd_repeat_pass, Toast.LENGTH_SHORT).show();
//            valid = false;
//        } else if (!txt_password_txt.equals(txt_re_password_txt)) {
//            Toast.makeText(SignUpActivity.this, R.string.rd_passwords_not_match, Toast.LENGTH_SHORT).show();
//            valid = false;
//        } else if (txt_company_et.isEmpty()) {
//            Toast.makeText(SignUpActivity.this, R.string.rd_company, Toast.LENGTH_SHORT).show();
//            valid = false;
//        }
//        return valid;
//    }
}