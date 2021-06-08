package com.example.now.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.now.Adapter.HomeAdapter;
import com.example.now.Adapter.OffersAdapter;
import com.example.now.GeneralClasses.Global;
import com.example.now.GeneralClasses.PreferencesHandler;
import com.example.now.Model.Offers;
import com.example.now.Networking.ApiInterface;
import com.example.now.Networking.RetrofitClientInstance;
import com.example.now.R;
import com.example.now.UI.Fragments.UploadImagesFragment;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOffersActivity extends AppCompatActivity implements View.OnClickListener, FragmentManager.OnBackStackChangedListener {

    private LinearLayout back_button;
    ApiInterface service;
    private Button btn_draft;
    private Button btn_scheduled;
    private Button btn_published;
    private ImageView img_add;

    private OffersAdapter offersAdapter;
    private List<Offers.Datum> offersList;
    private RecyclerView rv_offers;
    public FrameLayout fragment;
    PreferencesHandler preferencesHandler;
    private TextView tv_no_record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offers);
        initViews();

        getSupportFragmentManager().addOnBackStackChangedListener(this) ;

    }

    @Override
    public void onBackStackChanged() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if(backStackCount == 0) {
            getAllOffers("draft");
        } //back to home screen
    }

    private void initViews() {
        tv_no_record = findViewById(R.id.tv_no_record);
        preferencesHandler = new PreferencesHandler();
        fragment = findViewById(R.id.fragment);
        img_add = findViewById(R.id.img_add);
        img_add.setOnClickListener(this);
        btn_draft = findViewById(R.id.btn_draft);
        btn_draft.setOnClickListener(this);
        btn_scheduled = findViewById(R.id.btn_scheduled);
        btn_scheduled.setOnClickListener(this);
        btn_published = findViewById(R.id.btn_published);
        btn_published.setOnClickListener(this);
        service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        rv_offers = findViewById(R.id.rv_offers);
        rv_offers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        offersList = new ArrayList<>();
        offersAdapter = new OffersAdapter(this, offersList,preferencesHandler);
        rv_offers.setAdapter(offersAdapter);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.back_button:
                finish();
                break;
            case R.id.btn_draft:
                changeColor(0);
                offersList = new ArrayList<>();
                offersAdapter = new OffersAdapter(this, offersList,preferencesHandler);
                rv_offers.setAdapter(offersAdapter);
                getAllOffers("draft");
                break;
            case R.id.btn_scheduled:
                offersList = new ArrayList<>();
                offersAdapter = new OffersAdapter(this, offersList,preferencesHandler);
                rv_offers.setAdapter(offersAdapter);
                getAllOffers("scheduled");
                changeColor(1);
                break;
            case R.id.btn_published:
                offersList = new ArrayList<>();
                offersAdapter = new OffersAdapter(this, offersList,preferencesHandler);
                rv_offers.setAdapter(offersAdapter);
                getAllOffers("published");
                changeColor(2);
                break;
            case R.id.img_add:
                gotoUploadImagesFragment();
                break;
        }

    }

    private void gotoUploadImagesFragment() {
        UploadImagesFragment uploadImagesFragment = new UploadImagesFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment,uploadImagesFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void changeColor(int i) {

        switch (i){
            case 0:
                btn_draft.setTextColor(getColor(R.color.colorWhite));
                btn_draft.setBackgroundColor(getColor(R.color.colorRed));

                btn_scheduled.setTextColor(getColor(R.color.colorBlack));
                btn_scheduled.setBackgroundColor(getColor(R.color.colorTransparent));

                btn_published.setTextColor(getColor(R.color.colorBlack));
                btn_published.setBackgroundColor(getColor(R.color.colorTransparent));
                break;

            case 1:
                btn_scheduled.setTextColor(getColor(R.color.colorWhite));
                btn_scheduled.setBackgroundColor(getColor(R.color.colorRed));

                btn_draft.setTextColor(getColor(R.color.colorBlack));
                btn_draft.setBackgroundColor(getColor(R.color.colorTransparent));

                btn_published.setTextColor(getColor(R.color.colorBlack));
                btn_published.setBackgroundColor(getColor(R.color.colorTransparent));
                break;

            case 2:
                btn_published.setTextColor(getColor(R.color.colorWhite));
                btn_published.setBackgroundColor(getColor(R.color.colorRed));

                btn_scheduled.setTextColor(getColor(R.color.colorBlack));
                btn_scheduled.setBackgroundColor(getColor(R.color.colorTransparent));

                btn_draft.setTextColor(getColor(R.color.colorBlack));
                btn_draft.setBackgroundColor(getColor(R.color.colorTransparent));
                break;

        }
    }

    private void getAllOffers(String type) {
        offersList.clear();
        Global.mKProgressHUD = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.7f).setAnimationSpeed(2).setLabel("Please Wait").setCancellable(true);
        Global.mKProgressHUD.show();
        Call<Offers> call = service.getOffers(preferencesHandler.getApiToken(),type);
        call.enqueue(new Callback<Offers>() {
            @Override
            public void onResponse(Call<Offers> call, Response<Offers> response) {
                Global.mKProgressHUD.dismiss();
                response.body();

                ArrayList<Offers.Datum> data = (ArrayList<Offers.Datum>) response.body().getData();
                Collections.sort(data, new StringDateComparator());

                if(data.size() > 0){
                    tv_no_record.setVisibility(View.GONE);
                    for (int i = 0; i < data.size(); i++) {
                        offersList.add(data.get(i));
                    }
                    offersAdapter.notifyDataSetChanged();
                }else{
                    tv_no_record.setVisibility(View.VISIBLE);
                    offersAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<Offers> call, Throwable t) {
                Global.mKProgressHUD.dismiss();
                Toast.makeText(MyOffersActivity.this, "Something went wrong...Please try later!" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        offersList.clear();
        getAllOffers("draft");
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            if(count == 1){
                if(fragment.getVisibility() == View.VISIBLE){
                    fragment.setVisibility(View.GONE);
                }
            }
            getSupportFragmentManager().popBackStack();
        }

    }

    class StringDateComparator implements Comparator<Offers.Datum>
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        @Override
        public int compare(Offers.Datum o1, Offers.Datum o2) {
            try {
                return dateFormat.parse(o1.getCreatedAt()).compareTo(dateFormat.parse(o2.getCreatedAt()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

}