package com.example.now.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.now.Adapter.HomeAdapter;
import com.example.now.GeneralClasses.PreferencesHandler;
import com.example.now.Model.Offers;
import com.example.now.Networking.ApiInterface;
import com.example.now.Networking.RetrofitClientInstance;
import com.example.now.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostOfferActivity extends AppCompatActivity {

    private static final String TAG = PostOfferActivity.class.getSimpleName();
    private HomeAdapter homeAdapter;
    private List<Offers.Datum> offersList;
    private RecyclerView rv_home;
    ApiInterface service;
    PreferencesHandler preferencesHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();

    }

    private void initViews() {
        preferencesHandler = new PreferencesHandler();
        service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        rv_home = findViewById(R.id.rv_home);
        rv_home.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        offersList = new ArrayList<>();
        homeAdapter = new HomeAdapter(this, offersList);
        rv_home.setAdapter(homeAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllInterests();
    }

    private void getAllInterests() {
        Call<Offers> call = service.getOffers(preferencesHandler.getApiToken(),"published");
        call.enqueue(new Callback<Offers>() {
            @Override
            public void onResponse(Call<Offers> call, Response<Offers> response) {
                response.body();

                ArrayList<Offers.Datum> data = (ArrayList<Offers.Datum>) response.body().getData();
                for (int i = 0; i < data.size(); i++) {
                    offersList.add(data.get(i));
                }
                homeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Offers> call, Throwable t) {
                Toast.makeText(PostOfferActivity.this, "Something went wrong...Please try later!" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}