package com.example.now.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.now.Adapter.HomeAdapter;
import com.example.now.GeneralClasses.PreferencesHandler;
import com.example.now.Model.Offers;
import com.example.now.Networking.ApiInterface;
import com.example.now.Networking.RetrofitClientInstance;
import com.example.now.R;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private HomeAdapter homeAdapter;
    private List<Offers.Datum> offersList;
    private RecyclerView rv_home;
    ApiInterface service;
    private PreferencesHandler preferencesHandler;
    private TextView tv_no_record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        initViews();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_post_an_offer);
        navigationView.setNavigationItemSelectedListener(this);


        Menu nav_Menu = navigationView.getMenu();

        if(preferencesHandler.getLoginstatus() == true){
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
        }else{
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            nav_Menu.findItem(R.id.nav_my_offers).setVisible(false);
        }


    }

    private void initViews() {
        tv_no_record = findViewById(R.id.tv_no_record);
        preferencesHandler = new PreferencesHandler(MainActivity.this);
        service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        rv_home = findViewById(R.id.rv_home);
        rv_home.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        offersList = new ArrayList<>();
        homeAdapter = new HomeAdapter(this, offersList);
        rv_home.setAdapter(homeAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == R.id.nav_post_an_offer) {

        } else if (id == R.id.nav_my_interests) {
            Intent intent = new Intent(this, InterestsActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_my_offers){
            Intent intent = new Intent(this, MyOffersActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_logout){
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            preferencesHandler.setLoginstatus(false);
            finish();
        }else if(id == R.id.nav_login){
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getAllInterests() {
        Call<Offers> call = service.getAllOffers(preferencesHandler.getApiToken(),"3,2","26.282958453807595","50.21213367109322");
        call.enqueue(new Callback<Offers>() {
            @Override
            public void onResponse(Call<Offers> call, Response<Offers> response) {
                response.body();

                ArrayList<Offers.Datum> data = (ArrayList<Offers.Datum>) response.body().getData();
              //  Collections.sort(data, new StringDateComparator());

                Collections.sort(data, new Comparator<Offers.Datum>() {
                    public int compare(Offers.Datum o1, Offers.Datum o2) {
                        return o1.getCreatedAt().compareTo(o2.getCreatedAt());
                    }
                });

                if(data.size() > 0){
                    tv_no_record.setVisibility(View.GONE);
                    for (int i = 0; i < data.size(); i++) {
                        offersList.add(data.get(i));
                    }
                    homeAdapter.notifyDataSetChanged();
                }else{
                    tv_no_record.setVisibility(View.VISIBLE);
                    homeAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<Offers> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        offersList.clear();
        getAllInterests();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        finish();
    }



}