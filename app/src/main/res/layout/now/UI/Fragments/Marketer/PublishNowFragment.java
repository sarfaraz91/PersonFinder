package com.example.now.UI.Fragments.Marketer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.now.GeneralClasses.Global;
import com.example.now.GeneralClasses.PreferencesHandler;
import com.example.now.Model.TotalUsers;
import com.example.now.Networking.ApiInterface;
import com.example.now.Networking.RetrofitClientInstance;
import com.example.now.R;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PublishNowFragment extends Fragment implements View.OnClickListener{


    private TextView tv_next;
    private LinearLayout back_button;
    private ImageView img_add;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Calendar myCalendar;
    private Button btn_select_time;
    private Button btn_select_date;
    private Button btn_publish_now;
    private String ids;
    private String lat;
    private String lng;
    private String km;
    private String range;
    private String gender;
    ApiInterface service;
    PreferencesHandler preferencesHandler;
    String date = "";
    String time = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_publish_now, container, false);
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
        btn_publish_now = view.findViewById(R.id.btn_publish_now);
        btn_publish_now.setOnClickListener(this);
        service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        btn_select_time = view.findViewById(R.id.btn_select_time);
        btn_select_time.setOnClickListener(this);
        btn_select_date = view.findViewById(R.id.btn_select_date);
        btn_select_date.setOnClickListener(this);
        myCalendar = Calendar.getInstance();
        tv_next = view.findViewById(R.id.tv_next);
        tv_next.setOnClickListener(this);
        tv_next.setVisibility(View.VISIBLE);
        back_button = view.findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        img_add = view.findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);
        preferencesHandler = new PreferencesHandler();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btn_select_time:
                openTimePicker();
                break;
            case R.id.btn_select_date:
                openDatePicker();
                break;
            case R.id.btn_publish_now:
                publishOffer("now");
                break;
            case R.id.back_button:
                getFragmentManager().popBackStack();
                break;
            case R.id.tv_next:
                if(validate()){
                    publishOffer(null);
                }else{
                    Toast.makeText(getActivity(), "Please select date and time", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean validate() {
        boolean isValid = true;

        if(time.isEmpty() || date.isEmpty()){
            isValid = false;
        }
        return isValid;
    }

    private void openTimePicker() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),R.style.DialogTheme,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

//                        String AM_PM ;
//                        if(hourOfDay < 12) {
//                            AM_PM = "AM";
//                        } else {
//                            AM_PM = "PM";
//                        }

                        boolean isPM = (hourOfDay >= 12);
                        btn_select_time.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));


                      //  btn_select_time.setText(String.format("%02d:%02d", hourOfDay, minute)+" "+AM_PM);
                        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        myCalendar.set(Calendar.MINUTE, minute);
                        myCalendar.set(Calendar.SECOND, 0);

                        time = btn_select_time.getText().toString();

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void openDatePicker() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        btn_select_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        date = btn_select_date.getText().toString();

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void publishOffer(String now) {
        Global.mKProgressHUD = KProgressHUD.create(getActivity()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.7f).setAnimationSpeed(2).setLabel("Please Wait").setCancellable(true);
        Global.mKProgressHUD.show();
        Call<ResponseBody> call = service.publishOffer(preferencesHandler.getApiToken(),preferencesHandler.getOfferId(),ids, gender,range,lat,lng,date,time,now);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.mKProgressHUD.dismiss();
                Log.d("response","response ::: "+response);
                response.body();
                removeAllFragments(getActivity().getSupportFragmentManager());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Global.mKProgressHUD.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    private static void removeAllFragments(FragmentManager fragmentManager) {
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

}