package com.example.now.UI.Fragments.Marketer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.now.Adapter.InterestsAdapter;
import com.example.now.GeneralClasses.Global;
import com.example.now.Model.Interests;
import com.example.now.Networking.ApiInterface;
import com.example.now.Networking.RetrofitClientInstance;
import com.example.now.R;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectInterestsFragment extends Fragment implements View.OnClickListener{

    GridView GridView_interest;
    private InterestsAdapter interestsAdapter;
    private List<Interests> interestsList;
    ApiInterface service;
    private TextView tv_next;
    private LinearLayout back_button;
    private ImageView img_add;
    private String ids ="";
    private Set<String> hashset;
    String range;
    String gender;
    String lat;
    String lng;
    String km;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_interests, container, false);
        initViews(view);

        GridView_interest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridView_interest.getChildAt(position).findViewById(R.id.container_img).setBackground(getActivity().getDrawable(R.drawable.circle_yellow));
                Interests interests = interestsList.get(position);
                if(!hashset.contains(String.valueOf(interests.getId()))){
                    hashset.add(String.valueOf(interests.getId()));
                }
            }
        });

        return view;
    }

    private void initViews(View view) {
        if (getArguments() != null) {
            lat = getArguments().getString("lat");
            lng = getArguments().getString("lng");
            gender = getArguments().getString("gender");
            range = getArguments().getString("range");
            km = getArguments().getString("km");
        }
        hashset = new HashSet<String>();
        GridView_interest = view.findViewById(R.id.GridView_interest);
        interestsList = new ArrayList<>();
        interestsAdapter = new InterestsAdapter(getContext(), interestsList);
        GridView_interest.setAdapter(interestsAdapter);
        service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        tv_next = view.findViewById(R.id.tv_next);
        tv_next.setOnClickListener(this);
        tv_next.setVisibility(View.VISIBLE);
        back_button = view.findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        img_add = view.findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);
    }

    private void getAllInterests() {
        Global.mKProgressHUD = KProgressHUD.create(getActivity()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.7f).setAnimationSpeed(2).setLabel("Please Wait").setCancellable(true);
        Global.mKProgressHUD.show();
        Call<Interests.MyResponse> call = service.getAllInterests();
        call.enqueue(new Callback<Interests.MyResponse>() {
            @Override
            public void onResponse(Call<Interests.MyResponse> call, Response<Interests.MyResponse> response) {
                response.body();
                Global.mKProgressHUD.dismiss();
                ArrayList<Interests> data = (ArrayList<Interests>) response.body().data;
                for (int i = 0; i < data.size(); i++) {
                    interestsList.add(data.get(i));
                }
                interestsAdapter.notifyDataSetChanged();
                Log.d("", "data == " + data);
            }

            @Override
            public void onFailure(Call<Interests.MyResponse> call, Throwable t) {
                Global.mKProgressHUD.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        interestsList.clear();
        getAllInterests();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.back_button:
                getFragmentManager().popBackStack();
                break;
            case R.id.tv_next:
                if(hashset.size() > 0){
                    gotoTotalUsersFragment();
                }else{
                    Toast.makeText(getContext(), "Please select atleast one interest", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public static String toCSV(String[] array) {
        String result = "";

        if (array.length > 0) {
            StringBuilder sb = new StringBuilder();

            for (String s : array) {
                sb.append(s).append(",");
            }

            result = sb.deleteCharAt(sb.length() - 1).toString();
        }
        return result;
    }

    private void gotoTotalUsersFragment() {
        String[] abc = hashset.toArray(new String[0]);
        ids = toCSV(abc);

        Bundle bundle = new Bundle();
        bundle.putString("gender",gender);
        bundle.putString("range",range);
        bundle.putString("lat",lat);
        bundle.putString("lng",lng);
        bundle.putString("ids",ids);
        bundle.putString("km",km);
        TotalusersFragment totalusersFragment = new TotalusersFragment();
        totalusersFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, totalusersFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}