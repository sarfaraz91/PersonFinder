package com.example.now.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.now.Adapter.InterestsAdapter;
import com.example.now.Model.Interests;
import com.example.now.Networking.ApiInterface;
import com.example.now.Networking.RetrofitClientInstance;
import com.example.now.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InterestsActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = InterestsActivity.class.getSimpleName();
    GridView GridView_interest;
    private InterestsAdapter interestsAdapter;
    private List<Interests> interestsList;
    ApiInterface service;
    private ImageView img_add;
    private Switch swtich_select_all;
    private LinearLayout back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrerests);
        initViews();


        GridView_interest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                swtich_select_all.setChecked(false);
                GridView_interest.getChildAt(position).findViewById(R.id.container_img).setBackground(getDrawable(R.drawable.circle_yellow));

//                for (int i = 0; i < GridView_interest.getChildCount(); i++) {
//                    if (position == i) {
//                        GridView_interest.getChildAt(i).findViewById(R.id.container_img).setBackground(getDrawable(R.drawable.circle_yellow));
//                    } else {
//                        GridView_interest.getChildAt(i).findViewById(R.id.container_img).setBackground(getDrawable(R.drawable.circle_purple));
//                    }
//                }
            }
        });

        swtich_select_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < GridView_interest.getChildCount(); i++) {
                        GridView_interest.getChildAt(i).findViewById(R.id.container_img).setBackground(getDrawable(R.drawable.circle_yellow));
                    }
                }else{
                    for (int i = 0; i < GridView_interest.getChildCount(); i++) {
                        GridView_interest.getChildAt(i).findViewById(R.id.container_img).setBackground(getDrawable(R.drawable.circle_purple));
                    }
                }
            }
        });
    }

    private void initViews() {
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        swtich_select_all = findViewById(R.id.swtich_select_all);
        service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        GridView_interest = findViewById(R.id.GridView_interest);
        interestsList = new ArrayList<>();
        interestsAdapter = new InterestsAdapter(this, interestsList);
        GridView_interest.setAdapter(interestsAdapter);
        img_add = findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        interestsList.clear();
        getInterests();
    }

    private void getInterests() {
        Call<Interests.MyResponse> call = service.getAllInterests();
        call.enqueue(new Callback<Interests.MyResponse>() {
            @Override
            public void onResponse(Call<Interests.MyResponse> call, Response<Interests.MyResponse> response) {
                response.body();

                ArrayList<Interests> data = (ArrayList<Interests>) response.body().data;
                for (int i = 0; i < data.size(); i++) {
                    interestsList.add(data.get(i));
                }
                interestsAdapter.notifyDataSetChanged();
                Log.d(TAG, "data == " + data);
            }

            @Override
            public void onFailure(Call<Interests.MyResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(InterestsActivity.this, "Something went wrong...Please try later!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.back_button:
                finish();
                break;
        }
    }
}
