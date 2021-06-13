package com.example.personfinder.ui.person_listing.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.personfinder.GeneralClasses.Global;
import com.example.personfinder.Networking.ApiInterface;
import com.example.personfinder.Networking.RetrofitClientInstance;
import com.example.personfinder.R;
import com.example.personfinder.databinding.FragmentPersonListingBinding;
import com.example.personfinder.ui.person_listing.model.PersonComplaintData;
import com.example.personfinder.ui.person_listing.model.PersonComplaintListingRoot;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonListingFragment extends Fragment {

    private static PersonListingFragment instance;
    FragmentPersonListingBinding binding;
    View view;
    String[] personsStatusArr = {"--Select--", "Found", "Not Found", "Missing", "Dead"};
    List<PersonComplaintData> personComplaintData;


    public PersonListingFragment() {
        instance = this;
    }

    public static PersonListingFragment getInstance() {
        return instance;
    }

    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentPersonListingBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    public void init() {
        personComplaintData = new ArrayList<>();
        ArrayAdapter<String> isActiveAdapter = new ArrayAdapter<String>(requireContext(), R.layout.spinner_item, personsStatusArr);
        binding.isActive.setAdapter(isActiveAdapter);


        getComplaints();
    }

    public void getComplaints() {
        Global.mKProgressHUD = KProgressHUD.create(getContext()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.7f).setAnimationSpeed(2).setLabel("Please Wait").setCancellable(true);
        Global.mKProgressHUD.show();

        Call<PersonComplaintListingRoot> call = RetrofitClientInstance.getInstance().getApi().getComplaints("123");
        call.enqueue(new Callback<PersonComplaintListingRoot>() {
            @Override
            public void onResponse(Call<PersonComplaintListingRoot> call, Response<PersonComplaintListingRoot> response) {
                Global.mKProgressHUD.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        PersonComplaintListingRoot root = response.body();
                        if (root.getComplaintList() != null) {
                            if (root.getComplaintList().size() > 0) {
                                personComplaintData = root.getComplaintList();
                            }
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PersonComplaintListingRoot> call, Throwable t) {
                Global.mKProgressHUD.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
