package com.example.personfinder.ui.profile;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.personfinder.GeneralClasses.AsteriskPasswordTransformationMethod;
import com.example.personfinder.R;
import com.example.personfinder.databinding.FragmentProfileBinding;

import org.jetbrains.annotations.NotNull;

public class ProfileFragment extends Fragment {

    private static ProfileFragment instance;
    FragmentProfileBinding binding;
    View view;
    boolean isFirstTime, isCellValid, isWhatsappValid, passToggle, confPassToggle;

    public ProfileFragment() {
        instance = this;
    }

    public static ProfileFragment getInstance() {
        return instance;
    }

    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        binding.confPasswordToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (confPassToggle) {
                    binding.confPasswordToggle.setImageResource(R.drawable.ic_hide_pass);
                    binding.confPass.setTransformationMethod(new AsteriskPasswordTransformationMethod());
                } else {
                    binding.confPasswordToggle.setImageResource(R.drawable.ic_show_pass);
                    binding.confPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

                String pass = binding.confPass.getText().toString();
                binding.confPass.setText(pass);
                binding.confPass.setSelection(pass.length());
                confPassToggle = !confPassToggle;
            }
        });


        binding.passwordToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });


        return binding.getRoot();
    }
}
