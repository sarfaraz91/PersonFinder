package com.example.now.UI.Fragments.Marketer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.SimpleResource;
import com.example.now.Adapter.TotalUsersAdapter;
import com.example.now.GeneralClasses.Global;
import com.example.now.GeneralClasses.PreferencesHandler;
import com.example.now.Model.TotalUsers;
import com.example.now.Networking.ApiInterface;
import com.example.now.Networking.RetrofitClientInstance;
import com.example.now.R;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TotalusersFragment extends Fragment implements View.OnClickListener {

    private TotalUsersAdapter totalUsersAdapter;
    private ArrayList<TotalUsers.Data> totalUsersArrayList;
    private ListView listview;
    private TextView tv_next;
    private LinearLayout back_button;
    private ImageView img_add;
    ApiInterface service;
    PreferencesHandler preferencesHandler;
    private String ids;
    private String lat;
    private String lng;
    private String km;
    private String range;
    private String gender;
    private TextView tv_no_record;
    private TextView tv_total_amount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_totalusers, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        if (getArguments() != null) {
            ids = getArguments().getString("ids");
            lat = getArguments().getString("lat");
            lng = getArguments().getString("lng");
            gender = getArguments().getString("gender");
            range = getArguments().getString("range");
            km = getArguments().getString("km");
        }
        preferencesHandler = new PreferencesHandler();
        service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        listview = view.findViewById(R.id.listview);
        totalUsersArrayList = new ArrayList<>();
        totalUsersAdapter = new TotalUsersAdapter(getContext(),totalUsersArrayList);
        listview.setAdapter(totalUsersAdapter);
        tv_next = view.findViewById(R.id.tv_next);
        tv_next.setOnClickListener(this);
        tv_next.setVisibility(View.VISIBLE);
        back_button = view.findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        img_add = view.findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);
        tv_no_record = view.findViewById(R.id.tv_no_record);
        tv_total_amount = view.findViewById(R.id.tv_total_amount);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.back_button:
                getFragmentManager().popBackStack();
                break;
            case R.id.tv_next:
                gotoPublishNowFragment();
                break;
        }
    }

    private void gotoPublishNowFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("gender",gender);
        bundle.putString("range",range);
        bundle.putString("lat",lat);
        bundle.putString("lng",lng);
        bundle.putString("ids",ids);
        bundle.putString("km",km);
        PublishNowFragment publishNowFragment = new PublishNowFragment();
        publishNowFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, publishNowFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void getUsersByRadiusAndInterests() {
        totalUsersArrayList.clear();
        Global.mKProgressHUD = KProgressHUD.create(getActivity()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.7f).setAnimationSpeed(2).setLabel("Please Wait").setCancellable(true);
        Global.mKProgressHUD.show();
        Call<TotalUsers> call = service.getInterestedUsers(preferencesHandler.getApiToken(),ids, lat, lng,km);
        call.enqueue(new Callback<TotalUsers>() {
            @Override
            public void onResponse(Call<TotalUsers> call, Response<TotalUsers> response) {
                Global.mKProgressHUD.dismiss();
                Log.d("response","response ::: "+response);
                response.body();
                TotalUsers totalUsers = response.body();
                if(totalUsers.getStatus() == true){
                    tv_total_amount.setText(String.valueOf(totalUsers.getTotalPrice()));
                    if(totalUsers.getData().size() > 0){
                        tv_no_record.setVisibility(View.GONE);
                        for(int i=0; i<totalUsers.getData().size(); i++){
                            TotalUsers.Data data = totalUsers.getData().get(i);
                            totalUsersArrayList.add(data);
                        }
                        totalUsersAdapter.notifyDataSetChanged();
                    }else{
                        tv_no_record.setVisibility(View.VISIBLE);
                    }

                }

            }

            @Override
            public void onFailure(Call<TotalUsers> call, Throwable t) {
                Global.mKProgressHUD.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        getUsersByRadiusAndInterests();
    }
}