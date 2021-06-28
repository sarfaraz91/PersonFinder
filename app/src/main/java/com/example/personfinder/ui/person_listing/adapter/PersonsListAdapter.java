package com.example.personfinder.ui.person_listing.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personfinder.R;
import com.example.personfinder.ui.person_listing.AddPostInformationActivity;
import com.example.personfinder.ui.person_listing.model.PersonComplaintData;

import java.util.List;

public class PersonsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity activity;
    private List<PersonComplaintData> personsDataList;
    private PersonsListAdapter.OnItemClickListener listener;
    private Context context;

    public PersonsListAdapter(Activity activity, Context context, List<PersonComplaintData> personsDataList) {
        this.personsDataList = personsDataList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.persons_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            populateItemRows((ViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void populateItemRows(ViewHolder holder, int position) {
        PersonComplaintData personsData = personsDataList.get(position);

        try {
            String personTitle = personsData.getPersonName().substring(0, 1).toUpperCase() + personsData.getFatherName().substring(1).toLowerCase();
            holder.personTitle.setText(personTitle);
            holder.age.setText(String.valueOf(personsData.getPersonAge()));
            holder.height.setText(String.valueOf(personsData.getPersonHeight()));
            holder.dob.setText(personsData.getDateOfBirth());
            holder.cnic.setText(personsData.getCnic());
            holder.address.setText(personsData.getAddress() + ", " + personsData.getCityName() + ", " + personsData.getStatesName() + ", " + personsData.getCountryName());
            holder.mobileNo.setText(personsData.getMobileNo());
            holder.landline.setText(personsData.getLandLineNumber());
            holder.description.setText(personsData.getDescription());
            if (personsData.getIsMentallyDisturb())
                holder.isMentallyDisturb.setText("Yes");
            else
                holder.isMentallyDisturb.setText("No");

            if (personsData.getIsFounded())
                holder.status.setText("Found");
            else if (personsData.getIsMissing())
                holder.isMentallyDisturb.setText("Missing");
            else
                holder.isMentallyDisturb.setText("No Data Available");


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddPostInformationActivity.class);
                    intent.putExtra("data", personsData);
                    context.startActivity(intent);
                }
            });

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    public int getItemCount() {
        return personsDataList == null ? 0 : personsDataList.size();
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
        viewHolder.progressBar.setVisibility(View.VISIBLE);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView personTitle, status,
                age, height, dob, cnic, address, mobileNo, landline, description, isMentallyDisturb;

        public ViewHolder(View itemView) {
            super(itemView);
            personTitle = itemView.findViewById(R.id.personTitle);
            status = itemView.findViewById(R.id.status);
            age = itemView.findViewById(R.id.age);
            height = itemView.findViewById(R.id.height);
            dob = itemView.findViewById(R.id.dob);
            cnic = itemView.findViewById(R.id.cnic);
            address = itemView.findViewById(R.id.address);
            mobileNo = itemView.findViewById(R.id.mobileNo);
            landline = itemView.findViewById(R.id.landline);
            description = itemView.findViewById(R.id.description);
            isMentallyDisturb = itemView.findViewById(R.id.isMentallyDisturb);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }
}
