package com.example.personfinder.ui.person_listing.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.personfinder.GeneralClasses.NetworkHandler;
import com.example.personfinder.Networking.RetrofitClientInstance;
import com.example.personfinder.R;
import com.example.personfinder.databinding.FragmentPersonListingBinding;
import com.example.personfinder.ui.person_listing.AddPostInformationActivity;
import com.example.personfinder.ui.person_listing.adapter.PersonsListAdapter;
import com.example.personfinder.ui.person_listing.model.PersonComplaintData;
import com.example.personfinder.ui.person_listing.model.PersonComplaintListingRoot;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonListingFragment extends Fragment {

    private static PersonListingFragment instance;
    FragmentPersonListingBinding binding;
    String[] personsStatusArr = {"Found", "Missing", "Mentally Disturbed", "Dead"};
    List<PersonComplaintData> personComplaintData;
    public PersonsListAdapter personsListAdapter;
    ProgressDialog progressDialog;
    boolean isMissing;
    boolean isFounded;
    boolean isMentallyDisturb;
    boolean isDeath;


    public PersonListingFragment() {
        instance = this;
    }

    public static PersonListingFragment getInstance() {
        return instance;
    }

    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentPersonListingBinding.inflate(inflater, container, false);
        init();

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkHandler.isOnline()) {
                    if (!progressDialog.isShowing())
                        progressDialog.show();
                    binding.swipeRefresh.setRefreshing(false);
                    getComplaints();
                } else
                    Snackbar.make(binding.container, getString(R.string.no_internet), Snackbar.LENGTH_SHORT).show();


            }
        });


        binding.search.setOnEditorActionListener((v, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE) || actionId == 5) {
                if (NetworkHandler.isOnline()) {
                    if (!progressDialog.isShowing())
                        progressDialog.show();
                    binding.swipeRefresh.setRefreshing(false);
                    getComplaints();
                } else
                    Snackbar.make(binding.container, getString(R.string.no_internet), Snackbar.LENGTH_SHORT).show();

            }
            return false;
        });


        binding.personStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setBooleansFalse();
                switch (personsStatusArr[position]) {
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

                if (NetworkHandler.isOnline()) {
                    if (!progressDialog.isShowing())
                        progressDialog.show();
                    binding.swipeRefresh.setRefreshing(false);
                    getComplaints();
                } else
                    Snackbar.make(binding.container, getString(R.string.no_internet), Snackbar.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setBooleansFalse();

            }
        });
        return binding.getRoot();
    }


    public void setBooleansFalse() {
        isMissing = false;
        isFounded = false;
        isMentallyDisturb = false;
        isDeath = false;
    }


    public void init() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.pleaseWait));
        progressDialog.setCancelable(false);


        personComplaintData = new ArrayList<>();
        ArrayAdapter<String> isActiveAdapter = new ArrayAdapter<String>(requireContext(), R.layout.spinner_item, personsStatusArr);
        binding.personStatusSpinner.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.spinner_bg, null));
        binding.personStatusSpinner.setAdapter(isActiveAdapter);

        if (NetworkHandler.isOnline()) {
            if (!progressDialog.isShowing())
                progressDialog.show();
            isFounded = true;
            getComplaints();
        } else
            Snackbar.make(binding.container, getString(R.string.no_internet), Snackbar.LENGTH_SHORT).show();
    }

    public void setPersonsData(List<PersonComplaintData> personComplaintData) {
        if (personComplaintData != null) {
            if (personComplaintData.size() > 0) {
                binding.noRecords.noRecordLayout.setVisibility(View.GONE);
                binding.personsList.setVisibility(View.VISIBLE);

                personsListAdapter = new PersonsListAdapter(getActivity(), getContext(), personComplaintData);
                binding.personsList.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.personsList.setAdapter(personsListAdapter);
                personsListAdapter.notifyDataSetChanged();

                personsListAdapter.setOnItemClickListener(new PersonsListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        PersonComplaintData data = personComplaintData.get(position);
                        Intent intent = new Intent(getContext(), AddPostInformationActivity.class);
                        intent.putExtra("data", data);
                        startActivity(intent);
                    }
                });
            } else showNoData();
        } else showNoData();
    }

    public void showNoData() {
        binding.personsList.setVisibility(View.GONE);
        binding.noRecords.noRecordLayout.setVisibility(View.VISIBLE);
    }


    public void getComplaints() {
        String search = "";
        if (!binding.search.getText().toString().isEmpty())
            search = binding.search.getText().toString();

        Map<String, Object> data = new HashMap<>();
        data.put("filter", search);
        data.put("IsMissing", String.valueOf(isMissing));
        data.put("IsFounded", String.valueOf(isFounded));
        data.put("IsMentallyDisturb", String.valueOf(isMentallyDisturb));
        data.put("IsDeath", String.valueOf(isDeath));
        data.put("token", "123");
        data.put("UserId", "20");


        Call<PersonComplaintListingRoot> call = RetrofitClientInstance.getInstance().getApi().getComplaints(data);
        call.enqueue(new Callback<PersonComplaintListingRoot>() {
            @Override
            public void onResponse(Call<PersonComplaintListingRoot> call, Response<PersonComplaintListingRoot> response) {
                Log.e("Hello", "Hello");
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            PersonComplaintListingRoot root = response.body();
                            personComplaintData = root.getComplaintList();
                            setPersonsData(personComplaintData);
                        }
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PersonComplaintListingRoot> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
